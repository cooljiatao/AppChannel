package com.uuuo.appchannel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Created by JiaTao on 2018/4/11.
 * Email: 535318720@qq.com
 * Version: 1.0
 * Description:
 */
public class ChannelReaderUtil {
    private static final String TAG = "ChannelReaderUtil";
    private static String mChannelCache;

    public ChannelReaderUtil() {
    }

    public static String getChannel(Context context) {
        if(mChannelCache == null) {
            String channel = getChannelByV2(context);
            if(channel == null) {
                channel = getChannelByV1(context);
            }

            mChannelCache = channel;
        }

        return mChannelCache;
    }

    public static String getChannelByV2(Context context) {
        String apkPath = getApkPath(context);
        String channel = ChannelReader.getChannel(new File(apkPath));
        Log.i("ChannelReaderUtil", "getChannelByV2 , channel = " + channel);
        return channel;
    }

    public static String getChannelByV1(Context context) {
        String apkPath = getApkPath(context);
        String channel = ChannelReader.getChannelByZipComment(new File(apkPath));
        Log.i("ChannelReaderUtil", "getChannelByV1 , channel = " + channel);
        return channel;
    }

    public static String getStringValueById(Context context, int id) {
        String apkPath = getApkPath(context);
        String value = IdValueReader.getStringValueById(new File(apkPath), id);
        Log.i("ChannelReaderUtil", "id = " + id + " , value = " + value);
        return value;
    }

    public static byte[] getByteValueById(Context context, int id) {
        String apkPath = getApkPath(context);
        return IdValueReader.getByteValueById(new File(apkPath), id);
    }

    public static Map<Integer, ByteBuffer> getAllIdValueMap(Context context) {
        String apkPath = getApkPath(context);
        return IdValueReader.getAllIdValueMap(new File(apkPath));
    }

    private static String getApkPath(Context context) {
        String apkPath = null;

        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if(applicationInfo == null) {
                return null;
            }

            apkPath = applicationInfo.sourceDir;
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

        return apkPath;
    }
}
