package com.example.photodrawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.photodrawer.ColorPickerDialog.OnColorChangedListener;

public class DrawActivity extends Activity implements OnColorChangedListener {

	private DataBase database;
	private DrawingView drawingView;
	private ArrayAdapter<Float> adapter;
	private int defaultColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		
		defaultColor=Color.BLUE;
		database = DataBase.getInstance();
		drawingView = (DrawingView) findViewById(R.id.drawingView1);
		Drawable d = new BitmapDrawable(getResources(),database.getBitmap());
		drawingView.setBackground(d);
		Spinner widthSpinner=(Spinner) findViewById(R.id.spinner1);
		Float[] items = new Float[]{1f,2f,3f, 4f};
	    adapter = new ArrayAdapter<Float>(this,android.R.layout.simple_spinner_item, items);
		widthSpinner.setAdapter(adapter);
		widthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				drawingView.setPaintStrokeWidth(adapter.getItem(arg2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		drawingView.setPaintColor(defaultColor);
		Button bt=(Button)findViewById(R.id.button1);
		bt.setBackgroundColor(defaultColor);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		 Bitmap b = Bitmap.createBitmap( drawingView.getWidth(),drawingView.getHeight(), Bitmap.Config.ARGB_8888);                
		    Canvas c = new Canvas(b);
		    drawingView.layout(drawingView.getLeft(), drawingView.getTop(), drawingView.getRight(), drawingView.getBottom());
		    drawingView.draw(c);
		    database.setBitmap(b);
		super.onPause();
	}
	public void onClick(View view){
		ColorPickerDialog color = new ColorPickerDialog(this,this, "picker",defaultColor,defaultColor);
        color.show();
	}
	@Override
	public void colorChanged(String key, int color) {
		// TODO Auto-generated method stub
		Button bt=(Button)findViewById(R.id.button1);
		bt.setBackgroundColor(color);
		defaultColor=color;
		drawingView.setPaintColor(color);
	}

}
