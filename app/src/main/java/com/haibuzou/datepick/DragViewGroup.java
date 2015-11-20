package com.haibuzou.datepick;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.haibuzou.datepick.views.MonthView;

/**
 * Created by LCW009 on 2015/11/13.
 */
public class DragViewGroup extends FrameLayout {

    ViewDragHelper mViewDragHelper;
    View monthView;
    View contentView;
    float mInitialMotionX;
    float mInitialMotionY;
    Context mComtext;
    private int mDragRange;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mComtext = context;
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }


            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int height = getPaddingTop() + monthView.getHeight() / 3;
                if (child instanceof MonthView) {
                    return Math.min(Math.max(top, -height), getPaddingTop());

                } else {

                    return Math.min(Math.max(top, height), monthView.getHeight());
//                    return top;
                }

            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if (changedView instanceof MonthView) {
                    if (mViewDragHelper.smoothSlideViewTo(contentView, 0, monthView.getBottom())) {
                        ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
                    }
                } else {
//                    mViewDragHelper.smoothSlideViewTo(monthView, 0,  monthView.getBottom());
//                    ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
                }

            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if ((action != MotionEvent.ACTION_DOWN)) {
            mViewDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
//                interceptTap = mViewDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mViewDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mViewDragHelper.cancel();
                    return false;
                }
            }
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        monthView = getChildAt(0);
        contentView = getChildAt(1);
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();

        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft()
                + getPaddingRight(), lp.width);

        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(parentHeightMeasureSpec), MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(parentHeightMeasureSpec), MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
