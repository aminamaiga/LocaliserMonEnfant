package fr.umontpellier.localisermonenfant.models;

import java.util.Date;

public class Zone {
    Long id;
    Double latitude;
    Double longitute;
    String adress;
    String locality;
    String cityName;
    String contryName;
    String PostalCode;
    Integer code;
    Date date;

    public Zone(){
    }

    public Zone(Long id, Double latitude, Double longitute, String adress, String locality, String cityName, String contryName, String postalCode, Integer code, Date date) {
        this.id = id;
        this.latitude = latitude;
        this.longitute = longitute;
        this.adress = adress;
        this.locality = locality;
        this.cityName = cityName;
        this.contryName = contryName;
        PostalCode = postalCode;
        this.code = code;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitute() {
        return longitute;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
