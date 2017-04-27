package com.example.nick.growerland.APIProvider;

import java.util.Date;

public class Weather {

    //Data/time of calculation, UTC
    private Date mDate;

    //Expected temperature. Unit Default: Kelvin
    private double temperature;


    /*
     * Maximum temperature at the moment of calculation.
     * This is deviation from 'temp' that is possible for large cities and megalopolises
     * geographically expanded. Unit Default: Kelvin.
     */
    private double maxTemperature;

    /*
     * Minimum temperature at the moment of calculation.
     * This is deviation from 'temp' that is possible for large cities and megalopolises
     * geographically expanded. Unit Default: Kelvin.
     */
    private double minTemperature;

    //Atmospheric pressure on the sea level by default, hPa
    private double pressure;

    //Atmospheric pressure on the sea level, hPa
    private double seaLevel;

    //Atmospheric pressure on the ground level, hPa
    private double groundLevel;

    //Humidity, %
    private double humidity;

    //х*й знает, но пусть будет
    private double temperature_kf;

    // Weather condition id
    private int weatherId;

    //Group of weather parameters (Rain, Snow, Extreme etc.)
    private String weatherMain;

    //Weather condition within the group
    private String weatherDescription;

    //Weather icon id
    private String weatherIcon;

    //Cloudiness, %
    private int clouds;

    //Wind speed. Unit Default: meter/sec
    private double windSpeed;

    //Wind direction, degrees (meteorological)
    private double windDegree;

    //Rain volume for last 3 hours, mm
    private double rainVolume;

    //Snow volume for last 3 hours
    private double snowVolume;


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature_kf() {
        return temperature_kf;
    }

    public void setTemperature_kf(double temperature_kf) {
        this.temperature_kf = temperature_kf;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public double getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(double rainVolume) {
        this.rainVolume = rainVolume;
    }

    public double getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(double snowVolume) {
        this.snowVolume = snowVolume;
    }
}
