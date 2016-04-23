package com.ansen.developerheadlines.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ansen.developerheadlines.R;
import com.ansen.developerheadlines.entity.SelectedArticle;

/**
 * 精选列表适配器
 *
 * @author ansen
 */
public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedViewHolder> {
    private List<SelectedArticle> selectedArticles;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;//头文件View

    public SelectedAdapter() {
        selectedArticles = new ArrayList<SelectedArticle>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            SelectedArticle selectedArticle = new SelectedArticle(i, "Android开发666", i, i, "");
            selectedArticles.add(selectedArticle);
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null)
            return TYPE_NORMAL;
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public SelectedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        View view = LayoutInflater.from(context).inflate(R.layout.fragment_selected_item, parent, false);
//        return new SelectedViewHolder(view);

        if (mHeaderView != null && viewType == TYPE_HEADER){
            return new SelectedViewHolder(mHeaderView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_selected_item, parent, false);
        return new SelectedViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(SelectedViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_HEADER)
            return;

        SelectedArticle selectedArticle = selectedArticles.get(position);
        holder.title.setText(selectedArticle.getTitle());
        holder.like.setText("" + selectedArticle.getLikeNumber());
        holder.comment.setText("" + selectedArticle.getCommentNumber());
    }

    @Override
    public long getItemId(int position) {
        return selectedArticles.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return selectedArticles.size();
    }

    class SelectedViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView like;
        private TextView comment;

        public SelectedViewHolder(View view) {
            super(view);
            if(itemView == mHeaderView)
                return;
            title = (TextView) view.findViewById(R.id.tv_title);
            like = (TextView) view.findViewById(R.id.tv_like);
            comment = (TextView) view.findViewById(R.id.tv_comment);
        }
    }

}
