package com.pdclientmanager.util;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {

    public static Optional<String> getPreviousPage(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer"))
                .map(requestUrl -> "redirect:" + requestUrl);
    }
}
