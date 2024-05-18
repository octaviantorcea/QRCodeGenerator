import requests
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


def authorize_action(http_request):
    try:
        auth_token = http_request.headers["Authorization"]

        auth_response = requests.post(url=AUTH_URL, headers={"Authorization": auth_token})
    except KeyError as _:
        return "Unauthorized: token not found in header.", 401, None
    except Exception as e:
        return f"Unknown error while trying to fetch token from header. Exception: {e}", 500, None

    status_code = auth_response.status_code
    body = auth_response.json()
    message = body["message"]

    if status_code == 200:
        return None, 200, body["user_id"]
    elif status_code == 401:
        return f"Unauthorized; reason: {message}", 401, None
    else:
        return f"Unauthorized; received unknown status code: {status_code}", 401, None


@app.route("/save", methods=["POST"])
def save_qr_code():
    auth_resp_msg, auth_status_code, user_id = authorize_action(request)

    if auth_status_code != 200:
        return jsonify(
            {
                "message": auth_resp_msg
            }
        ), auth_status_code

    data = request.get_json()

    try:
        string_image = data["encodedStringImage"]
        encoded_data = data["encodedData"]
        qr_code_color = data["qrCodeColor"]

        db_cursor = get_cursor()
        db_cursor.execute(
            "SELECT * FROM qrcodes WHERE user_id=%s AND encoded_data=%s AND qr_code_color=%s",
            (user_id, encoded_data, qr_code_color)
        )
        if db_cursor.fetchone():
            return jsonify(
                {
                    "message": f"QR Code already saved."
                }
            ), 409

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
        ), 201
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


@app.route("/codes", methods=["GET"])
def get_codes():
    auth_resp_msg, auth_status_code, user_id = authorize_action(request)

    if auth_status_code != 200:
        return jsonify(
            {
                "message": auth_resp_msg
            }
        ), auth_status_code

    try:
        db_cursor = get_cursor()
        db_cursor.execute("SELECT encoded_data, string_image FROM qrcodes WHERE user_id=%s", (user_id,))
        result = db_cursor.fetchall()
        response = []

        for encoded_data, string_image in result:
            response_dict = {"encoded_data": encoded_data, "string_image": string_image}
            response.append(response_dict)

        return jsonify(
            {
                "saved_codes": response
            }
        ), 200
    except Exception as e:
        print(e)

        return jsonify(
            {
                "message": f"Database server error: {e}"
            }
        ), 500


if __name__ == "__main__":
    app.run(debug=True, host="localhost", port=12345)
