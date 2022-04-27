package com.haz.mymusic;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description:
 */

import android.app.Application;
import com.blankj.utilcode.util.Utils;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}
