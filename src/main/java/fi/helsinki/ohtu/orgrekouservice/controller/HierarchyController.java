package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeEdgeHistoryWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeWrapper;
import fi.helsinki.ohtu.orgrekouservice.service.UtilService;
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

    @Autowired
    private UtilService utilService;

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<NodeDTO> getParentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 2));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(parentNodes, parentNodesIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithAttributes(parentNodes, nodeDTOS, date);
        return utilService.getDisplayNames(nodeDTOS);
    }
    @RequestMapping(method = GET, value = "/parents/historyandcurrent/{id}/{date}")
    public List<NodeDTO> getParentHistoryAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 0));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getHistoryAndCurrentParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyHistoryAndCurrentNodes(parentNodesIdsWithTypes, date);
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(parentNodes, filteredNodeIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithFutureOrHistoryAttributes(parentNodes, nodeDTOS, date, true);
        return utilService.getDisplayNames(nodeDTOS);
    }

    @RequestMapping(method = GET, value = "/parents/futureandcurrent/{id}/{date}")
    public List<NodeDTO> getParentFutureAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date, 1));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getFutureAndCurrentParentNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyFutureAndCurrentNodes(parentNodesIdsWithTypes, date);
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(parentNodes, filteredNodeIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithFutureOrHistoryAttributes(parentNodes, nodeDTOS, date, false);
        return utilService.getDisplayNames(nodeDTOS);
    }

    @RequestMapping(method = GET, value = "/children/{id}/{date}")
    public List<NodeDTO> getChildNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 2));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(childNodes, childNodesIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithAttributes(childNodes, nodeDTOS, date);
        return utilService.getDisplayNames(nodeDTOS);
    }
    @RequestMapping(method = GET, value = "/children/historyandcurrent/{id}/{date}")
    public List<NodeDTO> getChildHistoryAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 0));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getHistoryAndCurrentChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyHistoryAndCurrentNodes(childNodesIdsWithTypes, date);
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(childNodes, filteredNodeIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithFutureOrHistoryAttributes(childNodes, nodeDTOS, date, true);
        return utilService.getDisplayNames(nodeDTOS);
    }

    @RequestMapping(method = GET, value = "/children/futureandcurrent/{id}/{date}")
    public List<NodeDTO> getChildFutureAndCurrentNodesWithTypesByIdAndDate(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<Node> childNodes = List.of(hierarchyService.getChildNodesByIdAndDate(nodeId, date, 1));
        List<NodeWrapper> childNodesIdsWithTypes = List.of(hierarchyService.getFutureAndCurrentChildNodeTypesByChildNodeIdAndDate(nodeId, date));
        List<NodeWrapper> filteredNodeIdsWithTypes = hierarchyService.filterOnlyFutureAndCurrentNodes(childNodesIdsWithTypes, date);
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithTypes(childNodes, filteredNodeIdsWithTypes);
        nodeDTOS = hierarchyService.getNodesWithFutureOrHistoryAttributes(childNodes, nodeDTOS, date, false);
        return utilService.getDisplayNames(nodeDTOS);
    }

    @RequestMapping(method = GET, value = "/predecessors/{id}/{date}")
    public List<NodeDTO> getPredecessorsById(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<NodeEdgeHistoryWrapper> predecessorNodes = List.of(hierarchyService.getPredecessorsById(nodeId));
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithPredecessorOrSuccessorAttributes(predecessorNodes, date,true);
        return utilService.getDisplayNames(nodeDTOS);
    }

    @RequestMapping(method = GET, value = "/successors/{id}/{date}")
    public List<NodeDTO> getSuccessorsById(@PathVariable("id") String nodeId, @PathVariable("date") String date) throws ParseException {
        List<NodeEdgeHistoryWrapper> predecessorNodes = List.of(hierarchyService.getSuccessorsById(nodeId));
        List<NodeDTO> nodeDTOS = hierarchyService.getNodesWithPredecessorOrSuccessorAttributes(predecessorNodes, date,false);
        return utilService.getDisplayNames(nodeDTOS);
    }
}
