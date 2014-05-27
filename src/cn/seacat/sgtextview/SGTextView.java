package cn.seacat.sgtextview;

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
	private Paint strokPaint = new Paint();
	private Paint gradientPaint = new Paint();

	public SGTextView(Context context) {
		this(context, null);
	}

	public SGTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setStyle(String strokeColor, String startColor,
			String endColor, float strokewidthDp, int gradientHeighDp) {
		setStyle(
				Color.parseColor(strokeColor),
				DimensUtils.dip2px(getContext(), strokewidthDp),
				new LinearGradient(0, 0, 0, DimensUtils.dip2px(getContext(),
						gradientHeighDp), new int[] {
						Color.parseColor(startColor),
						Color.parseColor(endColor) }, null, TileMode.CLAMP));
	}

	public void setStyle(int strokeColor, float strokewidth, Shader shader) {

		strokPaint.setAntiAlias(true);
		// 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		strokPaint.setDither(true);
		// 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
		// 速度，本设置项依赖于dither和xfermode的设置
		strokPaint.setFilterBitmap(true);

		strokPaint.setStrokeWidth(strokewidth);
		strokPaint.setColor(strokeColor);
		// 设置绘制时各图形的结合方式，如平滑效果等
		strokPaint.setStrokeJoin(Paint.Join.ROUND);
		// 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
		// Cap.ROUND,或方形样式Cap.SQUARE
		strokPaint.setStrokeCap(Paint.Cap.ROUND);
		strokPaint.setStyle(Paint.Style.STROKE);

		gradientPaint.setAntiAlias(true);
		gradientPaint.setDither(true);
		gradientPaint.setFilterBitmap(true);
		gradientPaint.setShader(shader);
		gradientPaint.setStrokeJoin(Paint.Join.ROUND);
		gradientPaint.setStrokeCap(Paint.Cap.ROUND);
		gradientPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		float textsize = getTextSize();
		strokPaint.setTextSize(textsize);
		gradientPaint.setTextSize(textsize);

	}

	public void setShadowLayer(float radius, float dx, float dy, String color) {
		strokPaint.setShadowLayer(radius, dx, dy, Color.parseColor(color));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		String text = getText().toString();
		int width = getMeasuredWidth();
		if (width == 0) {
			measure(0, 0);
			width = (int) (getMeasuredWidth() + strokPaint.getStrokeWidth() * 2);
			setWidth(width);
		}

		float y = getBaseline();
		float x = (width - strokPaint.measureText(text)) / 2;

		canvas.drawText(text, x, y, strokPaint);
		canvas.drawText(text, x, y, gradientPaint);
	}
}
