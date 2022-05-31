package fi.helsinki.ohtu.orgrekouservice.domain;

import fi.helsinki.ohtu.orgrekouservice.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class SteeringGroupTransformer {

    public static class Localized<T> {
        private T fi;
        private T sv;
        private T en;

        public Localized(T fi, T sv, T en) {
            this.fi = fi;
            this.sv = sv;
            this.en = en;
        }

        public T getFi() {
            return fi;
        }

        public T getSv() {
            return sv;
        }

        public T getEn() {
            return en;
        }
    }

    public static class DegreeProgramme {
        private String iamGroup;
        private String programmeCode;
        private String programmeName;
        private String steeringGroupName;

        public DegreeProgramme(DegreeProgrammeDTO source, String lang) {
            this.iamGroup = source.getIamGroup();
            this.programmeCode = source.getProgrammeCode();
            this.programmeName = lang.equals(Constants.LANG_CODE_FI) ? source.getProgrammeNameFi()
                    : lang.equals(Constants.LANG_CODE_SV) ? source.getProgrammeNameSv()
                    : lang.equals(Constants.LANG_CODE_EN) ? source.getProgrammeNameEn() : "";
            this.steeringGroupName = lang.equals(Constants.LANG_CODE_FI) ? source.getSteeringGroupNameFi()
                    : lang.equals(Constants.LANG_CODE_SV) ? source.getSteeringGroupNameSv()
                    : lang.equals(Constants.LANG_CODE_EN) ? source.getSteeringGroupNameEn() : "";
        }

        public String getIamGroup() {
            return iamGroup;
        }

        public String getProgrammeCode() {
            return programmeCode;
        }

        public String getProgrammeName() {
            return programmeName;
        }

        public String getSteeringGroupName() {
            return steeringGroupName;
        }

    }

    public static Localized<Map<String, Object>> transform(List<DegreeProgrammeDTO> input, List<TextDTO> degreeTitles) {

        Map<String, Object> fiMap = transform(input,  Constants.LANG_CODE_FI);
        Map<String, Object> svMap = transform(input, Constants.LANG_CODE_SV);
        Map<String, Object> enMap = transform(input, Constants.LANG_CODE_EN);

        return new Localized<>(
                buildListOfDegreeProgrammesAndTitlesByLanguage(Constants.LANG_CODE_FI, degreeTitles, fiMap),
                buildListOfDegreeProgrammesAndTitlesByLanguage(Constants.LANG_CODE_SV, degreeTitles, svMap),
                buildListOfDegreeProgrammesAndTitlesByLanguage(Constants.LANG_CODE_EN, degreeTitles, enMap)
        );
    }
    private static Map<String,Object> transform(List<DegreeProgrammeDTO> input, String lang) {
       return input.stream().collect(
                Collectors.groupingBy(dto -> dto.getType(),
                        Collectors.mapping(in -> new DegreeProgramme(in, lang),
                                Collectors.collectingAndThen(toList(), list -> sortedBySteeringGroupName(list)))));
    }

    private static List<DegreeProgramme> sortedBySteeringGroupName(List<DegreeProgramme> programmes) {
        return programmes.stream()
                .sorted(Comparator.comparing(d -> "" + d.getSteeringGroupName()))
                .collect(toList());
    }

    private static String getTitleByLanguage(String lang, String degree, List<TextDTO> degreeTitles){
        for (TextDTO textDTO: degreeTitles) {
            if(textDTO.getLanguage().equals(lang) && textDTO.getKey().equals(degree)){
                return textDTO.getValue();
            }
        }
        return "";
    }

    private static LinkedHashMap<String, Object> buildListOfDegreeProgrammesAndTitlesByLanguage(String lang, List<TextDTO> degreeTitles,
                                                                                                Map<String, Object>  degreeProgrammes ){
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put("bachelorsTitle", getTitleByLanguage(lang, Constants.BACHELORS_TITLE_TEXT_KEY, degreeTitles));
        hashMap.put("bachelorsProgrammes", degreeProgrammes.get(Constants.BACHELORS_PROGRAMMES_KEY));
        hashMap.put("mastersTitle", getTitleByLanguage(lang, Constants.MASTERS_TITLE_TEXT_KEY, degreeTitles));
        hashMap.put("mastersProgrammes", degreeProgrammes.get(Constants.MASTERS_PROGRAMMES_KEY));
        hashMap.put("doctoralsTitle", getTitleByLanguage(lang, Constants.DOCTORALS_TITLE_TEXT_KEY, degreeTitles));
        hashMap.put("doctoralsProgrammes", degreeProgrammes.get(Constants.DOCTORALS_PROGRAMMES_KEY));
        return hashMap;
    }
}
