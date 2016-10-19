package edu.feicui.com.myproject.bean;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class User {

    private String uname;
    private String pwd;

    public User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    public User() {
    }

    public String getUname() {
        return uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
