package com.qualityfull.reactivexandroidbyexamples.util;

import io.reactivex.Observable;

public final class Mask {

    public static Observable<String> applyMask(String number, String mask) {

        return Observable.fromCallable(() -> {

            if (number.length() > mask.length()) {
                return "";
            }
            String str = Mask.unMask(number);
            StringBuilder masked = new StringBuilder(str);
            int j = 0;
            if (str.length() <= mask.length()) {
                for (int i = 0; i < str.length(); i++) {
                    char c = mask.charAt(j);
                    if (c != '#') {
                        masked.insert(j, c);
                        j++;
                    }
                    j++;
                }
            }
            return masked.toString();
        });
    }

    public static Observable<Boolean> validateMask(String number, String mask) {

        return Observable.fromCallable(() -> {
            if (number.length() == mask.length()) {
                for (int i = 0; i < number.length(); i++) {
                    if (mask.charAt(i) != '#' && number.charAt(i) != mask.charAt(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        });
    }

    private static String unMask(String s) {
        return s.replaceAll("[.]", "")
                .replaceAll("[-]", "")
                .replaceAll("[/]", "")
                .replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }
}
