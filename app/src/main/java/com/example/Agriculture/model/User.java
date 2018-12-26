package com.example.Agriculture.model;

public class User {
    private int id;
    private String cropId;
    private String crop;
    private String cropVariety;
    private String cropStateId;

    public String getCropJoinStateName() {
        return cropJoinStateName;
    }

    public void setCropJoinStateName(String cropJoinStateName) {
        this.cropJoinStateName = cropJoinStateName;
    }

    private String cropJoinStateName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCropVariety() {
        return cropVariety;
    }

    public void setCropVariety(String cropVariety) {
        this.cropVariety = cropVariety;
    }

    public String getCropStateId() {
        return cropStateId;
    }

    public void setCropStateId(String cropStateId) {
        this.cropStateId = cropStateId;
    }


}
