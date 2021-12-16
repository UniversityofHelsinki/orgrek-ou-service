package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private HierarchyService hierarchyService;

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<NodeDTO> getParentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, false));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(parentNodes, parentNodesIdsWithTypes);
    }
    @RequestMapping(method = GET, value = "/parents/history/{id}/{date}")
    public List<NodeDTO> getParentHistoryNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, true  ));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getAllParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(parentNodes, parentNodesIdsWithTypes);
    }

    @RequestMapping(method = GET, value = "/children/{id}/{date}")
    public List<NodeDTO> getChildNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, false));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(childNodes, childNodesIdsWithTypes);
    }
    @RequestMapping(method = GET, value = "/children/history/{id}/{date}")
    public List<NodeDTO> getChildHistoryNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, true));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getAllChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(childNodes, childNodesIdsWithTypes);
    }
}
