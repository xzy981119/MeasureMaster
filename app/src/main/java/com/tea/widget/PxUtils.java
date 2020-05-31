package com.tea.widget;

import android.content.Context;

public class PxUtils {
    /**
     * px转sp
     * @param context 上下文
     * @param pxValue px
     * @return sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
