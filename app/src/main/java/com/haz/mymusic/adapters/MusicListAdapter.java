package com.haz.mymusic.adapters;
/**
 * @author: hswplus
 * @date: 2022/4/10
 * @Description: 音乐列表适配器
 */

import static com.haz.mymusic.activities.PlayMusicActivity.MUSIC_ID;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haz.mymusic.R;
import com.haz.mymusic.activities.PlayMusicActivity;
import com.haz.mymusic.models.Music;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcautionRyHeight;
    List<Music> allMusic;
    public MusicListAdapter(Context context, RecyclerView recyclerView, List<Music> allMusic) {
        mContext = context;
        mRv = recyclerView;
        this.allMusic = allMusic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mItemView=LayoutInflater.from(mContext).inflate(R.layout.item_list_music, viewGroup, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public int getItemCount() {
        return allMusic.size();
    }

    /**
     * 1.获取ItemView的高度
     * 2.itemView的数量
     * 3.使用itemViewHeight * itemViewNum = RecyclerView的高度
     */
    private void setRecyclerViewHeight() {
        if (isCalcautionRyHeight || mRv == null) {
            return;
        }
        isCalcautionRyHeight = true;
        // 1.获取ItemView的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        // 2.itemView的数量
        int itemCount = getItemCount();
        // 3.使用itemViewHeight * itemViewNum = RecyclerView的高度
        int recyclerViewHeight = itemViewLp.height * itemCount;
        // 4.设置RecyclerView高度
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height= recyclerViewHeight;
        mRv.setLayoutParams(rvLp);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
       Music music= allMusic.get(position);
        setRecyclerViewHeight();
        Glide.with(mContext)
                .load(music.getPoster())
                .into(viewHolder.ivIcon);

        viewHolder.mTvMusicName.setText(music.name);
        viewHolder.mTvMusicAuthor.setText(music.author);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(MUSIC_ID, music.musicId);
                mContext.startActivity(intent);
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        TextView mTvMusicName,mTvMusicAuthor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView=itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvMusicName = itemView.findViewById(R.id.tv_music_name);
            mTvMusicAuthor = itemView.findViewById(R.id.tv_music_author);
        }
    }
}
