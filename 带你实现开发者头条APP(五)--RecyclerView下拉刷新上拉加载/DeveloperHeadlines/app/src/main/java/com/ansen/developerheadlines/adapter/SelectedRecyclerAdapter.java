package com.ansen.developerheadlines.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.entity.SelectedArticle;

/**
 * 精选列表适配器
 *
 * @author ansen
 */
public class SelectedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SelectedArticle> selectedArticles;
    private LayoutInflater inflater;

    public SelectedRecyclerAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);

        selectedArticles = new ArrayList<SelectedArticle>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            SelectedArticle selectedArticle = new SelectedArticle(i, "Android开发666", i, i, "");
            selectedArticles.add(selectedArticle);
        }
    }

    public void loadMore(int page) {
        for (int i = 0; i < 5; i++) {
            SelectedArticle selectedArticle = new SelectedArticle(i, "第" + page + "页数据", i, i, "");
            selectedArticles.add(selectedArticle);
        }
    }

    public void getFirst() {
        selectedArticles.clear();
        initData();
    }

    @Override
    public int getItemCount() {
        return selectedArticles.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SelectedRecyclerHolder holder = (SelectedRecyclerHolder) viewHolder;

        SelectedArticle selectedArticle = selectedArticles.get(position);
        holder.title.setText(selectedArticle.getTitle());
        holder.like.setText("" + selectedArticle.getLikeNumber());
        holder.comment.setText("" + selectedArticle.getCommentNumber());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = inflater.inflate(R.layout.fragment_selected_item, null);
        return new SelectedRecyclerHolder(view);
    }

    public class SelectedRecyclerHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView like;
        private TextView comment;

        public SelectedRecyclerHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            like = (TextView) view.findViewById(R.id.tv_like);
            comment = (TextView) view.findViewById(R.id.tv_comment);
        }
    }
}


