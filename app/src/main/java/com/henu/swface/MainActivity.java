package com.henu.swface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.params.Face;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chenyufengweb.howold.FaceActivity;
import com.techshino.eyekeysdk.entity.FaceAttrs;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private Button button_register,button_sign_in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			callCamera();
		}
	}

	private void initUI() {
		button_register = (Button) this.findViewById(R.id.button_register);
		button_sign_in = (Button) this.findViewById(R.id.button_sign_in);
		button_register.setOnClickListener(this);
		button_sign_in.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.button_register:
				Intent intent = new Intent(this,VideoRecognise.class);
				startActivity(intent);
				break;
			case R.id.button_sign_in:
				Intent intent2 = new Intent(this,FaceActivity.class);
				startActivity(intent2);
				break;
			default:
				break;
		}
	}


	//处理申请权限的结果
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 1:
				int gsize=grantResults.length;
				int grantResult = grantResults[0];
				int flag=0;
				for(int i=0;i<gsize;i++)
				{
					if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
						flag=1;
					}
				}
				if (flag==0) {
					Toast.makeText(MainActivity.this, "申请权限成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "you refused the camera function", Toast.LENGTH_SHORT).show();
					this.finish();
				}
				break;
		}
	}

	public void callCamera() {
		String callPhone = Manifest.permission.CAMERA;
		String writestorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;;
		String[] permissions = new String[]{callPhone,writestorage};
		int selfPermission = ActivityCompat.checkSelfPermission(this, callPhone);
		int selfwrite = ActivityCompat.checkSelfPermission(this, writestorage);

		if (selfPermission != PackageManager.PERMISSION_GRANTED || selfwrite != PackageManager.PERMISSION_GRANTED ) {
			ActivityCompat.requestPermissions(this, permissions, 1);
		} else {

		}
	}
	public void startphoto(){
		Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file=new File(Environment.getExternalStorageDirectory().toString()+"/DCIM"+"head0.jpg");
		if(file.exists())
			file.delete();
		intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent2, 2);// 采用ForResult打开
	}

}
