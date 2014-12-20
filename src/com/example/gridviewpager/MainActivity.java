package com.example.gridviewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viewpagergridview.R;

public class MainActivity extends Activity {

    private WrapContentViewPager viewPager;
    private GridViewPagerAdapter<String> mBaseGridViewPagerAdapter;
    private List<String> mDatas = new ArrayList<String>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("activity", "onCreate");

        for (int i = 0; i < 24; i++) {
            mDatas.add(String.valueOf(i));
        }

        mBaseGridViewPagerAdapter = new GridViewPagerAdapter<String>(this, mDatas) {

            @Override
            public BaseAdapter getBaseAdapter(int pageNum) {
                return new MyAdapter(MainActivity.this, this.getComputedList(pageNum));
            }
        };

        viewPager = (WrapContentViewPager) findViewById(R.id.myviewpager);
        viewPager.setAdapter(mBaseGridViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mBaseGridViewPagerAdapter.getCount());
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mBaseGridViewPagerAdapter.setOnSizeChangeListener(viewPager);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBaseGridViewPagerAdapter.setHorizontalAndVerticalNum(6, 1);
        } else {
            mBaseGridViewPagerAdapter.setHorizontalAndVerticalNum(3, 2);
        }
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

    static class ViewHolder {
        private TextView nameTextView;
        private ImageView personImageView;
    }

    class MyAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private List<String> mDatas;

        public MyAdapter(Context context, List<String> datas) {
            mContext = context;
            mDatas = datas;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.grid_view_item, parent, false);
                holder = new ViewHolder();
                holder.nameTextView = (TextView) convertView.findViewById(R.id.textView1);
                holder.personImageView = (ImageView) convertView.findViewById(R.id.imageView1);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.nameTextView.setText(mDatas.get(position));
            holder.personImageView.setImageDrawable(mContext.getResources().getDrawable(
                    R.drawable.ic_launcher));
            return convertView;
        }
    }

}
