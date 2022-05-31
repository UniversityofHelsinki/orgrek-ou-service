package fi.helsinki.ohtu.orgrekouservice.util;

import java.util.List;

public class Constants {
    public final static String EDGE_PATH = "/api/edge";
    public static final String NODE_API_PATH = "/api/node";

    public static final String STEERING_API = "/api/steering/";
    public static final String DEGREE_TITLE_API = "/api/degreeTitles";

    public static final String NAME_FI = "name_fi";
    public static final String NAME_EN = "name_en";
    public static final String NAME_SV = "name_sv";
    public static final String EMO_LYHENNE = "emo_lyhenne";
    public static final String LYHENNE = "lyhenne";

    public static final String ECONOMY_HIERARCHY = "talous";

    public static final List<String> MAPPED_ROLES = List.of("ROLE_ADMIN", "ROLE_WRITER", "ROLE_READER_ALL");

    public static final String BACHELORS_TITLE_TEXT_KEY = "kandiohjelma-joryt";
    public static final String MASTERS_TITLE_TEXT_KEY = "maisteriohjelma-joryt";
    public static final String DOCTORALS_TITLE_TEXT_KEY = "tohtoriohjelma-joryt";

    public static final String BACHELORS_PROGRAMMES_KEY = "kandiohjelma";
    public static final String MASTERS_PROGRAMMES_KEY = "maisteriohjelma";
    public static final String DOCTORALS_PROGRAMMES_KEY = "tohtoriohjelma";

    public static final String LANG_CODE_FI = "fi";
    public static final String LANG_CODE_EN = "en";
    public static final String LANG_CODE_SV = "sv";
}
