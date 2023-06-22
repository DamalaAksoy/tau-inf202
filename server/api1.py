from flask import Flask, request, jsonify
import pymysql

app = Flask(__name__)

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()  # İstek verilerini JSON formatında alın
    email = data.get('email')
    password = data.get('password')

    # Veritabanı bağlantısı ve sorgu yapma
    conn = pymysql.connect(host='localhost', user='root', password='', database='ınf202')
    cursor = conn.cursor()

    # Kullanıcıyı veritabanında ara
    cursor.execute("SELECT * FROM persson WHERE mail=%s AND password=%s", (email, password))
    user = cursor.fetchone()
    conn.close()
    if user is not None:
        return '100'  # Doğru eşleşme durumunda '100' yanıtı döndürülür
    else:
        return '200'  # Eşleşme yoksa '200' yanıtı döndürülür


@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()  # İstek verilerini JSON formatında alın
    email = data.get('email')
    password = data.get('password')
    name = data.get('name')

    # Veritabanı bağlantısı ve sorgu yapma
    conn = pymysql.connect(host='localhost', user='root', password='', database='ınf202')
    cursor = conn.cursor()

    # Yeni kullanıcıyı veritabanına kaydetme
    cursor.execute("INSERT INTO persson (name, mail, password) VALUES (%s,%s, %s)", (name,email,password))
    conn.commit()
    conn.close()
    return '100' 


@app.route('/bibliothek', methods=['Get'])
def bibliothek():
    conn = pymysql.connect(host='localhost', user='root', password='', database='ınf202')
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM `abteilung der bibliothek`")
    data = cursor.fetchall()
    conn.close()
    return str(data)

if __name__ == '__main__':
    app.run()
