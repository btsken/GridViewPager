GridViewPager
==============

Custom view whith gridView in viewPager. 

You just inject your baseAdapter and use getComputedList() method to let adpater data match in current page.

Usage
------

```java
 WrapContentViewPager mWrapContentViewPager;
 GridViewPagerAdapter<YOUR_DATA_TYPE> mBaseGridViewPagerAdapter = 
	new GridViewPagerAdapter<String>(this, mDatas) {

		@Override
		public BaseAdapter getBaseAdapter(int pageNum) {
			// return your BaseAdapter, using getComputedList() to handle your data.
		}
	};
 mWrapContentViewPager.setAdapter(mBaseGridViewPagerAdapter);
```

For more demo, seeing MainActivity.java

Screenshots
------------

![port](https://raw.github.com/btsken/GridViewPager/master/screenshot1.png)
![land](https://raw.github.com/btsken/GridViewPager/master/screenshot2.png)

