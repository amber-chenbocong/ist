//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.amberweather.ist_library.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.amberweather.ist_library.Installation;
import com.amberweather.ist_library.R.string;
import com.amberweather.ist_library.utils.HttpRequest;
import com.amberweather.ist_library.utils.PreferenceUtils;
import com.amberweather.ist_library.utils.HttpRequest.HttpRequestListener;
import java.net.URLEncoder;

public class IstService extends Service {
    public static final String SERVICE_TYPE = "service_type";
    public static final String STAR = "star";
    public static final String INSTALL = "install";
    public static final String NO_TYPE = "no_type";
    public static final String REFERRER = "referrer";
    static String installApi = " http://api.imobiletracking.net/api/v2/track?tokenid=";
    HttpRequest httpRequest = new HttpRequest();
    private Context context;

    public IstService() {
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String serviceType = "no_type";

        try {
            serviceType = intent.getStringExtra("service_type");
        } catch (NullPointerException var8) {
            var8.printStackTrace();
        }

        if(serviceType.equals("star")) {
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    IstService.this.onAppStarted(IstService.this.context);
                }
            }, 3000L);
        } else if(serviceType.equals("install")) {
            String referrer = null;

            try {
                referrer = intent.getStringExtra("referrer");
            } catch (NullPointerException var7) {
                var7.printStackTrace();
            }

            this.onAppInstalled(this.context, referrer);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void onAppInstalled(Context context, String referrer) {
        if(!PreferenceUtils.hasSendInstallEvent(context)) {
            PreferenceUtils.setSendInstallEvent(context, true);
            PreferenceUtils.saveReferrer(context, referrer);
            this.httpRequest.request(this.getInstallUrl(context, referrer), new IstService.requestListener());
        }

    }

    public void onAppStarted(Context context) {
        if(!PreferenceUtils.hasSendInstallEvent(context)) {
            this.onAppInstalled(context, (String)null);
            PreferenceUtils.setFirstStartAppStatue(context, false);
        } else if(!PreferenceUtils.getFirstStartAppStatue(context)) {
            this.httpRequest.request(this.getStartUrl(context), new IstService.requestListener());
        } else {
            PreferenceUtils.setFirstStartAppStatue(context, false);
        }

    }

    public String getImei(Context context) {
        return Installation.id(context);
    }

    public String getInstallUrl(Context context, String referrer) {
        if (referrer == null||referrer.length()==0){
            referrer = "null";
        }
        referrer = URLEncoder.encode(referrer);
        return installApi + PreferenceUtils.getIstTokenId(context) + "&imei=" + this.getImei(context) + "&event=install" + "&referrer=" + referrer;
    }

    public String getStartUrl(Context context) {
        return installApi + PreferenceUtils.getIstTokenId(context) + "&imei=" + this.getImei(context) + "&event=start";
    }

    class requestListener implements HttpRequestListener {
        requestListener() {
        }

        public void onComplete(String data) {
            Log.d("wzw", "API REQUEST COMPLETED :" + data);
            IstService.this.onDestroy();
        }

        public void onError() {
            Log.d("wzw", "API REQUEST ERROR !");
            IstService.this.onDestroy();
        }
    }
}
