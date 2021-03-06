package cn.sensorsdata.demo.util;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yang on 2017/12/15
 */

public class InstrumentationHook extends Instrumentation {

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token,
                                Application application, Intent intent, ActivityInfo info,
                                CharSequence title, Activity parent, String id,
                                Object lastNonConfigurationInstance) throws InstantiationException,
            IllegalAccessException {
        //Log.d(context, " CustomInstrumentation#newActivity call 1");
        return super.newActivity(clazz, context, token, application, intent, info,
                title, parent, id, lastNonConfigurationInstance);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        //Log.d(this, " CustomInstrumentation#newActivity call 3 parmas className:" + className + " intent:" + intent.toString());
        Activity activity = createActivity(intent);
        if (activity != null) {
            return activity;
        }
        return super.newActivity(cl, className, intent);
    }

    /*可以在createActivity拦截替换某个activity，下面自是一个简单例子*/
    protected Activity createActivity(Intent intent) {
        String className = intent.getComponent().getClassName();
        //Log.d(this, "createActivity className=" + className);
        if ("cn.sensorsdata.demo.MainActivity".equals(className)) {
            try {
                Class<? extends Activity> PluginActivity = (Class<? extends Activity>) Class
                        .forName("cn.sensorsdata.demo.DemoActivity");
                return PluginActivity.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}