package other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	private Paint paint = new Paint();
	private Path path = new Path();
	private List<Path> paths=new ArrayList<Path>();
	private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>(); 
	private Map<Path,Float> thicknessMap=new HashMap<Path,Float>();
	private int selectedColor=Color.BLUE;
	private float selectedthickness=5f;

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setStrokeWidth(5f);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}
	public void setPaintColor(int c){
		paint.setColor(c);
		selectedColor=c;
	}
	public void setPaintStrokeWidth(float f){
		paint.setStrokeWidth(f);
		selectedthickness=f;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		  for (Path p : paths)
		    {
		        paint.setColor(colorsMap.get(p));
		        paint.setStrokeWidth(thicknessMap.get(p));
		        canvas.drawPath(p, paint);
		    }
		    paint.setColor(selectedColor);
		    paint.setStrokeWidth(selectedthickness);
		    canvas.drawPath(path, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.reset();
			path.moveTo(eventX, eventY);
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(eventX, eventY);
			break;
		case MotionEvent.ACTION_UP:
		    paths.add(path);
		    colorsMap.put(path,selectedColor); // store the color of mPath
		    thicknessMap.put(path, selectedthickness);
		    path=new Path();
		default:
			return false;
		}
		invalidate();
		return super.onTouchEvent(event);
	}

}
