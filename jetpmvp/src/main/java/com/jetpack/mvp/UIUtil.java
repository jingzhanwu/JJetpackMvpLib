package com.jetpack.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @anthor created by jzw
 * @date 2019/12/23
 * @change
 * @describe 界面及view操作辅助类
 **/
public class UIUtil {
    /**
     * 弹出toast提示
     *
     * @param msg
     */
    public void toast(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 根据OS版本,将状态栏、标题栏置为透明状态,
     */
    public static void translucentStatusBar(Window window, int statusBarColor) {
        //得到当前界面的装饰视图
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View decorView = getWindow().getDecorView();
//            //设置让应用主题内容占据状态栏和导航栏
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            //设置状态栏和导航栏颜色为透明
//            setStatusBarColor(statusBarColor);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置让应用主题内容占据状态栏和导航栏
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            setStatusBarColor(window, statusBarColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @SuppressLint("NewApi")
    public static void setStatusBarColor(Window window, int color) {
        //设置状态栏和导航栏颜色为透明
        window.setStatusBarColor(color);
    }

    public static void startActivity(Activity activity, Class<?> clazz) {
        startActivity(activity, clazz, null);
    }

    public static void startActivity(Activity activity, Class<?> clazz, Bundle bundle) {
        startActivity(activity, clazz, bundle, 0);
    }

    public static void startActivity(Activity activity, Class<?> clazz, int requestCode) {
        startActivity(activity, clazz, null, requestCode);
    }

    public static void startActivity(Activity activity, Class<?> clazz, Bundle bundle, int requestCode) {
        try {
            Intent intent = new Intent(activity, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
