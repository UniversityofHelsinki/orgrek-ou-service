package fi.helsinki.ohtu.orgrekouservice.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public final static String EDGE_PATH = "/api/edge";
    public static final String NODE_API_PATH = "/api/node";

    public static final String NAME_FI = "name_fi";
    public static final String NAME_EN = "name_en";
    public static final String NAME_SV = "name_sv";
    public static final String EMO_LYHENNE = "emo_lyhenne";
    public static final String LYHENNE = "lyhenne";

    public static final String ECONOMY_HIERARCHY = "talous";

    public static final List<String> MAPPED_ROLES = List.of("ROLE_ADMIN", "ROLE_WRITER", "ROLE_READER_ALL");
}
