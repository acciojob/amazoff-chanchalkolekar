package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        // The deliveryTime has to converted from string to int and then stored in the
        // attribute
        String[] strarr = deliveryTime.split(":", 2);
        int intialanswer = 0, finalanswer = 0;
        for (int i = 0; i < strarr.length; i++) {
            String demo = strarr[i];
            if (i == 0) {
                int temp = Integer.parseInt(demo);
                intialanswer = temp * 60;
            } else {
                int temp = Integer.parseInt(demo);
                finalanswer = intialanswer + temp;
            }
        }
        this.deliveryTime = finalanswer;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
