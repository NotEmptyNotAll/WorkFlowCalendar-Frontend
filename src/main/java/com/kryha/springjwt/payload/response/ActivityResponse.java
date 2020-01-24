package com.kryha.springjwt.payload.response;

public class ActivityResponse {

    private  String nameAct;

    private  String nameCateg;

    public ActivityResponse() {
    }

    public ActivityResponse(String nameAct, String nameCateg) {
        this.nameAct = nameAct;
        this.nameCateg = nameCateg;
    }

    public String getNameAct() {
        return nameAct;
    }

    public void setNameAct(String nameAct) {
        this.nameAct = nameAct;
    }

    public String getNameCateg() {
        return nameCateg;
    }

    public void setNameCateg(String nameCateg) {
        this.nameCateg = nameCateg;
    }
}
