package edu.feicui.com.myproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.feicui.com.myproject.R;
import edu.feicui.com.myproject.bean.News;
import edu.feicui.com.myproject.util.ImageHelper;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<News> newList;
    private Context context;
    private LayoutInflater layoutInflater;
    private static final String TAG = "ListViewAdapter";

    public ListViewAdapter(Context context, List<News> newList) {
        this.newList = newList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (newList != null) {
            return newList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_item, null);
            vh = new ViewHolder();
            vh.stamp = (TextView) view.findViewById(R.id.tv_stamp);
            vh.title = (TextView) view.findViewById(R.id.tv_title);
            vh.summary = (TextView) view.findViewById(R.id.tv_summary);
            vh.imageView = (ImageView) view.findViewById(R.id.iv_home);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        News news = newList.get(i);
        vh.title.setText(news.getTitle());
        vh.stamp.setText(news.getStamp());
        vh.summary.setText(news.getSummary());
        new ImageHelper(context).display(vh.imageView, news.getIcon());
//        DownPic(news.getIcon(),vh.imageView);
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title, summary, stamp;
    }

    public void DownPic(final String path, final ImageView imageView) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    Bitmap bm = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bm);
                    Log.d(TAG, "handleMessage() called with: " + "msg = [" + msg.obj + "]");

                }
            }
        };


        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    int code = urlConnection.getResponseCode();
                    if (code == 200) {
                        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        Message msg = new Message();
                        msg.what = 200;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


}
