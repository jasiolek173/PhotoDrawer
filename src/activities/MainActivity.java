package activities;

import java.io.File;
import java.io.FileOutputStream;

import other.DataBase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.photodrawer.R;
import com.example.photodrawer.R.layout;
import com.example.photodrawer.R.menu;
import com.example.photodrawer.R.string;

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
		Uri u=dataBase.saveBitmap("Image.jpg");
		if(u!=null){
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			emailIntent.setType("application/image");
			emailIntent.putExtra(Intent.EXTRA_STREAM, u);
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));}
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
