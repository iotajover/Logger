package com.example.logger.customfonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextView_Roboto_Regular extends TextView {

    public TextView_Roboto_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_Roboto_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_Roboto_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }

}