package com.example.springreviewhub.core.util;

import java.util.function.Consumer;

public class UpdateUtils {

    public static <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
