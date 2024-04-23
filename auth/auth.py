from datetime import datetime, timedelta

import jwt
import psycopg2
from flask import Flask, request, jsonify

app = Flask(__name__)

# AUTH DB CREDENTIALS
DB_NAME = "authorization_db"
DB_USER = "postgres"
DB_PASSWORD = "root"
DB_HOST = "localhost"
DB_PORT = "5432"

# JWT secret key
JWT_SECRET_KEY = 'EXTREMELY_SECRET_KEY'


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


@app.route("/register", methods=["POST"])
def register():
    data = request.get_json()

    try:
        username = data["username"]
        password = data["password"]
    except Exception as e:
        return jsonify(
            {
                "message": f"Wrong request body. Reason: {e}"
            }
        ), 400

    db_cursor = get_cursor()

    # check unique username
    db_cursor.execute("SELECT * FROM users WHERE username=%s", (username,))
    if db_cursor.fetchone():
        return jsonify(
            {
                "message": "User already exists"
            }
        ), 400

    # add user into db
    db_cursor.execute(
        "INSERT INTO users (username, password) VALUES (%s, %s)",
        (username, password)
    )
    db_cursor.connection.commit()
    db_cursor.connection.close()

    return jsonify(
        {
            'message': 'User registered successfully'
        }
    ), 201


@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()

    try:
        username = data["username"]
        password = data["password"]
    except Exception as e:
        return jsonify(
            {
                "message": f"Wrong request body. Reason: {e}"
            }
        ), 400

    db_cursor = get_cursor()

    # get user credentials
    db_cursor.execute(
        "SELECT * FROM users WHERE username=%s",
        (username,)
    )
    query_result = db_cursor.fetchone()
    db_cursor.connection.close()

    # check user
    if not query_result:
        return jsonify(
            {
                "message": "Username not registered"
            }
        ), 401

    # check password
    if password == query_result[2]:
        token = jwt.encode(
            {
                "username": username,
                "user_id": query_result[0],
                "exp": datetime.utcnow() + timedelta(minutes=2)
            },
            JWT_SECRET_KEY
        )

        return jsonify(
            {
                "token": token
            }
        ), 200
    else:
        return jsonify(
            {
                'message': 'Invalid password'
            }
        ), 401


@app.route("/authorize", methods=["POST"])
def authorize():
    token = request.headers.get("Authorization")

    if not token:
        return jsonify(
            {
                "message": "Token is missing"
            }
        ), 401

    try:
        decoded_token = jwt.decode(token, JWT_SECRET_KEY, algorithms=["HS256"])
        username = decoded_token["username"]
        user_id = decoded_token["user_id"]

        return jsonify(
            {
                "message": f"Authorized user: {username}",
                "user_id": user_id,
                "isAuthorized": True
            }
        ), 200
    except jwt.ExpiredSignatureError:
        return jsonify(
            {
                "message": "Token has expired",
                "isAuthorized": False
            }
        ), 401
    except jwt.InvalidTokenError:
        return jsonify(
            {
                "message": "Invalid token",
                "isAuthorized": False
            }
        ), 401
    except Exception as e:
        return jsonify(
            {
                "message": f"Unknown error: {e}",
                "isAuthorized": False
            }
        ), 401


if __name__ == "__main__":
    app.run(debug=True, host="localhost", port=8089)
