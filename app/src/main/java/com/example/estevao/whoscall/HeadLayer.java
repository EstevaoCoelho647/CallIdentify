package com.example.estevao.whoscall;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Creates the head layer view which is displayed directly on window manager.
 * It means that the view is above every application's view on your phone -
 * until another application does the same.
 */
public class HeadLayer extends View {

    private Context mContext;
    private String tel;
    private String name;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private ImageView btnClose;

    public HeadLayer(Context context, String tel, String name) {
        super(context);
        mContext = context;
        this.tel = tel;
        this.name = name;
        mFrameLayout = new FrameLayout(mContext);
        addToWindowManager();
    }

    public void addToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
        final View inflate = layoutInflater.inflate(R.layout.layout, mFrameLayout);
        inflate.setOnTouchListener(
                new OnTouchListener() {
                    private int initialX;
                    private int initialY;
                    private float initialTouchX;
                    private float initialTouchY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                initialX = params.x;
                                initialY = params.y;
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();
                                return true;
                            case MotionEvent.ACTION_UP:
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                params.x = initialX
                                        + (int) (event.getRawX() - initialTouchX);
                                params.y = initialY
                                        + (int) (event.getRawY() - initialTouchY);
                                mWindowManager.updateViewLayout(inflate, params);
                                return true;
                        }
                        return false;
                    }
                }

        );
        TextView textViewMsg = (TextView) inflate.findViewById(R.id.msg);
        TextView textViewName = (TextView) inflate.findViewById(R.id.name);
        btnClose = (ImageView) inflate.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                destroy();
            }
        });
        textViewMsg.setText(tel);
        textViewName.setText(name);
    }

    /**
     * Removes the view from window manager.
     */

    public void destroy() {
        if (ViewCompat.isAttachedToWindow(mFrameLayout))
            mWindowManager.removeView(mFrameLayout);
    }
}
