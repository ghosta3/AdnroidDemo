package com.example.bezier;

import java.util.Date;

import android.graphics.Point;
import android.graphics.PointF;

public class BezierMaker {
	
	public static PointF fromLinearFunc(PointF p0, PointF p1, float t) {
		return pointPlus(pointMulti(p0, 1 - t), pointMulti(p1, t));
	}

	public static PointF formQuadraticFunc(PointF p0, PointF p1, PointF p2, float t) {
		float oneSubT = 1 - t;

		return pointPlus(
				pointMulti(p0, oneSubT * oneSubT), 
				pointMulti(p1, 2 * t * oneSubT), 
				pointMulti(p2, t * t));
	}
	
	public static PointF fromCubiFunc(PointF p0, PointF p1, PointF p2, PointF p3, float t){
		float oneSubT = 1 - t;
		
		return pointPlus(
				pointMulti(p0, oneSubT * oneSubT * oneSubT),
				pointMulti(p1, 3 * t * oneSubT * oneSubT),
				pointMulti(p2, 3 * t * t * oneSubT),
				pointMulti(p3, t * t * t)
				);
	}

	public static PointF pointMulti(PointF p, float multiplier) {
		PointF returnPoint = new PointF(p.x, p.y);
		returnPoint.x *= multiplier;
		returnPoint.y *= multiplier;
		return returnPoint;
	}

	public static PointF pointPlus(PointF... points) {
		PointF p = new PointF(0, 0);

		for (PointF tp : points) {
			p.x += tp.x;
			p.y += tp.y;
		}

		return p;
	}
}
