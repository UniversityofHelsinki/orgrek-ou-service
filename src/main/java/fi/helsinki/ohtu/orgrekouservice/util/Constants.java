package fi.helsinki.ohtu.orgrekouservice.util;

import java.util.*;

public class Constants {
    public final static String EDGE_PATH = "/api/edge";

    public final static String HIERARCHY_PUBLICITY_PATH = "/api/hierarchyPublicity";
    public static final String NODE_API_PATH = "/api/node";

    public static final String SECTION_API_PATH = "/api/section";

    public static final String HIERARCHY_FILTER_PATH = "/api/hierarchyfilter";

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

    public static final String ROLE_READER = "ROLE_READER";

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
    public static final String NODE_DATE_VALIDATION_MESSAGE_KEY = "node_start_date_after_end_date";

    public static final String ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY = "attribute_start_date_after_end_date";
    public static final String ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY = "attribute_value_is_mandatory";
    public static final String ATTRIBUTE_VALUE_LENGTH_VALIDATION_MESSAGE_KEY = "attribute_value_is_too_long";

    public static final String ATTRIBUTE_TYPE_VALIDATION_MESSAGE_KEY = "attribute_type_invalid";

    public static final int ATTRIBUTE_NAME_MAXIMUM_LENGTH = 250;

    public static final String ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY = "attribute_id_is_mandatory";
    public static final String ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY = "attribute_start_date_is_mandatory";
    public static final String ATTRIBUTE_KEY_VALIDATION_MESSAGE_KEY = "attribute_key_is_mandatory";
    public static final String ATTRIBUTE_CHILD_NODE_ID_VALIDATION_MESSAGE_KEY = "attribute_child_node_id_is_mandatory";
    public static final String ATTRIBUTE_PARENT_NODE_ID_VALIDATION_MESSAGE_KEY = "attribute_parent_node_id_is_mandatory";
    public static final String ATTRIBUTE_HIERARCHY_VALIDATION_MESSAGE_KEY = "attribute_hierarchy_is_mandatory";

    public static final String EDGE_CYCLE_DETECTED_MESSAGE_KEY = "edge_cycle_detected";

    public static final String SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE = "section_attribute_not_found_at_hierarchy_filter_table";

    public static final String SECTION_ATTRIBUTE_FOUND_AT_SECTION_ATTRIBUTE_TABLE = "section_attribute_found_at_section_attribute_table";
    public static final String SECTION_ATTRIBUTE_ID_IS_NULL = "section_attribute_id_is_null";

    public static final String NAME_ATTRIBUTES = "names";
    public static final String TYPE_ATTRIBUTES = "types";
    public static final String CODE_ATTRIBUTES = "codes";
    public static final String OTHER_ATTRIBUTES = "other_attributes";

    public static final String NEW_EDGES = "NEW";
    public static final String UPDATED_EDGES = "UPDATED";
    public static final String DELETED_EDGES = "DELETED";
    public static final String NODE_HIERARCHY_VALIDATION_MESSAGE_KEY = "node_hierarchy_is_mandatory";
    public static final String NODE_START_DATE_VALIDATION_MESSAGE_KEY = "node_start_date_is_mandatory";
    public static final String NODE_NAMES_VALIDATION_MESSAGE_KEY = "node_names_are_mandatory";

    public static final String NEW_SECTION_ATTRIBUTE = "new_section_attribute";
    public static final String UPDATE_SECTION_ATTRIBUTE = "update_section_attribute";

    public static final String DELETE_SECTION_ATTRIBUTE = "delete_section_attribute";

    public static final String HIERARCHY_PUBLICITY_NAME_EMPTY = "hierarchy_publicity_name_is_empty";

    public static final String HIERARCHY_PUBLICITY_NAME_NOT_MATCHING = "hierarchy_publicity_name_not_matching";

    public static final String HIERARCHY_PUBLICITY_CHILD_ID_EMPTY = "hierarchy_child_id_is_empty";
    public static final String HIERARCHY_PUBLICITY_NAME_LIST_EMPTY = "hierarchy_publicity_name_list_empty";
    public static final String HIERARCHY_PUBLICITY_NAME_OR_LANGUAGE_EMPTY = "hierarchy_publicity_name_or_language_emtpy";
}
