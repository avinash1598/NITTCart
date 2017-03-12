package com.example.avinash.nittcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by AVINASH on 3/11/2017.
 */
public class postadd extends MainActivity {
    public static View popupView;
    public static PopupWindow pwindo;
    public static ImageView chatHead;
    Context context;

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
        pwindo.setWidth(width - 10);

        pwindo.showAsDropDown(chatHead, 30, 230);
        ctgitem c =new ctgitem(context,popupView);
        itemcondition i = new itemcondition(context,popupView);
        ImageView iv = (ImageView)pwindo.getContentView().findViewById(R.id.cam);
    }

    public void dismiss(){
        pwindo.dismiss();

    }

}
