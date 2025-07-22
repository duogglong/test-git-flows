package com.ndl.trustviec.common.type;


import com.ndl.trustviec.utils.StringUtils;

import java.util.Objects;

public enum SendEmailType {
    FIRST_PASSWORD;

    public static boolean isValid(String type) {
        if (StringUtils.isNull(type)) {
            return false;
        }
        SendEmailType sendEmailType = getType(type);
        if (Objects.isNull(sendEmailType)) {
            return false;
        }
        switch (sendEmailType) {
            case FIRST_PASSWORD -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public static boolean isInvalid(String type) {
        return !isValid(type);
    }

    public static SendEmailType getType(String type) {
        if (StringUtils.isNull(type)) {
            return null;
        }
        return switch (type) {
            case "FIRST_PASSWORD" -> FIRST_PASSWORD;
            default -> null;
        };
    }
}
