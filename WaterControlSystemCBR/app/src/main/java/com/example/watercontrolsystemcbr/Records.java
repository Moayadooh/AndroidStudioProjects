package com.example.watercontrolsystemcbr;

public class Records {
    double temperature;
    double humidity;
    double soilMoisture;
    double waterAmount;
    String sensorsStatus;
    String dateTime;

    public Records() {
        //Empty constructor
    }

    public Records(double temperature, double humidity, double soilMoisture, double waterAmount, String sensorsStatus, String dateTime) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.soilMoisture = soilMoisture;
        this.waterAmount = waterAmount;
        this.sensorsStatus = sensorsStatus;
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getSoilMoisture() {
        return soilMoisture;
    }

    public double getWaterAmount() {
        return waterAmount;
    }

    public String getSensorsStatus() {
        return sensorsStatus;
    }

    public String getDateTime() {
        return dateTime;
    }

}
