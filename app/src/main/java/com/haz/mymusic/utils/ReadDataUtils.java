package com.haz.mymusic.utils;


import static com.haz.mymusic.constants.TableConstant.DB_NAME;
import static com.haz.mymusic.constants.TableConstant.DB_TABLE_ALBUM;
import static com.haz.mymusic.constants.TableConstant.DB_TABLE_MUSIC;
import static com.haz.mymusic.constants.TableConstant.DB_TABLE_USER;
import static com.haz.mymusic.constants.TableConstant.DB_VERSION;
import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_ID;
import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_LIST;
import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_NAME;
import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_PLAY_NUM;
import static com.haz.mymusic.constants.TableConstant.KEY_ALBUM_POSTER;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_AUTHOR;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_ID;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_NAME;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_PATH;
import static com.haz.mymusic.constants.TableConstant.KEY_MUSIC_POSTER;
import static com.haz.mymusic.constants.TableConstant.KEY_PASSWORD;
import static com.haz.mymusic.constants.TableConstant.KEY_PHONE;
import android.content.Context;

import com.haz.mymusic.adapters.DBAdpter;
import com.haz.mymusic.models.Album;
import com.haz.mymusic.models.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadDataUtils {
    /**
     * 读取资源文件中的数据
     */
    public static void getDateFromJson(Context context, DBAdpter dbAdpter) {

//        DBAdpter dbAdpter = new DBAdpter(context);
//        dbAdpter.open();
        Music music = new Music();
        Album album = new Album();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("DataSource.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            JSONObject object = new JSONObject(builder.toString());//builder读取了JSON中的数据。
            String arrayName = "list";

            JSONArray albumArray = object.getJSONArray("album");    //得到为json的数组
            for (int i = 0; i < albumArray.length(); i++) {

                JSONObject subObject = albumArray.getJSONObject(i);//得到为json的数组

                album.setAlbumId(subObject.getString(KEY_ALBUM_ID));
                album.setName(subObject.getString(KEY_ALBUM_NAME));
                album.setPoster(subObject.getString(KEY_ALBUM_POSTER));
                album.setPlayNum(subObject.getString(KEY_ALBUM_PLAY_NUM));

                JSONArray listArray = subObject.getJSONArray(KEY_ALBUM_LIST);//得到为json的数组
                String musicIdStr= new String();
                for (int j = 0; j < listArray.length(); j++) {
                    JSONObject list = listArray.getJSONObject(j);
                    music.setMusicId(list.getString(KEY_MUSIC_ID));
                    music.setName(list.getString(KEY_MUSIC_NAME));
                    music.setPoster(list.getString(KEY_MUSIC_POSTER));
                    music.setPath(list.getString(KEY_MUSIC_PATH));
                    music.setAuthor(list.getString(KEY_MUSIC_AUTHOR));
                    if (j < listArray.length() - 1) {
                        musicIdStr += list.getInt(KEY_MUSIC_ID) + ",";
                    } else {
                        musicIdStr += list.getInt(KEY_MUSIC_ID);
                    }
                    dbAdpter.saveMusic(music);
                }
                album.setList(musicIdStr);
                dbAdpter.saveAlbum(album);
            }

            JSONArray hotArray = object.getJSONArray("hot");    //得到为json的数组
            for (int i = 0; i < hotArray.length(); i++) {
                JSONObject hot = hotArray.getJSONObject(i);
                music.setMusicId(hot.getString(KEY_MUSIC_ID));
                music.setName(hot.getString(KEY_MUSIC_NAME));
                music.setPoster(hot.getString(KEY_MUSIC_POSTER));
                music.setPath(hot.getString(KEY_MUSIC_PATH));
                music.setAuthor(hot.getString(KEY_MUSIC_AUTHOR));
                dbAdpter.saveMusic(music);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}