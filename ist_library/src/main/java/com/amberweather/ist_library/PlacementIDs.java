package com.amberweather.ist_library;

import android.content.Context;

import com.amberweather.ist_library.utils.PreferenceUtils;

/**
 * Created by back2Nature on 2016/5/8.
 */
public class PlacementIDs {
    public static String getNativeAdId(Context context){
      return   PreferenceUtils.getNativeAdPlacementId(context);
    }
    public static String getInterstitialAdId(Context context){
        return PreferenceUtils.getInterstitialAdPlacementId(context);
    }

    public static boolean isFromIST(Context context){
        return PreferenceUtils.getSavedReferrer(context).toLowerCase().contains("ist_media");
    }
}
