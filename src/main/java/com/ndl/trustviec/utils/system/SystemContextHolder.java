package com.ndl.trustviec.utils.system;


import com.ndl.trustviec.utils.JwtUtils;

public class SystemContextHolder {

    private static ThreadLocal<SystemContext> instant = new ThreadLocal<>();

    public static void create(JwtUtils jwtUtil, String token) {
        SystemContext context = new SystemContext();
        context.setCif(jwtUtil.getSubject(token));
        context.setToken(token);
        context.setClaims(jwtUtil.getClaims(token));
        instant.set(context);
    }

    public static ThreadLocal<SystemContext> getCurrentSystem() {
        return instant;
    }

    public static SystemContext getCurrentContext() {
        if (instant.get() == null)
            instant.set(new SystemContext());
        return instant.get();
    }

    public static String getCurrentUserId() {
        SystemContext context = getCurrentSystem().get();
        if (context != null) {
            return context.getCif();
        }
        return null;
    }

    public static void clear() {
        instant.remove();
    }
}
