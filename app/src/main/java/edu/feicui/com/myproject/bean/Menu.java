package edu.feicui.com.myproject.bean;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Menu {
    private int cateid;
    private String cateurl;
    private String catetitle;
    private String catecontent;
    private String cateprice;

    public Menu() {
    }

    public Menu(int cateid, String cateurl, String catetitle, String catecontent, String cateprice) {
        this.cateid = cateid;
        this.cateurl = cateurl;
        this.catetitle = catetitle;
        this.catecontent = catecontent;
        this.cateprice = cateprice;
    }

    public int getCateid() {
        return cateid;
    }

    public String getCateurl() {
        return cateurl;
    }

    public String getCatetitle() {
        return catetitle;
    }

    public String getCatecontent() {
        return catecontent;
    }

    public String getCateprice() {
        return cateprice;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public void setCateurl(String cateurl) {
        this.cateurl = cateurl;
    }

    public void setCatetitle(String catetitle) {
        this.catetitle = catetitle;
    }

    public void setCatecontent(String catecontent) {
        this.catecontent = catecontent;
    }

    public void setCateprice(String cateprice) {
        this.cateprice = cateprice;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "cateid=" + cateid +
                ", cateurl='" + cateurl + '\'' +
                ", catetitle='" + catetitle + '\'' +
                ", catecontent='" + catecontent + '\'' +
                ", cateprice='" + cateprice + '\'' +
                '}';
    }
}
