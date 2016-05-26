package com.qslll.library.fabs.simple;

import android.graphics.Bitmap;

/**
 * Created by Qs on 16/5/26.
 */
public abstract class QsBaseController {

    public QsBaseController() {

    }

    public abstract void init(int bitmapH, int bitmapW);

    public abstract Bitmap draw(float rate,int color);

    public abstract Bitmap drawDefault(int color);

    public abstract long getDurtion();


}
