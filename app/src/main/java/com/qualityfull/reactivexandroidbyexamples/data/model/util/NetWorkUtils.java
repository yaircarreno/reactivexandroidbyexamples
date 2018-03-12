package com.qualityfull.reactivexandroidbyexamples.data.model.util;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;
import java.util.Collection;

public final class NetWorkUtils {

    private static final int CODE = 200;
    private static final String STATUS = "Ok";

    private static boolean statusResponse(Characters characters) {
        return characters.getCode() == CODE && characters.getStatus().equals(STATUS);
    }

    private static boolean statusData(Characters characters) {
        return characters.getData() != null && characters.getData().getCount() > 0;
    }

    public static boolean isDataResponseValid(Characters characters) {
        return statusResponse(characters) && statusData(characters);
    }

    public static boolean isListValid(Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
