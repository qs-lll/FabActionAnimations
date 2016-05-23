package com.qslll.library.fabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

/**
 *
 * Created by Qs on 16/5/23.
 */
public class QsJumpFab extends ViewGroup {
    private int maxImageSize = 24;//dp
    private long duration = 1000;
    private ImageView mJumpImageView;
    private FloatingActionButton mFab;

    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private ObjectAnimator translationY_down;
    private ObjectAnimator translationY_up;

    private int currentCount = 0;//多次循环计数

    public QsJumpFab(Context context) {
        super(context);
        init(context);
    }

    public QsJumpFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QsJumpFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (maxImageSize == 24)
            maxImageSize = Util.dp2px(context, maxImageSize);
        mFab = new FloatingActionButton(context);
        mJumpImageView = new ImageView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJumpImageView.setElevation(mFab.getElevation() + 1f);
        }
        mJumpImageView.setMaxHeight(maxImageSize);
        mJumpImageView.setMaxWidth(maxImageSize);
        mJumpImageView.setAdjustViewBounds(true);
        addView(mFab);
        addView(mJumpImageView);
    }

    /**
     * 跳跃高度取决于控件的高度
     */
    private void initAnimator() {
        int jumpH = getMeasuredHeight() - mFab.getMeasuredHeight();
        translationY_down = ObjectAnimator.ofFloat(mJumpImageView, "translationY", -jumpH, 0);
        translationY_down.setInterpolator(new BounceInterpolator());
        translationY_up = ObjectAnimator.ofFloat(mJumpImageView, "translationY", 0, -jumpH);
        translationY_up.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void setImageResource(int resId) {
        mJumpImageView.setImageResource(resId);
    }

    public void setBitmapImage(Bitmap bitmap) {
        mJumpImageView.setImageBitmap(bitmap);
    }

    /**
     * {@link QsJumpFab}
     * {@link #setBackgroundTintList(ColorStateList)}
     * @param color
     */
    public void setBackgroundTintColor(int color) {
        setBackgroundTintList(ColorStateList.valueOf(color));
    }
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        mFab.setBackgroundTintList(tint);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
        initAnimator();
        if (duration >= 0)
            mAnimatorSet.setDuration(duration);
        mAnimatorSet.play(translationY_up).before(translationY_down);
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

    @Override
    public void setOnClickListener(final OnClickListener l) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    l.onClick(v);
                }
            });
        }
    }

    //// TODO: 16/5/23 need to base
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int h = getMeasuredHeight();
        int w = getMeasuredHeight();
        int fabh = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            int ch = childAt.getMeasuredHeight();
            int cw = childAt.getMeasuredWidth();
            int cl = (w - cw) / 2;
            int ct = (h - ch);
            int cr = (w - cw) / 2 + cw;
            int cb = h;
            //注意addView顺序,整反了赋不了值
            if (childAt instanceof FloatingActionButton) {
                fabh = ch;
            } else if (childAt instanceof ImageView) {
                ct = ct - fabh / 2 + cw / 2;
                cb = ct + cw;
            }
            childAt.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(0, 0);
        }
    }
}
