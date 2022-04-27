package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/10
 * @Description: 更改密码页面
 */

import android.os.Bundle;

import com.haz.mymusic.R;

public class ChangePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        initView();
    }

    private void initView() {
        intiNavBar(true,"修改密码",false);
    }

    /**
     * 修改密码后转跳到登录页面
     */
    // onConfirmPasswordClick TODO

}