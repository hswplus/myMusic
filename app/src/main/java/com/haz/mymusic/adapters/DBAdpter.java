package com.haz.mymusic.adapters;
/**
 * @author: hswplus
 * @date: 2022/5/11
 * @Description:
 */

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

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.blankj.utilcode.util.EncryptUtils;
import com.haz.mymusic.models.Album;
import com.haz.mymusic.models.Music;
import com.haz.mymusic.models.User;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: hswplus
 * @date: 2022/5/10
 * @Description: 动态建库
 */

public class DBAdpter {

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;



    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper( Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * 创建用户user表
         */
        private  static final String DB_CREATE_TABLE_USER;
        static {
            DB_CREATE_TABLE_USER = "create table " + DB_TABLE_USER + " ("
                    + KEY_PHONE + " integer primary key, "
                    + KEY_PASSWORD + " text not null)";
        }

        /**
         * 创建专辑album表
         */
        private  static final String DB_CREATE_TABLE_ALBUM;
        static {
            DB_CREATE_TABLE_ALBUM="create table "+ DB_TABLE_ALBUM + " ("
                + KEY_ALBUM_ID + " text, "
                + KEY_ALBUM_NAME +" text, "
                + KEY_ALBUM_POSTER + " text, "
                + KEY_ALBUM_PLAY_NUM + " text, "
                + KEY_ALBUM_LIST + " text)";
        }

        /**
         * 创建muisc表
         */
        private  static final String DB_CREATE_TABLE_MUSIC;
        static {
           DB_CREATE_TABLE_MUSIC="create table "+ DB_TABLE_MUSIC + " ("
                + KEY_MUSIC_ID + " text, "
            + KEY_MUSIC_NAME +" text, "
            + KEY_MUSIC_POSTER + " text, "
            + KEY_MUSIC_PATH + " text, "
            + KEY_MUSIC_AUTHOR + " text)";
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE_TABLE_USER);
            _db.execSQL(DB_CREATE_TABLE_ALBUM);
            _db.execSQL(DB_CREATE_TABLE_MUSIC);

            // 系统默认用户
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_PHONE, "15555555555");
            contentValues.put(KEY_PASSWORD, EncryptUtils.encryptMD5ToString("123"));
            _db.insert(DB_TABLE_USER, null, contentValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int i, int i1) {
            _db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE_USER);
            _db.execSQL("DROP TABLE IF EXISTS "+DB_CREATE_TABLE_ALBUM);
            _db.execSQL("DROP TABLE IF EXISTS "+DB_CREATE_TABLE_MUSIC);
            onCreate(_db);
        }
    }

    public DBAdpter(Context _context) {
        context = _context;
    }

    /**
     * 打开数据库连接
     * @throws SQLiteException
     */
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        if (db != null) {
            db.close();
            db=null;
        }
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    public long saveUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PHONE, user.phone);
        contentValues.put(KEY_PASSWORD, user.password);
        return db.insert(DB_TABLE_USER, null, contentValues);
    }

    /**
     * 修改密码
     * @param phone
     * @param password
     * @return
     */
    public long changePassword(String phone, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASSWORD,EncryptUtils.encryptMD5ToString(password));
        return db.update(DB_TABLE_USER,  contentValues,KEY_PHONE+"="+phone,null);

    }

    /**
     * ConvertToUsers
     * @param cursor
     * @return
     */
    @SuppressLint("Range")
    private List<User> ConvertToUsers(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }

        List<User> userList = new ArrayList<>();
        User user = new User();
        for (int i = 0; i < resultCounts; i++) {
            user.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            userList.add(user);
            cursor.moveToNext();
        }
        return userList;
    }


    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    public List<User> getOneUser(String phone) {
        Cursor query = db.query(DB_TABLE_USER, new String[]{KEY_PHONE, KEY_PASSWORD}, KEY_PHONE + "=" + phone, null, null, null, null);
        return ConvertToUsers(query);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> getAllUser() {
        Cursor results = db.query(DB_TABLE_USER, new String[]{KEY_PHONE, KEY_PASSWORD},
                null, null, null, null, null);
        return ConvertToUsers(results);
    }

    /**
     * 读取资源文件中的数据
     * 保存音乐源数据
     */
    public void saveMusic(Music music) {
//        open();
        ContentValues saveValues = new ContentValues();
        saveValues.put(KEY_MUSIC_ID,music.musicId);
        saveValues.put(KEY_MUSIC_NAME,music.name);
        saveValues.put(KEY_MUSIC_POSTER,music.poster);
        saveValues.put(KEY_MUSIC_PATH,music.path);
        saveValues.put(KEY_MUSIC_AUTHOR,music.author);
        db.insert(DB_TABLE_MUSIC,null,saveValues);
//        close();
    }

    /**
     * 读取资源文件中的数据
     * 保存音乐源数据
     */
    public void saveAlbum(Album album) {
        ContentValues saveValues = new ContentValues();
        saveValues.put(KEY_ALBUM_ID,album.albumId);
        saveValues.put(KEY_ALBUM_NAME,album.name);
        saveValues.put(KEY_ALBUM_POSTER,album.poster);
        saveValues.put(KEY_ALBUM_PLAY_NUM,album.playNum);
        saveValues.put(KEY_ALBUM_LIST, String.valueOf(album.list));
        db.insert(DB_TABLE_ALBUM,null,saveValues);

    }


    //   删除音乐元数据
    public void removeMusicSource(){
        db.delete(DB_TABLE_ALBUM, "1", null);
        db.delete(DB_TABLE_MUSIC, "1", null);
    }


    /**
     * ConvertToAlbums
     * @param cursor
     * @return
     */
    @SuppressLint("Range")
    private List<Album> ConvertToAlbums(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }

        List<Album> albumList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            Album album = new Album();
            album.setAlbumId(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_ID)));
            album.setName(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_NAME)));
            album.setPoster(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_POSTER)));
            album.setPlayNum(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_PLAY_NUM)));
            album.setList(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_LIST)));
            albumList.add(album);
            cursor.moveToNext();
        }
        return albumList;
    }

    /**
     * 根据专辑ID查询专辑
     * @param mAlbumId
     * @return
     */
    public List<Album> getAlbumByAlbumId(String mAlbumId) {
        Cursor results = db.query(DB_TABLE_ALBUM, new String[]{KEY_ALBUM_ID, KEY_ALBUM_NAME,KEY_ALBUM_POSTER,KEY_ALBUM_PLAY_NUM,KEY_ALBUM_LIST},
                KEY_ALBUM_ID +"="+mAlbumId, null, null, null, null);
        return ConvertToAlbums(results);
    }

    /**
     * 获取专辑信息
     * @return
     */
    public List<Album> getAllAlbum() {
        Cursor results = db.query(DB_TABLE_ALBUM, new String[]{KEY_ALBUM_ID, KEY_ALBUM_NAME,KEY_ALBUM_POSTER,KEY_ALBUM_PLAY_NUM,KEY_ALBUM_LIST},
                null, null, null, null, null);
        return ConvertToAlbums(results);
    }


    /**
     * ConvertToMusic
     * @param cursor
     * @return
     */
    @SuppressLint("Range")
    private List<Music> ConvertToMusic(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }

        List<Music> musicList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            Music music = new Music();
            music.setMusicId(cursor.getString(cursor.getColumnIndex(KEY_MUSIC_ID)));
            music.setName(cursor.getString(cursor.getColumnIndex(KEY_MUSIC_NAME)));
            music.setPoster(cursor.getString(cursor.getColumnIndex(KEY_MUSIC_POSTER)));
            music.setPath(cursor.getString(cursor.getColumnIndex(KEY_MUSIC_PATH)));
            music.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_MUSIC_AUTHOR)));
            musicList.add(music);
            cursor.moveToNext();
        }
        return musicList;
    }

    /**
     * 根据音乐ID查询音乐信息
     * @param
     * @return
     */
    public List<Music> getMusicByMusicId (String musicId ) {
        Cursor query = db.query(DB_TABLE_MUSIC, new String[]{KEY_MUSIC_ID, KEY_MUSIC_NAME,KEY_MUSIC_POSTER,KEY_MUSIC_PATH,KEY_MUSIC_AUTHOR}, KEY_MUSIC_ID + "=" + musicId, null, null, null, null);
        return ConvertToMusic(query);
    }

    /**
     * 获取音乐源信息 前14个
     * @return
     */
    public List<Music> getMusicList() {
        Cursor results = db.query(DB_TABLE_MUSIC, new String[]{KEY_MUSIC_ID, KEY_MUSIC_NAME,KEY_MUSIC_POSTER,KEY_MUSIC_PATH,KEY_MUSIC_AUTHOR},
                KEY_MUSIC_ID + "<=" +14, null, null, null, null);
        return ConvertToMusic(results);
    }
    /**
     * 获取音乐源信息
     * @return
     */
    public List<Music> getAllMusic() {
        Cursor results = db.query(DB_TABLE_MUSIC, new String[]{KEY_MUSIC_ID, KEY_MUSIC_NAME,KEY_MUSIC_POSTER,KEY_MUSIC_PATH,KEY_MUSIC_AUTHOR},
                null, null, null, null, null);
        return ConvertToMusic(results);
    }
}
