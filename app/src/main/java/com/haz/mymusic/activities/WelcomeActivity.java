package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 欢迎页面
 * 功能：
 *  1.延迟三秒
 *  2.跳转页面
 *
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.haz.mymusic.R;
import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.utils.ReadDataUtils;
import com.haz.mymusic.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        boolean isLogin = UserUtils.validateUserLogin(this);
        // 初始话音乐资源
        DBAdpter dbAdpter= new DBAdpter(this) ;
        dbAdpter.open();
        dbAdpter.removeMusicSource();
        ReadDataUtils.getDateFromJson(this,dbAdpter);
        dbAdpter.close();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.e("WelcomeActivity ", "当前线程：" + Thread.currentThread());
//                toMain();
                if (isLogin) {
                    toMain();
                } else {
                    toLogin();
                }
            }
        },3*1000);
    }


    /**
     * 跳转到MainActivity
     */
    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 跳转到LoginActivity
     */
    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}