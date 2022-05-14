package com.haz.mymusic.models;

/**
 * @author: hswplus
 * @date: 2022/5/11
 * @Description: 专辑
 */

public class Album {
    public String albumId;
    public String name;
    public String poster;
    public String playNum;
    public String list;

    public Album() {
    }

    public Album(String albumId, String name, String poster, String playNum, String list) {
        this.albumId = albumId;
        this.name = name;
        this.poster = poster;
        this.playNum = playNum;
        this.list = list;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }


}
