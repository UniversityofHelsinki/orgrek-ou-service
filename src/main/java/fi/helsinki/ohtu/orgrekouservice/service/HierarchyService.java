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

    RestTemplate restTemplate = new RestTemplate();

    public Node[] getParentNodesByIdAndDate(String nodeId, String date) {
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/" + nodeId + "/" + date;
        ResponseEntity<Node[]> response = restTemplate.getForEntity(parentNodesResourceUrl, Node[].class);
        return response.getBody();
    }

    public NodeWrapper[] getParentNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(parentNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public Node[] getChildNodesByIdAndDate(String nodeId, String date) {
        String childrenNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/" + nodeId + "/" + date;
        ResponseEntity<Node[]> response = restTemplate.getForEntity(childrenNodesResourceUrl, Node[].class);
        return response.getBody();
    }

    public NodeWrapper[] getChildNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String childNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(childNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public List<NodeDTO> getNodesWithTypes(List<Node> nodes, List<NodeWrapper> nodesIdsWithTypes) {
        List<NodeDTO> nodeDTOList = new ArrayList<>();

        for (Node node : nodes) {
            List<String> hierarchies = new ArrayList<>();
            NodeDTO nodeDTO = new NodeDTO();
            for (NodeWrapper wrapper : nodesIdsWithTypes) {
                if (wrapper.getNodeId().equals(node.getId())) {
                    hierarchies.add(wrapper.getType());
                }
            }
            nodeDTO.setNode(node);
            nodeDTO.setHierarchies(hierarchies);
            nodeDTOList.add(nodeDTO);
        }

        return nodeDTOList;
    }

}
