package com.jzw.jetpack.room;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.jzw.jetpack.dao.IWordDao;
import com.jzw.jetpack.entrys.Word;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jzw
 * @date 2019/12/10
 * @change
 * @describe 数据库room
 **/
@Database(entities = {Word.class}, version = 1)
public abstract class CommonRoomDatabase extends RoomDatabase {

    private static volatile CommonRoomDatabase INSTANCE;

    public static CommonRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (CommonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , CommonRoomDatabase.class, "Word_database").build();
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract IWordDao wordDao();
}
