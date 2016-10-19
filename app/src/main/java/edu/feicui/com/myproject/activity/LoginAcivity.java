package edu.feicui.com.myproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.feicui.com.myproject.R;
import edu.feicui.com.myproject.bean.User;
import edu.feicui.com.myproject.util.DBUtils;
import edu.feicui.com.myproject.view.ClearEditText;

public class LoginAcivity extends Activity implements View.OnClickListener {
    private TextView tv_regist,tv_remeber;
    private ClearEditText edit_username,edit_userpwd;
    private Button btn_login;
    private DBUtils dbUtils;
    //自定义的弹出框类
    SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        tv_remeber = (TextView) findViewById(R.id.tv_remeber);
        btn_login = (Button) findViewById(R.id.btn_login);
        edit_username = (ClearEditText) findViewById(R.id.edit_username);
        edit_userpwd = (ClearEditText) findViewById(R.id.edit_userpwd);
        dbUtils = new DBUtils(this);
        tv_regist.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_remeber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                Intent intent = new Intent(LoginAcivity.this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String name = edit_username.getText().toString();
                String pwd = edit_userpwd.getText().toString();
                User user = new User(name,pwd);
                boolean bo = dbUtils.login(user);
                if (bo){
                    Toast.makeText(LoginAcivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginAcivity.this, "用户名或密码不存在！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_remeber:
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(LoginAcivity.this, this);
                //显示窗口
                menuWindow.showAtLocation(LoginAcivity.this.findViewById(R.id.tv_remeber), Gravity
                        .BOTTOM | Gravity
                        .CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
    }

}
