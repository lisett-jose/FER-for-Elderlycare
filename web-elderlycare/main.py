from flask import Flask

from public import public
from api import api

app=Flask(__name__)
app.secret_key="main"

app.register_blueprint(public)
app.register_blueprint(api,url_prefix='/api')

app.run(debug="true",host="192.168.43.140",port=5013)