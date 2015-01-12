package com.example.photodrawer;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

public class DrawActivity extends Activity {

	private DataBase database;
	private DrawingView drawingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		database = DataBase.getInstance();
		drawingView = (DrawingView) findViewById(R.id.drawingView1);
		Drawable d = new BitmapDrawable(getResources(), database.getBitmap());
		drawingView.setBackground(d);
		drawingView.setImageDrawable(database.d);
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
		//BitmapDrawable d=(BitmapDrawable) drawingView.getDrawable();
		//database.setBitmap(d.getBitmap());
		database.d=drawingView.getDrawable();
		super.onPause();
	}

}
