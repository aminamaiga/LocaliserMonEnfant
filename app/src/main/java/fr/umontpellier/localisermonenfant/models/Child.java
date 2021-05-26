package fr.umontpellier.localisermonenfant.models;

import java.io.Serializable;

public class Child implements Serializable {
    String _id;
    String userId;
    String firstname;
    String lastname;
    Integer code;
    String picture;
    String parent;

    public  Child(){
    }

    public Child(String id, String firstname, String lastname, Integer code, String picture, String parent) {
        this.userId = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.code = code;
        this.picture = picture;
        this.parent = parent;
    }

    public Child(String firstname, String lastname, Integer code, String parent) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.code = code;
        this.parent = parent;
    }

    public String getUserId() {
        return userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUserId(String id) {
        this.userId = id;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
