package com.example.gridviewpager;

import com.example.gridviewpager.GridViewPagerAdapter.OnSizeChangeListener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom ViewPager for warpContent
 * @author Ken Huang
 *
 */
public class WrapContentViewPager extends ViewPager implements OnSizeChangeListener {

    private int verticalNum = 2; // initial value
    private int widthMeasureSpec, heightMeasureSpec;

    public WrapContentViewPager(Context context) {
        super(context);
    }

    public WrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Let ViewPager can warpContent
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h * verticalNum;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * update gridView size change
     */
    @Override
    public void onSizeChange(int verticalNum) {
        this.verticalNum = verticalNum;
        this.measure(widthMeasureSpec, heightMeasureSpec);
    }
}
