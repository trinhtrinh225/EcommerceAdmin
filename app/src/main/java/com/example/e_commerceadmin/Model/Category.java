package com.example.e_commerceadmin.Model;

public class Category {
    private String cate_name, cate_image;

    public Category(String cate_name, String cate_image) {
        this.cate_name = cate_name;
        this.cate_image = cate_image;
    }

    public Category() {

    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_image() {
        return cate_image;
    }

    public void setCate_image(String cate_image) {
        this.cate_image = cate_image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cate_name='" + cate_name + '\'' +
                ", cate_image='" + cate_image + '\'' +
                '}';
    }
}
