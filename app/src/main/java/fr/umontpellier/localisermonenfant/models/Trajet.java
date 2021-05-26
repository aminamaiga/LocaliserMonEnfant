package fr.umontpellier.localisermonenfant.models;

import com.squareup.moshi.Json;

import java.util.Date;

public class Trajet {
    Long id;
    Double latitude;
    Double longitude;
    String adress;
    String locality;
    String cityName;
    String contryName;
    String PostalCode;
    Integer code;
    String child;
    @Json(name = "dateT")
    Date dateT;
    String firstname;
    String lastname;
    String parent;

    public Trajet() {
    }

    public Trajet(Double latitude, Double longitute, String adress, String locality, String cityName, String contryName, String postalCode, Integer code, Date date,
                  String child, String childName, String childLastName, String parent) {
        this.latitude = latitude;
        this.longitude = longitute;
        this.adress = adress;
        this.locality = locality;
        this.cityName = cityName;
        this.contryName = contryName;
        PostalCode = postalCode;
        this.code = code;
        this.dateT = date;
        this.child = child;
        this.firstname = childName;
        this.lastname = childLastName;
        this.parent = parent;
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
        return longitude;
    }

    public void setLongitute(Double longitute) {
        this.longitude = longitute;
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
        return dateT;
    }

    public void setDate(Date date) {
        this.dateT = date;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getDateT() {
        return dateT;
    }

    public void setDateT(Date dateT) {
        this.dateT = dateT;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
