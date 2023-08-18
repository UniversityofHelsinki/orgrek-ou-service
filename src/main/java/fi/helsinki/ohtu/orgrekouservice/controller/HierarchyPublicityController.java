package fi.helsinki.ohtu.orgrekouservice.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import fi.helsinki.ohtu.orgrekouservice.domain.EmptyJsonResponse;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.domain.NewHierarchyPublicityDTO;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityService;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityValidationService;

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
    public ResponseEntity<?> updatePublicity(@RequestBody HierarchyPublicity hierarchyPublicity) {
        try {
            HierarchyPublicity foundHierarchyPublicity = hierarchyPublicityService.getHierarchyTypeById(hierarchyPublicity.getId());
            ResponseEntity<?> response = hierarchyPublicityValidationService.validateHierarchyPublicity(hierarchyPublicity, foundHierarchyPublicity);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                hierarchyPublicityService.update(hierarchyPublicity);
                return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/insertPublicity", headers = "user", produces = "application/json")
    public ResponseEntity<?> insertPublicity(@RequestBody NewHierarchyPublicityDTO hierarchyPublicityDTO, @RequestHeader String user) {
        try {
            ResponseEntity<?> response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(hierarchyPublicityDTO);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                hierarchyPublicityService.insert(hierarchyPublicityDTO, user);
                return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
}
