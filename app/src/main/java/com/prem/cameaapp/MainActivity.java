package com.prem.cameaapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    FileOutputStream outputStream;
    File filePath,dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasCamera())
            finish();
        if (!CheckThePermission.CheckPermissions(this))
            CheckThePermission.RequestPermissions(this);
         dir=SetThePath.SetPathFroImage();
         ImageCapture();
       }
    //to open Camera App
    public void ImageCapture() {
        Intent intent = new Intent();
            // if (intent.resolveActivity(getPackageManager()) != null) {
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            // }
      }
    //on getting the photo (it will run just after startActivityForResult()
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                File file = new File(dir, "Img" + System.currentTimeMillis() + ".jpg");
                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    ImageCapture();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //imageView.setImageBitmap(bitmap);
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
    //check either device has camera or not
    public  boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
