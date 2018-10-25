package com.paandw.pieceofcake;

import android.app.Application;

public class CakeApplication extends Application {

    private static CakeApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static CakeApplication get() { return app; }
}
