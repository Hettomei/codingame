from http.server import HTTPServer, BaseHTTPRequestHandler
import traceback
import tempfile
import json
import re
import os

SUB_TMP = os.path.join(tempfile.gettempdir(), "sendpics")

def build_path(prefix, filename):
    match = re.search(r'(\d{4})(\d{2})(\d{2})', filename)
    if match:
        yyyy, mm = match.group(1), match.group(2)
        return os.path.join(SUB_TMP, yyyy, f"{prefix}-{yyyy}-{mm}", filename)
    else:
        return os.path.join(SUB_TMP, f"{prefix}-other", filename)

def build_dir(dest):
    os.makedirs(dest, exist_ok=True)
    return dest

def file_exists(prefix, filename):
    return os.path.isfile(build_path(prefix, filename))

def make_dirs(save_path):
    os.makedirs(os.path.dirname(save_path), exist_ok=True)

class Handler(BaseHTTPRequestHandler):

    def do_GET(self):
        print(f"GET {self.path}")
        print(dict(self.headers))
        self.send_response(200)
        self.end_headers()

    def handle_prepare(self):
        length = int(self.headers.get("Content-Length", 0))
        body = self.rfile.read(length)

        try:
            all_json = json.loads(body)
            print(f"INFO << {all_json}")
        except Exception as e:
            print(f"ERROR : {e}")
            print(traceback.format_exc())
            self.send_response(400)
            self.send_header("Content-Type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps({"error": str(e)}).encode())
            return

        prefix = all_json["prefix"]
        result = [
            {"fichier": build_path(prefix, filename), "isPresent": file_exists(prefix, filename)}
            for filename in all_json["filenames"]
        ]

        response = json.dumps(result)
        print(f"INFO >> {response}")
        response = response.encode()
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", len(response))
        self.end_headers()
        self.wfile.write(response)

    def do_POST(self):
        if self.path == "/prepare":
            self.handle_prepare()
        elif self.path == "/upload":
            self.handle_save()
        else:
            response = json.dumps({"path": self.path}).encode()
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Content-Length", len(response))
            self.end_headers()
            self.wfile.write(response)

    def handle_save(self):
        # 1. Récupérer le type de contenu et la boundary
        content_type = self.headers.get('Content-Type', "")
        if "boundary=" not in content_type:
            self.send_response(400)
            self.end_headers()
            return

        print(f"From: User-Agent: {self.headers.get('User-Agent', 'Aucun User-Agent')}")
        boundary = content_type.split("boundary=")[1].encode()
        length = int(self.headers.get('Content-Length', 0))

        # 2. Lire le début pour trouver le nom du fichier et le prefix
        # On lit les 2048 premiers octets pour trouver l'en-tête multipart
        body_chunck = self.rfile.read(min(length, 2048))
        re_prefix = re.search(rb'name="prefix"\r\n\r\n(.+?)\r\n', body_chunck)
        prefix = re_prefix.group(1).decode('utf-8')

        match = re.search(b'filename="(.+?)"', body_chunck)
        filename = match.group(1).decode('utf-8')
        save_path = build_path(prefix, filename)
        make_dirs(save_path)
        print(f"📥 Réception de {filename} - {length / 1024 / 1024:.2f} MB")

        # 3. Trouver où commence réellement le contenu binaire
        # Le contenu commence après la double ligne vide \r\n\r\n

        try:
            # on degage la partie prefix
            header_end = body_chunck.index(b'\r\n\r\n') + 4
            file_data_start = body_chunck[header_end:]
            # on degage la partie file
            header_end = file_data_start.index(b'\r\n\r\n') + 4
            file_data_start = file_data_start[header_end:]
        except ValueError as e:
            print(e)
            print(traceback.format_exc())
            self.send_response(400)
            self.end_headers()
            return

        # 4. Écriture par morceaux (Chunks) dans le dossier temporaire
        remaining = length - len(body_chunck)

        with open(save_path, 'wb') as f:
            # Écrire le reliquat du premier chunk lu
            f.write(file_data_start)

            # Lire le reste par morceaux de 64 KB
            while remaining > 0:
                chunk_size = min(remaining, 65536)
                chunk = self.rfile.read(chunk_size)
                if not chunk:
                    break
                f.write(chunk)
                remaining -= len(chunk)

        # 5. Nettoyage de la fin du fichier
        # Le dernier chunk contient la boundary de fermeture (\r\n--boundary--\r\n)
        # On rouvre le fichier pour tronquer cette partie inutile
        with open(save_path, 'rb+') as f:
            f.seek(-len(boundary) - 20, 2) # On recule un peu avant la fin
            last_bytes = f.read()
            # On cherche l'index de la boundary de fin pour couper juste avant
            idx = last_bytes.find(b'\r\n--' + boundary)
            if idx != -1:
                f.seek(-len(last_bytes) + idx, 2)
                f.truncate()

        print(f"✅ Fichier sauvegardé : {save_path}")
        response = json.dumps({"file": save_path, "saved": True}).encode()
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", len(response))
        self.end_headers()
        self.wfile.write(response)

print("Send any GET or POST to http://localhost:8000 or http://0.0.0.0:8000")

try:
    HTTPServer(('0.0.0.0', 8000), Handler).serve_forever()
except KeyboardInterrupt:
    print("Bye")
