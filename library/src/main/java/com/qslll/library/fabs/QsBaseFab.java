package com.qslll.library.fabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Qs on 16/5/24.
 */
public abstract class QsBaseFab extends ViewGroup {

    private AnimatorSet mAnimatorSet;

    protected int maxImageSize = 24;//dp
    private long duration = 1000;
    private int currentCount = 0;//多次循环计数

    public QsBaseFab(Context context) {
        super(context);
        initBase(context);
    }

    public QsBaseFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBase(context);
    }

    public QsBaseFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase(context);
    }
    private void initBase(Context context) {
        if (maxImageSize == 24)
            maxImageSize = Util.dp2px(context, maxImageSize);
    }

    public void start() {
        start(1);
    }

    /**
     * @param count 次数
     */
    public void start(int count) {
        if (count == ValueAnimator.INFINITE)
            currentCount = 999999;
        else
            currentCount = count;
//        if (mAnimatorSet == null) {
            mAnimatorSet = getAnimatorSet();
//        }
        if (duration >= 0)
            mAnimatorSet.setDuration(duration);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (--currentCount > 0) {
                    animation.start();
                }
            }
        });
        mAnimatorSet.start();
    }

    public void stop() {
        if (mAnimatorSet != null) {
            currentCount = 0;
        }
    }


    /**
     * {@link QsJumpFab}
     * {@link #setBackgroundTintList(ColorStateList)}
     *
     * @param color
     */
    public void setBackgroundTintColor(int color) {
        setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        getFab().setBackgroundTintList(tint);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    protected abstract AnimatorSet getAnimatorSet();

    protected abstract FloatingActionButton getFab();

    @Override
    public abstract void setOnClickListener(OnClickListener l);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int max
        //// TODO: 16/5/25 need minimum method
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        super.onMeasure();
    }
}
