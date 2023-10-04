package adam.weatheradapter.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import adam.weatheradapter.Exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorServiceImpl implements SensorService{
    
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorRepositoryCustom sensorRepositoryCustom;

    @Override
    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public Sensor getSensorById(long sensorId) {

        Optional < Sensor > sensorDb = this.sensorRepository.findById(sensorId);

        if (sensorDb.isPresent()) {
            return sensorDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + sensorId);
        }
    }

    /**
     * Receives a JSON obeject as a String, converts this to Sensor objects and saves them in db.
     * 
     * @param JSON object of sensors as a String.
     * @return List of saved Sensors.
     */
    @Override
    public List<Sensor> setupSensorData(String sensors) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Sensor> sensorList;
        try {
            sensorList = objectMapper.readValue(sensors, new TypeReference<List<Sensor>>() {});
        } catch (JsonMappingException e) {
            sensorList = new ArrayList<>();
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            sensorList = new ArrayList<>();
            e.printStackTrace();
        }

        for(Sensor sensor: sensorList) {
            sensorRepository.save(sensor);
        }

        return sensorList;
    }

    /**
	 * Queries the saved sensors with given parameters, and returns results as a Sensor object.
	 * 
	 * @param sensorName Name of the sensor.
	 * @param metricsString Metrics to use.
	 * @param statistic Statistic to use: min, max, sum, average.
	 * @param startDate Optional start date.
	 * @param endDate Optional end date.
	 * @return Sensor object mathching the given parameters.
	 */
    @Override
    public Sensor querySensorData(String sensorName, List<String> metricsString, String statistic, String startDate, String endDate) {
        ArrayList<Sensor> sensors = new ArrayList<>();

        if (startDate != null && endDate != null) {
            sensors = sensorRepositoryCustom.findByNameAndDate(sensorName, startDate, endDate);;
        } else {
            sensors = sensorRepositoryCustom.findByName(sensorName);
        }
        
        Sensor resultSensor = new Sensor();
        resultSensor.setSensorName(sensorName);
        switch(statistic) {
            case "min":
                resultSensor = this.getMinValue(resultSensor, sensors);
                break;
            case "max":
                resultSensor = this.getMaxValue(resultSensor, sensors);
                break;
            case "sum":
                resultSensor = this.getSumValue(resultSensor, sensors);
                break;
            case "average":
                resultSensor = this.getAverageValue(resultSensor, sensors);
                break;
            default:
                throw new IllegalArgumentException("Invalid statistic: " + statistic + ". Valid statistics are: min, max, sum, average");
        
        }
        
        return resultSensor;
    }

    /**
     * Get the minimum value of the metrics.
     * 
     * @param resultSensor New sensor used to store results.
     * @param sensors List of sensors to use.
     * @return The results sensor.
     */
    public Sensor getMinValue(Sensor resultSensor, ArrayList<Sensor> sensors) {
        double minTemp = sensors.get(0).getTemperature();
        double minHumid = sensors.get(0).getHumidity();
        double minWind = sensors.get(0).getWindspeed();
        for (Sensor sensor: sensors) {
            if (sensor.getTemperature() < minTemp) {
                minTemp = sensor.getTemperature();
            }
            if (sensor.getHumidity() < minHumid) {
                minHumid = sensor.getHumidity();
            }
            if (sensor.getWindspeed() < minWind) {
                minWind = sensor.getWindspeed();
            }
        }
        resultSensor.setTemperature(minTemp);
        resultSensor.setHumidity(minHumid);
        resultSensor.setWindspeed(minWind);
        return resultSensor;
    }

     /**
     * Get the max value of the metrics.
     * 
     * @param resultSensor New sensor used to store results.
     * @param sensors List of sensors to use.
     * @return The results sensor.
     */
    public Sensor getMaxValue(Sensor resultSensor, ArrayList<Sensor> sensors) {
        double maxTemp = sensors.get(0).getTemperature();
        double maxHumid = sensors.get(0).getHumidity();
        double maxWind = sensors.get(0).getWindspeed();
        for (Sensor sensor: sensors) {
            if (sensor.getTemperature() > maxTemp) {
                maxTemp = sensor.getTemperature();
            }
            if (sensor.getHumidity() > maxHumid) {
                maxHumid = sensor.getHumidity();
            }
            if (sensor.getWindspeed() > maxWind) {
                maxWind = sensor.getWindspeed();
            }
        }
        resultSensor.setTemperature(maxTemp);
        resultSensor.setHumidity(maxHumid);
        resultSensor.setWindspeed(maxWind);
        return resultSensor;
    }

     /**
     * Get the sum value of the metrics.
     * 
     * @param resultSensor New sensor used to store results.
     * @param sensors List of sensors to use.
     * @return The results sensor.
     */
    public Sensor getSumValue(Sensor resultSensor, ArrayList<Sensor> sensors) {
        double sumTemp = 0.0;
        double sumHumid = 0.0;
        double sumWind = 0.0;
        for (Sensor sensor: sensors) {
            sumTemp += sensor.getTemperature();
            sumHumid += sensor.getHumidity();
            sumWind += sensor.getWindspeed();
        }
        resultSensor.setTemperature(sumTemp);
        resultSensor.setHumidity(sumHumid);
        resultSensor.setWindspeed(sumWind);
        return resultSensor;
    }

     /**
     * Get the average value of the metrics.
     * 
     * @param resultSensor New sensor used to store results.
     * @param sensors List of sensors to use.
     * @return The results sensor.
     */
    public Sensor getAverageValue(Sensor resultSensor, ArrayList<Sensor> sensors) {
        double avgTemp = 0.0;
        double avgHumid = 0.0;
        double avgWind = 0.0;
        for (Sensor sensor: sensors) {
            avgTemp += sensor.getTemperature();
            avgHumid += sensor.getHumidity();
            avgWind += sensor.getWindspeed();
        }
        avgTemp = (double)Math.round((avgTemp / sensors.size()) * 10000d) / 10000d;
        avgHumid = (double)Math.round((avgHumid / sensors.size()) * 10000d) / 10000d;
        avgWind = (double)Math.round((avgWind / sensors.size()) * 10000d) / 10000d;
        resultSensor.setTemperature(avgTemp);
        resultSensor.setHumidity(avgHumid);
        resultSensor.setWindspeed(avgWind);
        return resultSensor;
    }
}
