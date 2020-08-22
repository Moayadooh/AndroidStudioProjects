package com.example.watercontrolsystemcbr;

public class Records {
    double temperature;
    double soilMoisture;
    double waterAmount;
    String dateTime;

    public Records() {
        //Empty constructor
    }

    public Records(double temperature, double soilMoisture, double waterAmount, String dateTime) {
        this.temperature = temperature;
        this.soilMoisture = soilMoisture;
        this.waterAmount = waterAmount;
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSoilMoisture() {
        return soilMoisture;
    }

    public double getWaterAmount() {
        return waterAmount;
    }

    public String getDateTime() {
        return dateTime;
    }

}
