package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/10
 * @Description: 个人页面
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haz.mymusic.R;
import com.haz.mymusic.helps.MediaPlayerHelper;
import com.haz.mymusic.utils.UserUtils;
import com.haz.mymusic.views.PlayMusicView;

public class MeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }

    private void initView() {
        intiNavBar(true,"个人中心",false);
    }

    /**
     * 修改密码点击事件
     * @param view
     */
    public void onChangeClick(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登录按钮
     * @param view
     */
    public void onLogoutClick(View view) {
        UserUtils.Logout(this);
        Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
    }
}