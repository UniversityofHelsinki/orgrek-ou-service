package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/node/attributes")
public class NodeAttributeController {

    @Autowired
    private NodeAttributeService nodeAttributeService;

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

}
