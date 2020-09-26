package com.example.revise_12_1;

public class Fruit {
    private String Name;
    private int imageId;

    public Fruit(String name, int imageId) {
        Name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return Name;
    }

    public int getImageId() {
        return imageId;
    }
}
