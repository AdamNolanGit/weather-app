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

You can run this in a seperate terminal than the json-server (so they can call each other).
To run, install the dependencies: 

mvn install

and then to run:

mvn spring-boot:run


The application has several APIs that can be called, firstly:

/getSensorData          -   Calls the /sensors API for the json-server which will receive a JSON of sensor data, this is then saved to an in-memory H2 database. If successful returns the updated list of Sensors.

/createSensorData       -   Creates a single Sensor object, and stores it to the H2 database. Requires a JSON object with the same fields as the Sensor object to be passed in the body.

/sensors/{id}           -   Returns the Sensor with the mathching ID.

/query/{sensorName}     -   Queries the sensors with the following parameters: systemName, metrics, statistic, startDate, endDate. The startDate and endData are optional.
                            The queries are passed in the url, here are two example calls(running locally):

                            http://localhost:8080/query/London?metrics=temperature&statistic=max        - Gets the maximum of the metrics for the Sensor with the systemName London.

                            http://localhost:8080/query/London?metrics=temperature&statistic=average&startDate=2022-09-28T08:00:00&endDate=2024-09-28T08:00:00  - - Gets the average of the metrics for the Sensor with the systemName London between the given dates.

