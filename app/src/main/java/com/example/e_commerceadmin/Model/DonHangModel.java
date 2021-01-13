package com.example.e_commerceadmin.Model;

public class DonHangModel {
    private String dated;
    private String oder_address;
    private String oder_email;
    private String oder_name;
    private String oder_phone;
    private String state;
    private String time;
    private String total_mount;



    private String uid;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public DonHangModel() {
    }

    public DonHangModel(String uid, String dated, String oder_address, String oder_email, String oder_name, String oder_phone, String state, String time, String total_mount) {
        this.dated = dated;
        this.oder_address = oder_address;
        this.oder_email = oder_email;
        this.oder_name = oder_name;
        this.oder_phone = oder_phone;
        this.state = state;
        this.time = time;
        this.total_mount = total_mount;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "DonHangModel{" +
                "dated='" + dated + '\'' +
                ", oder_address='" + oder_address + '\'' +
                ", oder_email='" + oder_email + '\'' +
                ", oder_name='" + oder_name + '\'' +
                ", oder_phone='" + oder_phone + '\'' +
                ", state='" + state + '\'' +
                ", time='" + time + '\'' +
                ", total_mount='" + total_mount + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    public String getOder_address() {
        return oder_address;
    }

    public void setOder_address(String oder_address) {
        this.oder_address = oder_address;
    }

    public String getOder_email() {
        return oder_email;
    }

    public void setOder_email(String oder_email) {
        this.oder_email = oder_email;
    }

    public String getOder_name() {
        return oder_name;
    }

    public void setOder_name(String oder_name) {
        this.oder_name = oder_name;
    }

    public String getOder_phone() {
        return oder_phone;
    }

    public void setOder_phone(String oder_phone) {
        this.oder_phone = oder_phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_mount() {
        return total_mount;
    }

    public void setTotal_mount(String total_mount) {
        this.total_mount = total_mount;
    }
}
