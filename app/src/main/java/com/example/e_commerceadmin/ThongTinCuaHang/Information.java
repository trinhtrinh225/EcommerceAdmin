package com.example.e_commerceadmin.ThongTinCuaHang;

public class Information {
    private String diaChi;
    private String thoiGian;
    private String imgHinh;
    private String SDT;
    private String moTa;



    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(String imgHinh) {
        this.imgHinh = imgHinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "Information{" +
                "diaChi='" + diaChi + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", imgHinh='" + imgHinh + '\'' +
                ", SDT='" + SDT + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
