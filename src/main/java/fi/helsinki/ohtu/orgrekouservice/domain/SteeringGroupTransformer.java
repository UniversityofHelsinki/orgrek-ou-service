package fi.helsinki.ohtu.orgrekouservice.domain;

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
            this.programmeName = lang.equals("fi") ? source.getProgrammeNameFi()
                    : lang.equals("sv") ? source.getProgrammeNameSv()
                    : lang.equals("en") ? source.getProgrammeNameEn() : "";
            this.steeringGroupName = lang.equals("fi") ? source.getSteeringGroupNameFi()
                    : lang.equals("sv") ? source.getSteeringGroupNameSv()
                    : lang.equals("en") ? source.getSteeringGroupNameEn() : "";
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

    public static Localized<Map<String, List<DegreeProgramme>>> transform(List<DegreeProgrammeDTO> input) {
        return new Localized<>(
                transform(input, "fi"),
                transform(input, "sv"),
                transform(input, "en"));
    }

    private static Map<String, List<DegreeProgramme>> transform(List<DegreeProgrammeDTO> input, String lang) {
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

}
