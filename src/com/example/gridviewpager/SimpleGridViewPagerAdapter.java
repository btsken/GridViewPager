package com.example.gridviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class SimpleGridViewPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private BaseAdapter mAdapter;
    private OnGridItemClickListener mOnGridItemClickListener;

    private int horizontalNum = 3;
    private int verticalNum = 2;
    private int pageItemNumber = 6;

    public interface OnGridItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public void setOnGridItemClickListener(OnGridItemClickListener l) {
        mOnGridItemClickListener = l;
    }

    public SimpleGridViewPagerAdapter(Context context, BaseAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GridView gv = new GridView(mContext);
        gv.setNumColumns(horizontalNum);
        gv.setAdapter(getComputedList(position));
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnGridItemClickListener != null) {
                    mOnGridItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
        ((ViewPager) container).addView(gv);
        return gv;
    }

    public int getPageNumber() {
        return (int) Math.ceil(getCount() / pageItemNumber);
    }

    private BaseAdapter getComputedList(final int pageNum) {
        int size = getCount();
        int adapterSizeTemp = 0;

        if (size >= (pageNum + 1) * pageItemNumber) {

            for (int i = pageNum * pageItemNumber; i < (pageNum * pageItemNumber)
                    + pageItemNumber; i++) {
                adapterSizeTemp++;
            }
        } else {

            for (int i = pageNum * pageItemNumber; i < (pageNum * pageItemNumber)
                    + pageItemNumber; i++) {

                if (i < (size % pageItemNumber) + (pageNum * pageItemNumber)) {
                    adapterSizeTemp++;
                }
            }
        }
        final int adapterSize = adapterSizeTemp;
        BaseAdapter adapter = new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mAdapter.getView((pageNum - 1) + position, convertView, parent);
            }

            @Override
            public long getItemId(int position) {
                return mAdapter.getItemId((pageNum - 1) + position);
            }

            @Override
            public Object getItem(int position) {
                return mAdapter.getItem((pageNum - 1) + position);
            }

            @Override
            public int getCount() {
                return adapterSize;
            }
        };

        return adapter;
    }

    @Override
    public int getCount() {
        return mAdapter.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    private void updatePageItemNumber() {
        pageItemNumber = horizontalNum * verticalNum;
    }

    public int getHorizontalNum() {
        return horizontalNum;
    }

    public void setHorizontalNum(int horizontalNum) {
        this.horizontalNum = horizontalNum;
        updatePageItemNumber();
    }

    public int getVerticalNum() {
        return verticalNum;
    }

    public void setVerticalNum(int verticalNum) {
        this.verticalNum = verticalNum;
        updatePageItemNumber();
    }

}
