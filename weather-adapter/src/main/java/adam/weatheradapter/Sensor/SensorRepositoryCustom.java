package adam.weatheradapter.Sensor;

import java.util.ArrayList;

public interface SensorRepositoryCustom {
    
    public ArrayList<Sensor> findByName(String sensorName);

    public ArrayList<Sensor> findByNameAndDate(String sensorName, String startData, String endData);
}
