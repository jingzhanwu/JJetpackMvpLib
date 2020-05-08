package com.jzw.jetpack.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jetpack.mvp.view.AppViewDelegate;
import com.jzw.jetpack.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2019/12/20
 * @change
 * @describe describe
 **/
public class UserView extends AppViewDelegate {
    private RecyclerView recyclerView;
    private Button btn;
    private MyAdapter mAdapter;

    private EditText editText;

    @Override
    public int getRootLayoutId() {
        return R.layout.act_word;
    }

    @Override
    public void initViews() {
        super.initViews();
        editText = get(R.id.etInput);
        recyclerView = get(R.id.recyclerView);
        btn = get(R.id.btnInsert);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyAdapter(new ArrayList<User>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

    }

    public void setUser(List<User> user) {
        System.out.println("本次更新的数据大小：" + user.size());
        mAdapter.setNewData(user);
    }

    /**
     * 返回 用户名
     *
     * @return
     */
    public String getUserName() {
        return editText.getText().toString();
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<User> mData;

        public MyAdapter(List<User> data) {
            mData = data == null ? new ArrayList<User>() : data;
        }

        public void setNewData(List<User> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
            MyAdapter.ViewHolder holder = new MyAdapter.ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
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
