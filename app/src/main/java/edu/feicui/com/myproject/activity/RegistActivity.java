package edu.feicui.com.myproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.feicui.com.myproject.R;
import edu.feicui.com.myproject.bean.User;
import edu.feicui.com.myproject.util.DBUtils;
import edu.feicui.com.myproject.view.ClearEditText;

public class RegistActivity extends Activity {
    private static final String TAG = "RegistActivity";
    private ClearEditText edit_registname;
    private ClearEditText edit_registpwd;
    private ClearEditText edit_regiscompwd;
    private Button btn_finish;
    private DBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        edit_registname = (ClearEditText) findViewById(R.id.edit_registname);//用户姓名
        edit_registpwd = (ClearEditText) findViewById(R.id.edit_registpwd);  //注册密码
        edit_regiscompwd = (ClearEditText) findViewById(R.id.edit_regiscompwd); //确认密码
        btn_finish = (Button) findViewById(R.id.btn_finish);  //完成注册的按钮
        dbUtils = new DBUtils(RegistActivity.this);
        //监听事件
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edit_registname.getText().toString();
                String userPwd = edit_registpwd.getText().toString();
                String userComPwd = edit_regiscompwd.getText().toString();
                if (userName == null || userName.length() < 3 | userName.length() > 10) {
                    Toast.makeText(RegistActivity.this, "注册的用户名长度在3-10之间", Toast.LENGTH_SHORT).show();
                    return;
                } else if (userPwd == null || userPwd.length() < 3 || userPwd.length() > 10) {
                    Toast.makeText(RegistActivity.this, "密码长度在3-10之间", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!userComPwd.equals(userPwd)) {
                    Toast.makeText(RegistActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    User user = new User(userName, userPwd);
                    boolean bo = dbUtils.CheckName(user);
                    if (!bo) {
                        dbUtils.saveData(user);
                        Toast.makeText(RegistActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegistActivity.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}
