package com.qslll.library.fabs;

import android.content.Context;

/**
 * Created by Qs on 16/5/24.
 */
public class Util {
    public static int dp2px(Context context,float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
