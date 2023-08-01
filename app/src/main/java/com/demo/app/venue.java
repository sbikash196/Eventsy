package com.demo.app;

public class venue {
    private int id;
    private String name;
    private String location;
    private double price;
    private String imageUrl;

    public venue(int id, String name, String location, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}



