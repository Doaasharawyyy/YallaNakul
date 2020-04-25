package com.example.yallanakul.Models;

public class CartList {
    private String FoodId,Foodname,Price,Quantity,Details;

    public CartList() {

    }

    public CartList(String foodId, String foodname, String price, String quantity, String details) {
        FoodId = foodId;
        Foodname = foodname;
        Price = price;
        Quantity = quantity;
        Details = details;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
