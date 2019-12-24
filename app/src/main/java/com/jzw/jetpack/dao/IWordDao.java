package com.jzw.jetpack.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jzw.jetpack.entrys.Word;

import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jzw
 * @date 2019/12/10
 * @change
 * @describe 数据访问接口，必须是接口或者抽象类
 * 默认情况下，所有查询必须在独立的线程
 **/
@Dao
public interface IWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Query("select * from word_table order by word asc")
    LiveData<List<Word>> queryAll();

    @Query("delete from word_table")
    void deleteAll();
}
