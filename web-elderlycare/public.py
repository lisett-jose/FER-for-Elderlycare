from flask import Blueprint,render_template,request,session,redirect,url_for,flash
import database as db

from real_time_video import *
from keras import backend as K


public = Blueprint('public',__name__,template_folder='templates/public')

@public.route('/',methods=['get','post'])
def home():
	return render_template('public_home.html')

@public.route('/opencamera',methods=['get','post'])
def opencamera():
	accesscamera()
	K.clear_session()
	return redirect('/')

@public.route('/logout',methods=['get','post'])
def logout():
	session.clear()
	return redirect('/')


