package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
public enum T5p4FieldsEnum {
    IMPRESSION("Impression", "impression", "Impression", 2, "#9674C9"),
    CLICKS("Clicks", "clicks", "Clicks", 2, "#9674C9"),
    ENGAGEMENT("Engagement", "engagement", "Engagement", 2, "#9674C9"),
    ADSTOCK("Adstock", "adStock", "Adstock", 1, "#5678C3"),
    SPENDING("Spending", "spending", "Spending", 2, "#9674C9"),
    COST("Cost", "cost", "Cost", 2, "#9674C9"),
    VIEWS("Views", "views", "Views", 2, "#9674C9"),
    NO_OF_PANELS("No. of panels", "noOfPanels", "No. of panels", 2, "#9674C9"),
    NO("No.", "no", "No.", 2, "#9674C9"),
    NUMELEMENTS("Numelements", "numelements", "Numelements", 2, "#9674C9"),
    LIKES("Likes", "likes", "Likes", 2, "#9674C9"),
    COMMENTS("Comments", "comments", "Comments", 2, "#9674C9"),
    REACH("Reach", "reach", "Reach", 2, "#9674C9"),
    SUBSCRIBERS("Subscribers", "subscribers", "Subscribers", 2, "#9674C9"),
    BUSES("Buses", "buses", "Buses", 2, "#9674C9"),
    EXPOSURE("Exposure", "exposure", "Exposure", 2, "#9674C9"),
    IMPACTS("Impacts", "impacts", "Impacts", 2, "#9674C9"),
    GRP("GRP", "gpr", "GRP", 2, "#9674C9"),
    TRP("TRP", "tpr", "TRP", 2, "#9674C9"),
    CIRCULATIONS("Circulations", "circulations", "Circulations", 2, "#9674C9"),
    INSERTIONS("Insertions", "insertions", "Insertions", 2, "#9674C9"),
    FOLLOWERS("Followers", "followers", "Followers", 2, "#9674C9"),
    VISITS("Visits", "visits", "Visits", 2, "#9674C9"),
    FOLLOWS("Follows", "follows", "Follows", 2, "#9674C9"),
    REPLIES("Replies", "replies", "Replies", 2, "#9674C9"),
    FORWARDS("Forwards", "forwards", "Forwards", 2, "#9674C9"),
    QUANTITIES("Quantities", "quantities", "Quantities", 2, "#9674C9"),
    TIMES("Times", "times", "Times", 2, "#9674C9"),
    COMPLETION("Completion%", "completion", "Completion%", 2, "#9674C9"),
    LENGTH("Length", "length", "Length", 2, "#9674C9"),
    EXECUTION("Execution", "execution", "Execution", 2, "#9674C9"),
    DISCOUNT("Discount", "discount", "Discount", 2, "#9674C9"),
    VOLUME("Volume", "volume", "Volume", 2, "#9674C9"),
    SHARES("Shares", "shares", "Shares", 2, "#9674C9"),
    FREQUENCY("Frequency", "frequency", "Frequency", 2, "#9674C9"),
    VIDEO_COMPLETION("Video Completion", "videoCompletion", "Video Completion", 2, "#9674C9"),
    VIDEO_COMPLETION_RATE("Video Completion Rate", "videoCompletionRate", "Video Completion Rate", 2, "#9674C9"),
    MAX_MARGIN("max_margin", "maxMargin", "Max Margin", 1, "#54B9B1"),

    MAX_ROI("max_roi", "maxRoi", "Max ROI", 1, "#FFA52F"),
    GRP_TEST("grp", "grp", "Grp", 2, "#9674C9"),
    AD_STOCK_TEST("adstock", "adStock", "Ad-stock", 1, "#5678C3"),

    IMPRESSION_TEST("imp", "impression", "Impression", 2, "#9674C9"),
    ;

    public final String name;
    public final String fieldName;
    public final String displayName;

    public final String color;
    /**
     * 图表形状 1-折线 2-柱状
     */
    public final int type;

    T5p4FieldsEnum(String name, String fieldName, String displayName, int type, String color) {
        this.name = name;
        this.fieldName = fieldName;
        this.displayName = displayName;
        this.type = type;
        this.color = color;
    }

    public static T5p4FieldsEnum getByName(String name) {
        if (null == name) {
            return null;
        }
        for (T5p4FieldsEnum value : values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
