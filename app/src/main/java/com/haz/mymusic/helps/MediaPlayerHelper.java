package com.haz.mymusic.helps;
/**
 * @author: hswplus
 * @date: 2022/4/11
 * @Description: 音乐播放
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;


public class MediaPlayerHelper {

    private static MediaPlayerHelper instance;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;

    public void setOnMeidaPlayerHelperListener(OnMeidaPlayerHelperListener onMeidaPlayerHelperListener) {
        this.onMeidaPlayerHelperListener = onMeidaPlayerHelperListener;
    }

    private OnMeidaPlayerHelperListener onMeidaPlayerHelperListener;

    public static MediaPlayerHelper getInstance(Context context){
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelper(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 1.setPath：当前需要播放的音乐
     * 2.start：播放音乐
     * 3.pause：暂停播放
     */

    /**
     * 当前需要播放的音乐
     * @param path 需要播放音乐的路径（即要知道放哪一个）
     */
    public void setPath(String path) {
        mPath = path;
        // 1.音乐正在播放，重置音乐播放状态
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
        }
        // 2.设置播放音乐的路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3.准备播放
        mMediaPlayer.prepareAsync();// 异步加载
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (onMeidaPlayerHelperListener != null) {
                    onMeidaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });
    }

    /**
     * 返回正在播放音乐的路径
     * @return mPath
     */
    public String  getPath() {
        return mPath;
    }
    /**
     * 播放音乐
     */
    public void start() {
        if (mMediaPlayer.isPlaying()) {
            return;
        }mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    public interface OnMeidaPlayerHelperListener{
        void  onPrepared(MediaPlayer mediaPlayer);
    }
}
