package fr.umontpellier.localisermonenfant.utils;

import android.app.ActivityManager;
import android.content.Context;

public class CheckIfServiceIsRunning {
    public Context context;
    public CheckIfServiceIsRunning(Context context){
        this.context = context;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
