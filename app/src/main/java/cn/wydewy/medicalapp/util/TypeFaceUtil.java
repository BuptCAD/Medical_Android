package cn.wydewy.medicalapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by wydewy on 2016/7/26.
 */
public class TypeFaceUtil {
    /**
     * 设置字体
     */
    public static void setTypeFace(TextView textView, String path, Context context) {
        // 将字体文件保存在assets/fonts/目录下，www.linuxidc.com创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), path);
        // 应用字体
        textView.setTypeface(typeFace);
    }
}
