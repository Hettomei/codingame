from http.server import HTTPServer, BaseHTTPRequestHandler
import tempfile
import re
import os

class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        print(f"GET {self.path}")
        print(dict(self.headers))
        self.send_response(200)
        self.end_headers()

    def do_POST(self):
        # 1. Récupérer le type de contenu et la boundary
        content_type = self.headers.get('Content-Type', "")
        if "boundary=" not in content_type:
            self.send_response(400)
            self.end_headers()
            return

        boundary = content_type.split("boundary=")[1].encode()
        length = int(self.headers.get('Content-Length', 0))

        print(f"📥 Réception d'un fichier ({length / 1024 / 1024:.2f} MB)...")

        # 2. Lire le début pour trouver le nom du fichier (filename)
        # On lit les 2048 premiers octets pour trouver l'en-tête multipart
        header_chunk = self.rfile.read(2048)
        match = re.search(b'filename="(.+?)"', header_chunk)
        filename = match.group(1).decode('utf-8') if match else "upload.tmp"

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
        save_path = os.path.join(tempfile.gettempdir(), filename)
        print(f"📁 Enregistrement temporaire vers : {save_path}")
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

print("Send any GET or POST to http://localhost:8000")
HTTPServer(('0.0.0.0', 8000), Handler).serve_forever()
