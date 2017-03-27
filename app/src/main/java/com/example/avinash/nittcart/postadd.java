package com.example.avinash.nittcart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.IOException;
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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                pickimage();
            }
        }, 700);

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

    public postadd(){}

    public postadd(WindowManager windowManager, Context context, FloatingActionsMenu fabMenu) {

        this.context = context;
        chatHead = new ImageView(context);
        chatHead.setImageResource(android.R.drawable.ic_dialog_email);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                PixelFormat.TRANSLUCENT);
        Point p = new Point();
        windowManager.getDefaultDisplay().getSize(p);
        int width = p.x;
        int height = p.y;

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        //finish();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.from(context).inflate(R.layout.postad, null);
        pwindo = new PopupWindow(popupView, AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT);

        pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        pwindo.setWidth(width - 10);

        pwindo.setContentView(popupView);
        //pwindo.showAtLocation(popupView,Gravity.CENTER,30,230);
        pwindo.showAsDropDown(chatHead, 30, 230);

        pwindo.setOutsideTouchable(true);
        //pwindo.setFocusable(true);


        pwindo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pwindo.update();

        ctgitem c =new ctgitem(context,popupView);
        itemcondition i = new itemcondition(context,popupView);
        ImageView iv = (ImageView)pwindo.getContentView().findViewById(R.id.cam);
        keyboardmanager();

    }

    public void dismiss(){
        //pwindo.setFocusable(false);
        //pwindo.update();
        pwindo.dismiss();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void keyboardmanager(){

        MainActivity.frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                MainActivity.frameLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = MainActivity.frameLayout.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d("keyboard", "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    Log.d("opened", "keyboard");
                    pwindo.setFocusable(true);
                    pwindo.update();
                } else {
                    // keyboard is closed
                    Log.d("closed", "keyboard");
                    pwindo.setFocusable(false);
                    pwindo.update();

                }
            }
        });

        ScrollView scroll = (ScrollView) pwindo.getContentView().findViewById(R.id.scroll);

        /*scroll.OnScrollChangeListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.d("iside", "drag");
                move = true;
            }
        });*/

        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("avi","down");
                        move=false;
                        break;
                    case MotionEvent.ACTION_MOVE://Log.d("iside","move");
                        move=true;
                        break;
                    case MotionEvent.ACTION_UP:if(move){pwindo.setFocusable(false);
                        pwindo.update();}
                        Log.d("iside","up");
                        break;
                }
                return false;
            }
        });

        /*scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        move = false;
                        Log.d("iside", "down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("iside", "");
                        move = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (move) {
                            pwindo.setFocusable(false);
                            pwindo.update();
                        }
                        break;
                }

                return false;
            }
        });
        */


        final EditText e=(EditText)pwindo.getContentView().findViewById(R.id.item_price);
        final EditText itemname = (EditText)pwindo.getContentView().findViewById(R.id.item_name);
        final EditText des = (EditText)pwindo.getContentView().findViewById(R.id.item_description);


        e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: move=false;
                        pwindo.setFocusable(true);
                        pwindo.update();
                    break;
                    case MotionEvent.ACTION_MOVE:Log.d("iside",""); move=true;
                    break;
                    case MotionEvent.ACTION_UP:if(move){pwindo.setFocusable(false);
                        pwindo.update();}
                        Log.d("iside","up");
                        break;
                }
                return false;

            }
        });


        itemname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: move=false;
                        pwindo.setFocusable(true);
                        pwindo.update();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (move) {
                            pwindo.setFocusable(false);
                            pwindo.update();
                        }
                        Log.d("iside","up");
                        break;
                }
                return false;
            }
        });

        des.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: move=false;
                        Log.d("avi","down");
                        pwindo.setFocusable(true);
                        pwindo.update();
                        break;
                    case MotionEvent.ACTION_MOVE: move=true;
                        break;
                    case MotionEvent.ACTION_UP:if(move){pwindo.setFocusable(false);
                        pwindo.update();}
                        Log.d("iside","up");
                        break;
                }
                return false;
            }
        });
    }


    public void pickimage(){
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "NittCart" + File.separator);
        root.mkdirs();
        final String fname = "NittCart"+System.currentTimeMillis()+".png";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        //final Intent galleryIntent = new Intent();
        //galleryIntent.setType("image/*");
        //galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("inside", "result");
        if (resultCode == RESULT_OK) {Log.d("inside","result2");
            if(requestCode == 2){
                //Bundle extras = data.getExtras();
                Bitmap pic;// = extras.getParcelable("data");
                Uri imageUri = data.getData();
                try {
                    pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    iv.setImageBitmap(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Log.d("image size",""+pic.getConfig());
            }
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {Log.d("inside","result3");
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {Log.d("inside","result4");
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                    Crop(selectedImageUri);
                    Log.d("image",selectedImageUri.toString());
                } else {
                    Log.d("aaya", "data.getdata()");
                    selectedImageUri = data == null ? null : data.getData();
                    Crop(selectedImageUri);
                }
            }
        }
    }

    public void Crop(Uri picuri){

        try{//Intent cropIntent = new Intent("com.android.camera.action.CROP");
            final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "NittCart" + File.separator);
            root.mkdirs();
            final String fname = "NittCart"+System.currentTimeMillis()+".png";
            final File sdImageMainDirectory = new File(root, fname);
            outputFileUri2 = Uri.fromFile(sdImageMainDirectory);

            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            File f = new File(picuri.toString());
            Uri contentUri = Uri.fromFile(f);
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
