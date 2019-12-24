package com.jzw.jetpack.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;

import com.jzw.jetpack.dao.IWordDao;
import com.jzw.jetpack.entrys.Word;
import com.jzw.jetpack.room.CommonRoomDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jzw
 * @date 2019/12/10
 * @change
 * @describe Word 的数据管理类
 **/
public class WordRepository {

    private IWordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    private CompositeDisposable compositeDisposable;

    public WordRepository(Application application) {
        CommonRoomDatabase db = CommonRoomDatabase.getDatabase(application);
        compositeDisposable = new CompositeDisposable();
        mWordDao = db.wordDao();
        mAllWords = mWordDao.queryAll();
    }

    public void destory() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(final Word word) {
        compositeDisposable.add(Observable.create(new ObservableOnSubscribe<Word>() {
                    @Override
                    public void subscribe(ObservableEmitter<Word> emitter) throws Exception {
                        mWordDao.insert(word);
                        emitter.onNext(word);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Word>() {
                            @Override
                            public void accept(Word word) throws Exception {
                                System.out.println("插入成功:" + word.getWord());
                            }
                        })
        );
    }
}
