package com.qslll.library.fabs;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * Created by Qs on 16/5/24.
 */
public class QsShakeFab extends QsBaseFab {
    private FloatingActionButton mFab;
    private ImageView mShakeImage;
    private ImageView mRipple;
    private ImageView mBackRipple;

    private int backRippleColor = Color.parseColor("#01579B");
    private int centerRippleColor = Color.WHITE;

    private AnimatorSet mAnimatorset = new AnimatorSet();

    public QsShakeFab(Context context) {
        super(context);
        init(context);
    }

    public QsShakeFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QsShakeFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mFab = new FloatingActionButton(context);
        mRipple = new ImageView(context);
        mShakeImage = new ImageView(context);
        mBackRipple = new ImageView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackRipple.setElevation(0);
            mFab.setElevation(0);
            mRipple.setElevation(mFab.getElevation() + 2f);
            mShakeImage.setElevation(mFab.getElevation() + 3f);
        }
//        mRipple.setVisibility(INVISIBLE);
        mShakeImage.setImageResource(android.R.drawable.stat_sys_speakerphone);
        mShakeImage.setMaxHeight(maxImageSize);
        mShakeImage.setMinimumHeight(maxImageSize);
        mShakeImage.setMaxWidth(maxImageSize);
        mShakeImage.setMinimumWidth(maxImageSize);
        addView(mBackRipple);
        addView(mFab);
        addView(mRipple);
        addView(mShakeImage);
    }

    @Override
    protected AnimatorSet getAnimatorSet() {
        //设置backimageview的大小同fab一样
        mBackRipple.setImageBitmap(getRoundbitmapByColor(mFab.getWidth(), mFab.getHeight(), backRippleColor));
        //ripple
        mRipple.setImageBitmap(getRoundbitmapByColor(mShakeImage.getWidth(), mShakeImage.getHeight(), centerRippleColor));
        //inAnimations
        ObjectAnimator fabInAnimator;
        ObjectAnimator shakeInAnimator;
        int scope = (mFab.getMeasuredWidth() - mShakeImage.getMeasuredWidth()) / 2 / 3;//shake scope
        ObjectAnimator shakeXAnimator = ObjectAnimator.ofFloat(mShakeImage, View.TRANSLATION_X, 0, -scope, scope, -scope, scope, -scope, scope, -scope, scope, -scope, scope, -scope, scope, -scope, scope, 0);
        PropertyValuesHolder scale12X = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.2f);
        PropertyValuesHolder scale12Y = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.2f);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PropertyValuesHolder translation12Z = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, 1, 1.2f);
            fabInAnimator = ObjectAnimator.ofPropertyValuesHolder(mFab, scale12X, scale12Y, translation12Z);
            shakeInAnimator = ObjectAnimator.ofPropertyValuesHolder(mShakeImage, scale12X, scale12Y, translation12Z);
        } else {
            fabInAnimator = ObjectAnimator.ofPropertyValuesHolder(mFab, scale12X, scale12Y);
            shakeInAnimator = ObjectAnimator.ofPropertyValuesHolder(mShakeImage, scale12X, scale12Y);
        }
        fabInAnimator.setInterpolator(new OvershootInterpolator());
        shakeInAnimator.setInterpolator(new OvershootInterpolator());
        fabInAnimator.setRepeatMode(2);
        shakeInAnimator.setRepeatMode(2);
        fabInAnimator.setRepeatCount(1);
        shakeInAnimator.setRepeatCount(1);
        //outAnimations
        float hRate = ((float) getMeasuredHeight()) / ((float) mFab.getHeight());
        float wRate = ((float) getMeasuredWidth()) / ((float) mFab.getWidth());
        float scaleRate = Math.min(hRate, wRate);
        PropertyValuesHolder back2X = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, scaleRate);
        PropertyValuesHolder back2Y = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, scaleRate);
        PropertyValuesHolder backAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0.3f, 0f);
        ObjectAnimator back2XY = ObjectAnimator.ofPropertyValuesHolder(mBackRipple, back2X, back2Y, backAlpha);

        PropertyValuesHolder rippleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.8f, 2);
        PropertyValuesHolder rippleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.8f, 2);
        PropertyValuesHolder rippleAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 0.6f);
        ObjectAnimator rippleAnimator = ObjectAnimator.ofPropertyValuesHolder(mRipple, rippleAlpha, rippleX, rippleY);
        rippleAnimator.setRepeatMode(2);
        rippleAnimator.setRepeatCount(1);
        mAnimatorset.play(fabInAnimator).with(shakeInAnimator).with(shakeXAnimator).before(back2XY).with(rippleAnimator);
        return mAnimatorset;
    }

    @NonNull
    private Bitmap getRoundbitmapByColor(int w, int h, int color) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
//        canvas.drawRoundRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), bitmap.getWidth() / 2, bitmap.getHeight() / 2, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        return bitmap;
    }

    public void setImageBitmap(Bitmap bitmap) {
        mShakeImage.setImageBitmap(bitmap);
    }

    public void setImageResource(@DrawableRes int resId) {
        mShakeImage.setImageResource(resId);
    }

    public int getCenterRippleColor() {
        return centerRippleColor;
    }

    public void setCenterRippleColor(int centerRippleColor) {
        this.centerRippleColor = centerRippleColor;
    }

    public int getBackRippleColor() {
        return backRippleColor;
    }

    public void setBackRippleColor(int backRippleColor) {
        this.backRippleColor = backRippleColor;
    }

    @Override
    protected FloatingActionButton getFab() {
        return mFab;
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        getChildAt(1).setOnClickListener(l);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            int ch = childAt.getMeasuredHeight();
            int cw = childAt.getMeasuredWidth();

            int cl = (w - cw) / 2;
            int ct = (h - ch) / 2;
            int cr = cl + cw;
            int cb = ct + ch;

            childAt.layout(cl, ct, cr, cb);
        }

    }
}
