package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.NewNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.NodeService;
import fi.helsinki.ohtu.orgrekouservice.service.NodeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeValidationService nodeValidationService;

    @PostMapping("/{id}/insert")
    public ResponseEntity insertNode(@PathVariable("id") int parentNodeUniqueId, @RequestBody NewNodeDTO newNodeDTO) {
        try {
            Node parentNode = nodeService.getNodeByUniqueId(parentNodeUniqueId);
            newNodeDTO = nodeService.updateParentNodeId(newNodeDTO, parentNode);
            newNodeDTO = nodeService.insertNewNode(newNodeDTO);
            return new ResponseEntity<>(newNodeDTO, HttpStatus.OK);

            /*
            ResponseEntity response = nodeValidationService.validateNode(node);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeService.updateNode(updatedNode);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
            */
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    };

    @PutMapping("/{id}/update")
    public ResponseEntity updateNode(@PathVariable("id") int nodeUniqueId, @RequestBody Node node) {
        try {
            Node foundNode = nodeService.getNodeByUniqueId(nodeUniqueId);
            Node updatedNode = nodeService.updateNodeDates(foundNode, node);
            ResponseEntity response = nodeValidationService.validateNode(updatedNode);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                nodeService.updateNode(updatedNode);
                return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(), HttpStatus.BAD_REQUEST);
        }
    };

}
