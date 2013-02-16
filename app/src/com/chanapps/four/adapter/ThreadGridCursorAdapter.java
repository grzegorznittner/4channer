package com.chanapps.four.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import com.chanapps.four.activity.R;
import com.chanapps.four.component.ChanGridSizer;
import com.chanapps.four.data.ChanHelper;

/**
 * Created with IntelliJ IDEA.
 * User: johnarleyburns
 * Date: 2/4/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThreadGridCursorAdapter extends AbstractThreadCursorAdapter {

    protected static final int GRID_ITEM_HEIGHT_DP = 80;

    public ThreadGridCursorAdapter(Context context, int layout, ViewBinder viewBinder, String[] from, int[] to) {
        super(context, layout, viewBinder, from, to);
    }

    @Override
    protected View newView(Context context, ViewGroup parent, String tag, int position) {
        if (DEBUG) Log.d(TAG, "Creating " + tag + " layout for " + position);
        if (ChanHelper.LAST_ITEM.equals(tag)) {
            return mInflater.inflate(R.layout.thread_grid_item_final, parent, false);
        }
        else if (ChanHelper.AD_ITEM.equals(tag)) {
            return mInflater.inflate(R.layout.board_grid_item_ad, parent, false);
        }
        else if (ChanHelper.POST_RESTO.equals(tag) || ChanHelper.POST_RESTO_NARROW.equals(tag)) { // first item is the post which started the thread
            RelativeLayout view = (RelativeLayout)mInflater.inflate(R.layout.thread_grid_item, parent, false);
            AbsListView.LayoutParams viewParams = (AbsListView.LayoutParams)view.getLayoutParams();
            if (viewParams == null) {
                int viewHeightPx = ChanGridSizer.dpToPx(context.getResources().getDisplayMetrics(), GRID_ITEM_HEIGHT_DP);
                viewParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewHeightPx);
            }
            viewParams.width = parent.getWidth();
            view.setLayoutParams(viewParams);
            return view;
        }
        else if (ChanHelper.POST_OMITTED_POSTS.equals(tag)) {
            return mInflater.inflate(R.layout.thread_grid_item_null, parent, false);
        }
        else if (ChanHelper.POST_IMAGE_URL.equals(tag)) {
            return mInflater.inflate(R.layout.thread_grid_item, parent, false);
        } else {
            return mInflater.inflate(R.layout.thread_grid_item_no_image, parent, false);
        }
    }

    @Override
    protected int getThumbnailImageId() {
        return R.id.grid_item_image;
    }

    @Override
    protected void setHighlightViews(View v, String tag, long postNo) {
        if (highlightPostNo == postNo || highlightIdPostNos.contains(postNo)) {
            v.findViewById(R.id.grid_item_self_highlight).setVisibility(View.VISIBLE);
            v.findViewById(R.id.grid_item_prev_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_next_highlight).setVisibility(View.INVISIBLE);
        }
        else if (highlightPrevPostNos.contains(postNo)) {
            v.findViewById(R.id.grid_item_self_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_prev_highlight).setVisibility(View.VISIBLE);
            v.findViewById(R.id.grid_item_next_highlight).setVisibility(View.INVISIBLE);
        }
        else if (highlightNextPostNos.contains(postNo)) {
            v.findViewById(R.id.grid_item_self_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_prev_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_next_highlight).setVisibility(View.VISIBLE);
        }
        else if (!tag.equals(ChanHelper.LAST_ITEM) && !tag.equals(ChanHelper.AD_ITEM)){
            v.findViewById(R.id.grid_item_self_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_prev_highlight).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.grid_item_next_highlight).setVisibility(View.INVISIBLE);
        }
    }

}
