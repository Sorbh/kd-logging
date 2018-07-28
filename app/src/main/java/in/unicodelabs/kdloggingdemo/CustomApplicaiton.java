package in.unicodelabs.kdloggingdemo;

import android.app.Application;

import in.unicodelabs.kdlogging.data.KdLog;

public class CustomApplicaiton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KdLog.initialize(this);
    }
}
