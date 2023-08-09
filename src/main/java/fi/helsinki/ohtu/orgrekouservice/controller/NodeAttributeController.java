package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.service.*;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/node")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @Autowired
    private SectionAttributeService sectionAttributeService;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @Autowired
    private NodeService nodeService;

    @PutMapping("/{id}/attributes/names")
    public ResponseEntity updateNameAttributes(@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(sanitizedAttributes, node.getId());
            List<SectionAttribute> sectionAttributes = sectionAttributeService.getValidAttributesFor(Constants.NAME_ATTRIBUTES);
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, sectionAttributes);
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
            List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(sanitizedAttributes, node.getId());
            List<SectionAttribute> sectionAttributes = sectionAttributeService.getValidAttributesFor(Constants.TYPE_ATTRIBUTES);
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, sectionAttributes);
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


    @PutMapping("/{id}/attributes/codes")
    public ResponseEntity updateCodeAttributes (@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(attributes, node.getId());
            List<SectionAttribute> sectionAttributes = sectionAttributeService.getValidAttributesFor(Constants.CODE_ATTRIBUTES);
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, sectionAttributes);
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

    @PutMapping("/{id}/attributes/others")
    public ResponseEntity updateOtherAttributes (@PathVariable("id") int nodeUniqueId, @RequestBody List<Attribute> attributes) {
        try {
            Node node = nodeService.getNodeByUniqueId(nodeUniqueId);
            nodeAttributeService.sanitizeAttributes(attributes);
            List<Attribute> updatedAttributes = nodeAttributeService.updateNodeIdToAttributes(attributes, node.getId());
            List<SectionAttribute> sectionAttributes = sectionAttributeService.getValidAttributesFor(Constants.OTHER_ATTRIBUTES);
            ResponseEntity response = nodeAttributeValidationService.validateNodeAttributes(updatedAttributes, sectionAttributes);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeAttributeService.updateNodeOtherAttributes(updatedAttributes);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/attributes/distinctattributes")
    public Map<String, List<String>> getDistinctNodeAttrs() throws RestClientException {

        List<NodeAttributeKeyValueDTO> nodeAttributeKeyValues = nodeAttributeService.getDistinctNodeAttrs();
        Map<String, List<String>> nodeAttributeKeyValuesMap = new HashMap<>();
        for (NodeAttributeKeyValueDTO nodeAttributeKeyValueDTO : nodeAttributeKeyValues) {
            if (!nodeAttributeKeyValuesMap.containsKey(nodeAttributeKeyValueDTO.getKey())) {
                nodeAttributeKeyValuesMap.put(nodeAttributeKeyValueDTO.getKey(), new ArrayList<String>());
            }
            nodeAttributeKeyValuesMap.get(nodeAttributeKeyValueDTO.getKey()).add(nodeAttributeKeyValueDTO.getValue());
        }
        return nodeAttributeKeyValuesMap;
    }

}
