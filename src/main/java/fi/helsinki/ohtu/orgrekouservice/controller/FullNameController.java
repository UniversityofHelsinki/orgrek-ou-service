package fi.helsinki.ohtu.orgrekouservice.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.orgrekouservice.domain.FullName;
import fi.helsinki.ohtu.orgrekouservice.service.FullNameService;

@RestController
@RequestMapping("/api/node/fullname")
public class FullNameController {

    @Autowired
    private FullNameService fullNameService;

    private Map<String, List<FullName>> emptyMap() {
        Map<String, List<FullName>> emptyMap = new HashMap<>();
        emptyMap.put("fi", new ArrayList<>());
        emptyMap.put("en", new ArrayList<>());
        emptyMap.put("sv", new ArrayList<>());
        return emptyMap;
    }

    private Comparator<FullName> byDates = new Comparator<FullName>() {
        @Override
        public int compare(FullName a, FullName b) {
            if (a.getStartDate() != null && b.getStartDate() != null) {
                return -a.getStartDate().compareTo(b.getStartDate());
            } else if (a.getStartDate() != null) {
                return 1;
            } else if (b.getStartDate() != null) {
                return -1;
            }
            return 0;
        }
    };

    @RequestMapping(method = GET, value = "/{id}/{date}")
    public Map<String, List<FullName>> getFullNames(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date) {
        List<FullName> fullNames = fullNameService.getFullNamesByUniqueIdAndDate(uniqueId, date);
        if (fullNames.isEmpty()) {
            return emptyMap();
        }
        return fullNames.stream().sorted(byDates).collect(Collectors.groupingBy(FullName::getLanguage));
    }

    @RequestMapping(method = GET, value = "/historyandcurrent/{id}/{date}")
    public Map<String, List<FullName>> getHistoryAndCurrentFullNames(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date) {
        List<FullName> fullNames = fullNameService.getHistoryAndCurrentFullNames(uniqueId, date);
        if (fullNames.isEmpty()) {
            return emptyMap();
        }
        return fullNames.stream().sorted(byDates).collect(Collectors.groupingBy(FullName::getLanguage));
    }

    @RequestMapping(method = GET, value = "/futureandcurrent/{id}/{date}")
    public Map<String, List<FullName>> getFutureAndCurrentFullNames(@PathVariable("id") Integer uniqueId, @PathVariable("date") String date) {
        List<FullName> fullNames = fullNameService.getFutureAndCurrentFullNames(uniqueId, date);
        if (fullNames.isEmpty()) {
            return emptyMap();
        }
        return fullNames.stream().sorted(byDates).collect(Collectors.groupingBy(FullName::getLanguage));
    }
}
