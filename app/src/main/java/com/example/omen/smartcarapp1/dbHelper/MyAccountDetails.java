package com.example.omen.smartcarapp1.dbHelper;


public class MyAccountDetails {
    private String name = "Azamat";
    private String carModel = "Toyota Camry 50";

    public MyAccountDetails(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getName() {
        return name;
    }

    public String getCarModel() {
        return carModel;
    }



}
