from http.server import HTTPServer, BaseHTTPRequestHandler
import tempfile
import json
import re
import os


class Handler(BaseHTTPRequestHandler):
    SUB_TMP = None

    @staticmethod
    def prepare_tmp_dir():
        Handler.SUB_TMP = os.path.join(tempfile.gettempdir(), "sendpics")
        os.makedirs(Handler.SUB_TMP, exist_ok=True)
        print(f"Temp dir ready: {Handler.SUB_TMP}")

    def do_GET(self):
        print(f"GET {self.path}")
        print(dict(self.headers))
        self.send_response(200)
        self.end_headers()

    def handle_prepare(self):
        length = int(self.headers.get("Content-Length", 0))
        body = self.rfile.read(length)

        try:
            filenames = json.loads(body)
            if not isinstance(filenames, list):
                raise ValueError("Expected a list")
        except Exception as e:
            print(f"ERROR : {e}")
            self.send_response(400)
            self.send_header("Content-Type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps({"error": str(e)}).encode())
            return

        result = [
            {"fichier": name, "isPresent": os.path.isfile(os.path.join(Handler.SUB_TMP, name))}
            for name in filenames
        ]

        response = json.dumps(result).encode()
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", len(response))
        self.end_headers()
        self.wfile.write(response)

    def do_POST(self):
        if self.path == "/prepare":
            self.handle_prepare()
        else:
            self.handle_save()

    def handle_save(self):
        # 1. Récupérer le type de contenu et la boundary
        content_type = self.headers.get('Content-Type', "")
        if "boundary=" not in content_type:
            self.send_response(400)
            self.end_headers()
            return

        boundary = content_type.split("boundary=")[1].encode()
        length = int(self.headers.get('Content-Length', 0))

        # 2. Lire le début pour trouver le nom du fichier (filename)
        # On lit les 2048 premiers octets pour trouver l'en-tête multipart
        header_chunk = self.rfile.read(2048)
        match = re.search(b'filename="(.+?)"', header_chunk)
        filename = match.group(1).decode('utf-8') if match else "upload.tmp"
        save_path = os.path.join(Handler.SUB_TMP, filename)
        print(f"📥 Réception de {filename} - {length / 1024 / 1024:.2f} MB")

        # 3. Trouver où commence réellement le contenu binaire
        # Le contenu commence après la double ligne vide \r\n\r\n
        try:
            header_end = header_chunk.index(b'\r\n\r\n') + 4
            file_data_start = header_chunk[header_end:]
        except ValueError:
            self.send_response(400)
            self.end_headers()
            return

        # 4. Écriture par morceaux (Chunks) dans le dossier temporaire
        remaining = length - len(header_chunk)
        remaining = length - len(header_chunk)

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
        print(f"From: User-Agent: {self.headers.get('User-Agent', 'Aucun User-Agent')}")
        self.send_response(200)
        self.end_headers()
        self.wfile.write(f"Transfert de {filename} réussi".encode())

Handler.prepare_tmp_dir()
print("Send any GET or POST to http://localhost:8000 or http://0.0.0.0:8000")

try:
    HTTPServer(('0.0.0.0', 8000), Handler).serve_forever()
except KeyboardInterrupt:
    print("Bye")
