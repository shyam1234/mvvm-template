package com.duyp.architecture.mvvm.ui.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.adapter.BaseHeaderFooterAdapter;
import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.AnimHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/26/17.
 *
 */

public abstract class BaseAdapter<T> extends BaseHeaderFooterAdapter {

    @Getter
    @Nullable
    protected List<T> data;

    @Nullable
    @Setter
    protected PlainConsumer<T> itemClickListener;

    private static final String TAG = "adapter";

    @Getter private boolean isProgressAdded = false;
    private int lastKnowingPosition = -1;
    private boolean enableAnimation = PrefGetter.isRVAnimationEnabled();

    private View progressView;

    protected final NavigatorHelper navigatorHelper;

    protected final Context mContext;
    protected final LayoutInflater mInflater;

    public BaseAdapter(@ActivityContext Context context, NavigatorHelper navigatorHelper) {
        setHasStableIds(true);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.navigatorHelper = navigatorHelper;
    }

    public void setData(@Nullable List<T> newData) {
        if (newData != data) {
            this.data = newData;
        }
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int itemType) {
        RecyclerView.ViewHolder holder = createItemHolder(viewGroup, itemType);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                T item = getItem(holder.getAdapterPosition());
                if (item != null) {
                    itemClickListener.accept(item);
                }
            });
        }
        return holder;
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, int position) {
        T item = getItem(position);
        if (item != null) {
            bindItemViewHolder(viewHolder, item);
            animate(viewHolder, position);
        }
    }

    protected abstract RecyclerView.ViewHolder createItemHolder(ViewGroup viewGroup, int itemType);

    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, @NonNull T t);

    @Override
    public int getItemCountExceptHeaderFooter() {
        return data != null ? data.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    protected T getItem(int adapterPosition) {
        if (data != null) {
            int pos = getRealItemPosition(adapterPosition);
            if (pos >= 0 && pos < data.size()) {
                return data.get(pos);
            }
        }
        return null;
    }

    public void addProgress() {
        Log.d(TAG, "addProgress: ");
        isProgressAdded = true;
        addFooter(getProgressView());
    }

    public void removeProgress() {
        Log.d(TAG, "removeProgress: ");
        isProgressAdded = false;
        removeFooter(getProgressView());
    }

    private View getProgressView() {
        if (progressView == null) {
            progressView = mInflater.inflate(R.layout.progress_layout, null);
        }
        return progressView;
    }

    protected void animate(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isEnableAnimation() && position > lastKnowingPosition) {
            AnimHelper.startBeatsAnimation(holder.itemView);
            lastKnowingPosition = position;
        }
    }

    @SuppressWarnings("WeakerAccess") public boolean isEnableAnimation() {
        return enableAnimation;
    }
}