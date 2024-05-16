import requests
import jwt
import psycopg2
from flask import Flask, request, jsonify


app = Flask(__name__)

# QRCODES DB CREDENTIALS
DB_NAME = "qrcodes_db"
DB_USER = "postgres"
DB_PASSWORD = "root"
DB_HOST = "localhost"
DB_PORT = "5432"

# JWT secret key
JWT_SECRET_KEY = 'EXTREMELY_SECRET_KEY'

# AUTH API URL
AUTH_URL = "http://127.0.0.1:8089/authorize"


def get_connect_to_db():
    return psycopg2.connect(
        dbname=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD,
        host=DB_HOST,
        port=DB_PORT
    )


def get_cursor():
    return get_connect_to_db().cursor()


@app.route("/save", methods=["POST"])
def save_qr_code():
    try:
        auth_token = request.headers["Authorization"]

        auth_response = requests.post(url=AUTH_URL, headers={"Authorization": auth_token})
    except Exception as e:
        return jsonify(
            {
                "message": "Unauthorized: exception when trying to extract token from request "
                           f"header. Exception: {e}"
            }
        ), 401

    status_code = auth_response.status_code
    body = auth_response.json()
    message = body["message"]

    if status_code == 200:
        user_id = body["user_id"]
    elif status_code == 401:
        return jsonify(
            {
                "message": f"Unauthorized; reason: {message}"
            }
        ), 401
    else:
        return jsonify(
            {
                "message": f"Unauthorized; received unknown status code: {status_code}"
            }
        ), 401

    data = request.get_json()

    try:
        string_image = data["encodedStringImage"]
        encoded_data = data["encodedData"]
        qr_code_color = data["qrCodeColor"]

        db_cursor = get_cursor()
        db_cursor.execute(
            "INSERT INTO qrcodes (user_id, encoded_data, qr_code_color, string_image) VALUES (%s, %s, %s, %s)",
            (user_id, encoded_data, qr_code_color, string_image)
        )
        db_cursor.connection.commit()
        db_cursor.connection.close()

        return jsonify(
            {
                "message": f"QR Code saved."
            }
        ), 200
    except KeyError as e:
        return jsonify(
            {
                "message": f"Bad body request format: {e}"
            }
        ), 400
    except Exception as e:
        print(e)

        return jsonify(
            {
                "message": f"Database server error: {e}"
            }
        ), 500


if __name__ == "__main__":
    app.run(debug=True, host="localhost", port=12345)
