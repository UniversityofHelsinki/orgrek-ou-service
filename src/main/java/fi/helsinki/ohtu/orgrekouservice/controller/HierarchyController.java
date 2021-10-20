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

        List<Node> parentNodes = List.of(hierarchyService.getParentNodesByIdAndDate(nodeId, date));
        List<NodeWrapper> parentNodesIdsWithTypes = List.of(hierarchyService.getParentNodeTypesByChildNodeIdAndDate(nodeId, date));

        return hierarchyService.getParentNodesWithTypes(parentNodes, parentNodesIdsWithTypes);


        /*
        List<Node> parents = orgUnitDao.getCurrentParentsByChildNodeId(nodeId, date);
        List<NodeWrapper> wrapperList = orgUnitDao.getCurrentTypesByChildNodeId(nodeId, date);

        List<NodeDTO> nodeDTOList = new ArrayList<>();

        for (Node parent : parents) {
            List<String> hierarchies = new ArrayList<>();
            NodeDTO nodeDTO = new NodeDTO();
            for (NodeWrapper wrapper : wrapperList) {
                if (wrapper.getParentNodeId().equals(parent.getId())) {
                    hierarchies.add(wrapper.getType());
                }
            }
            nodeDTO.setNode(parent);
            nodeDTO.setHierarchies(hierarchies);
            nodeDTOList.add(nodeDTO);
        }

        return nodeDTOList;

         */
    }
}
