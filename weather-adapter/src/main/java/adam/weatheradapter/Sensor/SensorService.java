package adam.weatheradapter.Sensor;

import java.util.List;

public interface SensorService {
    
    Sensor createSensor(Sensor sensor);

    Sensor getSensorById(long sensorId);

    List<Sensor> setupSensorData(String sensors);

    Sensor querySensorData(String sensorName, List<String> metricsString, String statistic, String startDate, String endDate);
}
