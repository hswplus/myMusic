package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 专辑列表页面
 */

import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_ID;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.haz.mymusic.R;
import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.adapters.MusicListAdapter;
import com.haz.mymusic.models.Album;
import com.haz.mymusic.models.Music;

import java.util.ArrayList;
import java.util.List;

public class AlbumListActivity extends BaseActivity {

    public static final String ALBUM_ID = KEY_ALBUM_ID;
    private RecyclerView mRvList;
    private MusicListAdapter mListAdapter;
    private String mAlbumId;
    DBAdpter dbAdpter = new DBAdpter(this);

    List<Album> album;
    int listLen;
    List<Music> musicList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        initData();
        intiView();
    }

    private void initData() {
        dbAdpter.open();
        mAlbumId = getIntent().getStringExtra(ALBUM_ID);
        album=dbAdpter.getAlbumByAlbumId(mAlbumId);
        listLen= album.get(0).list.split(",").length;
        String[] musicIdList = new String[listLen];
        // 获取专辑数组列表
        musicIdList=album.get(0).list.split(",");
        for (String musicId : musicIdList) {
            Music music = new Music();
            List<Music> musicByMusicId = dbAdpter.getMusicByMusicId(musicId);
            music.setMusicId(musicByMusicId.get(0).getMusicId());
            music.setName(musicByMusicId.get(0).getName());
            music.setPath(musicByMusicId.get(0).getPath());
            music.setPoster(musicByMusicId.get(0).getPoster());
            music.setAuthor(musicByMusicId.get(0).getAuthor());
            musicList.add(music);
        }
    }

    private void intiView() {
        intiNavBar(true,"专辑音乐",false);

        mRvList= fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mListAdapter = new MusicListAdapter(this, null,musicList);
        mRvList.setAdapter(mListAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdpter.close();

    }
}