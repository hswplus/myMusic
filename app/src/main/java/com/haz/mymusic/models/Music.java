package com.haz.mymusic.models;
/**
 * @author: hswplus
 * @date: 2022/5/11
 * @Description: 音乐
 */

public class Music {
    public String  musicId;
    public String name;
    public String poster;
    public String path;
    public String author;

    public Music() {
    }

    public Music(String musicId, String name, String poster, String path, String author) {
        this.musicId = musicId;
        this.name = name;
        this.poster = poster;
        this.path = path;
        this.author = author;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
