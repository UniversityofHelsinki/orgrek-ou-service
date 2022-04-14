package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HierarchyService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    private String historyParameter = "historyandcurrent/";
    private String futureParameter = "futureandcurrent/";

    @Autowired
    private UtilService utilService;

    public Node getNodeByNodeId(String nodeId) {
        String getNodeByIdResourceUrl = dbUrl + Constants.NODE_API_PATH + "/id/" + nodeId;
        ResponseEntity<Node> response = restTemplate.getForEntity(getNodeByIdResourceUrl, Node.class);
        return response.getBody();
    }

    public Node[] getParentNodesByIdAndDate(String nodeId, String date, int time) {
       String timeParameter = "";
        if(time == 0){
            timeParameter = historyParameter;
       }else if(time == 1){
            timeParameter = futureParameter;
        }
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/" + timeParameter +  nodeId + "/" + date;
        ResponseEntity<Node[]> response = restTemplate.getForEntity(parentNodesResourceUrl, Node[].class);
        return response.getBody();
    }

    public NodeWrapper[] getParentNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(parentNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public Attribute[] getNodeAttributesByNodeIdAndDate(int nodeId, String date) {
        String nodeAttributesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/" + nodeId + "/" + date + "/attributes";
        ResponseEntity<Attribute[]> response = restTemplate.getForEntity(nodeAttributesResourceUrl, Attribute[].class);
        return response.getBody();
    }

    public Attribute[] getNodeHistoryAndCurrentAttributesByNodeIdAndDate(int nodeId, String date) {
        String nodeAttributesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/historyandcurrent/" + nodeId + "/" + date + "/attributes";
        ResponseEntity<Attribute[]> response = restTemplate.getForEntity(nodeAttributesResourceUrl, Attribute[].class);
        return response.getBody();
    }

    public Attribute[] getNodeFutureAndCurrentAttributesByNodeIdAndDate(int nodeId, String date) {
        String nodeAttributesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/futureandcurrent/" + nodeId + "/" + date + "/attributes";
        ResponseEntity<Attribute[]> response = restTemplate.getForEntity(nodeAttributesResourceUrl, Attribute[].class);
        return response.getBody();
    }

    public NodeWrapper[] getHistoryAndCurrentParentNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/historyandcurrent/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(parentNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public NodeWrapper[] getFutureAndCurrentParentNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String parentNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/parents/futureandcurrent/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(parentNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public Node[] getChildNodesByIdAndDate(String nodeId, String date, int time) {
        String timeParameter = "";
        if(time == 0){
            timeParameter = historyParameter;
        }else if(time == 1){
            timeParameter = futureParameter;
        }
        String childrenNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/" + timeParameter + nodeId + "/" + date;
        ResponseEntity<Node[]> response = restTemplate.getForEntity(childrenNodesResourceUrl, Node[].class);
        return response.getBody();
    }

    public NodeWrapper[] getChildNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String childNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(childNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public NodeWrapper[] getHistoryAndCurrentChildNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String childNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/historyandcurrent/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(childNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public NodeWrapper[] getFutureAndCurrentChildNodeTypesByChildNodeIdAndDate(String nodeId, String date) {
        String childNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/children/futureandcurrent/types/" + nodeId + "/" + date;
        ResponseEntity<NodeWrapper[]> response = restTemplate.getForEntity(childNodesResourceUrl, NodeWrapper[].class);
        return response.getBody();
    }

    public NodeEdgeHistoryWrapper[] getPredecessorsById(String nodeId) {
        String predecessorsNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/predecessors/" + nodeId;
        ResponseEntity<NodeEdgeHistoryWrapper[]> response = restTemplate.getForEntity(predecessorsNodesResourceUrl, NodeEdgeHistoryWrapper[].class);
        return response.getBody();
    }

    public NodeEdgeHistoryWrapper[] getSuccessorsById(String nodeId) {
        String successorsNodesResourceUrl = dbUrl + Constants.NODE_API_PATH + "/successors/" + nodeId;
        ResponseEntity<NodeEdgeHistoryWrapper[]> response = restTemplate.getForEntity(successorsNodesResourceUrl, NodeEdgeHistoryWrapper[].class);
        return response.getBody();
    }

    private boolean isNodeFutureOrCurrentNode(Node node, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date nodeDate = formatter.parse(date);
        if (node.getEndDate() == null || node.getEndDate().after(nodeDate)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNodeHistoryOrCurrentNode(Node node, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date nodeDate = formatter.parse(date);
        if (node.getStartDate() == null || node.getStartDate().before(nodeDate)) {
            return true;
        } else {
            return false;
        }
    }

    public List<NodeWrapper> filterOnlyFutureAndCurrentNodes(List<NodeWrapper> nodeWrapperList, String date) throws ParseException {
        List<NodeWrapper> onlyFutureAndCurrentNodes = new ArrayList<>();
        Map<String, Node> validFutureAndCurrentNodes = new HashMap<String, Node>();
        Node node;
        for (NodeWrapper wrapper : nodeWrapperList) {
            NodeWrapper onlyFutureOrCurrentNode = new NodeWrapper();
            if (validFutureAndCurrentNodes.containsKey(wrapper.getNodeId())) {
                node = validFutureAndCurrentNodes.get(wrapper.getNodeId());
            } else {
                node = getNodeByNodeId(wrapper.getNodeId());
            }
            if (isNodeFutureOrCurrentNode(node, date)) {
                validFutureAndCurrentNodes.put(node.getId(), node);
                onlyFutureOrCurrentNode.setNodeId(wrapper.getNodeId());
                onlyFutureOrCurrentNode.setType(wrapper.getType());
                onlyFutureAndCurrentNodes.add(onlyFutureOrCurrentNode);
            }
        }
        return onlyFutureAndCurrentNodes;
    }

    public List<NodeWrapper> filterOnlyHistoryAndCurrentNodes(List<NodeWrapper> nodeWrapperList, String date) throws ParseException {
       List<NodeWrapper> onlyHistoryAndCurrentNodes = new ArrayList<>();
        Map<String, Node> validHistoryAndCurrentNodes = new HashMap<String, Node>();
        Node node;
        for (NodeWrapper wrapper : nodeWrapperList) {
            NodeWrapper onlyHistoryOrCurrentNode = new NodeWrapper();
            if (validHistoryAndCurrentNodes.containsKey(wrapper.getNodeId())) {
                node = validHistoryAndCurrentNodes.get(wrapper.getNodeId());
            } else {
                node = getNodeByNodeId(wrapper.getNodeId());
            }
            if (isNodeHistoryOrCurrentNode(node, date)) {
                validHistoryAndCurrentNodes.put(node.getId(), node);
                onlyHistoryOrCurrentNode.setNodeId(wrapper.getNodeId());
                onlyHistoryOrCurrentNode.setType(wrapper.getType());
                onlyHistoryAndCurrentNodes.add(onlyHistoryOrCurrentNode);
            }
        }
        return onlyHistoryAndCurrentNodes;
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

    public List<NodeDTO> getNodesWithAttributes(List<Node> nodes, List<NodeDTO> nodeDTOs, String date) {
        for (Node node : nodes) {
            for(NodeDTO nodeDTO : nodeDTOs){
                if(node.getId().equals(nodeDTO.getNode().getId())){
                    nodeDTO.setAttributes(List.of(getNodeAttributesByNodeIdAndDate(node.getUnique_id(), date)));
                }
            }
        }
        return nodeDTOs;
    }

    public List<Attribute> chooseAttributes(int uniqueId, Date nodeDate, String date) throws ParseException {
        List<Attribute> attributes = List.of(getNodeAttributesByNodeIdAndDate(uniqueId, date));
        if(!attributes.isEmpty() && utilService.isThereNameAttributes(attributes)){
            return attributes;
        }else {
            //if there aren't current name attributes
            if(nodeDate!=null){
                String formattedDate = utilService.parseDateFromDatabase(nodeDate.toString());;
                attributes = List.of(getNodeAttributesByNodeIdAndDate(uniqueId, formattedDate));
                return attributes;
            }
        }
        return attributes;
    }

    public List<NodeDTO> getNodesWithFutureOrHistoryAttributes(List<Node> nodes, List<NodeDTO> nodeDTOs, String date, boolean isHistory) throws ParseException {
        for (Node node : nodes) {
            for(NodeDTO nodeDTO : nodeDTOs){
                if(node.getId().equals(nodeDTO.getNode().getId())){
                    if(isHistory){
                        nodeDTO.setAttributes(chooseAttributes(node.getUnique_id(), node.getEndDate(), date));
                    }else{
                        nodeDTO.setAttributes(chooseAttributes(node.getUnique_id(), node.getStartDate(), date));
                    }
                }
            }
        }
        return nodeDTOs;
    }

    public List<NodeDTO> getNodesWithPredecessorOrSuccessorAttributes(List<NodeEdgeHistoryWrapper> nodes, String date, boolean isPredecessor) throws ParseException {
        List<NodeDTO> nodeDTOs = new ArrayList<>();
        for (NodeEdgeHistoryWrapper nodeEdgeHistoryWrapper : nodes) {
            NodeDTO nodeDTO = new NodeDTO();
            nodeDTO.setNodeEdgeHistoryWrapper(nodeEdgeHistoryWrapper);
            if(isPredecessor){
                nodeDTO.setAttributes(chooseAttributes(nodeEdgeHistoryWrapper.getUnique_id(), nodeEdgeHistoryWrapper.getEndDate(), date));
            }else{
                nodeDTO.setAttributes(chooseAttributes(nodeEdgeHistoryWrapper.getUnique_id(), nodeEdgeHistoryWrapper.getStartDate(), date));
            }
            nodeDTOs.add(nodeDTO);
        }
        return nodeDTOs;
    }
}
