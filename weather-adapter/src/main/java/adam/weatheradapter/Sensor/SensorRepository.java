package adam.weatheradapter.Sensor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SensorRepository extends JpaRepository<Sensor, Long> {}
