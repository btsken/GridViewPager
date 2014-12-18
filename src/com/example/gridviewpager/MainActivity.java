package com.example.gridviewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.example.viewpagergridview.R;

public class MainActivity extends Activity {

    private WrapContentViewPager viewPager;
    private GridViewPagerAdapter<String> mGridViewPagerAdapter;
    private List<String> mDatas = new ArrayList<String>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 30; i++) {
            mDatas.add(String.valueOf(i));
        }
        mGridViewPagerAdapter = new GridViewPagerAdapter<String>(this, mDatas);

        viewPager = (WrapContentViewPager) findViewById(R.id.myviewpager);
        viewPager.setAdapter(mGridViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mGridViewPagerAdapter.getPageNumber());
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
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
