package com.example.curvedseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar.OnSeekBarChangeListener;
@SuppressLint("DrawAllocation")
public class CurvedSeekbar extends View {

	Path path;
	RectF outerCircle  = new RectF();
	RectF innerCircle = new RectF();
	int diameter = 20, mArcThickness = 8, centerX, centerY, mThumbDiameter;
	float maximumValue = 100, minimumValue = 0, value = 0, secValue = 0;
	float startAngle = 220f;
	float arcWidthInAngle = 100;
	float left, right;
    float width;
    float height;
    String TAG = "Arcen";
    private boolean fromUser = false;
    private OnSeekBarChangeListener mListener = null;
    
  //  float sweep = 0;
    
	public CurvedSeekbar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 path = new Path();
	}
	
	public CurvedSeekbar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CurvedSeekbar(Context context) {
		super(context);
	}
	
	public void setProcess(int value) {
		if (!fromUser) {
		this.value = value;
		invalidate();
		}
	}
	
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
		this.mListener = listener;
	}
	
	public OnSeekBarChangeListener getOnSeekBarChangeListener() {
		return this.mListener;
	}
	
	public int getProcess() {
		return (int) this.value;
	}
	
	public void setSecondaryProcess(int value) {
		this.secValue = value;
		invalidate();
	}
	
	public int getSecondaryProcess() {
		return (int) this.secValue;
	}
	
	public int getMaxValue() {
		return (int) maximumValue;
	}
	
	public void setMaxValue(int value) {
		maximumValue = value;
		invalidate();
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        width = (float) getWidth();
        height = (float) getHeight();
        
        float radius;

        if (width > height) {
            radius = height ;
        } else {
            radius = width;
        }

        mTrackColor = new Paint();
        mTrackColor.setAntiAlias(true);
        mTrackColor.setColor(Color.parseColor("#8F000000"));
        mTrackColor.setStrokeWidth(Dip(10));

        mTrackColor.setStyle(Paint.Style.STROKE);

        mFillColor = new Paint();
        mFillColor.setAntiAlias(true);
        mFillColor.setColor(Color.parseColor("#CAEBA01B"));
        mFillColor.setStrokeWidth(Dip(10));

        mFillColor.setStyle(Paint.Style.STROKE);
        
        mSecondaryColor = new Paint();
        mSecondaryColor.setAntiAlias(true);
        mSecondaryColor.setColor(Color.parseColor("#85EBA01B"));
        mSecondaryColor.setStrokeWidth(Dip(10));

        mSecondaryColor.setStyle(Paint.Style.STROKE);
        
        centerX = (int) (width /2);
        centerY = (int) (height + ( height / 2));

        left = centerX - radius;
        float top = centerY - radius;
        right = centerX + radius;
        float bottom = centerY + radius;
        diameter = (int) (radius * 2);
        outerCircle.set(left, top, right, bottom);
        innerCircle.set(left, top, right, bottom);
        drawSlider(canvas);
	}
	

	private void drawSlider(Canvas canvas) {
	//	value = 10;

	    float sweepDegrees = (value * arcWidthInAngle)
	            / (maximumValue - minimumValue);
	    
	    float SecsweepDegrees = (secValue * arcWidthInAngle)
        / (maximumValue - minimumValue);
	    
	    // the grey empty part of the arc       
	    drawArc(canvas, startAngle, arcWidthInAngle, mTrackColor);
	    
	    // the secondary process
	    drawArc(canvas, startAngle, SecsweepDegrees, mSecondaryColor);
	    
	    // the colored "filled" part of the arc
	    drawArc(canvas, startAngle, sweepDegrees, mFillColor);

	    

	    // the thumb to drag.       
	    int radius = ((diameter/2) - (mArcThickness/2));
	    Point thumbPoint = calculatePointOnArc(centerX, centerY, radius + 4, startAngle + sweepDegrees);

	    thumbPoint.x = thumbPoint.x - (mThumbDiameter/2);
	    thumbPoint.y = thumbPoint.y - (mThumbDiameter/2);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffd2c8b6);


        paint.setStyle(Paint.Style.FILL);
	    
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#85EBA01B")); //#85EBA01B
        
        canvas.drawCircle(thumbPoint.x, thumbPoint.y, Dip(16), paint);
        
        
        paint.setStyle(Paint.Style.STROKE);
	    
        paint.setStrokeWidth(3);
        paint.setColor(Color.parseColor("#CAEBA01B")); //#85EBA01B
        
        canvas.drawCircle(thumbPoint.x, thumbPoint.y, Dip(16), paint);
        
        
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#FFEBA01B"));
        paint.setStyle(Paint.Style.FILL);
        
        canvas.drawCircle(thumbPoint.x, thumbPoint.y, Dip(5), paint);
	    
	 //   Bitmap thumbBitmap = BitmapFactory.decodeResource(
	//            mContext.getResources(), R.drawable.circle25);

	  //  thumbBitmap = getResizedBitmap(thumbBitmap, mThumbDiameter, mThumbDiameter);
	  //  canvas.drawBitmap(thumbBitmap, thumbPoint.x, thumbPoint.y,
	   //         null);

	    //drawArc(canvas, startAngle, startAngle + sweepDegrees, white);
	}
	
    private int Dip(int value) {
    	Resources r = getResources();
    	float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    	return (int) px;

    }
	@SuppressWarnings("unused")
	private void drawArc(Canvas canvas, float startAngle, float sweepDegrees,
	        Paint paint) {
	    if (sweepDegrees <= 0 || sweepDegrees > arcWidthInAngle) {
	        return;
	    }
	    path.reset();


	    int radius = ((diameter/2) - (mArcThickness/2));
	    Point startPoint = calculatePointOnArc(centerX, centerY, radius, startAngle);
	    Point endPoint = calculatePointOnArc(centerX, centerY, radius, startAngle + sweepDegrees);

	   // Log.d(TAG, "Start: " + startPoint + " End: " + endPoint);

	    path.arcTo(outerCircle, startAngle, sweepDegrees);
	  path.arcTo(innerCircle, startAngle + sweepDegrees, -sweepDegrees);
	    // drawing the circle at both the end point of the arc to get it rounded look.
	   /// path.addCircle(startPoint.x, startPoint.y, mArcThickness/2, Path.Direction.CW);
	  //  path.addCircle(endPoint.x, endPoint.y, mArcThickness/2, Path.Direction.CW);

	    path.close();            

	    canvas.drawPath(path, paint);
	}
	    // this is to calculate the end points of the arc
	private Point calculatePointOnArc(int circleCeX, int circleCeY, int circleRadius, float endAngle) 
	    {
	    Point point = new Point();
	    double endAngleRadian = endAngle * (Math.PI / 180);
	   // Log.d(TAG, "angl: " + circleRadius);
	    int pointX = (int) Math.round((circleCeX  + (circleRadius) * Math.cos(endAngleRadian)));
	    int pointY = (int) Math.round((circleCeY  + (circleRadius) * Math.sin(endAngleRadian)));

	    point.x = pointX;
	    point.y = pointY;

	    return point;
	}
	// for the emboss effect set maskfilter of the paint to EmbossMaskFilter 
	    private Paint mTrackColor = new Paint();
	    private Paint mFillColor = new Paint();
	    private Paint mSecondaryColor = new Paint();
	    MaskFilter  mEmboss = new EmbossMaskFilter(new float[] { 0.0f, -1.0f, 0.5f},
	            0.8f, 15, 1.0f);
	    
	    double distance(double x, double y, double a, double b)
	    {
	        return Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
	    }
	    
	    @SuppressWarnings("unused")
		@Override
	    public boolean onTouchEvent(MotionEvent event) {

	        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN ) {
	            float xPosition = event.getX();
	            float yPosition = event.getY();
	            
	            fromUser = true;
	        	//how far from the arc should a touch point treated as it's on the arc
	        	//float maxDiff = (float) 20;

	            float radius;

	            if (width > height) {
	                radius = height ;
	            } else {
	                radius = width ;
	            }
	        	
	        	//calculate the distance of the touch point from the center of your circle
	        	float dist = (float) (Math.pow(xPosition-centerX,2.0) + Math.pow(yPosition -  centerY,2.0));
	        	dist = (float) Math.sqrt(dist); 
	        	dist = (float) distance(xPosition,yPosition, centerX, centerY);
	        	//We also need the bounding rect of the top half of the circle (the visible arc)
	        	RectF topBoundingRect = outerCircle;


	        //	if (Math.abs(dist - radius)  <= maxDiff  &&
	       //	  topBoundingRect.contains(xPosition, yPosition)) {
	        	  // the user is touching the arc 

	                if (outerCircle.contains(xPosition, yPosition)) {

	                    float x = xPosition - (left);
	                    float s = x * maximumValue;
	                    float b = s / outerCircle.width();
	                    value = Math.round(b);
	                  //  sweep = (122 / 100.0f) * (float) value;

	                    invalidate();

	                } else {
	                    if (xPosition < left) {
	                    //	value = value;

	                      //  sweep = (122 / 100.0f) * (float) value;
	                      //  invalidate();
	                    }
	                    if (xPosition > right) {
	                    	//value = value;

	                      //  sweep = (122 / 100.0f) * (float) value;
	                    //    invalidate();
	                    }
	                }
	        		
	        //	}
	        	
	        	


	      
	        	if (mListener != null)
	        	mListener.onProgressChanged(null, (int) value, fromUser);
	        	fromUser = false;
	        }

	        return true;
	    }
	
}
