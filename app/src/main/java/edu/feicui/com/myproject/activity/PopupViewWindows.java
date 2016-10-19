package edu.feicui.com.myproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import edu.feicui.com.myproject.R;

public class PopupViewWindows extends Activity {
    private Button btnClick;
    //自定义的弹出框类
    SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_view_window);
        btnClick = (Button) findViewById(R.id.btn);

        btnClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(PopupViewWindows.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(PopupViewWindows.this.findViewById(R.id.btn), Gravity
                        .BOTTOM | Gravity
                        .CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

            }
        });

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }


        }

    };
}
