package cn.wqgallery.myplugin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManger {

    private PackageInfo packageArchiveInfo;
    private DexClassLoader dexClassLoader;
    private Resources resources;

    private PluginManger() {
    }

    private static PluginManger pluginManger = new PluginManger();

    public static PluginManger getInstance() {

        return pluginManger;
    }


    /**
     * 加载插件到 主app里面
     *
     * @param context
     */
    public void loadPath(Context context) {
        //获取刚下载保存到data里面的apk文件
        File fileDir = context.getDir("taopiaopiao", Context.MODE_PRIVATE);
        String name = "taopiaopiao.apk";

        //获取文件的全路径
        String path = new File(fileDir, name).getAbsolutePath();

        //获取apk的全路径就可以使用packageManger 获取包名信息

        //通过上下文获取包管理器
        PackageManager packageManager = context.getPackageManager();
        //获取包里面activity信息
        packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        //创建保存dex 的文件
        File dex = context.getDir("dex", Context.MODE_PRIVATE);
        /**
         * 获取插件里面的dex文件
         * 参数（插件的包的全路径，从插件包里面获取dex文件后 保存的全路径，依赖包的全路径 ，classLoad对象）
         */
        dexClassLoader = new DexClassLoader(path, dex.getAbsolutePath(), null, context.getClassLoader());


        /**
         * 获取插件里面的资源文件
         */

        try {
            //反射获取AssetManger实例
            AssetManager assetManager = AssetManager.class.newInstance();

            //反射获取addAssetPath方法
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

            //把插件包 全路径放进方法里面
            addAssetPath.invoke(assetManager, path);

            //获取插件的资源对象
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());


        } catch (Exception e) {

        }


    }

    public PackageInfo getPackageArchiveInfo() {
        return packageArchiveInfo;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
