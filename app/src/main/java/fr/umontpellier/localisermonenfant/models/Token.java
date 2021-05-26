package fr.umontpellier.localisermonenfant.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
    private String _id;
    private String parent;
    @Json(name = "dateT")
    private Date dateT;
    private String token;

    public Token() {
    }

    public Token(String parent, Date dateT, String token) {
        this.parent = parent;
        this.dateT = dateT;
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Date getDateT() {
        return dateT;
    }

    public void setDateT(Date dateT) {
        this.dateT = dateT;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
