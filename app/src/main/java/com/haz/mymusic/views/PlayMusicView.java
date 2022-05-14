package com.haz.mymusic.views;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 自定义播放音乐布局
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
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
import com.haz.mymusic.models.Music;
import com.haz.mymusic.services.MusicService;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private Music mMusic;

    private boolean isPlaying,isBindService;
    private View mView;
    private FrameLayout mFlPlayMusic;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;

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
//        mMediaPlayerHelper = MediaPlayerHelper.getInstance(mContext);

        addView(mView);
    }

    /**
     * 设置光盘中显示是音乐封面图片
     */
    public void setMusicIcon() {
        Glide.with(mContext)
                .load(mMusic.getPoster())
                .into(mIvIcon);
    }

    /**
     * 切换播放状态
     */
    private void trigger() {
        if (isPlaying) {
            stopMusic();

        } else {
            playMusic();
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic() {
        isPlaying=true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        startMusicService();
    }

    /**
     * 停止播放音乐
     */
    public void stopMusic() {
        isPlaying=false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        if (mMusicBind != null) {
            mMusicBind.stopMusic();
        }
    }

    public void setMusic(Music music) {
        mMusic = music;
        setMusicIcon();
    }

    /**
     * 启动音乐服务
     */
    private void startMusicService() {
       // 启动Service
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        }else {
            mMusicBind.playMusic();
        }
        // 绑定 Service 当前 Service 未绑定，绑定服务
        if (!isBindService) {
            isBindService = true;
            mContext.bindService(mServiceIntent,conn,Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 接触绑定
     */
    public void destroy() {
        // 如果已经绑定了服务，解除绑定
        if (isBindService) {
            isBindService = false;
            mContext.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicBind = (MusicService.MusicBind) iBinder;
            mMusicBind.setMusic(mMusic);
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

}
