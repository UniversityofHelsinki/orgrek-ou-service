package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import fi.helsinki.ohtu.orgrekouservice.service.FullNameService;
import fi.helsinki.ohtu.orgrekouservice.service.UtilService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private FullNameService fullNameService;

    @Autowired
    private EdgeService edgeService;

    /**
     * Check if all hierarchies are selected
     * @param selectedHierarchies
     * @return
     */
    private boolean areAllHierarchiesSelected(String selectedHierarchies) {

        String[] hierarchyArr = selectedHierarchies.split(",");
        int selectedHierarchiesCount = hierarchyArr.length;

        List<String> types = edgeService.getHierarchyTypes();
        int typesCount = types.size() - 1; //subtract history

        return (selectedHierarchiesCount == typesCount);
    }
    private Attribute[] allOtherHierarchies(Attribute[] listAttributes, List<HierarchyFilter> allHierarchyFilters, Attribute[] selectedAttributes, boolean allHierarhiesSelected) {
        List<Attribute> attributeArr = new ArrayList<>();
        List<Attribute> selectedAttrList =  new ArrayList<Attribute>(Arrays.asList(selectedAttributes));

        Arrays.stream(listAttributes).forEach(attribute -> {
            if (attribute.getKey().equalsIgnoreCase(Constants.TYPE)) {
                //do not show in this case (attribute can be in selectedAttrList)
            } else
            if (allHierarhiesSelected && (attribute.getKey().equalsIgnoreCase(Constants.MAINARI) || attribute.getKey().equalsIgnoreCase(Constants.LASKUTUS_TUNNUS)) && !attributeArr.contains(attribute)) {
                //mainari and laskutus_tunnus attributes are shown if all hierarchies are selected
                //no neeed to check selectedAttrList, because mainari and laskutus_tunnus are not in HIERARCHY_FILTER table.
                attributeArr.add(attribute);
            } else if (!attribute.getKey().equalsIgnoreCase(Constants.MAINARI) && !attribute.getKey().equalsIgnoreCase(Constants.LASKUTUS_TUNNUS))  {
                boolean notFoundInHierarchyFilters = isFoundInAllHierarchyFilters(allHierarchyFilters, attribute);
                if (notFoundInHierarchyFilters && !attributeArr.contains(attribute) && !selectedAttrList.contains(attribute)) {
                    //attributes which are not found in hierarchy_filter table are shown
                    attributeArr.add(attribute);
                }
            }
        });
        selectedAttrList.addAll(attributeArr);

        return selectedAttrList.toArray(new Attribute[]{});
    }
    private boolean isFoundInAllHierarchyFilters(List<HierarchyFilter> allHierarchyFilters, Attribute attribute) {
        Stream<HierarchyFilter> attributeStream = allHierarchyFilters.stream().filter(
                o -> o.getKey().equalsIgnoreCase(attribute.getKey()) &&
                        (o.getValue() == null || o.getValue().equalsIgnoreCase(attribute.getValue()))
        );
        return (attributeStream.count() == 0);
    }
    private Attribute[] onlySelectedHierarchies(Attribute[] listAttributes, String selectedAttributes, List<HierarchyFilter> allHierarchyFilters) {
        List<Attribute> attributeArr = new ArrayList<>();
        String[] selectedAttributesArr = selectedAttributes.split(",");
        List<String> selectedAttributeList = Arrays.asList(selectedAttributesArr);

        Arrays.stream(listAttributes).forEach(attribute -> {
            allHierarchyFilters.forEach(hierarchy -> {
                if (selectedAttributeList.contains(hierarchy.getHierarchy()) && attribute.getKey().equalsIgnoreCase(hierarchy.getKey()) &&
                        (hierarchy.getValue() == null || attribute.getValue().equalsIgnoreCase(hierarchy.getValue())) && !attributeArr.contains(attribute)) {
                    attributeArr.add(attribute);
                }
            });
        });

        return attributeArr.toArray(new Attribute[]{});
    }
    private List<HierarchyFilter> getAllHierarchyFilters(String date, String whichtime) {

        List<HierarchyFilter> hierarchyFilters = hierarchyService.getAllHierarchyFilters(date, whichtime);
        return hierarchyFilters;
    }

    private <T> Map<String, List<T>> emptyMap() {
        Map<String, List<T>> emptyMap = new HashMap<>();
        emptyMap.put("fi", new ArrayList<>());
        emptyMap.put("en", new ArrayList<>());
        emptyMap.put("sv", new ArrayList<>());
        return emptyMap;
    }

    private Comparator<RelativeDTO> byName = new Comparator<>() {
        @Override
        public int compare(RelativeDTO a, RelativeDTO b) {
            return a.getFullName().compareTo(b.getFullName());
        }
    };

    private Comparator<Relative> byNameRelative = new Comparator<>() {
        @Override
        public int compare(Relative a, Relative b) {
            return a.getFullName().compareTo(b.getFullName());
        }
    };


    private Collection<RelativeDTO> combineHierarchies(List<Relative> relatives, List<String> selectedHierarchies) {
        Map<String, Map<String, RelativeDTO>> hierarchiesCombined = new HashMap<>();
        List<RelativeDTO> results = new ArrayList<>();
        boolean includeAllHierarchies = selectedHierarchies == null;
        relatives.forEach(p -> {
            if (!includeAllHierarchies && !selectedHierarchies.contains(p.getHierarchy())) {
                return;
            }
            if (!hierarchiesCombined.containsKey(p.getId())) {
                hierarchiesCombined.put(p.getId(), new HashMap<>());
            }
            if (hierarchiesCombined.containsKey(p.getId()) && !hierarchiesCombined.get(p.getId()).containsKey(p.getLanguage())) {
                RelativeDTO rdto = new RelativeDTO(p);
                hierarchiesCombined.get(p.getId()).put(p.getLanguage(), rdto);
                results.add(rdto);
            }
            hierarchiesCombined.get(p.getId()).get(p.getLanguage()).addHierarchy(p);
        });
        return results;
    }

    private Collection<RelativeDTO> combineHierarchies(List<Relative> relatives) {
        return combineHierarchies(relatives, null);
    }

    private List<Relative> filterRelativesBy(List<String> hierarchies, List<Relative> in) {
        return in.stream().filter(r -> hierarchies.contains(r.getHierarchy())).collect(Collectors.toList());
    }

    @RequestMapping(method = GET, value = "/predecessors/{id}/{date}")
    public Map<String, List<Relative>> getPredecessors(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date) {
        List<Relative> predecessors = hierarchyService.getPredecessors(uniqueId, date);
        if (predecessors.isEmpty()) {
            return emptyMap();
        }
        return predecessors.stream().sorted(byNameRelative).collect(Collectors.groupingBy(Relative::getLanguage));
    }

    @RequestMapping(method = GET, value = "/successors/{id}/{date}")
    public Map<String, List<Relative>> getSuccessors(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date) {
        List<Relative> successors = hierarchyService.getSuccessors(uniqueId, date);
        if (successors.isEmpty()) {
            return emptyMap();
        }
        return successors.stream().sorted(byNameRelative).collect(Collectors.groupingBy(Relative::getLanguage));
    }

    private Map<String, List<RelativeDTO>> byLanguage(List<String> selectedHierarchies, List<Relative> relatives) {
        if (relatives.isEmpty()) {
            return emptyMap();
        }
        return combineHierarchies(relatives, selectedHierarchies).stream().filter(r ->
                r.getHierarchies().stream().map(h -> h.getHierarchy()).anyMatch(selectedHierarchies::contains)
        ).sorted(byName).collect(Collectors.groupingBy(RelativeDTO::getLanguage));
    }

    @RequestMapping(method = GET, value = "/parents/{id}/{date}/{rawHierarchies}")
    public Map<String, List<RelativeDTO>> getParents(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date, @PathVariable("rawHierarchies") String rawHierarchies) {
        List<Relative> parents = hierarchyService.getParents(uniqueId, date);
        List<String> selectedHierarchies = Arrays.asList(rawHierarchies.split(","));
        return byLanguage(selectedHierarchies, parents);
    }

    @RequestMapping(method = GET, value = "/parents/all/{id}/{date}/{rawHierarchies}")
    public Map<String, List<RelativeDTO>> getAllParents(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date, @PathVariable("rawHierarchies") String rawHierarchies) {
        List<Relative> parents = hierarchyService.getAllParents(uniqueId, date);
        List<String> selectedHierarchies = Arrays.asList(rawHierarchies.split(","));
        return byLanguage(selectedHierarchies, parents);
    }
    @RequestMapping(method = GET, value = "/children/{id}/{date}/{rawHierarchies}")
    public Map<String, List<RelativeDTO>> getChildren(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date, @PathVariable("rawHierarchies") String rawHierarchies) {
        List<Relative> children = hierarchyService.getChildren(uniqueId, date);
        List<String> selectedHierarchies = Arrays.asList(rawHierarchies.split(","));
        return byLanguage(selectedHierarchies, children);
    }

    @RequestMapping(method = GET, value = "/children/all/{id}/{date}/{rawHierarchies}")
    public Map<String, List<RelativeDTO>> getAllChildren(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date, @PathVariable("rawHierarchies") String rawHierarchies) {
        List<Relative> children = hierarchyService.getAllChildren(uniqueId, date);
        List<String> selectedHierarchies = Arrays.asList(rawHierarchies.split(","));
        return byLanguage(selectedHierarchies, children);
    }

    @RequestMapping(method = GET, value = "/{id}/allParents/{rawHierarchies}")
    public List<RelationDTO> getAllParentsBySelectedHierarchies(@PathVariable("id") Integer uniqueId, @PathVariable("rawHierarchies") String rawHierarchies) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        List<Relative> parents = hierarchyService.getAllParents(uniqueId, formatter.format(new Date()));
        List<String> selectedHierarchies = Arrays.asList(rawHierarchies.split(","));
        Map<String, List<RelativeDTO>> parentsMap = byLanguage(selectedHierarchies, parents);
        return hierarchyService.mergeRelativeMaps(parentsMap);
    }

    @PutMapping("/successor")
    public List<EdgeWrapper> updateSuccessors(@RequestBody List<EdgeWrapper> edges) {
        hierarchyService.updateSuccessors(edges);
        return edges;
    }

}
