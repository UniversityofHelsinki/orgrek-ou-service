package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node/attributes")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @RequestMapping(method = GET, value = "/names/{id}")
    public ResponseEntity<List<Attribute>> getNodeNameAttributes (@PathVariable("id") int nodeUniqueId) {
        try {
            List<Attribute> nodeAttributes = nodeAttributeService.getNodeNameAttributesByNodeId(nodeUniqueId);
            return new ResponseEntity<>(nodeAttributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/names")
    public ResponseEntity updateNameAttributes(@RequestBody List<Attribute> attributes) {
        try {
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(attributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeNameAttributes(attributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/types")
    public ResponseEntity updateTypeAttributes(@RequestBody List<Attribute> attributes) {
        try {
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(attributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeTypeAttributes(attributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
}
