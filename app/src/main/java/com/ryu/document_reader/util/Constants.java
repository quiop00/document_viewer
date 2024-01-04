package com.ryu.document_reader.util;

public final class Constants {
    public static final String ALL = "all";

    public static final String APP_NAME = "AllDocumentReader2022";

    private static long CURERENT_VERSION_CODE = 0L;

    private static long DISTANCE_TIME_ADS_OTHER = 0L;

    private static long DISTANCE_TIME_ADS_SAME = 0L;

    private static long DISTANCE_TIME_WAIT_MAX = 0L;

    public static final String EVENT_CLICK = "event_click_";

    public static final String EXCEL = "excel";

    public static final String FAVORITE = "favorite";

    public static final String FROM_ANOTHER_APP = "FROM_ANOTHER_APP";

    public static final String FROM_HOME = "FROM_HOME";

    public static final String FROM_SAVE_FILE = "FROM_SAVE_FILE";

    public static final String IMAGE = "image";

    public static final String IMAGE_LIST = "IMAGE_LIST";

    public static final Constants INSTANCE = new Constants();

    public static final String IS_SET_LANG = "isSetLang";

    public static final String IS_SHOW_RATING = "isShowRating";

    public static final String KEY_IS_FIRST_USE_APP = "keyFirstUseApp";

    public static final String KEY_IS_RATING = "isRating";

    public static final String KEY_LANG = "keyLang";

    public static final String KEY_NUMBER_READER_FILE = "keyNumberReaderFile";

    private static long MAX_TIME_AT_SPLASH = 0L;

    private static long MINIMUM_VERSION_CODE = 0L;

    public static final String NAME = "name";

    public static final String PATH = "path";

    public static final String PDF = "pdf";

    public static final String POWER_POINT = "power_point";

    public static final String RECENT = "recent";

    public static final String SET_DEFAULT_APP = "SET_DEFAULT_APP";

    public static final String TEXT = "text";

    public static final String THEME = "Theme";

    public static final String TIME = "time";

    public static final String TYPE = "type";

    public static final int TYPE_INTER_ADS = 0;

    public static final int TYPE_OPEN_ADS = 1;

    public static final String URL = "url";

    public static final String WORD = "word";

    static {
        DISTANCE_TIME_ADS_OTHER = 20L;
        DISTANCE_TIME_ADS_SAME = 30L;
        DISTANCE_TIME_WAIT_MAX = 5L;
        MAX_TIME_AT_SPLASH = 5000L;
        CURERENT_VERSION_CODE = 5L;
        MINIMUM_VERSION_CODE = 3L;
    }

    public final long getCURERENT_VERSION_CODE() {
        return CURERENT_VERSION_CODE;
    }

    public final long getDISTANCE_TIME_ADS_OTHER() {
        return DISTANCE_TIME_ADS_OTHER;
    }

    public final long getDISTANCE_TIME_ADS_SAME() {
        return DISTANCE_TIME_ADS_SAME;
    }

    public final long getDISTANCE_TIME_WAIT_MAX() {
        return DISTANCE_TIME_WAIT_MAX;
    }

    public final long getMAX_TIME_AT_SPLASH() {
        return MAX_TIME_AT_SPLASH;
    }

    public final long getMINIMUM_VERSION_CODE() {
        return MINIMUM_VERSION_CODE;
    }

    public final void setCURERENT_VERSION_CODE(long paramLong) {
        CURERENT_VERSION_CODE = paramLong;
    }

    public final void setDISTANCE_TIME_ADS_OTHER(long paramLong) {
        DISTANCE_TIME_ADS_OTHER = paramLong;
    }

    public final void setDISTANCE_TIME_ADS_SAME(long paramLong) {
        DISTANCE_TIME_ADS_SAME = paramLong;
    }

    public final void setDISTANCE_TIME_WAIT_MAX(long paramLong) {
        DISTANCE_TIME_WAIT_MAX = paramLong;
    }

    public final void setMAX_TIME_AT_SPLASH(long paramLong) {
        MAX_TIME_AT_SPLASH = paramLong;
    }

    public final void setMINIMUM_VERSION_CODE(long paramLong) {
        MINIMUM_VERSION_CODE = paramLong;
    }

    public static final class Theme {
        public static final int DARK = 1;

        public static final int DEFAULT = 0;

        public static final Theme INSTANCE = new Theme();
    }
}

