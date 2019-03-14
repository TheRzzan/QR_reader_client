package com.morozov.qr_reader_v2_0;

import android.hardware.Camera;
import android.widget.TextView;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.SymbolSet;

/**
 * This class will determine how the camera behave while previewing the image.
 */
public class CamEvent implements Camera.PreviewCallback {
    private ImageScanner scanner;
    private TextView textView_result;

    public CamEvent(TextView textView_result_){
        textView_result = textView_result_;

        scanner = new ImageScanner();
        //Start scanner configuration.
        //Three parameters: 1-element to configure, 2-configuration, 3-config value.
        scanner.setConfig(0, Config.X_DENSITY, 3); //This is the striding value while scanning in X direction.
        // The first zero means general configurations.
        scanner.setConfig(0, Config.Y_DENSITY, 3); //The same for Y.
        scanner.setConfig(0, Config.ENABLE, 0); //To disable all symbols used in scanner.
        scanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1); //Enabling QR code scanning ability only for optimization and speed.
    }

    //onPreviewFrame is called while the camera is previewing on the screen.
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Camera.Parameters camPara = camera.getParameters();
        Camera.Size size = camPara.getPreviewSize();

        int width = size.width;
        int height = size.height;

        Image image = new Image(width, height, "Y800"); //Y800 is the image type = colored.
        image.setData(data); //Set image data to camera byte array = camera displayed frame.
        int result = scanner.scanImage(image);

        String value = "";
        if (result != 0 && result != -1){
            SymbolSet symbols = scanner.getResults(); //The returned results from the scanned image.
            for (Symbol sym : symbols){
                value = sym.getData(); //Returns data scanned in a string form.
                break; //To end the loop after getting the string.
            }

            //Do something
            textView_result.setText(value);

            camera.setPreviewCallback(null);
            camera.stopPreview(); //To stop camera after finish scanning.
        }else {
            textView_result.setText("Не распознал");
        }
    }
}
