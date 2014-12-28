package com.example.bezier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BezierView extends View {
	private Paint basePaint;
	private float precision = 0.05f;
	private PointF p1;
	private PointF p0;
	private PointF p2;
	private PointF p3;

	private PointF movePoint;

	public BezierView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BezierView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BezierView(Context context) {
		super(context);
		init();
	}

	private void init() {
		basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		basePaint.setStyle(Paint.Style.STROKE);
		basePaint.setColor(Color.BLUE);
		basePaint.setStrokeWidth(6.0f);

		p0 = new PointF(20, 100);
		p2 = new PointF(400, 400);
		p1 = new PointF(110, 400);
		p3 = new PointF(310, 100);

		setDrawingCacheEnabled(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		PointF preP = p0;
		PointF nowP;

		canvas.drawCircle(p0.x, p0.y, 10, basePaint);
		canvas.drawCircle(p1.x, p1.y, 10, basePaint);
		canvas.drawCircle(p2.x, p2.y, 10, basePaint);
		canvas.drawCircle(p3.x, p3.y, 10, basePaint);

		for (float t = 0; t <= 1; t += precision) {
			nowP = BezierMaker.fromCubiFunc(p0, p1, p2, p3, t);
			canvas.drawLine(preP.x, preP.y, nowP.x, nowP.y, basePaint);
			preP = nowP;
		}

		canvas.save();

		super.onDraw(canvas);

		canvas.restore();
	}

	public float getPrecision() {
		return precision;
	}

	public void setPrecision(float precision) {
		this.precision = precision;
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			PointF touchP = new PointF(event.getX(), event.getY());
			float x = event.getX();
			float y = event.getY();
			RectF rf = new RectF(x - 25, y - 25, x + 25, y + 25);

			if (rf.contains(p0.x, p0.y))
				movePoint = p0;
			else if (rf.contains(p1.x, p1.y))
				movePoint = p1;
			else if (rf.contains(p2.x, p2.y))
				movePoint = p2;
			else if (rf.contains(p3.x, p3.y))
				movePoint = p3;
			else
				movePoint = null;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (movePoint != null) {
				movePoint.x = event.getX();
				movePoint.y = event.getY();
				invalidate();
			}
		} else
			movePoint = null;
		return true;
	}

}
