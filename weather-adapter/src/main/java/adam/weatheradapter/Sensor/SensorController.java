package adam.weatheradapter.Sensor;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SensorController {

	@Autowired
	SensorService sensorService;

	/**
	 * Receives the sensor data from an API call, then stores in H2 database.
	 * 
	 * @return List of the saved Sensors
	 */
    @GetMapping(value = "/getSensorData")
	public List<Sensor> getSensorData() {
		String uri = "http://127.0.0.1:3000/sensors";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		List<Sensor> output = sensorService.setupSensorData(result);
		return output;
	}

	/**
	 * Creates a single sensor.
	 * 
	 * @param sensor Sensor to be created.
	 * @return Returns the sensor if successful.
	 */
	@PostMapping("/createSensorData")
    public Sensor createSensorData(@RequestBody Sensor sensor) {
		sensorService.createSensor(sensor);
        return sensor;
    }

	/**
	 * Returns the sensor with the given ID.
	 * 
	 * @param id Sensor ID.
	 * @return	Sensor mathcing the ID.
	 */
	@GetMapping("/sensors/{id}")
    public ResponseEntity < Sensor > getProductById(@PathVariable long id) {
        return ResponseEntity.ok().body(sensorService.getSensorById(id));
    }

	/**
	 * Queries the saved sensors with given parameters, and returns results as a Sensor object.
	 * 
	 * @param sensorName Name of the sensor.
	 * @param metrics Metrics to use.
	 * @param statistic Statistic to use: min, max, sum, average.
	 * @param startDate Optional start date.
	 * @param endDate Optional end date.
	 * @return Sensor object mathching the given parameters.
	 */
	@GetMapping("/query/{sensorName}")
	public Sensor querySensorData(
		@PathVariable String sensorName,
		@RequestParam(value="metrics") List<String> metrics,
		@RequestParam String statistic,
		@RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate) {

		return sensorService.querySensorData(sensorName, metrics, statistic, startDate, endDate);
	}
    
}
