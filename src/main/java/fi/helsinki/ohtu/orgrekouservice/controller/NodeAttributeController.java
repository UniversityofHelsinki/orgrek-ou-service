package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node/attributes")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @RequestMapping(method = GET, value = "/names/{id}")
    public ResponseEntity<List<Attribute>> getNodeNameAttributes (@PathVariable("id") int nodeUniqueId) {
        try {
            List<Attribute> nodeAttributes = nodeAttributeService.getNodeNameAttributesByNodeId(nodeUniqueId);
            return new ResponseEntity<>(nodeAttributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/names")
    public ResponseEntity<List<Attribute>> updateNameAttributes(@RequestBody List<Attribute> attributes) {
        try {
            /*
                Here goes the validation logic
             */
            nodeAttributeService.updateNodeNameAttributes(attributes);
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(attributes, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/names")
    public ResponseEntity<List<Attribute>> addNameAttributes(@RequestBody List<Attribute> attributes) {
        try {
            /*
                Here goes the validation logic
             */
            nodeAttributeService.addNodeNameAttributes(attributes);
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(attributes, HttpStatus.BAD_REQUEST);
        }
    }
}
