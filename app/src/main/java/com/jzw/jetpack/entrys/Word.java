package com.jzw.jetpack.entrys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by jzw
 * @date 2019/12/10
 * @change
 * @describe describe
 **/

@Entity(tableName = "word_table")
public class Word{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }

    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
