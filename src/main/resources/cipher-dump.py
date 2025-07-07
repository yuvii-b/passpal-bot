# imports list of passwords text file, encrypts using AES and stores in DB
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import psycopg2
import base64
import os
from dotenv import load_dotenv
from pathlib import Path

path = Path(__file__).resolve().parents[3] / '.env'
load_dotenv(dotenv_path=path)

ENCRYPTION_KEY = os.getenv("CIPHER_KEY")[:16].ljust(16)
DB_CONFIG = {
    'dbname': 'bot',
    'user': 'postgres',
    'password': os.getenv("DB_PASSWORD"),
    'host': 'localhost',
    'port': 5432
}
FILE_PATH = 'passwords.txt'

def encrypt(plain_text: str, key: str) -> str:
    cipher = AES.new(key.encode(), AES.MODE_ECB)
    ct_bytes = cipher.encrypt(pad(plain_text.encode(), AES.block_size))
    return base64.b64encode(ct_bytes).decode('utf-8')

def main():
    conn = psycopg2.connect(**DB_CONFIG)
    cur = conn.cursor()

    with open(FILE_PATH, 'r') as f:
        for line in f:
            if line.strip():
                name, password = line.strip().split(" ", 1)
                encrypted_pass = encrypt(password, ENCRYPTION_KEY)
                cur.execute("INSERT INTO passwords (name, password) VALUES (%s, %s)", (name, encrypted_pass))

    conn.commit()
    cur.close()
    conn.close()
    print("All passwords imported and encrypted.")

if __name__ == "__main__":
    main()
