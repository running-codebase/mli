package ca.travis.awesome.mli;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CompassView extends View {
	
	private Player player;
	private Combat combat;
	
	private Path path;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint yellowPaint; 
	private Paint redPaint; 

	private float rotation = 0.0f;
	
	
	//measurement variables
	private int width, height;
	private int centX, centY;
	
	private int boundingRadius = 100;
	
	
	public CompassView(Context context) {
		super(context);
		path = new Path();
		
		this.setBackgroundColor(Color.BLACK);
		initPaint();
	}

	private void initPaint() {
		yellowPaint = new Paint();
		yellowPaint.setDither(true);
		yellowPaint.setColor(0xFFFFFF00);
		yellowPaint.setStyle(Paint.Style.STROKE);
		yellowPaint.setStrokeJoin(Paint.Join.ROUND);
		yellowPaint.setStrokeCap(Paint.Cap.ROUND);
		yellowPaint.setStrokeWidth(3);

		redPaint = new Paint();
		redPaint.setDither(true);
		redPaint.setColor(0xFFFF0000);
		redPaint.setStyle(Paint.Style.STROKE);
		redPaint.setStrokeJoin(Paint.Join.ROUND);
		redPaint.setStrokeCap(Paint.Cap.ROUND);
		redPaint.setStrokeWidth(3);

	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setCombat(Combat combat) {
		this.combat = combat;
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		PathWithPaint pp = new PathWithPaint();
//		mCanvas.drawPath(path, mPaint);
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			path.moveTo(event.getX(), event.getY());
//			path.lineTo(event.getX(), event.getY());
//		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//			path.lineTo(event.getX(), event.getY());
//			pp.setPath(path);
//			pp.setmPaint(mPaint);
//			_graphics1.add(pp);
//		}
//		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (combat != null && player != null) {

			rotation = -player.getOrientationWrapper().getOrientation()[0]*360/(2*3.14159f); //North direction
			
			//Draw the hit zone

			if (combat.inRange) {
				RectF oval = new RectF(centX - boundingRadius ,centY - boundingRadius , centX + boundingRadius, centY + boundingRadius);
				canvas.drawArc(oval, -100, 20 , true, redPaint); //-100 brings it back to -10 of north, 20 is the number of degrees 
			} else {
				RectF oval = new RectF(centX - boundingRadius ,centY - boundingRadius , centX + boundingRadius, centY + boundingRadius);
				canvas.drawArc(oval, -100, 20 , true, yellowPaint); //-100 brings it back to -10 of north, 20 is the number of degrees 
			}
			
			//Draw north
			canvas.save(Canvas.MATRIX_SAVE_FLAG); 
			canvas.rotate(rotation, centX, centY);
			canvas.drawCircle(centX, centY, boundingRadius, yellowPaint);
			canvas.drawText("N",centX , centY - boundingRadius - 20, yellowPaint);
			canvas.restore();

			//Draw the bad guy
			canvas.save(Canvas.MATRIX_SAVE_FLAG); 
			canvas.rotate((float)combat.bearingToEnemy() + rotation, centX, centY);
			//canvas.drawCircle(centX, centY, boundingRadius, mPaint);
			canvas.drawText("o",centX , centY - boundingRadius - 20, redPaint);
			canvas.restore();

			
			
			
		};
		this.invalidate();
	}
	
	 @Override
	 protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
	 {
		 super.onSizeChanged(xNew, yNew, xOld, yOld);
		 if (xNew >0 && yNew > 0) {
				width = getMeasuredWidth();
				height = getMeasuredHeight();
				centX = (int)(width /2);
				centY = (int)(height /2);
				mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				mCanvas = new Canvas(mBitmap);
		 }
	 }
}
