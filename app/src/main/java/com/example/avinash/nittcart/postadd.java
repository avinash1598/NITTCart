package com.example.avinash.nittcart;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AVINASH on 3/11/2017.
 */
public class postadd extends AppCompatActivity {
    public static View popupView;
    public static PopupWindow pwindo;
    public static ImageView chatHead;
    Context context;
    private Uri outputFileUri,outputFileUri2;
    private int YOUR_SELECT_PICTURE_REQUEST_CODE=0;
    boolean move=false;
    Bitmap image;

    String[] tag = {
            "Book","Drafter","Sports","Electronics","Others"
    } ;
    int[] imageId = {
            R.drawable.book,R.drawable.book,
            R.drawable.book,R.drawable.book,R.drawable.book
    };

    String[] tag2 = {
            "Good as new", "Fairly used", "Heavily used"
    };
    int[] imageId2 = {
            R.drawable.condnew , R.drawable.used, R.drawable.old
    };

    @Bind(R.id.gridview)
    GridView gridtype;
    @Bind(R.id.gridview2)
    GridView gridcondition;
    @Bind(R.id.cam)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postad);
        ButterKnife.bind(this);

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                pickimage();
            }
        }, 700);*/

        itemtype adapter = new itemtype(postadd.this, tag, imageId);
        gridtype.setAdapter(adapter);
        itemtype adapter2 = new itemtype(postadd.this, tag2, imageId2);
        gridcondition.setAdapter(adapter2);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickimage();
            }
        });
    }



    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public void pickimage(){

        startActivityForResult(getPickImageChooserIntent(), 200);

    }

    private Uri getCaptureImageOutputUri() {
        final File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "NittCart" + File.separator);
        root.mkdirs();
        final String fname = "NittCart"+System.currentTimeMillis()+".jpeg";
        final File sdImageMainDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fname);
        //outputFileUri = Uri.fromFile(sdImageMainDirectory);
        outputFileUri= getImageContentUri(postadd.this,sdImageMainDirectory);
        //Uri outputFileUri = null;
        //File getImage = Environment.getExternalStorageDirectory();
        //if (getImage != null) {
          //  outputFileUri = Uri.fromFile(new  File(getImage.getPath(), "nittcart"+System.currentTimeMillis()+".jpeg"));
        //}
        return outputFileUri;
    }

    public Uri getPickImageResultUri(Intent  data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            Log.e("not null",data.getData().toString());
            String action = data.getAction();
            isCamera = action != null  && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ?  getCaptureImageOutputUri() : data.getData();
    }

    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    protected void onActivityResult(int  requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = getPickImageResultUri(data);
            //Log.d("imageuri",imageUri.toString());
            if(data.getExtras()!=null){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(photo);}
            //iv.setImageBitmap(iv.getCroppedImage(500, 500));
            //Log.d("imageuri", imageUri.toString());
            if (requestCode == 2) {
                //Bundle extras = data.getExtras();
                Bitmap pic;// = extras.getParcelable("data");
                //Uri imageUri = data.getData();
                try {
                    pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    iv.setImageBitmap(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Log.d("image size",""+pic.getConfig());
            } else {
                Log.e("inside", "yobaby");

                //iv.setImageURI(imageUri);
                // For API >= 23 we need to check specifically that we have permissions to read external storage,
                // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
                boolean requirePermissions = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        isUriRequiresPermissions(imageUri)) {

                    // request permissions and handle the result in onRequestPermissionsResult()
                    requirePermissions = true;
                    outputFileUri = imageUri;
                    Log.d("outputuri", imageUri.toString());
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }

                if (!requirePermissions) {
                    //mCropImageView.setImageUriAsync(imageUri);
                }
                Crop(imageUri);
            }
        }
    }

    public Intent getPickImageChooserIntent() {

// Determine Uri of camera image to  save.
        Uri outputFileUri =  getCaptureImageOutputUri();
        List<Intent> allIntents = new  ArrayList<>();
        PackageManager packageManager =  getPackageManager();

// collect all camera intents
        Intent captureIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam =  packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new  Intent(captureIntent);
            intent.setComponent(new  ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                Log.d("output",outputFileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
        Intent galleryIntent = new  Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery =  packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new  Intent(galleryIntent);
            intent.setComponent(new  ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

// the main intent is the last in the  list  so pickup the useless one
        Intent mainIntent =  allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if  (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

// Create a chooser from the main  intent
        Intent chooserIntent =  Intent.createChooser(mainIntent, "Select source");

// Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,  allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    public void Crop(Uri picuri){

        try{//Intent cropIntent = new Intent("com.android.camera.action.CROP");
            final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "NittCart_cropped" + File.separator);
            root.mkdirs();
            final String fname = "NittCart"+System.currentTimeMillis()+".jpeg";
            final File sdImageMainDirectory = new File(root, fname);
            outputFileUri2 = Uri.fromFile(sdImageMainDirectory);

            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            //File f = new File(picuri.toString());
            //Uri contentUri = Uri.fromFile(f);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri2);
            cropIntent.setDataAndType(picuri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 500);
            cropIntent.putExtra("outputY", 500);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 2);

        }catch (Exception e){
            e.printStackTrace();
        }}


}
