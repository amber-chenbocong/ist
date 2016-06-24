//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.amberweather.ist_library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.amberweather.ist_library.R.string;

public class PreferenceUtils {
    public static boolean isForIst = true;
    static String IST_PREFERENCE = "IST";

    public PreferenceUtils() {
    }

    public static void saveReferrer(Context context, String referrer) {
        if (referrer == null) {
            referrer = "";
        }

        getEditor(context).putString("ist_referrer", referrer).commit();
        if (context.getPackageName().equals("mobi.infolife.ezweather")) {
            if (referrer.contains("FROM_IST_WIDGET")) {
                saveIstTokenId(context, "cf51cb941930344c6a853938f794802e");
            }
            if (!referrer.contains("IST_MEDIA") && referrer.contains("campaignid")) {
                saveIstTokenId(context, "791d11f4cc49e92fd8715c6d1b87c502");
            }
            if (!referrer.contains("_FROM_IST_WIDGET") && (referrer.contains("utm_source%3dwidget") || referrer.contains("utm_source=widget"))) {
                saveIstTokenId(context, "a256e56d1e49b858972697c629da846f");
            }
        } else if (context.getPackageName().startsWith("mobi.infolife.ezweather.widget")) {
            if (referrer.contains("amber_weather")) {
                saveIstTokenId(context, "3c455ded49e5c8ec819019b9c36a1f1f");
            }
            if (!referrer.contains("IST_MEDIA") && referrer.contains("campaignid")) {
                saveIstTokenId(context, "7b91e648f7042055f6ebd5bbb471c7f8");
            }
        }

        String istTokenId = "";
        String nativeAdPlaceMent = " ";
        String interAdPaceMent = " ";

        try {
            istTokenId = referrer.split("\\|")[1];
            saveIstTokenId(context, istTokenId);
            nativeAdPlaceMent = referrer.split("\\|")[2];
            if (nativeAdPlaceMent.contains(context.getResources().getString(string.ist_facebook_app_id))) {
                saveNativeAdPlacementId(context, nativeAdPlaceMent);
            }

            interAdPaceMent = referrer.split("\\|")[3];
            if (interAdPaceMent.contains(context.getResources().getString(string.ist_facebook_app_id))) {
                saveInterPlacementId(context, interAdPaceMent);
            }

            Log.d("wzw", "1:" + istTokenId + ",2:" + nativeAdPlaceMent + ",3:" + interAdPaceMent);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static String getSavedReferrer(Context context) {
        return getPre(context).getString("ist_referrer", "");
    }

    public static void saveNativeAdPlacementId(Context context, String placeMentId) {
        getEditor(context).putString("ist_native_placementid", placeMentId).commit();
        Log.d("wzw", "SAVE NATIVE AD ID :" + placeMentId);
    }

    public static String getNativeAdPlacementId(Context context) {
        return getPre(context).getString("ist_native_placementid", context.getResources().getString(string.ist_default_placement_id));
    }

    public static void saveInterPlacementId(Context context, String placeMentId) {
        getEditor(context).putString("ist_inter_placementid", placeMentId).commit();
        Log.d("wzw", "SAVE INTER AD ID :" + placeMentId);
    }

    public static String getInterstitialAdPlacementId(Context context) {
        return getPre(context).getString("ist_inter_placementid", context.getResources().getString(string.ist_default_placement_interstitial_id));
    }

    public static void saveIstTokenId(Context context, String tokenId) {
        getEditor(context).putString("ist_token_id", tokenId).commit();
    }

    public static String getIstTokenId(Context context) {
        return getPre(context).getString("ist_token_id", context.getResources().getString(string.ist_token_id));
    }

    static Editor getEditor(Context context) {
        return context.getSharedPreferences(IST_PREFERENCE, 0).edit();
    }

    static SharedPreferences getPre(Context context) {
        return context.getSharedPreferences(IST_PREFERENCE, 0);
    }

    public static void setSendInstallEvent(Context context, boolean firstStartAppStatue) {
        getEditor(context).putBoolean("send_install_event", firstStartAppStatue).commit();
    }

    public static boolean hasSendInstallEvent(Context context) {
        return getPre(context).getBoolean("send_install_event", false);
    }

    public static void setFirstStartAppStatue(Context context, boolean firstStartAppStatue) {
        getEditor(context).putBoolean("first_start_app_statue", firstStartAppStatue).commit();
    }

    public static boolean getFirstStartAppStatue(Context context) {
        return getPre(context).getBoolean("first_start_app_statue", true);
    }
}
