package com.duyp.architecture.mvvm.ui.widgets.recyclerview.scroll;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.duyp.architecture.mvvm.ui.base.adapter.BaseAdapter;

import io.realm.RealmResults;

/**
 * Created by Kosh on 8/2/2015. copyrights are reserved @
 * modified by Duy Pham
 */
public abstract class InfiniteScroll extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 3;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private RecyclerView.LayoutManager layoutManager;

    private BaseAdapter adapter;

    private boolean newlyAdded = true;

    public InfiniteScroll() {}

    private void initLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager) {
            visibleThreshold = visibleThreshold * ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            visibleThreshold = visibleThreshold * ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (newlyAdded) {
            newlyAdded = false;
            return;
        }
        onScrolled(dy > 0);
        if (layoutManager == null) {
            initLayoutManager(recyclerView.getLayoutManager());
        }
        if (adapter == null) {
            if (recyclerView.getAdapter() instanceof BaseAdapter) {
                adapter = (BaseAdapter) recyclerView.getAdapter();
            }
        }
        if (adapter != null && adapter.isProgressAdded()) return;

        int lastVisibleItemPosition = 0;
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // if current data is load from realm, don't perform load more event
        if (!loading && !(adapter.getData() instanceof RealmResults)
                &&(lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            boolean isCallingApi = onLoadMore(currentPage, totalItemCount);
            loading = true;
            if (adapter != null && isCallingApi) {
//                recyclerView.post(() -> {
                    adapter.addProgress();
//                });
            }
        }
    }

    public void reset() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public void initialize(int page, int previousTotal) {
        this.currentPage = page;
        this.previousTotalItemCount = previousTotal;
        this.loading = true;
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

    public void onScrolled(boolean isUp) {}

}

