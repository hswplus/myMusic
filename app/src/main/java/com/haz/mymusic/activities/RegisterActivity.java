package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 注册页面
 */


import android.os.Bundle;
import android.view.View;

import com.haz.mymusic.R;
import com.haz.mymusic.utils.UserUtils;
import com.haz.mymusic.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword,mInputPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        intiNavBar(true,"注册",false);
        mInputPhone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);
        mInputPasswordConfirm = fd(R.id.input_password_confirm);

    }

    /**
     * 注册按钮点击事件
     * onRegister4Click
     * 1. 用户输入合法点击事件
     *    1.1用户输入的手机号是否合法
     *    1.2用户是否输入了密码和确认密码，并且这两次输入是否相同
     *    1.3输入的手机号是否已经被注册 TODO
     * 2.保存用户输入的手机号和密码(MD5加密密码）
     */
    public void onRegisterClick(View view) {

        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this, phone, password, passwordConfirm);
        if (!result) {
            return;
        }
        // 注册成功后后退到登录页面
        onBackPressed();

    }
}