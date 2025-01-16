package com.video.parser.platform.instagram;

import java.util.ArrayList;
import java.util.List;

public class InstagramApiErrorMessageHolder {

    private static final List<String> errors = new ArrayList<>();


    public static void addError(String error) {
        errors.add(error);
    }

    public static void clear() {
        errors.clear();
    }

    public static List<String> getErrors() {
        return errors;
    }
}
