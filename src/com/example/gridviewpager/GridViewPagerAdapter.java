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
import android.widget.BaseAdapter;
import android.widget.GridView;

public abstract class GridViewPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mDatas;
    private OnGridItemClickListener mOnGridItemClickListener;
    private OnSizeChangeListener mOnSizeChangeListener;

    private int horizontalNum = 3; // default value
    private int verticalNum = 2; // default value
    private int pageItemNum = 6; // default value

    public interface OnGridItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id, Object o);
    }

    public interface OnSizeChangeListener {
        void onSizeChange(int verticalNum);
    }

    public void setOnGridItemClickListener(OnGridItemClickListener l) {
        mOnGridItemClickListener = l;
    }

    public GridViewPagerAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    public abstract BaseAdapter getBaseAdapter(int currentPageNum);

    public void setOnSizeChangeListener(OnSizeChangeListener l) {
        mOnSizeChangeListener = l;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int currentPageNum) {
        GridView gv = new GridView(mContext);
        gv.setNumColumns(horizontalNum);
        final BaseAdapter adapter = getBaseAdapter(currentPageNum);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnGridItemClickListener != null) {
                    mOnGridItemClickListener.onItemClick(parent, view, position, id,
                            adapter.getItem(position));
                }
            }
        });
        ((ViewPager) container).addView(gv);
        return gv;
    }

    public List<T> getComputedList(int currentPageNum) {
        int totalNum = getDataSize();
        List<T> result = new ArrayList<T>();

        if (totalNum >= (currentPageNum + 1) * pageItemNum) {
            // totalNum bigger than pageItemNum
            for (int i = currentPageNum * pageItemNum; i < (currentPageNum * pageItemNum)
                    + pageItemNum; i++) {
                result.add(mDatas.get(i));
            }
        } else {

            for (int i = currentPageNum * pageItemNum; i < (currentPageNum * pageItemNum)
                    + pageItemNum; i++) {

                if (i < (totalNum % pageItemNum) + (currentPageNum * pageItemNum)) {
                    result.add(mDatas.get(i));
                }
            }
        }
        return result;
    }

    /**
     * 
     * @return total data size
     */
    private int getDataSize() {
        return mDatas.size();
    }

    /**
     * ViewPager total page size
     */
    @Override
    public int getCount() {
        return (int) Math.ceil(getDataSize() / (double) pageItemNum);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewGroup) container).removeView((View) object);
        object = null;
    }

    private void updatePageItemNumber() {
        pageItemNum = horizontalNum * verticalNum;
        mOnSizeChangeListener.onSizeChange(verticalNum);
    }

    public int getHorizontalNum() {
        return horizontalNum;
    }

    public void setHorizontal(int horizontalNum) {
        this.horizontalNum = horizontalNum;
        updatePageItemNumber();
    }

    public void setHorizontalAndVerticalNum(int horizontalNum, int verticalNum) {
        this.horizontalNum = horizontalNum;
        this.verticalNum = verticalNum;
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
