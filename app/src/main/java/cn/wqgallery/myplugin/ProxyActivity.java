package cn.wqgallery.myplugin;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import cn.wqgallery.pluginstand.PayInterfaceActivity;

/**
 * 代理activity
 */
public class ProxyActivity extends AppCompatActivity {

    //接口实现类全类名
    private String className = "";
    private PayInterfaceActivity payInterfaceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取跳转的插件的activity 的全类名
        className = getIntent().getStringExtra("className");

        //反射拿到payInterfaceActivity接口子类
        try {
            Class<?> aClass = getClassLoader().loadClass(className);
            //所有activity实现了接口，所以强转成他的父类
            payInterfaceActivity = (PayInterfaceActivity) aClass.newInstance();
            //实现接口绑定方法，使实现接口的类都可以获取上下文
            payInterfaceActivity.attach(this);

            //如果需要数据传输可以实现接口onCreate方法 把主APP的bundle传递过去
            payInterfaceActivity.onCreate(savedInstanceState);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 重新 classLoad 加载插件里面的类
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        //返回自定义的classLoad
        return PluginManger.getInstance().getDexClassLoader();
    }

    /**
     * 重新 资源加载类
     */
    @Override
    public Resources getResources() {
        //返回自定义的资源
        return PluginManger.getInstance().getResources();
    }


    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1=new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",className);
        super.startActivity(intent1);
    }



    /**
     * 实现接口里面所有方法，这样插件就可以调用接口里面方法
     */

    @Override
    protected void onStart() {
        super.onStart();
     payInterfaceActivity.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        payInterfaceActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        payInterfaceActivity.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        payInterfaceActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payInterfaceActivity.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        payInterfaceActivity.onSaveInstanceState(outState);
    }


    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        payInterfaceActivity.onTouchEvent(event);
        return super.onTrackballEvent(event);

    }


}
