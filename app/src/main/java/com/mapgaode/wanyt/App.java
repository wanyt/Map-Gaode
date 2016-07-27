package com.mapgaode.wanyt;

import android.app.Application;

import com.amap.api.maps.AMap;

/**
 * Created on 2016/7/27 14:24
 * <p/>
 * author wanyt
 * <p/>
 * Description:
 */
public class App extends Application {

    private App() {}
    public static App getInstance(){return Single.app;}
    private static class Single{
        private static final App app = new App();
    }

    public int mapViewMode = AMap.MAP_TYPE_NORMAL;

    public int getMapViewMode() {
        return mapViewMode;
    }

    public void setMapViewMode(int mapViewMode) {
        this.mapViewMode = mapViewMode;
    }
}
