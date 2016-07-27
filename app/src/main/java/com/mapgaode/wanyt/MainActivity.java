package com.mapgaode.wanyt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.mapgaode.wanyt.helper.PopWindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    MapView mMapView = null;
    private AMapLocationClientOption locationClientOption;
    private AMapLocationClient mapLocationClient;
    private OnLocationChangedListener locationChangedListener;
    private AMap map;

    @BindView(R.id.ib_main_location)
    ImageButton btLocation;
    @BindView(R.id.ib_main_zoomin)
    ImageButton btZoomin;
    @BindView(R.id.ib_main_zoomout)
    ImageButton btZoomout;
    @BindView(R.id.ib_main_viewmode)
    ImageView btViewMode;
    @BindView(R.id.vi_main_pop_bg)
    View viPopBg;
    @BindView(R.id.ib_main_traffic)
    ImageButton ibTraffic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);

        initMapConfig();
    }

    /**
     * 初始化地图配置
     */
    private void initMapConfig() {
        map = mMapView.getMap();
        map.setLocationSource(this);
        map.getUiSettings().setMyLocationButtonEnabled(false);//隐藏定位按钮
        map.getUiSettings().setZoomControlsEnabled(false);//隐藏放大缩小按钮
        map.setMyLocationEnabled(true);
        map.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    private boolean trafficEnable;

    @OnClick(R.id.ib_main_traffic)
    public void traffic(){
        trafficEnable = !trafficEnable;
        map.setTrafficEnabled(trafficEnable);
    }

    private PopWindowHelper popHelper;
    private PopupWindow viewModeWindow;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.ib_main_viewmode)
    public void viewMode(){
        if(popHelper == null){
            popHelper = new PopWindowHelper(this);
        }

        PopupWindow popupWindow = popHelper.configWindow(map, viewModeWindow);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            popupWindow.showAsDropDown(btViewMode, 0, -105,
                    Gravity.CENTER_HORIZONTAL);
        }else{
            popupWindow.showAsDropDown(btViewMode);
        }

        viPopBg.setVisibility(View.VISIBLE);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                viPopBg.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 定位
     */
    @OnClick(R.id.ib_main_location)
    public void location(){
        map.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 放大
     */
    @OnClick(R.id.ib_main_zoomin)
    public void zoomIn(){
        map.animateCamera(CameraUpdateFactory.zoomIn());//有过渡动画
//        map.moveCamera(CameraUpdateFactory.zoomIn());//没有过渡动画
    }

    /**
     * 缩小
     */
    @OnClick(R.id.ib_main_zoomout)
    public void zoomOut(){
        map.animateCamera(CameraUpdateFactory.zoomOut());//有过渡动画
//        map.moveCamera(CameraUpdateFactory.zoomOut());//没有过渡动画
    }

    /**
     * 激活定位
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.locationChangedListener = onLocationChangedListener;
        if(mapLocationClient == null){
            locationClientOption = new AMapLocationClientOption();
            //设置定位模式
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mapLocationClient = new AMapLocationClient(this);
            mapLocationClient.setLocationListener(this);//设置定位监听
            mapLocationClient.setLocationOption(locationClientOption);//设置定位参数
            //每隔一段时间会检测一次位置，最小为2000ms，在声明周期中一定要停止并且这个方法会耗费电量
            mapLocationClient.startLocation();//当前是2000m
        }
    }

    /**
     *注销定位
     */
    @Override
    public void deactivate() {
        locationChangedListener = null;
        if(mapLocationClient != null){
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient = null;
    }

    /**
     * 位置改变时回调
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(mapLocationClient != null && aMapLocation != null){
            locationChangedListener.onLocationChanged(aMapLocation);//显示定位标识
        }
    }

    /**
     * 地图和声明周期方法，必须绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if(mapLocationClient != null){
            mapLocationClient.stopLocation();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
