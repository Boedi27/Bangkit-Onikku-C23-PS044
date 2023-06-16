from flask import Flask, request, jsonify
from PIL import Image, ImageOps
from tensorflow import keras
from tensorflow.keras.preprocessing.image import img_to_array, ImageDataGenerator, load_img
import tensorflow as tf
import numpy as np
import io
import os
import pymysql
import gunicorn

model = keras.models.load_model('/tmp/model.h5')

#bucket
storage_client = storage.Client()
bucket_name = 'onikku-bucket'
model_file_name = 'model.h5'
bucket = storage_client.bucket(bucket_name)
model_blob = bucket.blob('model.h5')
model_blob.download_to_filename('/tmp/model.h5')

#image to array
def transform_image(img):
    imgs = []
    img = img.resize((256, 256))
    img_array = img_to_array(img)
    img_array = img_array.astype(np.float32) / 255
    img_array = np.expand_dims(img_array, axis=0)
    
    return img_array

#sql
def create_connection():
    return pymysql.connect(
        host=os.environ.get('HOST'),
        user=os.environ.get('USER'),
        password=os.environ.get('PASSWORD'),
        database=os.environ.get('DATABASE'),
        cursorclass=pymysql.cursors.DictCursor
    )

#construct prediction, dah diubah
def predict(x):
    predictions = model(x)
    pred = np.argmax(predictions, axis=1)
    return pred


app = Flask(__name__)
   
@app.route("/status", methods=["POST"])
def status():
    if request.method == "POST":
        file = request.files.get('file')
        if file is None or file.filename == "":
            return jsonify({"error": "no file"})

        try:
            image_bytes = file.read()
            pillow_img = Image.open(io.BytesIO(image_bytes)).convert('RGB')
            prediction = predict(transform_image(pillow_img))
            data = {"prediction": int(prediction)}
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

@app.route("/", methods=["GET"])
def upload_file():
    return "OK"



if __name__ == '__main__':
    #run Flask
    port = int(os.environ.get("PORT", 5000))
    app.run()
    

