package edu.feicui.com.myproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ImageHelper {
    //LruCache 缓存，内存缓存
    private static LruCache<String, Bitmap> mCache;

    private static Handler mHandler;
    private static ExecutorService mThreadPool;
    private static Map<ImageView, Future<?>> mTaskTags = new LinkedHashMap<ImageView, Future<?>>();
    private Context mContext;

    public ImageHelper(Context context) {
        this.mContext = context;
        if (mCache == null) {
            // 最大使用的内存空间
            int maxSize = (int) (Runtime.getRuntime().freeMemory() / 4);
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        if (mHandler == null) {
            mHandler = new Handler();
        }

        if (mThreadPool == null) {
            // 最多同时允许的线程数为3个
            mThreadPool = Executors.newFixedThreadPool(3);
        }
    }

    public void display(final ImageView iv, final String url) {
        // 1.去内存中取
        Bitmap bitmap = mCache.get(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }

        //去硬盘上去取
        bitmap = loadBitmapFromLocal(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }
        //从网络上去取图片
        new Thread() {
            @Override
            public void run() {
                // 获取连接
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(30 * 1000);// 设置连接服务器超时时间
                    conn.setReadTimeout(30 * 1000);// 设置读取响应超时时间
                    // 连接网络
                    conn.connect();
                    // 获取响应码
                    int code = conn.getResponseCode();
                    if (200 == code) {
                        InputStream is = conn.getInputStream();
                        // 将流转换为bitmap
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);

                        // 存储到本地
                        write2Local(url, bitmap);

                        // 存储到内存
                        mCache.put(url, bitmap);



                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // iv.setImageBitmap(bitmap);
                                iv.setImageBitmap(bitmap);

                            }
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
//        new InageLoadTask(url, iv);

    }

    /**
     * 网络加载图片
     */
    private class InageLoadTask implements Runnable {
        private String mUrl;
        private ImageView iv;

        public InageLoadTask(String mUrl, ImageView iv) {
            this.mUrl = mUrl;
            this.iv = iv;
        }

        @Override
        public void run() {
            // 获取连接
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(mUrl).openConnection();
                conn.setConnectTimeout(30 * 1000);// 设置连接服务器超时时间
                conn.setReadTimeout(30 * 1000);// 设置读取响应超时时间
                // 连接网络
                conn.connect();
                // 获取响应码
                int code = conn.getResponseCode();
                if (200 == code) {
                    InputStream is = conn.getInputStream();
                    // 将流转换为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    // 存储到本地
                    write2Local(mUrl, bitmap);

                    // 存储到内存
                    mCache.put(mUrl, bitmap);

                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // iv.setImageBitmap(bitmap);

                            display(iv, mUrl);
                        }
                    });

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    private void write2Local(String url, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            File file = new File(getCacheDir(), url);
            fos = new FileOutputStream(file);

            // 将图像写到流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 从本地去加载图片
     *
     * @param url
     * @return
     */
    private Bitmap loadBitmapFromLocal(String url) {
        File file = new File(getCacheDir(), url);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mCache.put(url, bitmap);
            return bitmap;
        }
        return null;
    }


    private String getCacheDir() {
        String sate = Environment.getExternalStorageState();//获得外部存储媒体目录。（/mnt/sdcard or /storage/sdcard0）
        File dir = null;
        //MEDIA_MOUNTED 存储媒体已经挂载，并且挂载点可读/写。
        if (Environment.MEDIA_MOUNTED.equals(sate)) {
            //有sd卡
            //getExternalStorageDirectory() 获得外部存储媒体目录。（/mnt/sdcard or /storage/sdcard0）
            dir = new File(Environment.getExternalStorageDirectory(), "/Android/data/" + mContext.getPackageName() + "/icon");
        } else {
            dir = new File(mContext.getCacheDir(), "/icon");
        }
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getAbsolutePath();
    }


}
