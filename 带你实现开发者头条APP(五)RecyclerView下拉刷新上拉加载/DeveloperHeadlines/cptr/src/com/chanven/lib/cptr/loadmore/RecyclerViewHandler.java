package com.chanven.lib.cptr.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

public class RecyclerViewHandler implements ViewHandler {
    @Override
    public boolean handleSetAdapter(View contentView, ILoadViewMoreFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        final RecyclerAdapterWithHF adapter = (RecyclerAdapterWithHF) recyclerView.getAdapter();
        if (loadMoreView != null){
            final Context context = recyclerView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadViewMoreFactory.FootViewAdder() {
                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId,recyclerView,false);
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    adapter.addFooter(view);
                    return view;
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScollBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();//滚动到底部
                }
            }
        }

        /**
         * 是否滚动到底部
         * @param recyclerView
         * @return
         */
        private boolean isScollBottom(RecyclerView recyclerView) {
            return !isCanScollVertically(recyclerView);
        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            if (android.os.Build.VERSION.SDK_INT < 14) {
                return ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
            } else {
                return ViewCompat.canScrollVertically(recyclerView, 1);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

    }

}
