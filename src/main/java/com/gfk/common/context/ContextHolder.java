package com.gfk.common.context;

/**
 * @author wzl
 * @version 1.0 2023/2/26
 */
public class ContextHolder {
    private static final ThreadLocal<Context> holder = new ThreadLocal<>();

    public static Context currentContext() {
        Context c = holder.get();
        if (null == c) {
            c = new Context();
            holder.set(c);
        }
        return c;
    }
}
