package com.mapgaode.wanyt.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created on 2016/7/26 18:07
 * <p>
 * author wanyt
 * <p>
 * Description:
 */
public class ScreenUtils {

    public static int getScreenWidth(Context context){
//        Resources resources = context.getResources();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        return displayMetrics.widthPixels;

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
