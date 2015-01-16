package ca.travis.awesome.mli;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CompassView extends View {
	
	private Player player;
	private Combat combat;
	
	private Path path;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint; 

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
		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(0xFFFFFF00);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(3);
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

//			if (player.getLocationAndOrientation().getOrientation()[0] == null) {
				rotation = -player.getLocationAndOrientation().getOrientation()[0]*360/(2*3.14159f);
//			}
			
	
			
			canvas.save(Canvas.MATRIX_SAVE_FLAG); //Saving the canvas and later restoring it so only this image will be rotated.
			canvas.rotate(rotation, centX, centY);//player.getLocationAndOrientation().getLocation().getBearing());

			canvas.drawCircle(centX, centY, boundingRadius, mPaint);
			canvas.drawText("N",centX , centY - boundingRadius - 20, mPaint);
			
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
