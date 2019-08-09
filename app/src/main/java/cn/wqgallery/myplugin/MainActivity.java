package cn.wqgallery.myplugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zdh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 加载插件
     * 下载APK后 复制到data 》data>包名》
     * <p>
     * （就是把apk从一个地方保存到另一个地方）
     * <p>
     * //实际开发 可以直接下载放到data里面
     *
     * @param view
     */
    public void add(View view) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                LoadApk();
            }
        }.start();




    }

    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, "dex overwrite", Toast.LENGTH_SHORT).show();
            //初始化 插件管理类
            PluginManger.getInstance().loadPath(MainActivity.this);



        }
    };

    /**
     * 从SD卡 把taopiaopiao.apk拷贝到data/data 里面
     */
    private void LoadApk() {

        File filesDir = this.getDir("taopiaopiao", Context.MODE_PRIVATE);
        String name = "taopiaopiao.apk";
        String filePath = new File(filesDir, name).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        InputStream is = null;
        FileOutputStream os = null;
        try {
            Log.i(TAG, "加载插件 " + new File(Environment.getExternalStorageDirectory(), name).getAbsolutePath());
            is = new FileInputStream(new File(Environment.getExternalStorageDirectory(), name));
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            File f = new File(filePath);

            handler.sendMessage(handler.obtainMessage());
//            if (f.exists()) {
//                Toast.makeText(this, "dex overwrite", Toast.LENGTH_SHORT).show();
//            }

            //删除SDK里面的apk
            if (filesDir.exists()) {
                filesDir.delete();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转
     *
     * @param view
     */
    public void skip(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);

        //通过插件包名信息类 可以获取包名需要的信息比如activity的全类名

        //获取插件包名里面activity信息集合
        ActivityInfo[] activityInfos = PluginManger.getInstance().getPackageArchiveInfo().activities;
        //获取第一个activity全包名信息
        //activityInfos数组是根据你mainfest里面从上向下解析每个activity节点保存到数组里面，如果mainactivity没有写在第一就不会跳转到mainactivity
        String className = activityInfos[0].name;
        intent.putExtra("className", className);
        startActivity(intent);


    }


}
