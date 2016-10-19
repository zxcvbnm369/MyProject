package edu.feicui.com.myproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.feicui.com.myproject.R;
import edu.feicui.com.myproject.adapter.ListViewAdapter;
import edu.feicui.com.myproject.bean.News;
import edu.feicui.com.myproject.util.DBTools;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private ListViewAdapter adapter;
    private DBTools dbTools;
    private List<News> newsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        dbTools = new DBTools(MainActivity.this);
        newsList = dbTools.findAll();
        if (newsList.size() != 0) {
            adapter = new ListViewAdapter(MainActivity.this, newsList);
            listView.setAdapter(adapter);
        } else {
            sendQuesone();
        }


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String content = (String) msg.obj;
                List<News> listData = getList(content);
                for (int i = 0; i < listData.size(); i++) {
                    dbTools.saveData(listData.get(i));
                    adapter = new ListViewAdapter(MainActivity.this, listData);
                    listView.setAdapter(adapter);
                }
            }
        }
    };


    private void sendQuesone() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection httpConection;  //网络请求得一个对象   HttpClicent
                try {
                    URL url = new URL("http://192.168.3.80:8080/test/home.json");  //url是将一个字符串转换成地址
                    httpConection = (HttpURLConnection) url.openConnection(); //获得urlConnection对象
                    httpConection.setConnectTimeout(8000);//设置一个连接超时的时间
                    httpConection.setReadTimeout(8000);//读取超时
                    httpConection.connect();//发起连接
                    InputStream in = httpConection.getInputStream();//从百度的连接地址打开一个输入流
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));//缓冲流
                    StringBuilder str = new StringBuilder();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        str.append(line);
                    }
                    System.out.print(str.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = str.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 解析json字符串
     *
     * @param json
     * @return
     */
    public List<News> getList(String json) {
        List<News> list = new ArrayList<News>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int status = jsonObject.getInt("status");
            String msg = jsonObject.getString("message");
            JSONArray arr = jsonObject.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String icon = obj.getString("icon");
                String title = obj.getString("title");
                String summary = obj.getString("summary");
                String stamp = obj.getString("stamp");
                News news = new News(icon, title, summary, stamp);
                list.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
