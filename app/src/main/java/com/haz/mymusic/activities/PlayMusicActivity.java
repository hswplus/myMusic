package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 播放音乐页面
 */

import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_ID;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.haz.mymusic.R;
import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.models.Music;
import com.haz.mymusic.views.PlayMusicView;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    public static final String MUSIC_ID = KEY_MUSIC_ID;

    private ImageView mIvBg;
    private PlayMusicView mPlayMusicView;
    TextView mTvMusicName,mTvMusicAuthor;
    DBAdpter dbAdpter = new DBAdpter(this);

    List<Music> musicList;
    Music music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        // 隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();
    }

    private void initData() {
        dbAdpter.open();
        String mMusicId = getIntent().getStringExtra(MUSIC_ID);
        musicList = dbAdpter.getMusicByMusicId(mMusicId);
        music = musicList.get(0);

    }

    private void initView() {
        mIvBg = fd(R.id.iv_bg);
        mTvMusicName = fd(R.id.tv_music_name);
        mTvMusicAuthor = fd(R.id.tv_music_author);
        // glide-transformations
        Glide.with(this)
                .load(music.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10) {
                }))
                .into(mIvBg);
        mPlayMusicView = fd(R.id.play_music_view);

        mPlayMusicView.setMusic(music);
        mTvMusicName.setText(music.name);
        mTvMusicAuthor.setText(music.author);
//        mPlayMusicView.setMusicIcon(music.getPoster());
        mPlayMusicView.playMusic();
    }
    /**
     * 后退按钮点击事件
     */
    public void onBackClick(View view) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayMusicView.destroy();
        dbAdpter.close();
    }
}