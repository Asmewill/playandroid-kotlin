package fall.out.wanandroid.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiuJie on 2015/12/21.
 */
public class SharedPreUtil {
    public static final String SHAREDPREF_URI = "share_ip_port";
    private static SharedPreferences sharedPreUtil = null;
    //缓存key
    public static final String KEY_URI = "key_ip_port";
    public static final String KEY_INFO = "key_info";
    //储位缓存
    public static final String KEY_USELOCATION = "key_uselocation";
    //入库单缓存
    public static final String KEY_INMAKE_LOCALPROD = "key_inmake_orders";
    //工单备料，备料单缓存
    public static final String KEY_SCMAKE_PREPARE = "key_scmake_prepare";
    //飞达上料，备料单缓存
    public static final String KEY_SCMAKE_FEEDER = "key_scmake_feeder";


    //清除IP和PORT的所有缓存
    public static void removeAll(Context pContext) {
        if (sharedPreUtil == null) {
            sharedPreUtil = pContext.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        //清除储位缓存
        sharedPreUtil.edit().remove(KEY_USELOCATION).commit();
        //清除入库单缓存
        sharedPreUtil.edit().remove(KEY_INMAKE_LOCALPROD).commit();
        //清除备料单缓存
        sharedPreUtil.edit().remove(KEY_SCMAKE_PREPARE).commit();
        //清除飞达上料缓存
        sharedPreUtil.edit().remove(KEY_SCMAKE_FEEDER).commit();
    }

    public static void saveString(Context pContext, String key, String value) {
        if (sharedPreUtil == null) {
            sharedPreUtil = pContext.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        boolean is = sharedPreUtil.edit().putString(key, value).commit();
    }

    public static void removeString(Context pContext, String key) {
        if (sharedPreUtil == null) {
            sharedPreUtil = pContext.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        sharedPreUtil.edit().remove(key).commit();
    }

    public static String getString(Context pContext, String key, String defaultStr) {
        if (sharedPreUtil == null) {
            sharedPreUtil = pContext.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        return sharedPreUtil.getString(key, defaultStr);
    }

    public static void saveInt(Context context, String key, int value) {
        if (sharedPreUtil == null) {
            sharedPreUtil = context.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        sharedPreUtil.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultInt) {
        if (sharedPreUtil == null) {
            sharedPreUtil = context.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        return sharedPreUtil.getInt(key, defaultInt);
    }

    public static void removeInt(Context context, String key) {
        if (sharedPreUtil == null) {
            sharedPreUtil = context.getSharedPreferences(SHAREDPREF_URI, 0);
        }
        sharedPreUtil.edit().remove(key).commit();
    }

    /**
     *
     */
    private SharedPreUtil() {
    }
}
