package com.securitydemo.enums;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Optional;

/**
 * status enum
 */
public enum Status {
    SUCCESS(200, "SUCCESS", "成功"),
    FORBIDDEN(403, "FORBIDDEN", "权限不足"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "用户认证失败");


    private final int code;
    private final String enMsg;
    private final String zhMsg;

    Status(int code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.enMsg;
        }
    }

    /**
     * Retrieve Status enum entity by status code.
     */
    public static Optional<Status> findStatusBy(int code) {
        for (Status status : Status.values()) {
            if (code == status.getCode()) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
