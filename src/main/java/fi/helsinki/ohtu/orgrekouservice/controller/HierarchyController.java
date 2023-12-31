package fi.helsinki.ohtu.orgrekouservice.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.RelationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Relative;
import fi.helsinki.ohtu.orgrekouservice.domain.RelativeDTO;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private HierarchyService hierarchyService;

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
