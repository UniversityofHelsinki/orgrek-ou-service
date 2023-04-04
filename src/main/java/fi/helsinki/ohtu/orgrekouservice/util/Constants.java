package fi.helsinki.ohtu.orgrekouservice.util;

import java.util.*;

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

    public static final String ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY = "attribute_start_date_after_end_date";
    public static final String ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY = "attribute_value_is_mandatory";
    public static final String ATTRIBUTE_VALUE_LENGTH_VALIDATION_MESSAGE_KEY = "attribute_value_is_too_long";

    public static final String ATTRIBUTE_TYPE_VALIDATION_MESSAGE_KEY = "attribute_type_invalid";

    public static final int ATTRIBUTE_NAME_MAXIMUM_LENGTH = 250;

    public static final String ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY = "attribute_id_is_mandatory";
    public static final String ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY = "attribute_start_date_is_mandatory";
    public static final String ATTRIBUTE_KEY_VALIDATION_MESSAGE_KEY = "attribute_key_is_mandatory";

    public static final String NAME_ATTRIBUTE = "name";
    public static final String TYPE_ATTRIBUTE = "type";
    public static final String CODE_ATTRIBUTE = "code";

    public static final List<String> CODE_ATTRIBUTES = Arrays.asList("lyhenne", "emo_lyhenne","hr_lyhenne","iam_ryhma", "hr_tunnus", "laskutus_tunnus",
            "mainari_tunnus","oppiaine_tunnus","talous_tunnus","tutkimus_tunnus");

    public static final List<String> CODE_ATTRIBUTES_ORDER = Arrays.asList(
            "lyhenne",
            "emo_lyhenne",
            "iam_ryhma",
            "talous_tunnus",
            "hr_lyhenne",
            "hr_tunnus",
            "tutkimus_tunnus",
            "oppiaine_tunnus",
            "laskutus_tunnus",
            "mainari_tunnus"
    );

    public static final Map<String, List<String>> ATTRIBUTE_TYPE_MAP = new HashMap<>();
    static {
        ATTRIBUTE_TYPE_MAP.put(NAME_ATTRIBUTE,  Arrays.asList(NAME_FI, NAME_EN, NAME_SV));
        ATTRIBUTE_TYPE_MAP.put(TYPE_ATTRIBUTE,  Arrays.asList(TYPE));
        ATTRIBUTE_TYPE_MAP.put(CODE_ATTRIBUTE,  CODE_ATTRIBUTES);
    }
}
