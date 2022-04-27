package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 专辑列表页面
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.haz.mymusic.R;
import com.haz.mymusic.adapters.MusicListAdapter;

public class AlbumListActivity extends BaseActivity {

    private RecyclerView mRvList;
    private MusicListAdapter mListApdter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        intiView();
    }

    private void intiView() {
        intiNavBar(true,"专辑音乐",false);

        mRvList= fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mListApdter = new MusicListAdapter(this, null);
        mRvList.setAdapter(mListApdter);
    }
}