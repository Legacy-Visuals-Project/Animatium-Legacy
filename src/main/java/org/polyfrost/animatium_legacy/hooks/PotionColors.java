package org.polyfrost.animatium_legacy.hooks;

import java.util.HashMap;
import java.util.Map;

public final class PotionColors {
    public static final Map<Integer, Integer> POTION_COLORS = new HashMap<Integer, Integer>() {{
        put(1, 3402751);
        put(2, 9154528);
        put(3, 14270531);
        put(4, 4866583);
        put(5, 16762624);
        put(6, 16262179);
        put(7, 11101546);
        put(8, 16646020);
        put(9, 5578058);
        put(10, 13458603);
        put(11, 9520880);
        put(12, 0xFF9900);
        put(13, 10017472);
        put(14, 0xF6F6F6);
        put(15, 2039587);
        put(16, 12779366);
        put(17, 5797459);
        put(18, 0x484D48);
        put(19, 8889187);
        put(20, 7561558);
        put(21, 16284963);
        put(22, 0x2552A5);
        put(23, 16262179);
    }};

    public static boolean shouldReload;

    public static void reloadColor() {
        shouldReload = true;
    }
}
