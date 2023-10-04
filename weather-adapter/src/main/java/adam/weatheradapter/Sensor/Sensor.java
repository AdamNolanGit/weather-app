package adam.weatheradapter.Sensor;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    private long  sensorId;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "humidity")
    private String humidity;

    @Column(name = "windspeed")
    private String windspeed;

    public Sensor(){}

    public Sensor(long  sensorId, String temperature, String humidity, String windspeed, String timestamp) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windspeed = windspeed;
        this.timestamp = timestamp;
    }

    public long  getSensorId() {
        return sensorId;
    }

    public void setSensorId(long  sensorId) {
         this.sensorId = sensorId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
         this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
         this.humidity = humidity;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
         this.windspeed = windspeed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
         this.timestamp = timestamp;
    }
}
