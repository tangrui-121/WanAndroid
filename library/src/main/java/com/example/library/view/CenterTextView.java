package com.example.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mr'Tang on 2018/5/17.
 */

@SuppressLint("AppCompatCustomView")
public class CenterTextView extends TextView {
    private StaticLayout mStaticLayout;
    private TextPaint mTextPaint;

    public CenterTextView(Context context) {
        super(context);
    }

    public CenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    private void initView() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getCurrentTextColor());
        mStaticLayout = new StaticLayout(getText(), mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStaticLayout.draw(canvas);
    }
}