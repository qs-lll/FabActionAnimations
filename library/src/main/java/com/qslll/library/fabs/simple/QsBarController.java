package com.qslll.library.fabs.simple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import java.util.Random;

/**
 * Created by Qs on 16/5/26.
 */
public class QsBarController extends QsBaseController {
    private final int space = 2;
    private final long durtion = 300;
    private int bitmapW, bitmapH;
    private int defaultHeight = 10;
    private int minH;

    int h1, h2, h3; //rect hight
    int lastH1, lastH2, lastH3; //last rect hight
    int weight; //rect weight
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();

    @Override
    public void init(int bitmapH, int bitmapW) {
        this.bitmapW = bitmapW;
        this.bitmapH = bitmapH;

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(bitmapW, bitmapW, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
        }

        if (lastH1 != 0) {
            lastH1 = h1;
            lastH2 = h2;
            lastH3 = h3;
        }
        h1 = new Random().nextInt(bitmapH);
        h2 = new Random().nextInt(bitmapH);
        h3 = new Random().nextInt(bitmapH);
        minH = bitmapH / 5;//minHight
        weight = (bitmapW - 4) / 3;

        defaultHeight = bitmapH / 5;
        if (lastH1 == 0) {
            lastH1 = defaultHeight;
            lastH2 = defaultHeight;
            lastH3 = defaultHeight;
        }
    }

    @Override
    public Bitmap draw(float rate, int color) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setAntiAlias(true);
        paint.setColor(color);
        //1
        canvas.drawRect(0, bitmapH - Math.max(minH, lastH1 + (h1 - lastH1) * rate), weight, bitmapH, paint);
        //2
        canvas.drawRect(weight + space, bitmapH - Math.max(minH, lastH2 + (h2 - lastH2) * rate), weight * 2 + space, bitmapH, paint);
        //3
        canvas.drawRect(weight * 2 + space * 2, bitmapH - Math.max(minH, lastH3 + (h3 - lastH3) * rate), bitmapW, bitmapH, paint);
        return bitmap;
    }

    @Override
    public Bitmap drawDefault(int color) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        paint.setAntiAlias(true);
        paint.setColor(color);
        //1
        canvas.drawRect(0, bitmapH - defaultHeight, weight, bitmapH, paint);
        //2
        canvas.drawRect(weight + space, bitmapH - defaultHeight, weight * 2 + space, bitmapH, paint);
        //3
        canvas.drawRect(weight * 2 + space * 2, bitmapH - defaultHeight, bitmapW, bitmapH, paint);
        if (lastH1 != defaultHeight) {
            lastH1 = defaultHeight;
            lastH2 = defaultHeight;
            lastH3 = defaultHeight;
        }
        return bitmap;
    }

    //循环一次动画的时间
    @Override
    public long getDurtion() {
        return durtion;
    }
}
