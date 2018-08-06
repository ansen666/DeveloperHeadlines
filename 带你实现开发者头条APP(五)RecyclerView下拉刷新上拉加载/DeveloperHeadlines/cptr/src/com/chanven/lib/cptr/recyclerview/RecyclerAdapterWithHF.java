package com.chanven.lib.cptr.recyclerview;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class RecyclerAdapterWithHF extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_MANAGER_OTHER = 0;
    public static final int TYPE_MANAGER_LINEAR = 1;
    public static final int TYPE_MANAGER_GRID = 2;
    public static final int TYPE_MANAGER_STAGGERED_GRID = 3;

    public static final int TYPE_HEADER = 7898;
    public static final int TYPE_FOOTER = 7899;
    public static final int TYPE_CAROUSE = 7900;

    private List<View> mHeaders = new ArrayList<View>();
    private List<View> mFooters = new ArrayList<View>();
    private List<View> mCarouse = new ArrayList<View>();//保存轮播View

    //可以添加轮播View
    public void addCarouse(View view){
        mCarouse.add(view);
    }

    private int mManagerType;

    public int getHeadSize() {
        return mHeaders.size();
    }

    public int getFootSize() {
        return mFooters.size();
    }

    public int getManagerType() {
        return mManagerType;
    }

    public void notifyDataSetChangedHF() {
        notifyDataSetChanged();
    }

    public void notifyItemChangedHF(int position) {
        notifyItemChanged(getRealPosition(position));
    }

    public void notifyItemMovedHF(int fromPosition, int toPosition) {
        notifyItemMovedHF(getRealPosition(fromPosition), getRealPosition(toPosition));
    }

    public void notifyItemRangeChangedHF(int positionStart, int itemCount) {
        notifyItemRangeChanged(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRangeRemovedHF(int positionStart, int itemCount) {
        notifyItemRangeRemoved(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRemovedHF(int position) {
        notifyItemRemoved(getRealPosition(position));
    }

    public void notifyItemInsertedHF(int position) {
        notifyItemInserted(getRealPosition(position));
    }

    public void notifyItemRangeInsertedHF(int positionStart, int itemCount) {
        notifyItemRangeInserted(getRealPosition(positionStart), itemCount);
    }

    @Override
    public final long getItemId(int position) {
        return getItemIdHF(getRealPosition(position));
    }

    public long getItemIdHF(int position) {
        return mAdapter.getItemId(position);
    }

    public RecyclerView.ViewHolder onCreateViewHolderHF(ViewGroup viewGroup, int type) {
        return mAdapter.onCreateViewHolder(viewGroup, type);
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        //正常Item
        if (type != TYPE_HEADER && type != TYPE_FOOTER && type != TYPE_CAROUSE) {
            ViewHolder vh = onCreateViewHolderHF(viewGroup, type);
            return vh;
        } else {//header/footer或者轮播图
            //创建FrameLayout
            FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
            //FrameLayout设置LayoutParams，宽高都匹配父类
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    @Override
    public final void onBindViewHolder(final RecyclerView.ViewHolder vh, int position){
        // 检查当前下标是什么类型数据
        if (isHeader(position)){//header类型
            View v = mHeaders.get(position);
            prepareHeaderFooter((HeaderFooterViewHolder) vh, v);//显示header类型
        }else if(mCarouse.size()>0&&position==mHeaders.size()){//轮播图类型
//            System.out.println("有多少个头View:"+mHeaders.size()+"值等于多少:"+(mHeaders.size()-1));
            View v = mCarouse.get(mHeaders.size());//取出轮播的View
            prepareHeaderFooter((HeaderFooterViewHolder) vh, v);//显示轮播图类型
        } else if (isFooter(position)) {//footer类型
            View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
            prepareHeaderFooter((HeaderFooterViewHolder) vh, v);//显示footer类型
        } else {
            vh.itemView.setOnClickListener(new MyOnClickListener(vh));
            vh.itemView.setOnLongClickListener(new MyOnLongClickListener(vh));
            //正常的Item显示
            onBindViewHolderHF(vh, getRealPosition(position));
        }
    }

    public int getRealPosition(int position) {
        return position - mHeaders.size();
    }

    public void onBindViewHolderHF(ViewHolder vh, int position) {
        mAdapter.onBindViewHolder(vh, position);
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
        if (mManagerType == TYPE_MANAGER_STAGGERED_GRID) {//如果瀑布流 重新设置LayoutParams
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        //清除FrameLayout所有子View，然后添加header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);
    }

    private boolean isHeader(int position) {
        return (position < mHeaders.size());
    }

    private boolean isFooter(int position) {
        return (position >= mHeaders.size() + getItemCountHF());
    }

    @Override
    public final int getItemCount() {
        return mHeaders.size() + getItemCountHF() + mFooters.size();
    }

    public int getItemCountHF() {
        return mAdapter.getItemCount();
    }

    @Override
    public final int getItemViewType(int position) {
        // check what type our position is, based on the assumption that the
        // order is headers > items > footers
        if (isHeader(position)) {
            return TYPE_HEADER;

        } else if (mCarouse.size()>0&&mHeaders.size()==position){ //判断集合个数&&position==0，这时mHeaders中还没有值
            return TYPE_CAROUSE;
        }else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = getItemViewTypeHF(getRealPosition(position));
        if (type == TYPE_HEADER || type == TYPE_FOOTER|| type == TYPE_CAROUSE) {
            throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
        }
        return type;
    }


    public int getItemViewTypeHF(int position) {
        return mAdapter.getItemViewType(position);
    }

    // add a header to the adapter
    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            // animate
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    // remove a header from the adapter
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            // animate
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    // add a footer to the adapter
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            // animate
            notifyItemInserted(mHeaders.size() + getItemCountHF() + mFooters.size() - 1);
        }
    }

    // remove a footer from the adapter
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            // animate
            notifyItemRemoved(mHeaders.size() + getItemCountHF() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    // our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder{
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            base = (FrameLayout) itemView;
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        Log.d("eeee", "setOnItemClickListener " + this.onItemClickListener);
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private class MyOnClickListener implements OnClickListener {
        private ViewHolder vh;

        public MyOnClickListener(ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public void onClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(RecyclerAdapterWithHF.this, vh, position);
            }
            onItemClick(vh, position);
        }
    }

    private class MyOnLongClickListener implements OnLongClickListener {
        private ViewHolder vh;

        public MyOnLongClickListener(ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (RecyclerAdapterWithHF.this.onItemLongClickListener != null) {
                RecyclerAdapterWithHF.this.onItemLongClickListener.onItemLongClick(RecyclerAdapterWithHF.this, vh,position);
            }
            onItemLongClick(vh, position);
            return true;
        }

    }

    protected void onItemClick(ViewHolder vh, int position) {
    }

    protected void onItemLongClick(ViewHolder vh, int position){

    }

    public static interface OnItemClickListener {
        void onItemClick(RecyclerAdapterWithHF adapter, ViewHolder vh, int position);
    }

    public static interface OnItemLongClickListener {
        void onItemLongClick(RecyclerAdapterWithHF adapter, ViewHolder vh, int position);
    }

    private RecyclerView.Adapter<ViewHolder> mAdapter;

    public RecyclerAdapterWithHF(RecyclerView.Adapter<ViewHolder> adapter) {
        super();
        this.mAdapter = adapter;
        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition + getHeadSize(), toPosition + getHeadSize());
        }
    };
}
