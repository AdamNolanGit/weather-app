package adam.weatheradapter.Sensor;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sensorId;

    private String sensorName;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "humidity")
    private double humidity;

    @Column(name = "windspeed")
    private double windspeed;

    public Sensor(){}

    public Sensor(long  sensorId, String sensorName, double temperature, double humidity, double windspeed, String timestamp) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windspeed = windspeed;
        this.timestamp = timestamp;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
         this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
         this.sensorName = sensorName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
         this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
         this.humidity = humidity;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
         this.windspeed = windspeed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
         this.timestamp = timestamp;
    }
}
