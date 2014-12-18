package com.example.gridviewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class GridViewPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mDatas;
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

    public GridViewPagerAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GridView gv = new GridView(mContext);
        gv.setNumColumns(horizontalNum);
        gv.setAdapter(new ArrayAdapter<T>(mContext,
                android.R.layout.simple_expandable_list_item_1, getSix(position)));
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnGridItemClickListener != null) {
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

    private List<T> getSix(int position) {
        int size = getCount();
        List<T> result = new ArrayList<T>();

        if (size >= (position + 1) * pageItemNumber) {

            for (int i = position * pageItemNumber; i < (position * pageItemNumber)
                    + pageItemNumber; i++) {
                result.add(mDatas.get(i));
            }
        } else {

            for (int i = position * pageItemNumber; i < (position * pageItemNumber)
                    + pageItemNumber; i++) {

                if (i < (size % pageItemNumber) + (position * pageItemNumber)) {
                    result.add(mDatas.get(i));
                }
            }
        }
        return result;
    }

    @Override
    public int getCount() {
        return mDatas.size();
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
