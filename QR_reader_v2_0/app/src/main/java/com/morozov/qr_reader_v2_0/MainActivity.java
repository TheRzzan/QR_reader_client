package com.morozov.qr_reader_v2_0;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private FrameLayout frameLayout;
    private Button button_scan;
    private Button button_login;
    private TextView textView_result;
    private String USER_NAME = "EMPTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initElements();
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    protected void initialize(){
        //Assigning the preview and callback class to the camera.
        CameraPreview cameraPreview = new CameraPreview(this, camera); //Passing the camera to the CameraPreview class.
        camera.setPreviewCallback(new CamEvent(textView_result));

        frameLayout.addView(cameraPreview); //This means the camera preview is added in the frame layout UI element to show image
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondStart();
            }
        });
    }

    private void initElements(){
        button_scan = (Button)findViewById(R.id.button_scan);
        button_scan.setEnabled(false);

        button_login = (Button)findViewById(R.id.button_login);

        textView_result = (TextView)findViewById(R.id.textView_result);
        frameLayout = (FrameLayout)findViewById(R.id.FL_camera);

        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); //this function returns a camera instance
        camera.setDisplayOrientation(90); //to run in portrait mode
        Camera.Parameters camPara = camera.getParameters(); //to get camera configurations
        camPara.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); //change it to auto focus
        camera.setParameters(camPara); //set the camera to new parameters
    }

    private void secondStart(){
        try {
            camera.stopPreview();
        }catch (Exception e){
            //Ignore: tried to stop non-existent preview
        }

        camera.setPreviewCallback(new CamEvent(textView_result));
        camera.startPreview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data == null) {return;}
        USER_NAME = data.getStringExtra("USER_NAME");
        textView_result.setText("User name: " + USER_NAME);
        button_scan.setEnabled(true);
    }
}
