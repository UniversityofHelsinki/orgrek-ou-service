package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityService;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/hierarchy")
public class HierarchyPublicityController {

    @Autowired
    private HierarchyPublicityService hierarchyPublicityService;

    @Autowired
    private HierarchyPublicityValidationService hierarchyPublicityValidationService;

    @RequestMapping(method = GET, value = "/types", headers = "user", produces = "application/json")
    public List<String> getHierarchyTypes(@RequestHeader String user) throws JsonProcessingException {
        return hierarchyPublicityService.getHierarchyTypesForUser(user);
    }

    @GetMapping(value = "/publicityList", produces = "application/json")
    public List<HierarchyPublicity> getHierarchyPublicityList() {
        return hierarchyPublicityService.getHierarchyPublicityList();
    }

    @PutMapping(value = "/updatePublicity", produces = "application/json")
    public ResponseEntity updatePublicity(@RequestBody HierarchyPublicity hierarchyPublicity) {
        try {
            HierarchyPublicity foundHierarchyPublicity = hierarchyPublicityService.getHierarchyTypeById(hierarchyPublicity.getId());
            ResponseEntity response = hierarchyPublicityValidationService.validateHierarchyPublicity(hierarchyPublicity, foundHierarchyPublicity);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                hierarchyPublicityService.update(hierarchyPublicity);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
 }
