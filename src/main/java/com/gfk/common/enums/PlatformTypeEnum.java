package com.gfk.common.enums;

/**
 * type1枚举类
 *
 * @author wzl
 * @version 1.0 2023/4/8
 */
public enum PlatformTypeEnum {
    GOOGLE("google", "ATL Digital", EffectEnum.DIRECT),
    META("meta", "ATL Digital", EffectEnum.DIRECT),
    TWITTER("twitter", "ATL Digital", EffectEnum.DIRECT),
    TIKTOK("tiktok", "ATL Digital", EffectEnum.DIRECT),
    TTD("ttd", "ATL Digital", EffectEnum.DIRECT),
    TREADS("treads", "ATL Digital", EffectEnum.DIRECT),
    TV("tv", "ATL Traditional", EffectEnum.DIRECT),
    RADIO("radio", "ATL Traditional", EffectEnum.DIRECT),
    PR("pr", "ATL Traditional", EffectEnum.DIRECT),
    OOH("pr", "ATL Traditional", EffectEnum.DIRECT),
    INSTORE("instore", "BTL", EffectEnum.DIRECT),
    PROMOTER("promoter", "BTL", EffectEnum.DIRECT),
    STORE_PROMOTE("store promote", "BTL", EffectEnum.DIRECT),
    HALO("halo", "HALO", EffectEnum.HALO);


    public final String type2;
    public final String type1;
    public final EffectEnum effect;

    PlatformTypeEnum(String type2, String type1, EffectEnum effect) {
        this.type2 = type2;
        this.type1 = type1;
        this.effect = effect;
    }

    public static String getType1(String type2) {
        if (null == type2) {
            return null;
        }
        for (PlatformTypeEnum value : values()) {
            if (value.type2.equals(type2)) {
                return value.type1;
            }
        }
        return null;
    }
}
