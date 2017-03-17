package com.weelviewdemo.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.weelviewdemo.android.wheel.WheelView;
import com.weelviewdemo.android.wheel.adapters.ArrayWheelAdapter;
import com.weelviewdemo.android.wheel.adapters.WheelViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationSelect(context,true);
            }
        });

    }

    /**
     * 城市地点选择器
     * @param context
     * @return
     */
    public static PopupWindow showLocationSelect(final Context context, boolean showRightSelect){

        int[] location = new int[2];
        ((Activity)context).findViewById(R.id.tvPopBottom).getLocationOnScreen(location);

        PopupWindow popupWindow = null;
        if(popupWindow == null) {
            View dataSelectView = LayoutInflater.from(context).inflate(R.layout.pop_date_select, null);
            Button btnCancel = (Button) dataSelectView.findViewById(R.id.btnCancel);
            Button btnConfirm = (Button) dataSelectView.findViewById(R.id.btnConfirm);
            TextView tvTitle = (TextView) dataSelectView.findViewById(R.id.tvTitle);
            final WheelView welProvince = (WheelView) dataSelectView.findViewById(R.id.welYear);
            final WheelView welCity = (WheelView) dataSelectView.findViewById(R.id.welMonth);
            final WheelView welQuXian = (WheelView) dataSelectView.findViewById(R.id.welDay);

            if(!showRightSelect){
                welQuXian.setVisibility(View.GONE);
            }

            popupWindow = new PopupWindow(dataSelectView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.popwin_image_select_anim_style);
            popupWindow.setBackgroundDrawable(null);

            popupWindow.showAtLocation(((Activity) context).findViewById(R.id.tvPopBottom), Gravity.NO_GRAVITY,
                    location[0], location[1] - popupWindow.getHeight());

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });



            // 设置可见条目数量
            welProvince.setVisibleItems(5);
            welCity.setVisibleItems(5);
            welQuXian.setVisibleItems(5);


            final List<String> provinceList = new ArrayList<>();
            final List<String>[] cityList = new List[]{new ArrayList<>()};
            final List<String>[] quXianList = new List[]{new ArrayList<>()};
            for(int i = 0 ; i < 26 ; i++){
                provinceList.add("A" + i);
            }
            for(int i = 0 ; i < 26 ; i++){
                cityList[0].add("B" + i);
            }
            for(int i = 0 ; i < 26 ; i++){
                quXianList[0].add("C" + i);
            }



            final ArrayWheelAdapter<String> proviceWelAdapter = new ArrayWheelAdapter<>(context, provinceList);
            final ArrayWheelAdapter<String>[] cityWelAdapter = new ArrayWheelAdapter[]{new ArrayWheelAdapter<>(context, cityList[0])};
            final ArrayWheelAdapter<String>[] quWelAdapter = new ArrayWheelAdapter[]{new ArrayWheelAdapter<>(context, quXianList[0])};
            proviceWelAdapter.setTextSize(15);
            cityWelAdapter[0].setTextSize(15);
            quWelAdapter[0].setTextSize(15);

            welProvince.setViewAdapter(proviceWelAdapter);
            welCity.setViewAdapter(cityWelAdapter[0]);
            welQuXian.setViewAdapter(quWelAdapter[0]);

            proviceWelAdapter.setSelectPosiTextColor(welProvince.getCurrentItem(), Color.GREEN);
            proviceWelAdapter.setSelectPosiTextSize(welProvince.getCurrentItem(), 30);
            cityWelAdapter[0].setSelectPosiTextColor(welCity.getCurrentItem(), Color.GREEN);
            cityWelAdapter[0].setSelectPosiTextSize(welCity.getCurrentItem(), 30);
            quWelAdapter[0].setSelectPosiTextColor(welQuXian.getCurrentItem(), Color.GREEN);
            quWelAdapter[0].setSelectPosiTextSize(welQuXian.getCurrentItem(), 30);


            //WheelView的滑动操作，自定义的滑动操作非系统滑动操作
            welProvince.setScrollChangeListener(new WheelView.SrollChange() {
                @Override
                public void srollChangeListener(final WheelViewAdapter wheelViewAdapter, int item) {
                    proviceWelAdapter.setSelectPosiTextColor(item, Color.GREEN);
                    proviceWelAdapter.setSelectPosiTextSize(item, 30);
                    wheelViewAdapter.notifyDataChangedEvent();


                }
            });

            //WheelView的滑动操作，自定义的滑动操作非系统滑动操作
            welCity.setScrollChangeListener(new WheelView.SrollChange() {
                @Override
                public void srollChangeListener(final WheelViewAdapter wheelViewAdapter, int item) {
                    cityWelAdapter[0].setSelectPosiTextColor(item, Color.GREEN);
                    cityWelAdapter[0].setSelectPosiTextSize(item, 30);
                    wheelViewAdapter.notifyDataChangedEvent();

                }
            });

            //WheelView的滑动操作，自定义的滑动操作非系统滑动操作
            welQuXian.setScrollChangeListener(new WheelView.SrollChange() {
                @Override
                public void srollChangeListener(final WheelViewAdapter wheelViewAdapter, int item) {
                    quWelAdapter[0].setSelectPosiTextColor(item, Color.GREEN);
                    quWelAdapter[0].setSelectPosiTextSize(item, 30);
                    wheelViewAdapter.notifyDataChangedEvent();
                }
            });


            final PopupWindow finalPopupWindow = popupWindow;
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalPopupWindow.dismiss();
                }
            });


            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalPopupWindow.dismiss();
                }
            });
        }else {
            popupWindow.showAtLocation(((Activity) context).findViewById(R.id.tvPopBottom), Gravity.NO_GRAVITY,
                    location[0], location[1] - popupWindow.getHeight());
        }
        return popupWindow;
    }


}
