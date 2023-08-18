package fi.helsinki.ohtu.orgrekouservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {
    @Autowired
    private EdgeService edgeService;

    @Autowired
    private EdgeAttributeValidationService edgeAttributeValidationService;

    @PutMapping("/parents")
    public ResponseEntity<?> updateParents(@RequestBody List<EdgeWrapper> attributes) {
        try {
            List<EdgeWrapper> sanitizedAttributes = edgeService.sanitizeAttributes(attributes);
            ResponseEntity<?> response = edgeAttributeValidationService.validateEdges(sanitizedAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                edgeService.updateEdges(sanitizedAttributes,"/parents");
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/children")
    public ResponseEntity<?> updateChildren(@RequestBody List<EdgeWrapper> attributes) {
        try {
            List<EdgeWrapper> sanitizedAttributes = edgeService.sanitizeAttributes(attributes);
            ResponseEntity<?> response = edgeAttributeValidationService.validateEdges(sanitizedAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                edgeService.updateEdges(sanitizedAttributes, "/children");
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
}
