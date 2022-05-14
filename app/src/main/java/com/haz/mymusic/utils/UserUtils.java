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

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.haz.mymusic.activities.LoginActivity;
import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.helps.UserHelp;
import com.haz.mymusic.models.User;

import java.util.List;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     * @param context
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    public static boolean validateLogin(Context context, String phone, String password) {
        DBAdpter dbAdpter = new DBAdpter(context);
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateUser(dbAdpter, phone, password)) {
            Toast.makeText(context, "账号或密码错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 保存用户登录标记
        boolean isSave = SPUtils.saveUser(context, phone);
        if (!isSave) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 在全局单例类之中，保存用户标记
        UserHelp.getInstance().setPhone(phone);
        return true;
    }


    /**
     * 退出登录
     */
    public static void Logout(Context context) {
        // 删除sp保存的用户标记
        boolean isRemove = SPUtils.removeUser(context);
        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
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

        DBAdpter dbAdpter = new DBAdpter(context);
        dbAdpter.open();
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
        // 1.3输入的手机号是否已经被注册
        if (UserUtils.userExitFromPhone(dbAdpter,phone)) {
            Toast.makeText(context, "该手机号已存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 2.保存用户输入的手机号和密码(MD5加密密码）
        User user = new User();
        user.setPhone(phone);
        user.setPassword(EncryptUtils.encryptMD5ToString(password));

        long result = dbAdpter.saveUser(user);
        if (result > 0) {
            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
//        }
        dbAdpter.close();
        return true;
    }

    /**
     * 根据手机号判断用户是否存在
     * boolean userExitFromPhone(String phone)
     */
    public static boolean userExitFromPhone(DBAdpter dbAdpter ,String phone) {
        boolean result = false;
        // 1. 查询所有的用户信息进行匹配
        List<User> allUser = dbAdpter.getAllUser();
        for (User user : allUser) {
            // 2. 循环进行匹配，如果存在 result= true，并跳出循环
            if (user.getPhone().equals(phone)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 验证用户合法性
     * @param dbAdpter
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateUser(DBAdpter dbAdpter, String phone, String password) {
        dbAdpter.open();
        boolean result = false;
        List<User> user = dbAdpter.getOneUser(phone);
        // 密码正确，返回true
        if (user.get(0).password.equals(EncryptUtils.encryptMD5ToString(password))) {
            return true;
        }
        dbAdpter.close();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     * @param context
     * @return
     */
    public static boolean validateUserLogin(Context context){
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1.数据验证，
     * 1.1原密码是否输入
     * 1.2新密码是否输入，且新密码与确定密码是否相同
     * 1.3原密码是否正确,获取到当前登录用户保存的原密码
     * 2.完成密码修改
     *
     * @return
     */
    public static boolean changePassword(Context context, String oldPassword, String password, String passwordConfirm) {
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        DBAdpter dbAdpter = new DBAdpter(context);
        dbAdpter.open();
        String phone =UserHelp.getInstance().getPhone();
        List<User> user = dbAdpter.getOneUser(phone);
        if (!user.get(0).password.equals(EncryptUtils.encryptMD5ToString(oldPassword))) {
            Toast.makeText(context, "原密码不匹配，请输入正确密码", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            long l = dbAdpter.changePassword(phone, password);
            if (l > 0) {
                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        dbAdpter.close();
        return true;
    }
}
