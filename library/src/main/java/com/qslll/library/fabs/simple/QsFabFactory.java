package com.qslll.library.fabs.simple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by Qs on 16/5/26.
 */
public class QsFabFactory {
    private int color;

    private QsBaseController control;
    private FloatingActionButton floatingActionButton;
    private ValueAnimator valueAnimator;
    private int currentCount = 0; //循环次数

    public static QsFabFactory loadControl(FloatingActionButton fab, QsBaseController control, int color) {
        return new QsFabFactory(fab, control, color);
    }

    public QsFabFactory(FloatingActionButton floatingActionButton, QsBaseController control, int color) {
        this.floatingActionButton = floatingActionButton;
        this.control = control;
        this.color = color;
        floatingActionButton.measure(0, 0);
        control.init(floatingActionButton.getMeasuredHeight(), floatingActionButton.getMeasuredWidth());
        setupFab(control.drawDefault(color));
        valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(control.getDurtion());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = (float) animation.getAnimatedValue();
                setupFab(QsFabFactory.this.control.draw(rate, QsFabFactory.this.color));
            }
        });
        valueAnimator.setStartDelay(10);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);
                if (--currentCount > 0) {
                    QsFabFactory.this.control.init(QsFabFactory.this.floatingActionButton.getMeasuredHeight(), QsFabFactory.this.floatingActionButton.getMeasuredWidth());
                    animation.start();
                } else {
                    setupFab(QsFabFactory.this.control.drawDefault(QsFabFactory.this.color));
                }
            }
        });
    }

    public void setColor(int color) {
        this.color = color;

    }

    public void start() {
        start(1);
    }

    public void start(int count) {
        if (count == ValueAnimator.INFINITE)
            currentCount = 999999;
        else
            currentCount = count;


        valueAnimator.start();
    }

    public void stop() {
        currentCount = 0;
//        control.drawDefault(color);
    }

    private void setupFab(Bitmap bitmap) {
        floatingActionButton.setImageBitmap(bitmap);
    }

}
