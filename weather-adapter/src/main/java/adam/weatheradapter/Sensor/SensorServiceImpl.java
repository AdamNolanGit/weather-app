package adam.weatheradapter.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

        return sensorList;

    }
}
