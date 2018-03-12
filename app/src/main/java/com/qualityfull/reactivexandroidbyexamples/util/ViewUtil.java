package com.qualityfull.reactivexandroidbyexamples.util;

import android.content.res.Resources;
import android.text.TextUtils;
import java.util.regex.Pattern;

public final class ViewUtil {

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /*
     * Text valid is not null and not empty
     * */
    public static boolean isTextValid(CharSequence charSequence) {
        return !isTextEmpty(charSequence);
    }

    /*************************************************************************
      (			    #   Start of group
      (?=.*\d)		#   must contains one digit from 0-9
      (?=.*[a-z])	#   must contains one lowercase characters
      (?=.*[A-Z])	#   must contains one uppercase characters
      (?=.*[@#$%])	#   must contains one special symbols in the list "@#$%"
      .		        #   match anything with previous condition checking
      {6,20}	    #   length at least 6 characters and maximum of 20
      )			    #   End of group
     **************************************************************************/
    public static boolean isPasswordValid(CharSequence password) {
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public static boolean isFieldValid(CharSequence charSequence, int minSize, int maxSize) {
        return isTextValid(charSequence) && isRangeValid(charSequence, minSize, maxSize);
    }

    public static boolean isFieldValidOrEmpty(CharSequence charSequence, int minSize, int maxSize) {
        return isTextEmpty(charSequence) || isRangeValid(charSequence, minSize, maxSize);
    }

    public static boolean isEmailFieldValidOrEmpty(CharSequence charSequence) {
        return isTextEmpty(charSequence) || isEmailValid(charSequence);
    }

    public static boolean isPasswordFieldValidOrEmpty(CharSequence charSequence) {
        return isTextEmpty(charSequence) || isPasswordValid(charSequence);
    }

    private static boolean isRangeValid(CharSequence charSequence, int minSize, int maxSize) {
        return charSequence.length() > minSize && charSequence.length() <= maxSize;
    }

    private static boolean isTextEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }
}
