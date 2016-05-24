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
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.qslll.fabactionanimationlibrary.R;

/**
 * Created by Qs on 16/5/23.
 */
public class QsJumpFab extends QsBaseFab {
    private ImageView mJumpImageView;
    private FloatingActionButton mFab;

    AnimatorSet mAnimatorSet = new AnimatorSet();
    private ObjectAnimator translationY_down;
    private ObjectAnimator translationY_up;


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

        mFab = new FloatingActionButton(context);
        mJumpImageView = new ImageView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFab.setElevation(0);
            mJumpImageView.setElevation(mFab.getElevation() + 1f);
        }
        mJumpImageView.setImageResource(android.R.drawable.stat_sys_speakerphone);
        mJumpImageView.setMaxHeight(maxImageSize);
        mJumpImageView.setMaxWidth(maxImageSize);
        mJumpImageView.setAdjustViewBounds(true);
        addView(mFab);
        addView(mJumpImageView);
    }


    public void setImageResource(int resId) {
        mJumpImageView.setImageResource(resId);
    }

    public void setBitmapImage(Bitmap bitmap) {
        mJumpImageView.setImageBitmap(bitmap);
    }

    @Override
    protected AnimatorSet getAnimatorSet() {
        initAnimator();
        mAnimatorSet.play(translationY_up).before(translationY_down);
        return mAnimatorSet;
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

    @Override
    protected FloatingActionButton getFab() {
        return mFab;
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

}
