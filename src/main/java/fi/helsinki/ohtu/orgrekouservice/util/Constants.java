package fi.helsinki.ohtu.orgrekouservice.util;

import java.util.List;

public class Constants {
    public final static String EDGE_PATH = "/api/edge";
    public static final String NODE_API_PATH = "/api/node";

    public static final String PUBLIC_STEERING_GROUPS_API = "/api/public/steeringGroups";
    public static final String PUBLIC_DEGREE_TITLE_API = "/api/public/degreeTitles";

    public static final String FULL_NAME_API_PATH = "/api/node/fullname";

    public static final String TREE_API_HIERARCHY = "/api/tree";

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
    public static final String HISTORY = "history";
    public static final String FUTURE = "future";
    public static final String NOW = "now";
    public static final String MAINARI = "mainari_tunnus";
    public static final String LASKUTUS_TUNNUS = "laskutus_tunnus";
    public static final String TYPE = "type";

    public static final String NEW_ATTRIBUTES = "newAttributes";
    public static final String UPDATED_ATTRIBUTES = "updatedAttributes";
    public static final String DELETED_ATTRIBUTES = "deletedAttributes";

    public static final String ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY = "attribute_start_date_before_end_date";
    public static final String ATTRIBUTE_NAME_VALIDATION_MESSAGE_KEY = "attribute_name_is_mandatory";
}
