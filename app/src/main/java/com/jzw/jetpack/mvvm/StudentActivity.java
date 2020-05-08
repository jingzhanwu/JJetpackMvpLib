package com.jzw.jetpack.mvvm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jetpack.mvp.annotation.BindViewModel;
import com.jetpack.mvp.base.BaseActivity;
import com.jzw.jetpack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe describe
 **/
@BindViewModel(StudentViewModel.class)
public class StudentActivity extends BaseActivity<StudentModel, StudentViewModel> {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.act_student;
    }

    @Override
    public void onInitViews(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(new ArrayList<Student>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        viewModel.initUser();
    }

    @Override
    public void onModelChanged(StudentModel model) {
        if (model == null) {
            return;
        }
        mAdapter.setNewData(model.getUsers());
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Student> mData;

        public MyAdapter(List<Student> data) {
            mData = data;
        }

        public void setNewData(List<Student> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
            MyAdapter.ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mData.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvText);
            }
        }
    }

}
