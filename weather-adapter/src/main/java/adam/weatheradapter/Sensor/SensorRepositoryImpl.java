package adam.weatheradapter.Sensor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.EntityNotFoundException;

public class SensorRepositoryImpl implements SensorRepositoryCustom {

    @Autowired
    @Lazy
    SensorRepository sensorRepository;

    /**
     * Returns a list of Sensors matching a given name.
     * 
     * @param sensorName
     * @return List of sensors that match.
     */
    @Override
    public ArrayList<Sensor> findByName(String sensorName) {
        List<Sensor> sensors = sensorRepository.findAll();
        ArrayList<Sensor> result = new ArrayList<Sensor>();
        for (Sensor sensor: sensors) {
            if (sensor.getSensorName().equals(sensorName)) {
                result.add(sensor);
            }
        }

        if (result.size() > 0) {
            return result;
        }
        throw new EntityNotFoundException("Sensor " + sensorName + " not found");
    }

    /**
     * Returns a list of Sensors matching a given name and date range
     * 
     * @param sensorName
     * @param startDate
     * @param endData
     * @return List of sensors that match.
     */
    @Override
    public ArrayList<Sensor> findByNameAndDate(String sensorName, String startDate, String endData) {
        List<Sensor> sensors = sensorRepository.findAll();
        Timestamp starTimestamp = this.stringToTimeStamp(startDate);
        Timestamp endTimestamp = this.stringToTimeStamp(endData);

        ArrayList<Sensor> result = new ArrayList<Sensor>();
        for (Sensor sensor: sensors) {
            Timestamp sensorTimestamp = this.stringToTimeStamp(sensor.getTimestamp());
            if (sensor.getSensorName().equals(sensorName) && sensorTimestamp.before(endTimestamp) && sensorTimestamp.after(starTimestamp)) {
                result.add(sensor);
            }
        }

        if (result.size() > 0) {
            return result;
        }
        throw new EntityNotFoundException("Sensor " + sensorName + " between timestamps: " + startDate + " " + endData + " not found");
    }

    /**
     * Converts String to a timestamp.
     * 
     * @param timeString
     * @return timestamp.
     */
    public Timestamp stringToTimeStamp(String timeString) {
        Timestamp timestamp;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(timeString);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + timeString);
        }
        return timestamp;
    }
}
