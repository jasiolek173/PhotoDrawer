package com.example.photodrawer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class DataBase {
	private Bitmap bitmap;
	public Drawable d;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	private static DataBase _instance;

	public synchronized static DataBase getInstance() {
		if (_instance == null) {
			_instance = new DataBase();
		}
		return _instance;
	}

	public DataBase() {
	}
}
