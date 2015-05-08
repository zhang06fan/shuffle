package com.shuffle;

import android.app.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class AlbumExplorerActivity extends Activity {
    private ViewPager mAlbumArtPager;
    private TextView mArtistText;
    private TextView mAlbumText;
    private ListView mLeftDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView[] mAlbumArts;
    private String[] mText = new String[] {
            "高圆圆1", "高圆圆2", "高圆圆3",
            "高圆圆4", "高圆圆5"
    };
    private int mCurrentSelectedPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_explorer);

        mAlbumArtPager = (ViewPager) findViewById(R.id.album_art_pager);
        mArtistText = (TextView) findViewById(R.id.artist_name);
        mAlbumText = (TextView) findViewById(R.id.album_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (ListView) findViewById(R.id.left_drawer);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.setDrawerListener(new MyDrawerListener());
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        mAlbumArts = new ImageView[5];
        int[] ids = new int[] {
                R.drawable.item01, R.drawable.item02,
                R.drawable.item03, R.drawable.item04, R.drawable.item06
        };
        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(ids[i]);
            mAlbumArts[i] = iv;
        }

        mAlbumArtPager.setAdapter(new AlbumArtPagerAdapter());
        mAlbumArtPager.setOnPageChangeListener(new AlbumArtPageChangeListener());
        mAlbumArtPager.setCurrentItem(5);

        mLeftDrawer.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                }));
        mLeftDrawer.setItemChecked(mCurrentSelectedPosition, true);
        mLeftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_explorer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AlbumArtPagerAdapter extends PagerAdapter {
        public AlbumArtPagerAdapter() {

        }

        @Override
        public int getCount() {
            // Log.d("fengjie", "getCount");
            return mAlbumArts.length * 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mAlbumArts[position % 5]);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mAlbumArts[position % 5]);
            return mAlbumArts[position % 5];
        }
    }

    private class AlbumArtPageChangeListener
            implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            mArtistText.setText(mText[arg0 % 5]);
            mAlbumText.setText(mText[arg0 % 5]);
        }
    }

    private class MyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerClosed(View arg0) {
            mActionBarDrawerToggle.onDrawerClosed(arg0);
        }

        @Override
        public void onDrawerOpened(View arg0) {
            mActionBarDrawerToggle.onDrawerOpened(arg0);
        }

        @Override
        public void onDrawerSlide(View arg0, float arg1) {
            mActionBarDrawerToggle.onDrawerSlide(arg0, arg1);
        }

        @Override
        public void onDrawerStateChanged(int arg0) {
            mActionBarDrawerToggle.onDrawerStateChanged(arg0);
        }
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mLeftDrawer != null) {
            mLeftDrawer.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mLeftDrawer);
        }
    }

}
