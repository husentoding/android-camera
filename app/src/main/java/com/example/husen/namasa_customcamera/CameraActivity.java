package com.example.husen.namasa_customcamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private Camera camera;
    private FrameLayout cameraFrame;
    private ShowCamera sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    100);
        }
        Button cangkir = (Button) findViewById(R.id.cangkir);
        cangkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage(view);
            }
        });

        cameraFrame = (FrameLayout) findViewById(R.id.cameraFrame);

        camera= camera.open();
        sc = new ShowCamera(getApplicationContext(), camera);
        cameraFrame.addView(sc);

    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picture = getOutputMediaFile();

            if(picture == null){
                return;
            }else{
                try {
                    FileOutputStream fos = new FileOutputStream(picture);
                    fos.write(data);
                    fos.close();
                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    private File getOutputMediaFile(){

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
//        String state = Environment.getExternalStorageState();
//        if(!state.equals(Environment.MEDIA_MOUNTED)){
//            return null;
//        }else{
//            File folder_gui= new File(Environment.getExternalStorageDirectory() + File.separator+ "GUI");
//            if(!folder_gui.exists()){
//                folder_gui.mkdirs();
//            }
//            File outputfile = new File(folder_gui, "temp.jpg");
//            return outputfile;
//        }
    }

    public  void captureImage(View view){
        if(camera!=null){
            camera.takePicture(null, null, mPictureCallback);
        }
    }
}
