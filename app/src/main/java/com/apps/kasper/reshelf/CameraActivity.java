package com.apps.kasper.reshelf;


        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Matrix;
        import android.graphics.Rect;
        import android.hardware.Camera;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.media.ExifInterface;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Vibrator;
        import android.util.Log;
        import android.view.Display;
        import android.view.Surface;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.FrameLayout;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;


public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    static File mediaFile;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        intent = new Intent(this, AddBookInfo.class);

        // Create an instance of Camera
        mCamera = AppConfig.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(25);
                mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                AppConfig.photoPath = mediaFile.toString();
                rotateImage(mediaFile.toString());
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "ReShelf");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ReShelf", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public void rotateImage(String file) throws IOException {

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(file, opts);
        int rotationAngle = 0;

        if (ScreenOrientation()=="Portrait") rotationAngle=90;
        else if (ScreenOrientation()=="Reverse Landscape") rotationAngle=180;
        else;

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        FileOutputStream fos = new FileOutputStream(file);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        //finish camera activity
        startActivity(intent);
        finish();
    }

    public String ScreenOrientation() {
        String orientation;
        int rotation =  getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_90:
                orientation = "Landscape";
                break;
            case Surface.ROTATION_270:
                orientation = "Reverse Landscape";
                break;
            default:
                orientation = "Portrait";
                break;
        }
        return orientation;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(intent);
        finish();
    }
}
