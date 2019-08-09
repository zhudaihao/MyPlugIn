package cn.wqgallery.taopiaopiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import cn.wqgallery.pluginstand.PayInterfaceActivity;

public class BaseActivity extends AppCompatActivity implements PayInterfaceActivity {

    protected Activity activity;
    private Bundle bundle;

    /**
     * 插件apk未安装时获取不到上下文的
     * 主APP实现接口方法
     * 插件获取主app的上下文
     * @param proxyActivity
     */
    @Override
    public void attach(Activity proxyActivity) {
        activity = proxyActivity;
    }

    /**
     * 重写关于和上下文的所有方法
     *
     * @param view
     */
    @Override
    public void setContentView(View view) {
        if (activity != null) {
            activity.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }



    @Override
    public void setContentView(int layoutResID) {
        if (activity != null) {
            activity.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }


    @Override
    public <T extends View> T findViewById(int id) {
        if (activity!=null){
            return activity.findViewById(id);
        }else {
            return super.findViewById(id);
        }


    }

    /**
     * 把跳转的
     * @param intent
     */

    @Override
    public void startActivity(Intent intent) {
        if (activity!=null){
            intent.putExtra("className",intent.getComponent().getClassName());
            activity.startActivity(intent);
        }else {
            super.startActivity(intent);
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        bundle = savedInstanceState;

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
