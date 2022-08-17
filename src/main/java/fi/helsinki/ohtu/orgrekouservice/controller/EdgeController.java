package fi.helsinki.ohtu.orgrekouservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyList;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {
    @Autowired
    private EdgeService edgeService;

    @RequestMapping(method = GET, value = "/types", headers = "user", produces = "application/json")
    public List<String> getHierarchyTypes(@RequestHeader String user) throws JsonProcessingException {
        return edgeService.getHierarchyTypesForUser(user);
    }

}
