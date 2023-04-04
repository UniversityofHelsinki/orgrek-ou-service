package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @Autowired
    private NodeService nodeService;

    @GetMapping("/{id}/attributes/names")
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
            List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(sanitizedAttributes, node.getId());
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, Constants.NAME_ATTRIBUTES);
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
    @GetMapping("/{id}/attributes/types")
    public ResponseEntity<List<Attribute>> getNodeTypeAttributes (@PathVariable("id") int nodeUniqueId) {
        try {
            List<Attribute> nodeAttributes = nodeAttributeService.getNodeTypeAttributesByNodeId(nodeUniqueId);
            return new ResponseEntity<>(nodeAttributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}/attributes/types")
    public ResponseEntity updateTypeAttributes(@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(sanitizedAttributes, node.getId());
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, Constants.TYPE_ATTRIBUTES);
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

    @GetMapping("/{id}/attributes/codes")
    public ResponseEntity<List<Attribute>> getNodeCodeAttributes (@PathVariable("id") int nodeUniqueId) {
        try {
            List<Attribute> nodeAttributes = nodeAttributeService.getNodeCodeAttributesByNodeId(nodeUniqueId);
            return new ResponseEntity<>(
                    nodeAttributes,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/attributes/codes")
    public ResponseEntity updateCodeAttributes (@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(attributes, node.getId());
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, Constants.CODE_ATTRIBUTES);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeCodeAttributes(updatedAttributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }
}
