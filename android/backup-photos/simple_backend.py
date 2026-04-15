
from http.server import HTTPServer, BaseHTTPRequestHandler

class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        print(f"GET {self.path}")
        print(dict(self.headers))
        self.send_response(200)
        self.end_headers()

    def do_POST(self):
        length = int(self.headers.get('Content-Length', 0))
        body = self.rfile.read(length)
        print(f"POST {self.path}")
        print(dict(self.headers))
        print(body[:500])  # les 500 premiers octets
        self.send_response(200)
        self.end_headers()

print("Send any GET or POST to http://localhost:8000")
HTTPServer(('0.0.0.0', 8000), Handler).serve_forever()
