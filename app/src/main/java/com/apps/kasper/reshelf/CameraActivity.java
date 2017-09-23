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
        import android.media.Image;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.Vibrator;
        import android.support.design.widget.Snackbar;
        import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
        import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
        import android.util.Log;
        import android.view.Display;
        import android.view.Surface;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.Toast;

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
    private ImageView front;
    private ImageView back;
    private ImageView imagePreview;
    private FrameLayout imagePreviewLayout;
    private FrameLayout barLayout;
    private ImageButton backButton;
    private ImageButton deleteButton;
    private ImageButton doneButton;
    private Button captureButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        front = (ImageView)findViewById(R.id.front);
        back = (ImageView)findViewById(R.id.back);
        imagePreview = (ImageView)findViewById(R.id.image_preview);
        imagePreviewLayout = (FrameLayout)findViewById(R.id.image_preview_layout);
        barLayout = (FrameLayout)findViewById(R.id.bar_layout);
        captureButton = (Button) findViewById(R.id.button_capture);
        backButton = (ImageButton) findViewById(R.id.back_button);
        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        doneButton = (ImageButton) findViewById(R.id.done_button);

        if(AppConfig.photoPathFront!="null" && AppConfig.photoPathBack!="null") {
            barLayout.setVisibility(View.VISIBLE);
            captureButton.setEnabled(false);
        }
        if(AppConfig.photoPathFront!="null"){
            front.setAlpha(1f);
            Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathFront);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),myBitmap);
            final float roundPx = (float) myBitmap.getWidth() * 0.04f;
            roundedBitmapDrawable.setCornerRadius(roundPx);
            front.setImageDrawable(roundedBitmapDrawable);
        }
        if(AppConfig.photoPathBack!="null"){
            back.setAlpha(1f);
            Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathBack);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),BitmapFactory.decodeFile(AppConfig.photoPathBack));
            final float roundPx = (float) myBitmap.getWidth() * 0.04f;
            roundedBitmapDrawable.setCornerRadius(roundPx);
            back.setImageDrawable(roundedBitmapDrawable);
        }

        if(AppConfig.photoFront==true){
            imagePreview.setImageBitmap(BitmapFactory.decodeFile(AppConfig.photoPathFront));
            imagePreviewLayout.setVisibility(View.VISIBLE);

        }
        if(AppConfig.photoBack==true){
            imagePreview.setImageBitmap(BitmapFactory.decodeFile(AppConfig.photoPathBack));
            imagePreviewLayout.setVisibility(View.VISIBLE);
        }
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
                if(AppConfig.photoPathFront!="null" || AppConfig.photoPathBack!="null") {
                    vibrator.vibrate(25);
                    mCamera.takePicture(null, null, mPicture);
                    captureButton.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            barLayout.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                }
                else {
                    vibrator.vibrate(25);
                    mCamera.takePicture(null, null, mPicture);
                    captureButton.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            captureButton.setEnabled(true);
                        }
                    }, 500);
                }
            }
        });

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppConfig.photoPathFront!="null"){
                    imagePreview.setImageBitmap(BitmapFactory.decodeFile(AppConfig.photoPathFront));
                    imagePreviewLayout.setVisibility(View.VISIBLE);
                    AppConfig.photoFront = true;
                    AppConfig.photoBack = false;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppConfig.photoPathBack!="null"){
                    imagePreview.setImageBitmap(BitmapFactory.decodeFile(AppConfig.photoPathBack));
                    imagePreviewLayout.setVisibility(View.VISIBLE);
                    AppConfig.photoBack = true;
                    AppConfig.photoFront = false;
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePreviewLayout.setVisibility(View.GONE);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppConfig.photoFront){
                    front.setImageDrawable(null);
                    front.setAlpha(0.5f);
                    AppConfig.photoPathFront="null";
                    barLayout.setVisibility(View.GONE);
                    captureButton.setEnabled(true);
                }
                if(AppConfig.photoBack){
                    back.setImageDrawable(null);
                    back.setAlpha(0.5f);
                    AppConfig.photoPathBack="null";
                    barLayout.setVisibility(View.GONE);
                    captureButton.setEnabled(true);
                }
                imagePreviewLayout.setVisibility(View.GONE);

            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
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
                if(AppConfig.photoPathFront=="null"){
                    AppConfig.photoPathFront = mediaFile.toString();
                }
                else{
                    AppConfig.photoPathBack = mediaFile.toString();
                }
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
        if(front.getDrawable()==null) {
            front.setAlpha(1f);
            Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathFront);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),myBitmap);
            final float roundPx = (float) myBitmap.getWidth() * 0.04f;
            roundedBitmapDrawable.setCornerRadius(roundPx);
            front.setImageDrawable(roundedBitmapDrawable);
        }
        else {
            back.setAlpha(1f);
            Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathBack);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),BitmapFactory.decodeFile(AppConfig.photoPathBack));
            final float roundPx = (float) myBitmap.getWidth() * 0.04f;
            roundedBitmapDrawable.setCornerRadius(roundPx);
            back.setImageDrawable(roundedBitmapDrawable);
        }
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
        if(AppConfig.photoPathFront!="null" && AppConfig.photoPathBack!="null") {
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"Please take pictures of both front and back of a book.",Toast.LENGTH_LONG).show();
        }
    }
}
