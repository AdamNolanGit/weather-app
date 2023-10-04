package adam.weatheradapter.Sensor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
}
