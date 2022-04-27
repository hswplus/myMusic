package com.haz.mymusic.activities;
/**
 * @author: hswplus
 * @date: 2022/4/9
 * @Description: 基础的Activities
 */

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.haz.mymusic.R;

public class BaseActivity extends Activity {
    private ImageView mIvBack,mTvMe;
    private TextView mTvTitle;

    /**
     * findViewById
     * @param id 接受资源文件的id
     * @param <T> View的子类
     * @return
     */
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * 初始化NavigationBar
     * @param isShowBar
     * @param title
     * @param isShowMe
     */
    protected void intiNavBar(boolean isShowBar, String title, boolean isShowMe) {
        mIvBack = fd(R.id.iv_back);
        mTvTitle = fd(R.id.tv_title);
        mTvMe = fd(R.id.iv_me);

        mIvBack.setVisibility(isShowBar?View.VISIBLE :View.GONE);
        mTvMe.setVisibility(isShowMe?View.VISIBLE :View.GONE);
        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseActivity.this, MeActivity.class);
                startActivity(intent);

            }
        });
    }
}
