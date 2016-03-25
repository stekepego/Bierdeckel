package com.example.dominik.bierdeckel;

/**
 * Created by dominik on 25.03.16.
 */
public class Food {
    public String foodName;
    public float price;
    public String description;
    public int count;

    public Food(String foodName, float price, String description, int count)
    {
        this.foodName = foodName;
        this.price = price;
        this.description = description;
        this.count = count;
    }
}
