package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @Autowired
    private NodeService nodeService;

    @RequestMapping(method = GET, value = "/{id}/attributes/names/")
    public ResponseEntity<List<Attribute>> getNodeNameAttributes (@PathVariable("id") int nodeUniqueId) {
        try {
            List<Attribute> nodeAttributes = nodeAttributeService.getNodeNameAttributesByNodeId(nodeUniqueId);
            return new ResponseEntity<>(nodeAttributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/attributes/names")
    public ResponseEntity updateNameAttributes(@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(attributes, node.getId());
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeNameAttributes(updatedAttributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/attributes/types")
    public ResponseEntity updateTypeAttributes(@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(attributes, node.getId());
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeTypeAttributes(updatedAttributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
}
