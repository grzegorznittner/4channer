package com.chanapps.four.component;

import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * User: arley
 * Date: 11/21/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChanGridSizer {

    private static final String TAG = ChanGridSizer.class.getSimpleName();
    private static final boolean DEBUG = false;

    private static final int[] MAX_COLUMN_WIDTHS = {
            ServiceType.SELECTOR.ordinal(), 170,
            ServiceType.BOARD.ordinal(), 170,
            ServiceType.THREAD.ordinal(), 80,
            ServiceType.WATCHLIST.ordinal(), 170
    };

    private static final int[] MAX_COLUMN_WIDTHS_LARGE = {
            ServiceType.SELECTOR.ordinal(), 300,
            ServiceType.BOARD.ordinal(), 300,
            ServiceType.THREAD.ordinal(), 80,
            ServiceType.WATCHLIST.ordinal(), 300
    };

    private GridView g;
    private Display d;
    private int numColumns = 0;
    private int columnWidth = 0;
    private int columnHeight = 0;
    private int maxColumnWidth = 200;
    private int paddingTop = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;

    public ChanGridSizer(View g, Display d, ServiceType serviceType) {
    	if (g instanceof GridView) {
    		this.g = (GridView)g;
    	}
        else if (g != null) { // we support padding only on container views, not on the grid view itself
            paddingTop = g.getPaddingTop();
            paddingLeft = g.getPaddingLeft();
            paddingRight = g.getPaddingRight();
            paddingBottom = g.getPaddingBottom();
        }
        this.d = d;
        int layoutMask = g.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int[] columnWidthArray =
                ((layoutMask == Configuration.SCREENLAYOUT_SIZE_LARGE) || (layoutMask == Configuration.SCREENLAYOUT_SIZE_XLARGE))
                ? MAX_COLUMN_WIDTHS_LARGE
                : MAX_COLUMN_WIDTHS;
        for (int i = 0; i < MAX_COLUMN_WIDTHS.length; i += 2) {
            if (serviceType.ordinal() == MAX_COLUMN_WIDTHS[i]) {
                int dp = MAX_COLUMN_WIDTHS[i + 1];
                maxColumnWidth = dpToPx(d, dp);
                return;
            }
        }
    }

    static public int dpToPx(Display d, int dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        return dpToPx(displayMetrics, dp);
    }
    static public int dpToPx(DisplayMetrics displayMetrics, int dp) {
        float dpf = dp;
        int pixels = (int)(displayMetrics.density * dpf + 0.5f);
        return pixels;
    }

    public void sizeGridToDisplay() {
        Point size = new Point();
        d.getSize(size);
        int width = size.x - paddingLeft - paddingRight;
        int height = size.y - paddingTop - paddingBottom;
        numColumns = width / maxColumnWidth == 1 ? 2 : width / maxColumnWidth;
        columnWidth = width / numColumns;
        columnHeight = columnWidth;
		if (DEBUG) Log.i(TAG, "sizeGridToDisplay width: " + width + ", height: " + height + ", numCols: " + numColumns);
        if (g != null) {
        	g.setNumColumns(numColumns);
        	g.setColumnWidth(columnWidth);
        }
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public enum ServiceType {
        SELECTOR,
        BOARD,
        THREAD,
        WATCHLIST
    }
}
