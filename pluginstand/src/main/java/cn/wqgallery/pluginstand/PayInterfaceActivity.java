package cn.wqgallery.pluginstand;


import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * 定义activity 接口
 */
public interface PayInterfaceActivity {

    /**
     * 绑定 activity
     *
     * @param proxyActivity
     */
    void attach(Activity proxyActivity);

    /**
     * 实现activity生命周期方法
     *
     * @param savedInstanceState
     */

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle savedInstanceState);

    boolean onTouchEvent(MotionEvent motionEvent);

    void onBackPressed();

}
