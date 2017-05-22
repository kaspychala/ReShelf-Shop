package com.apps.kasper.reshelf;


        import android.app.Activity;
        import android.graphics.Rect;
        import android.hardware.Camera;
        import android.os.Bundle;
        import android.widget.FrameLayout;

        import java.util.ArrayList;
        import java.util.List;


public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = AppConfig.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }
}