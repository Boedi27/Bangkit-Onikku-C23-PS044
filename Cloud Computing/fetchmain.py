import json
#import mysql.connector
import os
import pymysql
from flask import Flask, jsonify
import requests

app = Flask(__name__)

#database configuration dari mysql
config = {
    'rekomendasi_pengolahan_sampah' : 'rekomendasi_sampah',
    'nama' : 'nama_olahan',
    'bahan' : 'bahan_olahan',
    'database' : 'database_pengolahan',
    'port' : '5000', #port mysql
}

def create_connection():
    return pymysql.connect(
        host='your_host',
        user='your_user',
        password='your_password',
        database='your_database',
        cursorclass=pymysql.cursors.DictCursor
    )

@app.route('/recommend', methods=['GET'])
def get_data_from_mysql():
    try:
        #establish connection to remote mysql database
        #connection = mysql.connector.connect(**config)
        connection = create_connection()

        #execute sql queries
        #cursor = connection.cursor()
        with connection.cursor() as cursor:
        #SELECT query to fetch data from table
            query = "SELECT * FROM table_rekomendasi"
            cursor.execute(query)

        #fetch rows returned by query
            rows = cursor.fetchall()

        #convert rows to list of dictionaries > json serialization
        data = []
        for row in rows:
            record= {
                'id' : row[0],
                'nama_olahan' : row[1],
                'link_gambar' : row[2],
                'langkah_membuat' : row[3],
            }
            data.append(record)

        #close cursor and connection
        cursor.close()
        connection.close()

        #return fetched data as json output
        return jsonify
    
    except Exception as e:
        return str(e)
    
if __name__ == '__main__':
    app.run()
    
