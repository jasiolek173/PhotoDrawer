package com.example.photodrawer;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

public class DataBase {
	private Bitmap bitmap;
	public Drawable d;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public Uri saveBitmap(String nameOfFile){
		if(bitmap==null)
			return null;
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");    
		myDir.mkdirs();
		String fname = nameOfFile;
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete (); 
		try {
		       FileOutputStream out = new FileOutputStream(file);
		       bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();

		} catch (Exception e) {
		       e.printStackTrace();
		       return null;
		}
		return Uri.fromFile(file);
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
