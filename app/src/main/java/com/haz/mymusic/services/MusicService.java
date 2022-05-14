package com.haz.mymusic.services;
/**
 * @author: hswplus
 * @date: 2022/5/14
 * @Description: 通过Service连接PlayMusicView 和 MediaPlayHelper
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.haz.mymusic.helps.MediaPlayerHelper;
import com.haz.mymusic.models.Music;

/**
 * 1、PlayMusicView MediaPlayHelper
 *    1.1 播放音乐、暂停音乐
 *    1.2 启动Service、绑定Service、解除绑定Service
 * 2、MediaPlayHelperMediaPlayHelper
 *    1.1 播放音乐、暂停音乐
 *    1.2 监听音乐播放完成，停止Service
 */
public class MusicService extends Service {
    private MediaPlayerHelper mMediaPlayerHelper;
    private Music mMusic;

    public MusicService() {
    }

    public class MusicBind extends Binder {
        // 设置音乐
        public void setMusic(Music music) {
            mMusic = music;
        }

        // 播放音乐
        public void playMusic() {
            // 1.判断当前音乐是否是为 已经在播放的音乐A
            // 2.如果当前是播放的音乐A的话，直接执行start方法
            // 3.如果当前不是播放的音乐A的话，就调用setPath方法,播放音乐B
            if (mMediaPlayerHelper.getPath()!=null
                    && mMediaPlayerHelper.getPath().equals(mMusic.getPath())) {
                mMediaPlayerHelper.start();
            } else {
                mMediaPlayerHelper.setPath(mMusic.getPath());
                mMediaPlayerHelper.setOnMeidaPlayerHelperListener(new MediaPlayerHelper.OnMeidaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mMediaPlayerHelper.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopSelf();
                    }
                });
            }
        }

        // 暂停音乐
        public void stopMusic() {
            mMediaPlayerHelper.pause();
        }

    }
    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerHelper = MediaPlayerHelper.getInstance(this);
    }

    /**
     * 系统默认不允许不可见的后台服务播放音乐，
     * Notification
     */
}