package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;

import java.text.ParseException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private HierarchyService hierarchyService;

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<NodeDTO> getParentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 2));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(parentNodes, parentNodesIdsWithTypes);
    }
    @RequestMapping(method = GET, value = "/parents/historyandcurrent/{id}/{date}")
    public List<NodeDTO> getParentHistoryAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 0));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getHistoryAndCurrentParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyHistoryAndCurrentNodes(parentNodesIdsWithTypes, date);
        return hierarchyService.getNodesWithTypes(parentNodes, filteredNodeIdsWithTypes);
    }

    @RequestMapping(method = GET, value = "/parents/futureandcurrent/{id}/{date}")
    public List<NodeDTO> getParentFutureAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 1));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getFutureAndCurrentParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyFutureAndCurrentNodes(parentNodesIdsWithTypes, date);
        return hierarchyService.getNodesWithTypes(parentNodes, filteredNodeIdsWithTypes);
    }

    @RequestMapping(method = GET, value = "/children/{id}/{date}")
    public List<NodeDTO> getChildNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 2));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        return hierarchyService.getNodesWithTypes(childNodes, childNodesIdsWithTypes);
    }
    @RequestMapping(method = GET, value = "/children/historyandcurrent/{id}/{date}")
    public List<NodeDTO> getChildHistoryAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 0));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getHistoryAndCurrentChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyHistoryAndCurrentNodes(childNodesIdsWithTypes, date);
        return hierarchyService.getNodesWithTypes(childNodes, filteredNodeIdsWithTypes);
    }

    @RequestMapping(method = GET, value = "/children/futureandcurrent/{id}/{date}")
    public List<NodeDTO> getChildFutureAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 1));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getFutureAndCurrentChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyFutureAndCurrentNodes(childNodesIdsWithTypes, date);
        return hierarchyService.getNodesWithTypes(childNodes, filteredNodeIdsWithTypes);
    }
}
