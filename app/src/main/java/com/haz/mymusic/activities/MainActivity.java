package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 主页面
 */

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haz.mymusic.R;
import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.adapters.MusicGridAdapter;
import com.haz.mymusic.adapters.MusicListAdapter;
import com.haz.mymusic.models.Album;
import com.haz.mymusic.models.Music;
import com.haz.mymusic.views.GridSpaceItemDecoration;

import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid,mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;
    DBAdpter dbAdpter = new DBAdpter(this);

    List<Album> allAlbum;
    List<Music> allMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        dbAdpter.open();
        allAlbum = dbAdpter.getAllAlbum();
        allMusic = dbAdpter.getMusicList();
    }

    private void initView() {
        intiNavBar(false,"墨客音乐",true);

        mRvGrid = fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize), mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter = new MusicGridAdapter(this,allAlbum);
        mRvGrid.setAdapter(mGridAdapter);

        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter = new MusicListAdapter(this,mRvList ,allMusic);
        mRvList.setAdapter(mListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdpter.close();

    }
}