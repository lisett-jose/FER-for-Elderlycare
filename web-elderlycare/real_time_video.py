from keras.preprocessing.image import img_to_array

import imutils
import cv2
from keras.models import load_model
import numpy as np

from core import *


def accesscamera():

    # parameters for loading data and images
    detection_model_path = 'haarcascade_files/haarcascade_frontalface_default.xml'
    emotion_model_path = 'models/_mini_XCEPTION.106-0.65.hdf5'

    # hyper-parameters for bounding boxes shape
    # loading models
    face_detection = cv2.CascadeClassifier(detection_model_path)
    emotion_classifier = load_model(emotion_model_path, compile=False)
    EMOTIONS = ["angry" ,"disgust","scared", "happy", "sad", "surprised","neutral"]


    # starting video streaming
    cv2.namedWindow('your_face(press q to quit)')
    camera = cv2.VideoCapture(0)
    while True:
        frame = camera.read()[1]
        #reading the frame
        frame = imutils.resize(frame,width=400)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face_detection.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=5,minSize=(30,30),flags=cv2.CASCADE_SCALE_IMAGE)
        
        canvas = np.zeros((250, 300, 3), dtype="uint8")
        frameClone = frame.copy()
        if len(faces) > 0:
            faces = sorted(faces, reverse=True,
            key=lambda x: (x[2] - x[0]) * (x[3] - x[1]))[0]
            (fX, fY, fW, fH) = faces
                        # Extract the ROI of the face from the grayscale image, resize it to a fixed 48x48 pixels, and then prepare
                # the ROI for classification via the CNN
            roi = gray[fY:fY + fH, fX:fX + fW]
            roi = cv2.resize(roi, (48, 48))
            roi = roi.astype("float") / 255.0
            roi = img_to_array(roi)
            roi = np.expand_dims(roi, axis=0)
            preds = emotion_classifier.predict(roi)[0]
            emotion_probability = np.max(preds)
            label = EMOTIONS[preds.argmax()]

     
            for (i, (emotion, prob)) in enumerate(zip(EMOTIONS, preds)):
                    # construct the label text
                    text = "{}: {:.2f}%".format(emotion, prob * 100)
                    w = int(prob * 300)
                    cv2.rectangle(canvas, (7, (i * 35) + 5),
                    (w, (i * 35) + 35), (0, 0, 255), -1)
                    cv2.putText(canvas, text, (10, (i * 35) + 23),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.45,
                    (255, 255, 255), 2)
                    # cv2.putText(frameClone, label, (fX, fY - 10),
                    # cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)
                    cv2.rectangle(frameClone, (fX, fY), (fX + fW, fY + fH),
                                  (0, 0, 255), 2)

                    #Save just the rectangle faces in SubRecFaces
                    sub_face = frameClone[fY:fY+fH, fX:fX+fW]
                    FaceFileName = "static/test.jpg" #Savingq the current image from the webcam for testing.
                    cv2.imwrite(FaceFileName, sub_face)

                    val=rec_face_image(FaceFileName)
                    print(val)
                    # print("user",vals)
                    str1=""
                    for ele in val:  
                        str1 = ele
                    print(str1)
                    val=str1.replace("'","")
                    print("val : ",val)
                    if val!="":
                        q="SELECT * FROM camerainputs"
                        res=select(q)
                        if res:
                            q="SELECT * FROM family_members where member_id='%s'"%(str(val))
                            print(q)
                            res=select(q)
                            if res:
                                dstr=res[0]['first_name']+" "+res[0]['last_name']+"-"+label
                            # cv2.putText(frameClone, dstr, (fX, fY - 10),
                            # cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)
                                cv2.putText(frameClone, dstr, (fX, fY-10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36,255,12), 2)
                            else:
                                pass
            
                            if label=="angry" or label=="disgust" or label=="scared" or label=="sad":
                                print("detected...")
                                q="SELECT * FROM notifications where input_id=(select max(input_id) from camerainputs)"
                                res=select(q)
                                if res:
                                    print("no notifications")
                                else:
                                    q="INSERT INTO notifications VALUES(NULL,(select max(input_id) from camerainputs),now(),'pending')"
                                    insert(q)
                            else:
                                print("normal...")
                    else:
                        uperson="Unknown-"+label
                        cv2.putText(frameClone, uperson, (fX, fY-10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36,255,12), 2)
                        import datetime
                        currentDT = datetime.datetime.now()
                        cdate=currentDT.strftime("%Y-%m-%d %H:%M")
                        q="SELECT * FROM camerainputs ORDER BY input_id DESC LIMIT 1"
                        res=select(q)
                        if res:
                            ctime=res[0]['datetime']
                            print(ctime)
                            # ctime=ctime.rstrip(ctime[-3:]).upper()
                            ctime=ctime.split(':')
                            ctime=ctime[0]+":"+ctime[1]
                            if cdate.strip()==ctime.strip():
                                print("not")
                            else:
                                path='static/uploads/unknown'+str(uuid.uuid4())+".jpg"
                                cv2.imwrite(path, sub_face)
                                q="INSERT INTO camerainputs VALUES(NULL,'%s','unknown person','%s',now())" %(path,label)
                                insert(q)
                        else:
                            path='static/uploads/unknown'+str(uuid.uuid4())+".jpg"
                            cv2.imwrite(path, sub_face)
                            q="INSERT INTO camerainputs VALUES(NULL,'%s','unknown person','%s',now())" %(path,label)
                            insert(q)
                

        cv2.imshow('your_face(press q to quit)', frameClone)
        cv2.imshow("Probabilities", canvas)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
            return

    camera.release()
    cv2.destroyAllWindows()

# accesscamera()