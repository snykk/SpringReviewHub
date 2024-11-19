package com.example.springreviewhub.core.util;

import java.util.function.Consumer;

/**
 * Utility class for updating object properties conditionally.
 * <p>
 * This class provides helper methods to streamline the process of updating
 * object properties only if the provided values are not {@code null}.
 * </p>
 * <p>
 * This is particularly useful in scenarios where updates should not overwrite
 * existing properties with {@code null} values.
 * </p>
 */
public class UpdateUtils {

    /**
     * Updates a property of an object if the provided value is not {@code null}.
     * <p>
     * This method uses a {@link Consumer} to apply the update. If the provided
     * value is {@code null}, the update will be skipped, leaving the property
     * unchanged.
     * </p>
     *
     * @param <T>    the type of the value to be updated
     * @param setter a {@link Consumer} representing the setter method for the property
     * @param value  the value to update the property with, if not {@code null}
     */
    public static <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
