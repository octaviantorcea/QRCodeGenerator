from datetime import datetime, timedelta

import bcrypt
import jwt
from flask import Flask, request, jsonify

app = Flask(__name__)


test_map = {}
USER_ID = 0

# JWT secret key
JWT_SECRET_KEY = 'EXTREMELY_SECRET_KEY'


@app.route("/register", methods=["POST"])
def register():
    global USER_ID

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

    if username in test_map:
        return jsonify(
            {
                "message": "User already exists"
            }
        ), 400

    test_map[username] = {
        "password": bcrypt.hashpw(password.encode("utf-8"), bcrypt.gensalt()),
        "user_id": USER_ID
    }
    USER_ID += 1

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

    if username not in test_map:
        return jsonify({'message': 'Username not registered'}), 401

    if bcrypt.checkpw(password.encode("utf-8"), test_map[username]["password"]):
        token = jwt.encode(
            {
                "username": username,
                "user_id": test_map[username]["user_id"],
                "exp": datetime.utcnow() + timedelta(minutes=2)
            },
            JWT_SECRET_KEY
        )

        return jsonify(
            {
                "token": token,
                "user_id": test_map[username]["user_id"]
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
