package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeWrapper;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HierarchyService {

    @Value("${server.url}")
    private String dbUrl;

    public Node[] getParentNodesByIdAndDate(String nodeId, String date) {
        RestTemplate restTemplate = new RestTemplate();
        String parentNodesResourceUrl = "http://localhost:8000" + Constants.NODE_API_PATH + "/parents/" + nodeId + "/" + date;
        System.out.println(parentNodesResourceUrl);
        ResponseEntity<Node[]> response = restTemplate.getForEntity(parentNodesResourceUrl, Node[].class);
        return response.getBody();
    }

    public NodeWrapper[] getParentNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        RestTemplate restTemplate = new RestTemplate();
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(parentNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public List<NodeDTO> getParentNodesWithTypes(List<Node> parentNodes, List<NodeWrapper> parentNodesIdsWithTypes) {
        List<NodeDTO> nodeDTOList = new ArrayList<>();

        for (Node parent : parentNodes) {
            List<String> hierarchies = new ArrayList<>();
            NodeDTO nodeDTO = new NodeDTO();
            for (NodeWrapper wrapper : parentNodesIdsWithTypes) {
                if (wrapper.getParentNodeId().equals(parent.getId())) {
                    hierarchies.add(wrapper.getType());
                }
            }
            nodeDTO.setNode(parent);
            nodeDTO.setHierarchies(hierarchies);
            nodeDTOList.add(nodeDTO);
        }

        return nodeDTOList;
    }

}
