package com.amberweather.ist_library;

import android.content.Context;
import android.content.Intent;

import com.amberweather.ist_library.utils.IstService;


/**
 * Created by jsen on 2016/4/18.
 */
public class IstApi {
    public void onAppInstalled(Context context, String referrer) {
        Intent startIntent = new Intent(context, IstService.class);
        startIntent.putExtra(IstService.SERVICE_TYPE, IstService.INSTALL);
        startIntent.putExtra(IstService.REFERRER, referrer);
        context.startService(startIntent);
    }

    public void onAppStarted(Context context) {
        Intent startIntent = new Intent(context, IstService.class);
        startIntent.putExtra(IstService.SERVICE_TYPE, IstService.STAR);
        context.startService(startIntent);
    }
}