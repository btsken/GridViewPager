package com.example.gridviewpager;

import java.util.ArrayList;
import java.util.List;

import com.example.viewpagergridview.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends Activity {

    private WrapContentViewPager viewPager;
    // private RelativeLayout viewPagerContainer;
    private MyPagerAdapter mAdpater;

    private int pageNumber = 0;
    private List<String> datas = new ArrayList<String>();

    private static final int PAGE_ITEM_NUM = 6;
    private static final int LAND_ROW_NUM = 6;
    private static final int PORT_ROW_NUM = 3;
    private static final int MAX_PAGE = 4;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 30; i++) {
            datas.add(String.valueOf(i));
        }
        pageNumber = (datas.size() / PAGE_ITEM_NUM) + 1;
        pageNumber = pageNumber >= MAX_PAGE ? MAX_PAGE : MAX_PAGE;

        mAdpater = new MyPagerAdapter();
        viewPager = (WrapContentViewPager) findViewById(R.id.myviewpager);

        viewPager.setAdapter(mAdpater);
        viewPager.setOffscreenPageLimit(pageNumber);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pageNumber;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            GridView gv = new GridView(MainActivity.this);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                gv.setNumColumns(PORT_ROW_NUM);
            } else {
                gv.setNumColumns(LAND_ROW_NUM);
            }
            gv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_expandable_list_item_1, getSix(position)));
            ((ViewPager) container).addView(gv);
            return gv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((GridView) object);
        }
    }

    private List<String> getSix(int position) {
        int size = datas.size();
        List<String> result = new ArrayList<String>();

        if (size >= (position + 1) * PAGE_ITEM_NUM) {

            for (int i = position * PAGE_ITEM_NUM; i < (position * PAGE_ITEM_NUM) + PAGE_ITEM_NUM; i++) {
                result.add(datas.get(i));
            }
        } else {

            for (int i = position * PAGE_ITEM_NUM; i < (position * PAGE_ITEM_NUM) + PAGE_ITEM_NUM; i++) {

                if (i < (size % PAGE_ITEM_NUM) + (position * PAGE_ITEM_NUM)) {
                    result.add(datas.get(i));
                } else {
                    result.add("X");
                }
            }
        }
        return result;
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected : " + position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int
                positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
