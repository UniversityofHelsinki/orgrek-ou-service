package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/hierarchy")
public class HierarchyPublicityController {

    @Autowired
    private HierarchyPublicityService hierarchyPublicityService;

    @RequestMapping(method = GET, value = "/types", headers = "user", produces = "application/json")
    public List<String> getHierarchyTypes(@RequestHeader String user) throws JsonProcessingException {
        return hierarchyPublicityService.getHierarchyTypesForUser(user);
    }

    @GetMapping(value = "/publicityList", produces = "application/json")
    public List<HierarchyPublicity> getHierarchyPublicityList() {
        return hierarchyPublicityService.getHierarchyPublicityList();
    }
 }
