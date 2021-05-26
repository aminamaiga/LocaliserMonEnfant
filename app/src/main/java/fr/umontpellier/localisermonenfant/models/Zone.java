package fr.umontpellier.localisermonenfant.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.Date;

public class Zone implements Serializable {
    Long id;
    String _id;
    Double latitude;
    Double longitude;
    String parent;
    Double diametre;
    @Json(name = "dateT")
    Date dateT;
    int type;

    public Zone() {
    }

    public Zone(Double latitude, Double longitude, String parent, Double diametre, Date dateT, int type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.parent = parent;
        this.diametre = diametre;
        this.dateT = dateT;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Double getDiametre() {
        return diametre;
    }

    public void setDiametre(Double diametre) {
        this.diametre = diametre;
    }

    public Date getDateT() {
        return dateT;
    }

    public void setDateT(Date dateT) {
        this.dateT = dateT;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
