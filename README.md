# weather-app

This application consists of 2 parts, the first is the sensor-provider which contains a db.json file which holds sample sensor data, this is used with json-server to allow the data to be queried by API calls.
To get the json-server running:

    - $ npm install -g json-server
    - $ json-server --host 127.0.0.1 --watch db.json 

The following HTTP endpoints are created automatically by json-server:

GET    /sensors  
GET    /sensors/{id}  
POST   /sensors  
PUT    /sensors/{id}  
PATCH  /sensors/{id}  
DELETE /sensors/{id}  

When you make POST, PUT, PATCH or DELETE requests, changes will be saved to db.json. A POST, PUT or PATCH request include a content-type: application/json header to use the JSON in the request body.

The seconds part of the application is the weather-adapter, which uses Spring to create RESTful API to query sensor data.
