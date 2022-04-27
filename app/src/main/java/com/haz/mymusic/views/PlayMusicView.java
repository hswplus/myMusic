package com.haz.mymusic.views;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 自定义播放音乐布局
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.haz.mymusic.R;
import com.haz.mymusic.helps.MediaPlayerHelper;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private boolean isPlaying;
    private View mView;
    private String mPath;
    private FrameLayout mFlPlayMusic;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;
    private MediaPlayerHelper mMediaPlayerHelper;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        // 调用init方法初始化布局
        init(context);
    }



    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        mContext=context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);
        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger();
            }
        });
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        /**
         * 1.定义所需执行的动画
         *     1.1光盘转动的动画
         *     1.2指针指向光盘的动画
         *     1.3指针离开光盘的动画
         * 2. startAnimation
         */
        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_music_animation);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.stop_needle_anim);
        mMediaPlayerHelper = MediaPlayerHelper.getInstance(mContext);

        addView(mView);
    }

    /**
     * 设置光盘中显示是音乐封面图片
     */
    public void setMusicIcon(String icon) {
        Glide.with(mContext)
                .load(icon)
                .into(mIvIcon);
    }

    /**
     * 切换播放状态
     */
    private void trigger() {
        if (isPlaying) {
            stopMusic();

        } else {
            playMusic(mPath);
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic(String path) {
        mPath = path;
        isPlaying=true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);
        // 1.判断当前音乐是否是为 已经在播放的音乐A
        // 2.如果当前是播放的音乐A的话，直接执行start方法
        // 3.如果当前不是播放的音乐A的话，就调用setPath方法,播放音乐B
        if (mMediaPlayerHelper.getPath()!=null && mMediaPlayerHelper.getPath().equals(path)) {
            mMediaPlayerHelper.start();
        } else {
            mMediaPlayerHelper.setPath(path);
            mMediaPlayerHelper.setOnMeidaPlayerHelperListener(new MediaPlayerHelper.OnMeidaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayerHelper.start();
                }
            });
        }


    }

    /**
     * 停止播放音乐
     */
    public void stopMusic() {
        isPlaying=false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);
        mMediaPlayerHelper.pause();
    }
}
