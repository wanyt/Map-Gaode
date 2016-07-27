package com.mapgaode.wanyt.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.mapgaode.wanyt.App;
import com.mapgaode.wanyt.R;
import com.mapgaode.wanyt.utils.ScreenUtils;

/**
 * Created on 2016/7/26 16:15
 * <p>
 * author wanyt
 * <p>
 * Description:主界面popwindow的帮助类
 */
public class PopWindowHelper implements View.OnClickListener {

    private Context context;
    private ImageButton ivClose;

    public PopWindowHelper(Context context) {
        this.context = context;
    }

    private RelativeLayout rlViewModeCommon, rlViewModeSatellite, rlViewModeNight, rlViewModeNavigation;
    private TextView tvViewModeCommon, tvViewModeSatellite, tvViewModeNight, tvViewModeNavigation;
    private ImageView ivViewModeCommon, ivViewModeSatellite, ivViewModeNight, ivViewModeNavigation;
    private RelativeLayout llClose;
    private AMap map;
    private PopupWindow viewModeWindow;

    public PopupWindow configWindow(AMap map, PopupWindow viewModeWindow) {
        this.map = map;
        if(viewModeWindow == null){
            View popView = View.inflate(context, R.layout.pop_main_viewmode, null);

            int width = (int) (ScreenUtils.getScreenWidth(context) * 0.95);
            viewModeWindow = new PopupWindow(popView, width,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            viewModeWindow.setBackgroundDrawable(new ColorDrawable());
            this.viewModeWindow = viewModeWindow;

            llClose  = (RelativeLayout) popView.findViewById(R.id.ll_viewmode_close);
            ivClose = (ImageButton) popView.findViewById(R.id.ib_viewmode_close);

            rlViewModeCommon = (RelativeLayout) popView.findViewById(R.id.rl_viewmode_common);
            rlViewModeSatellite = (RelativeLayout) popView.findViewById(R.id.rl_viewmode_satellite);
            rlViewModeNight = (RelativeLayout) popView.findViewById(R.id.rl_viewmode_night);
            rlViewModeNavigation = (RelativeLayout) popView.findViewById(R.id.rl_viewmode_navigation);

            ivViewModeCommon = (ImageView) popView.findViewById(R.id.iv_viewmode_common);
            ivViewModeSatellite = (ImageView) popView.findViewById(R.id.iv_viewmode_satellite);
            ivViewModeNight = (ImageView) popView.findViewById(R.id.iv_viewmode_night);
            ivViewModeNavigation = (ImageView) popView.findViewById(R.id.iv_viewmode_navigation);

            tvViewModeCommon = (TextView) popView.findViewById(R.id.tv_viewmode_common);
            tvViewModeSatellite = (TextView) popView.findViewById(R.id.tv_viewmode_satellite);
            tvViewModeNight = (TextView) popView.findViewById(R.id.tv_viewmode_night);
            tvViewModeNavigation = (TextView) popView.findViewById(R.id.tv_viewmode_navigation);

            rlViewModeCommon.setOnClickListener(this);
            rlViewModeSatellite.setOnClickListener(this);
            rlViewModeNight.setOnClickListener(this);
            rlViewModeNavigation.setOnClickListener(this);
            llClose.setOnClickListener(this);
            ivClose.setOnClickListener(this);

            initView();
        }
        return viewModeWindow;
    }

    private void initView() {
        int mapViewMode = App.getInstance().getMapViewMode();
        switch (mapViewMode){
            case AMap.MAP_TYPE_NORMAL:
                setView(ivViewModeCommon, tvViewModeCommon, AMap.MAP_TYPE_NORMAL);
                break;
            case AMap.MAP_TYPE_SATELLITE:
                setView(ivViewModeSatellite, tvViewModeSatellite, AMap.MAP_TYPE_SATELLITE);
                break;
            case AMap.MAP_TYPE_NIGHT:
                setView(ivViewModeNight, tvViewModeNight, AMap.MAP_TYPE_NIGHT);
                break;
            case AMap.MAP_TYPE_NAVI:
                setView(ivViewModeNavigation, tvViewModeNavigation, AMap.MAP_TYPE_NAVI);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_viewmode_close:
            case R.id.ll_viewmode_close:
                if(viewModeWindow != null){
                    viewModeWindow.dismiss();
                }
                break;
            case R.id.rl_viewmode_common:
                setView(ivViewModeCommon, tvViewModeCommon, AMap.MAP_TYPE_NORMAL);
                break;
            case R.id.rl_viewmode_satellite:
                setView(ivViewModeSatellite, tvViewModeSatellite, AMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.rl_viewmode_night:
                setView(ivViewModeNight, tvViewModeNight, AMap.MAP_TYPE_NIGHT);
                break;
            case R.id.rl_viewmode_navigation:
                setView(ivViewModeNavigation, tvViewModeNavigation, AMap.MAP_TYPE_NAVI);
                break;
        }
    }

    private void setView(ImageView imageView, TextView textView, int mapMode) {
        resetView();
        setCheckedView(imageView, textView);
        map.setMapType(mapMode);
        App.getInstance().setMapViewMode(mapMode);
    }

    /**
     * 设置选中的背景
     * @param imageView
     * @param text
     */
    private void setCheckedView(ImageView imageView, TextView text) {
        imageView.setBackgroundResource(R.drawable.shape_rec_round_transparent_green);
        text.setBackgroundColor(context.getResources().getColor(R.color.transparent_green));
    }

    /**
     * 将选中视图恢复为未选中视图
     */
    private void resetView(){
        ivViewModeCommon.setBackgroundResource(R.drawable.shape_rec_round_transparent_grey);
        ivViewModeSatellite.setBackgroundResource(R.drawable.shape_rec_round_transparent_grey);
        ivViewModeNight.setBackgroundResource(R.drawable.shape_rec_round_transparent_grey);
        ivViewModeNavigation.setBackgroundResource(R.drawable.shape_rec_round_transparent_grey);

        tvViewModeCommon.setBackgroundColor(context.getResources().getColor(R.color.transparent_5));
        tvViewModeSatellite.setBackgroundColor(context.getResources().getColor(R.color.transparent_5));
        tvViewModeNight.setBackgroundColor(context.getResources().getColor(R.color.transparent_5));
        tvViewModeNavigation.setBackgroundColor(context.getResources().getColor(R.color.transparent_5));
    }
}
