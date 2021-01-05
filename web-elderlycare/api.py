from flask import Blueprint,render_template,redirect,url_for,session,request,flash
from database import *
import demjson
import uuid


import os
from core import *

api = Blueprint('api',__name__)


@api.route('/familyregistration',methods=['get','post'])
def familyregistration():
	data={}
	first_name = request.form['fname']
	last_name = request.form['lname']
	gender = request.form['gen']
	upload_image=request.files['upload_image']
	path='static/uploads/'+str(uuid.uuid4())+upload_image.filename
	upload_image.save(path)

	q = "insert into family_members values(null,'%s','%s','%s','%s')" % (first_name,last_name,path,gender)
	id = insert(q)

	image1=request.files['image1']
	path=""
	rid=str(id)
	isFile = os.path.isdir("static/trainimages/"+rid)  
	print(isFile)
	if(isFile==False):
		os.mkdir('static\\trainimages\\'+rid)
	path="static/trainimages/"+rid+"/"+str(uuid.uuid4())+image1.filename
	image1.save(path)

	image2=request.files['image2']
	path=""
	isFile = os.path.isdir("static/trainimages/"+rid)  
	print(isFile)
	if(isFile==False):
		os.mkdir('static\\trainimages\\'+rid)
	path="static/trainimages/"+rid+"/"+str(uuid.uuid4())+image2.filename
	image2.save(path)

	image3=request.files['image3']
	path=""
	isFile = os.path.isdir("static/trainimages/"+rid)  
	print(isFile)
	if(isFile==False):
		os.mkdir('static\\trainimages\\'+rid)
	path="static/trainimages/"+rid+"/"+str(uuid.uuid4())+image3.filename
	image3.save(path)
	enf("static/trainimages/")

	data['status'] = 'success'
	# else:
	# 	data['status'] = 'failed'
	return demjson.encode(data)
	
@api.route('/login',methods=['get','post'])
def login():
	data={}
	username = request.args['username']
	password = request.args['password']
	q = "select * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	print(res)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/updatestatus',methods=['get','post'])
def updatestatus():
	data={}
	nid= request.args['nid']
	q = "update notifications set notification_status='viewed' WHERE notification_id='%s'" % (nid)
	id = update(q)
	if id>0:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']="updatestatus"
	return demjson.encode(data)
	
@api.route('/viewemergency',methods=['get','post'])
def viewemergency():
	data={}
	q = "select * from camerainputs"
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']="viewemergency"
	return  demjson.encode(data)

@api.route('/viewnoti',methods=['get','post'])
def viewnoti():
	data={}
	q = "select * from camerainputs inner join notifications using(input_id) where notification_status='pending'"
	res = select(q)
	if res:
		data['status']  = "success"
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']="viewnoti"
	return  demjson.encode(data)


@api.route('/register',methods=['get','post'])
def register():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	phone=request.args['phone']
	email=request.args['email']
	user=request.args['username']
	passs=request.args['password']

	q = "insert into login values(null,'%s','%s','user')" %(user,passs)
	id= insert(q)
	q="insert into register values(null,'%s','%s','%s','%s','%s')" %(id,fname,lname,email,phone)
	insert(q)
	data['status']  = "success"
	
	data['method']="register"
	return  demjson.encode(data)
