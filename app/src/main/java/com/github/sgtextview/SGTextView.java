package com.github.sgtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;

public class SGTextView extends TextView {
    private Paint strokePaint = new Paint();
    private Paint gradientPaint = new Paint();

    public SGTextView(Context context) {
        this(context, null);
    }

    public SGTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStyle(String strokeColor, String startColor,
                         String endColor, float strokeWidthDp, int gradientHeightDp) {
        setStyle(
                Color.parseColor(strokeColor),
                dip2px(getContext(), strokeWidthDp),
                new LinearGradient(0, 0, 0, dip2px(getContext(),
                        gradientHeightDp), new int[]{
                        Color.parseColor(startColor),
                        Color.parseColor(endColor)}, null, TileMode.CLAMP));
    }

    public void setStyle(int strokeColor, float strokeWidth, Shader shader) {

        strokePaint.setAntiAlias(true);
        // 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        strokePaint.setDither(true);
        // 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
        // 速度，本设置项依赖于dither和xfermode的设置
        strokePaint.setFilterBitmap(true);

        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setColor(strokeColor);
        // 设置绘制时各图形的结合方式，如平滑效果等
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        // Cap.ROUND,或方形样式Cap.SQUARE
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStyle(Paint.Style.STROKE);

        gradientPaint.setAntiAlias(true);
        gradientPaint.setDither(true);
        gradientPaint.setFilterBitmap(true);
        gradientPaint.setShader(shader);
        gradientPaint.setStrokeJoin(Paint.Join.ROUND);
        gradientPaint.setStrokeCap(Paint.Cap.ROUND);
        gradientPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        float textSize = getTextSize();
        strokePaint.setTextSize(textSize);
        gradientPaint.setTextSize(textSize);

    }

    public void setShadowLayer(float radius, float dx, float dy, String color) {
        strokePaint.setShadowLayer(radius, dx, dy, Color.parseColor(color));
    }

    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        String text = getText().toString();
        int width = getMeasuredWidth();
        if (width == 0) {
            measure(0, 0);
            width = (int) (getMeasuredWidth() + strokePaint.getStrokeWidth() * 2);
            setWidth(width);
        }

        float y = getBaseline();
        float x = (width - strokePaint.measureText(text)) / 2;

        canvas.drawText(text, x, y, strokePaint);
        canvas.drawText(text, x, y, gradientPaint);
    }
}
