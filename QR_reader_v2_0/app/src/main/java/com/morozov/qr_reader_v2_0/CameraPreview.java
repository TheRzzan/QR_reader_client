package com.morozov.qr_reader_v2_0;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

/**
 * This class will be used to display the camera live photo.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera cam;
    private SurfaceHolder mHold;

    public CameraPreview(Context context, Camera camera){
        super(context);
        cam = camera;
        mHold = getHolder();
        mHold.addCallback(this);
        mHold.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Setting up the camera display and start working.
        //This function will make the camera start previewing image and working in auto focus mode.
        try {
            cam.setPreviewDisplay(mHold);
            cam.startPreview();
        }catch (IOException ex){
            //To do for exception.
        }
    }

    public void refreshCamera(Camera camera){
        if(mHold.getSurface() == null){
            //preview surface does not exist
            return;
        }

        //Stop preview before making change
        try {
            camera.stopPreview();
        }catch (Exception e){
            //Ignore: tried to stop non-existent preview
        }

        //Set preview size and make any resize, rotate or reformatting changes here
        //Start preview with new settings
        cam  = camera;
        try {
            cam.setPreviewDisplay(mHold);
            cam.startPreview();
        }catch (Exception e){
            Toast.makeText( getContext(), "Error starting camera preview: ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //refreshCamera(cam);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
