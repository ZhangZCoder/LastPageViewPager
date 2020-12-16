package com.zz.lastpageviewpager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by zz on 2020/12/11.
 */
public class LastPageViewPager extends ViewPager {


    public int toNextPage = 0;//当前position + 1，就可滑到下一页
    public String msg = "最后一页";
    private Context context;
    private float beforeX;
    private boolean isToast = false;
    private boolean isToast2 = false;
    private DisplayMetrics dm = getResources().getDisplayMetrics();
    private int screenWidth = dm.widthPixels;
    public LastPageViewPager(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                beforeX = ev.getX();
                isToast = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float motionValue = ev.getX() - beforeX;
                if (motionValue < 0) {//左滑
                    isToast2 = true;
                } else {//右滑
                    beforeX = ev.getX();
                    isToast2 = false;
                }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void scrollTo(int x, int y) {
        if (x > screenWidth * toNextPage) {
            setCurrentItem(toNextPage);
            super.scrollTo(screenWidth * toNextPage, y);
            if (isToast && isToast2){//避免多次提示
                if (msg != null && !msg.equals("")){
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
                isToast = false;
            }
        }else {
            isToast = true;
            super.scrollTo(x, y);
        }
    }
}
