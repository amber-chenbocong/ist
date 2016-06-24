package com.amberweather.ist_library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

/**
 * Created by jsen on 2016/4/18.
 */
public class ReferrerReceiver extends BroadcastReceiver {
    public static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";

    public void onReceive(final Context context, Intent intent) {

        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_INSTALL_REFERRER)) {
                try {
                    CampaignTrackingReceiver gaCampaignTrackingReceiver = new CampaignTrackingReceiver();
                    gaCampaignTrackingReceiver.onReceive(context, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String referrer = intent.getStringExtra("referrer");
                new IstApi().onAppInstalled(context, referrer);
                Log.d("WZW", "referrer:::" + referrer);
            }
        }
    }
}