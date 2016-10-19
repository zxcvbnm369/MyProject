package edu.feicui.com.myproject.bean;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Data {
    private String hotelname;
    private String address;
    private Menu menu;

    public Data() {
    }

    public Data(String hotelname, String address, Menu menu) {
        this.hotelname = hotelname;
        this.address = address;
        this.menu = menu;
    }

    public String getHotelname() {
        return hotelname;
    }

    public String getAddress() {
        return address;
    }

    public Menu getMenu() {
        return menu;
    }


    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Data{" +
                "hotelname='" + hotelname + '\'' +
                ", address='" + address + '\'' +
                ", menu=" + menu +
                '}';
    }
}
