package com.idea.opengles.ui;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.idea.opengles.render.AirHockeyRenderer;

public class AirHockeyActivity extends Activity {

    private GLSurfaceView mGLSurfaceView;
    private boolean mRenderSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        setContentView(mGLSurfaceView);
        initOpenGles();
    }

    private void initOpenGles() {
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportGLEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportGLEs2) {
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setRenderer(new AirHockeyRenderer());
            mRenderSet = true;
        } else {
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRenderSet) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRenderSet) {
            mGLSurfaceView.onPause();
        }
    }

}
