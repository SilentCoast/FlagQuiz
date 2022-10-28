package com.example.flagguizbysilentcoast.Classes;

public class Flag {
    public Flag(){}
    public Flag(String name,Integer imageId){
        this.name = name;
        this.imageId = imageId;
    }
    private String name;
    private Integer imageId;

    public Integer getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
