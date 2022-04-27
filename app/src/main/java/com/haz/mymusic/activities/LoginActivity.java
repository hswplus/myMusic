package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 登录页面
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haz.mymusic.R;
import com.haz.mymusic.utils.UserUtils;
import com.haz.mymusic.views.InputView;

//
public class LoginActivity extends BaseActivity {

    private InputView mInputPhone,mInputPasswprd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        intiNavBar(false,"登录",false);
        mInputPhone = fd(R.id.input_phone);
        mInputPasswprd = fd(R.id.input_password);

    }

    /**
     * 跳转注册页面点击事件
     */
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录按钮点击事件
     */
    public void onCommitClick(View view) {

        String phone = mInputPhone.getInputStr();
        String password = mInputPasswprd.getInputStr();
        // 验证用户输入是否合法
        if (!UserUtils.validateLogin(this, phone, password)) {
            return;
        }
        // 跳转到应用主页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}