package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/10
 * @Description: 更改密码页面
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haz.mymusic.R;
import com.haz.mymusic.utils.UserUtils;
import com.haz.mymusic.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassword,mPassword,mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        initView();
    }

    private void initView() {
        intiNavBar(true, "修改密码", false);
        mOldPassword = fd(R.id.input_old_password);
        mPassword = fd(R.id.input_password);
        mPasswordConfirm = fd(R.id.input_password_confirm);
    }

    /**
     * 修改密码后转跳到登录页面
     */
    public void onChangePasswordClick(View v) {
        String oldPassword = mOldPassword.getInputStr();
        String password = mPassword.getInputStr();
        String passwordConfirm = mPasswordConfirm.getInputStr();
        boolean isChangePassword = UserUtils.changePassword(this, oldPassword, password, passwordConfirm);
        if (isChangePassword) {
            UserUtils.Logout(this);
        }
    }

}