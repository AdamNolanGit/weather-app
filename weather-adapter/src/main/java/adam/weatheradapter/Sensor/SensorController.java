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


@RestController
public class SensorController {

	@Autowired
	SensorService sensorService;

    @GetMapping(value = "/getSensorData")
	public List<Sensor> getSensorData() {
		String uri = "http://127.0.0.1:3000/sensors";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		List<Sensor> output = sensorService.setupSensorData(result);
		return output;
	}

	@PostMapping("/createSensorData")
    public Sensor createSensorData(@RequestBody Sensor sensor) {
		sensorService.createSensor(sensor);
        return sensor;
    }

	@GetMapping("/sensors/{id}")
    public ResponseEntity < Sensor > getProductById(@PathVariable long id) {
        return ResponseEntity.ok().body(sensorService.getSensorById(id));
    }

	// @GetMapping("/{sensorId}/query")
	// public List<Sensor> querySensorMetrics(
	// 	@PathVariable String sensorId,
	// 	@RequestParam List<String> metricTypes,
	// 	@RequestParam String statistic,
	// 	@RequestParam(required = false) LocalDate startDate,
	// 	@RequestParam(required = false) LocalDate endDate) {
	// return sensorService.getSensorData(sensorId, metricTypes, statistic, startDate, endDate);
	// 	}
    
}
