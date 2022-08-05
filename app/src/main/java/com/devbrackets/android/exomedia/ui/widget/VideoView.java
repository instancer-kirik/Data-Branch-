package com.devbrackets.android.exomedia.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class VideoView extends View {
    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
