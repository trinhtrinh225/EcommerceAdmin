package com.example.e_commerceadmin.Model;

public class CartModel {
    
    private String id, p_name, p_date, p_discount, p_discount_price, p_price, p_price_final, p_quantity, p_time;

    public CartModel() {
    }

    public CartModel(String id, String p_name, String p_date, String p_discount, String p_discount_price, String p_price, String p_price_final, String p_quantity, String p_time) {
        this.id = id;
        this.p_name = p_name;
        this.p_date = p_date;
        this.p_discount = p_discount;
        this.p_discount_price = p_discount_price;
        this.p_price = p_price;
        this.p_price_final = p_price_final;
        this.p_quantity = p_quantity;
        this.p_time = p_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public String getP_discount() {
        return p_discount;
    }

    public void setP_discount(String p_discount) {
        this.p_discount = p_discount;
    }

    public String getP_discount_price() {
        return p_discount_price;
    }

    public void setP_discount_price(String p_discount_price) {
        this.p_discount_price = p_discount_price;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_price_final() {
        return p_price_final;
    }

    public void setP_price_final(String p_price_final) {
        this.p_price_final = p_price_final;
    }

    public String getP_quantity() {
        return p_quantity;
    }

    public void setP_quantity(String p_quantity) {
        this.p_quantity = p_quantity;
    }

    public String getP_time() {
        return p_time;
    }

    public void setP_time(String p_time) {
        this.p_time = p_time;
    }
}
