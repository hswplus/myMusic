package com.haz.mymusic.utils;

/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 用户工具类
 */

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.haz.mymusic.activities.LoginActivity;
import com.haz.mymusic.models.UserModel;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     * @param context
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    public static boolean validateLogin(Context context, String phone, String password) {

        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 退出登录
     */
    public static void Logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        // 添加intent标识符，清理task栈，并且新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 注册用户
     * @param context 上下文
     * @param phone 手机号码
     * @param password 密码
     * @param passwordConfirm 确认密码
     */
    public static boolean registerUser(Context context, String phone, String password, String passwordConfirm) {
        // 1.用户输入合法性验证
        // 1.1用户输入的手机号是否合法
       if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 1.2用户是否输入了密码和确认密码，并且这两次输入是否相同
        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false ;
        }
        // 1.3输入的手机号是否已经被注册 TODO

        // 2.保存用户输入的手机号和密码(MD5加密密码）
        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(password);

//        saveUser(userModel);
        return true;
    }

    /**
     * 保存用户到数据库
     *
     */

}
