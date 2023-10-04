package adam.weatheradapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.*;

import adam.weatheradapter.Sensor.Sensor;
import adam.weatheradapter.Sensor.SensorRepository;
import adam.weatheradapter.Sensor.SensorRepositoryCustom;
import adam.weatheradapter.Sensor.SensorRepositoryImpl;
import adam.weatheradapter.Sensor.SensorService;
import adam.weatheradapter.Sensor.SensorServiceImpl;

public class SensorServiceTest {

    private SensorService sensorService;

    @MockBean
    private SensorRepository sensorRepository;

    private SensorRepositoryImpl sensorRepositoryImpl = new SensorRepositoryImpl();

    private SensorServiceImpl sensorServiceImpl = new SensorServiceImpl();

    @Mock
    private SensorRepositoryCustom sensorRepositoryCustom;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStringToTimestamp() {
        String timeString = "2022-09-28 08:00:00";
        Timestamp timestamp = new Timestamp(1664348400000L); //epoch time
        
        assertEquals(timestamp, sensorRepositoryImpl.stringToTimeStamp(timeString));
    }

    @Test
    public void testGetMinValue() {
        Sensor resultSensor = new Sensor();
        ArrayList<Sensor> sensors = new ArrayList<>();
        Sensor mockSensor1 = new Sensor(2L, "Dublin", 1, 2, 3, "2022-09-28 08:00:00");
        Sensor mockSensor2 = new Sensor(2L, "Dublin", 4, 5, 6, "2023-11-15 08:30:00");
        sensors.add(mockSensor1);
        sensors.add(mockSensor2);

        assertEquals(sensorServiceImpl.getMinValue(resultSensor, sensors).getTemperature(), mockSensor1.getTemperature(), 3);
        assertEquals(sensorServiceImpl.getMinValue(resultSensor, sensors).getHumidity(), mockSensor1.getHumidity(), 3);
        assertEquals(sensorServiceImpl.getMinValue(resultSensor, sensors).getWindspeed(), mockSensor1.getWindspeed(), 3);
    }

    @Test
    public void testGetMaxValue() {
        Sensor resultSensor = new Sensor();
        ArrayList<Sensor> sensors = new ArrayList<>();
        Sensor mockSensor1 = new Sensor(2L, "Dublin", 1, 2, 3, "2022-09-28 08:00:00");
        Sensor mockSensor2 = new Sensor(2L, "Dublin", 4, 5, 6, "2023-11-15 08:30:00");
        sensors.add(mockSensor1);
        sensors.add(mockSensor2);

        assertEquals(sensorServiceImpl.getMaxValue(resultSensor, sensors).getTemperature(), mockSensor2.getTemperature(), 3);
        assertEquals(sensorServiceImpl.getMaxValue(resultSensor, sensors).getHumidity(), mockSensor2.getHumidity(), 3);
        assertEquals(sensorServiceImpl.getMaxValue(resultSensor, sensors).getWindspeed(), mockSensor2.getWindspeed(), 3);
    }

    @Test
    public void testGetAverageValue() {
        Sensor resultSensor = new Sensor();
        ArrayList<Sensor> sensors = new ArrayList<>();
        Sensor mockSensor1 = new Sensor(2L, "Dublin", 2, 2, 4, "2022-09-28 08:00:00");
        Sensor mockSensor2 = new Sensor(2L, "Dublin", 4, 6, 8, "2023-11-15 08:30:00");
        Sensor averageSensor = new Sensor(2L, "Dublin", 3, 4, 6, "2023-11-15 08:30:00");
        sensors.add(mockSensor1);
        sensors.add(mockSensor2);

        assertEquals(sensorServiceImpl.getAverageValue(resultSensor, sensors).getTemperature(), averageSensor.getTemperature(), 3);
        assertEquals(sensorServiceImpl.getAverageValue(resultSensor, sensors).getHumidity(), averageSensor.getHumidity(), 3);
        assertEquals(sensorServiceImpl.getAverageValue(resultSensor, sensors).getWindspeed(), averageSensor.getWindspeed(), 3);
    }

    @Test
    public void testGetSumValue() {
        Sensor resultSensor = new Sensor();
        ArrayList<Sensor> sensors = new ArrayList<>();
        Sensor mockSensor1 = new Sensor(2L, "Dublin", 1, 2, 3, "2022-09-28 08:00:00");
        Sensor mockSensor2 = new Sensor(2L, "Dublin", 4, 5, 6, "2023-11-15 08:30:00");
        Sensor sumSensor = new Sensor(2L, "Dublin", 5, 7, 9, "2023-11-15 08:30:00");
        sensors.add(mockSensor1);
        sensors.add(mockSensor2);

        assertEquals(sensorServiceImpl.getSumValue(resultSensor, sensors).getTemperature(), sumSensor.getTemperature(), 3);
        assertEquals(sensorServiceImpl.getSumValue(resultSensor, sensors).getHumidity(), sumSensor.getHumidity(), 3);
        assertEquals(sensorServiceImpl.getSumValue(resultSensor, sensors).getWindspeed(), sumSensor.getWindspeed(), 3);
    }
}
