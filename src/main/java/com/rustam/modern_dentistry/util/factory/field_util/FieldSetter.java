package com.rustam.modern_dentistry.util.factory.field_util;

import com.rustam.modern_dentistry.dao.entity.enums.Role;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public class FieldSetter {
    public static void setIfNotBlank(String value, Consumer<String> setter) {
        if (StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public static <T> void setIfNotEmpty(Collection<T> value, Consumer<Collection<T>> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    public static <T> void setIfNotEmpty(Set<T> value, Consumer<Set<T>> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }
}
