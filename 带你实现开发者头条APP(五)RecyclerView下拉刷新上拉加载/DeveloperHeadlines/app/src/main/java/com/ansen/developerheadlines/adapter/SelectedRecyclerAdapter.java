package com.ansen.developerheadlines.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.entity.SelectedArticle;

/**
 * 精选列表适配器
 *
 * @author ansen
 */
public class SelectedRecyclerAdapter extends RecyclerView.Adapter<SelectedRecyclerAdapter.ViewHolder> {
    private List<SelectedArticle> list;
    private LayoutInflater inflater;

    public SelectedRecyclerAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);

        list = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            SelectedArticle selectedArticle = new SelectedArticle(i, "Android开发666", i, i, "");
            list.add(selectedArticle);
        }
    }

    public void loadMore(int page) {
        for (int i = 0; i < 5; i++) {
            SelectedArticle selectedArticle = new SelectedArticle(i, "第" + page + "页数据", i, i, "");
            list.add(selectedArticle);
        }
    }

    public void getFirst() {
        list.clear();
        initData();
    }


    @Override
    public SelectedRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.fragment_selected_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedRecyclerAdapter.ViewHolder holder, int position) {
        SelectedArticle selectedArticle = list.get(position);
        holder.title.setText(selectedArticle.getTitle());
        holder.like.setText("" + selectedArticle.getLikeNumber());
        holder.comment.setText("" + selectedArticle.getCommentNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView like;
        private TextView comment;

        public ViewHolder(View view) {
            super(view);

            title =  view.findViewById(R.id.tv_title);
            like =  view.findViewById(R.id.tv_like);
            comment =  view.findViewById(R.id.tv_comment);
        }
    }
}


