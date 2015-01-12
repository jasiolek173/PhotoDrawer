package com.example.photodrawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	private DataBase dataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataBase = dataBase.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void makePhoto(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}

	public void draw(View view) {
		if (dataBase.getBitmap() != null) {
			Intent intent = new Intent(this, DrawActivity.class);
			startActivity(intent);
		} else
			Toast.makeText(this,
					getResources().getString(R.string.make_photo_text),
					Toast.LENGTH_SHORT).show();
	}

	public void sendToEmail(View view) {
	    String pathofBmp = Images.Media.insertImage(getContentResolver(), dataBase.getBitmap(),"title", null);
	    Uri bmpUri = Uri.parse(pathofBmp);
	    final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
	    emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri);
	    emailIntent1.setType("image/png");
	    startActivity(emailIntent1); 
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				dataBase.d=new BitmapDrawable(getResources(), (Bitmap) extras.get("data"));
				dataBase.setBitmap((Bitmap) extras.get("data"));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.did_not_make_photo),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
