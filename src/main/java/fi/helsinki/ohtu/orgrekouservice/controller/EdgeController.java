package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.domain.Edge;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyList;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {
    @Autowired
    private EdgeService edgeService;

    @Autowired
    private EdgeAttributeValidationService edgeAttributeValidationService;

    @RequestMapping(method = GET, value = "/types", headers = "user", produces = "application/json")
    public List<String> getHierarchyTypes(@RequestHeader String user) throws JsonProcessingException {
        return edgeService.getHierarchyTypesForUser(user);
    }

    @PutMapping("/parents")
    public ResponseEntity updateParents(@RequestBody List<EdgeWrapper> attributes) {
        try {
            List<EdgeWrapper> sanitizedAttributes = edgeService.sanitizeAttributes(attributes);
            ResponseEntity response = edgeAttributeValidationService.validateEdges(sanitizedAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                edgeService.updateParents(sanitizedAttributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

}
