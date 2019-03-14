package com.morozov.qr_reader_v2_0;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private FrameLayout frameLayout;
    private Button button_scan;
    private TextView textView_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        button_scan = (Button)findViewById(R.id.button_scan);
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });
    }
    protected void initialize(){
        textView_result = (TextView)findViewById(R.id.textView_result);

        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); //this function returns a camera instance
        camera.setDisplayOrientation(90); //to run in portrait mode
        Camera.Parameters camPara = camera.getParameters(); //to get camera configurations
        camPara.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); //change it to auto focus
        camera.setParameters(camPara); //set the camera to new parameters

        //Assigning the preview and callback class to the camera.
        CameraPreview cameraPreview = new CameraPreview(this, camera); //Passing the camera to the CameraPreview class.
        camera.setPreviewCallback(new CamEvent(textView_result));

        frameLayout = (FrameLayout)findViewById(R.id.FL_camera); //Initialize Frame Layout.
        frameLayout.addView(cameraPreview); //This means the camera preview is added in the frame layout UI element to show image
    }
}
