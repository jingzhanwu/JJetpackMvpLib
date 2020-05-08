package com.jzw.jetpack;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jzw.jetpack.mvvm.StudentActivity;
import com.jzw.jetpack.mvp.UserListActivity;

public class MainActivity extends AppCompatActivity {


    private TextView tv;
    private Button btn;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_text);
        btn = findViewById(R.id.btn);
        initLifeObserver();
        initViewModel();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.setText("景占午");
            }
        });

        findViewById(R.id.btnWord)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                        startActivity(intent);

                    }
                });

        findViewById(R.id.btnStudent)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void initLifeObserver() {
        MainLifeCyclerObserver observer = new MainLifeCyclerObserver(getLifecycle(), new MainLifeCyclerObserver.MainCallbak() {
            @Override
            public void toast(String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });

        getLifecycle().addObserver(observer);

    }


    private void initViewModel() {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        viewModel.liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                changeData(s);
            }
        });

        //测试map变换
        viewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                changeData(s);
            }
        });

        //测试switchMap变换
        viewModel.address.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                changeData(s);
            }
        });
    }


    private void changeData(String text) {
        tv.setText(text);
    }
}
