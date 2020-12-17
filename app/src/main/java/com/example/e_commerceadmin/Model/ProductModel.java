package com.example.e_commerceadmin.Model;

public class ProductModel {
    //product category
    private String productID;
    private String productTitle;
    private String productQuantity;
    private String productPrice;
    private String productCategory;
    private String productDescription;
    private String productIcon;
    private String discountAvailable;
    private String discountPrice;
    private String timestamp;
    private String uid;

    @Override
    public String toString() {
        return "SanPhamModel{" +
                "productID='" + productID + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productQuantity='" + productQuantity + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", discountAvailable='" + discountAvailable + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public ProductModel() {
    }

    public ProductModel(String productID, String productTitle, String productQuantity, String productPrice, String productCategory, String productDescription, String productIcon, String discountAvailable, String discountPrice, String timestamp, String uid) {
        this.productID = productID;
        this.productTitle = productTitle;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.discountAvailable = discountAvailable;
        this.discountPrice = discountPrice;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice + " đ";
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getDiscountAvailable() {
        return discountAvailable;
    }

    public void setDiscountAvailable(String discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public String getDiscountPrice() {
        return "-" + discountPrice + "% OFF";
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    
}
