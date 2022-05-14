package com.haz.mymusic.adapters;
/**
 * @author: hswplus
 * @date: 2022/4/10
 * @Description: 网格适配器
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haz.mymusic.R;
import com.haz.mymusic.activities.AlbumListActivity;
import com.haz.mymusic.models.Album;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {

    private Context mContext;
    private List<Album> allAlbum;

    public MusicGridAdapter(Context context, List<Album> allAlbum) {

        mContext = context;
        this.allAlbum = allAlbum;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Album album = allAlbum.get(position);
            Glide.with(mContext)
                    .load(album.getPoster())
                    .into(viewHolder.ivIcon);

        viewHolder.mTvPlayNum.setText(album.playNum);
        viewHolder.mTvName.setText(album.name);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AlbumListActivity.class);
                    intent.putExtra(AlbumListActivity.ALBUM_ID, album.getAlbumId());
                    mContext.startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return  allAlbum.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        View itemView;
        TextView mTvPlayNum,mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView=itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvPlayNum = itemView.findViewById(R.id.tv_play_num);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
