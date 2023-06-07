package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
    public List<HierarchyFilter> getAllHierarchyFilters(String date, String whichtime) {
        String predecessorsUrl = dbUrl + "/api/hierarchyfilter/" + date + "/" + whichtime;
        ResponseEntity<HierarchyFilter[]> response = restTemplate.getForEntity(predecessorsUrl, HierarchyFilter[].class);
        List<HierarchyFilter> hierarchyFilters = List.of(response.getBody());
        return hierarchyFilters;
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
                onlyFutureOrCurrentNode.setHierarchy(wrapper.getHierarchy());
                onlyFutureOrCurrentNode.setStartDate(wrapper.getStartDate());
                onlyFutureOrCurrentNode.setEndDate(wrapper.getEndDate());
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
                onlyHistoryOrCurrentNode.setHierarchy(wrapper.getHierarchy());
                onlyHistoryOrCurrentNode.setStartDate(wrapper.getStartDate());
                onlyHistoryOrCurrentNode.setEndDate(wrapper.getEndDate());
                onlyHistoryAndCurrentNodes.add(onlyHistoryOrCurrentNode);
            }
        }
        return onlyHistoryAndCurrentNodes;
    }

    public List<NodeDTO> getNodesWithTypes(List<Node> nodes, List<NodeWrapper> nodesIdsWithTypes) {
        List<NodeDTO> nodeDTOList = new ArrayList<>();

        for (Node node : nodes) {
            List<NodeWrapper> hierarchies = new ArrayList<>();
            NodeDTO nodeDTO = new NodeDTO();
            for (NodeWrapper wrapper : nodesIdsWithTypes) {
                if (wrapper.getNodeId().equals(node.getId())) {
                    hierarchies.add(wrapper);
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
                    nodeDTO.setAttributes(List.of(getNodeAttributesByNodeIdAndDate(node.getUniqueId(), date)));
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
                        nodeDTO.setAttributes(chooseAttributes(node.getUniqueId(), node.getEndDate(), date));
                    }else{
                        nodeDTO.setAttributes(chooseAttributes(node.getUniqueId(), node.getStartDate(), date));
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
                nodeDTO.setAttributes(chooseAttributes(nodeEdgeHistoryWrapper.getUniqueId(), nodeEdgeHistoryWrapper.getEndDate(), date));
            }else{
                nodeDTO.setAttributes(chooseAttributes(nodeEdgeHistoryWrapper.getUniqueId(), nodeEdgeHistoryWrapper.getStartDate(), date));
            }
            nodeDTOs.add(nodeDTO);
        }
        return nodeDTOs;
    }

    public List<Relative> getPredecessors(Integer uniqueId, String date) {
        String predecessorsUrl = dbUrl + Constants.NODE_API_PATH + "/predecessors1/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(predecessorsUrl, Relative[].class);
        List<Relative> predecessors = List.of(response.getBody());
        return predecessors;
    }

    public List<Relative> getSuccessors(Integer uniqueId, String date) {
        String successorsUrl = dbUrl + Constants.NODE_API_PATH + "/successors1/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(successorsUrl, Relative[].class);
        List<Relative> successors = List.of(response.getBody());
        return successors;
    }

    public List<Relative> getChildren(Integer uniqueId, String date) {
        String childrenUrl = dbUrl + Constants.NODE_API_PATH + "/children1/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(childrenUrl, Relative[].class);
        List<Relative> children = List.of(response.getBody());
        return children;
    }

    public List<Relative> getParents(Integer uniqueId, String date) {
        String parentsUrl = dbUrl + Constants.NODE_API_PATH + "/parents1/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(parentsUrl, Relative[].class);
        List<Relative> parents = List.of(response.getBody());
        return parents;
    }


    public List<Relative> getFutureAndCurrentChildren(Integer uniqueId, String date) {
        String childrenUrl = dbUrl + Constants.NODE_API_PATH + "/children1/futureandcurrent/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(childrenUrl, Relative[].class);
        List<Relative> children = List.of(response.getBody());
        return children;
    }

    public List<Relative> getFutureAndCurrentParents(Integer uniqueId, String date) {
        String parentsUrl = dbUrl + Constants.NODE_API_PATH + "/parents1/futureandcurrent/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(parentsUrl, Relative[].class);
        List<Relative> parents = List.of(response.getBody());
        return parents;
    }

    public List<Relative> getHistoryAndCurrentChildren(Integer uniqueId, String date) {
        String childrenUrl = dbUrl + Constants.NODE_API_PATH + "/children1/historyandcurrent/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(childrenUrl, Relative[].class);
        List<Relative> children = List.of(response.getBody());
        return children;
    }

    public List<Relative> getHistoryAndCurrentParents(Integer uniqueId, String date) {
        String parentsUrl = dbUrl + Constants.NODE_API_PATH + "/parents1/historyandcurrent/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(parentsUrl, Relative[].class);
        List<Relative> parents = List.of(response.getBody());
        return parents;
    }

    public List<Relative> getAllParents(Integer uniqueId, String date) {
        String parentsUrl = dbUrl + Constants.NODE_API_PATH + "/parents1/all/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(parentsUrl, Relative[].class);
        List<Relative> parents = List.of(response.getBody());
        return parents;
    }

    public List<Relative> getAllChildren(Integer uniqueId, String date) {
        String childrenUrl = dbUrl + Constants.NODE_API_PATH + "/children1/all/" + uniqueId + "/" + date;
        ResponseEntity<Relative[]> response = restTemplate.getForEntity(childrenUrl, Relative[].class);
        List<Relative> children = List.of(response.getBody());
        return children;
    }

    public List<RelationDTO> mergeRelativeMaps(Map<String, List<RelativeDTO>> relativeMaps) {
        List<RelationDTO> relativeList = new ArrayList<>();
        if (relativeMaps != null) {
            relativeMaps.values().forEach(relativeDTOList -> {
                relativeDTOList.forEach(relativeDTO -> {
                    RelationDTO foundRelative = relativeList.stream()
                            .filter(relative -> relativeDTO.getUniqueId().equals(relative.getUniqueId()))
                            .findAny()
                            .orElse(null);
                    if (foundRelative != null) {
                        updateRelation(relativeDTO , foundRelative);
                    } else {
                        addNewRelation(relativeList, relativeDTO);
                    }
                });
            });
        }
        return relativeList;
    }

    private static void updateRelation(RelativeDTO relativeDTO, RelationDTO foundRelative) {
        List<FullNameDTO> fullNames = foundRelative.getFullNames();
        addFullName(fullNames, relativeDTO);
        foundRelative.setFullNames(fullNames);
    }

    private static void addNewRelation(List<RelationDTO> relativeList, RelativeDTO relativeDTO) {
        RelationDTO relationDTO = new RelationDTO();
        relationDTO.setId(relativeDTO.getId());
        relationDTO.setUniqueId(relativeDTO.getUniqueId());
        relationDTO.setHierarchies(relativeDTO.getHierarchies());
        List<FullNameDTO> emptyFullNameList = new ArrayList<>();
        List<FullNameDTO> fullNames = addFullName(emptyFullNameList, relativeDTO);
        relationDTO.setFullNames(fullNames);
        relativeList.add(relationDTO);
    }

    private static List<FullNameDTO> addFullName(List<FullNameDTO> fullNames, RelativeDTO relativeDTO) {
        FullNameDTO fullNameDTO = new FullNameDTO();
        fullNameDTO.setName(relativeDTO.getFullName());
        fullNameDTO.setLanguage(relativeDTO.getLanguage());
        fullNames.add(fullNameDTO);
        return fullNames;
    }

    private List<Edge> sanitizeEdges(List<Edge> edges) {
        return edges.stream().filter(edge -> !(edge.isDeleted() && edge.isNew())).collect(Collectors.toList());
    }

    private Map<String, List<Edge>> groupEdges(List<Edge> edges) {
        Map<String, List<Edge>> container = new HashMap<>();
        List<Edge> newEdges = new ArrayList<>();
        List<Edge> deletedEdges = new ArrayList<>();
        List<Edge> updatedEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.isNew()) {
                newEdges.add(edge);
            } else if (edge.isDeleted()) {
                deletedEdges.add(edge);
            } else {
                updatedEdges.add(edge);
            }
        }
        container.put(Constants.NEW_EDGES, newEdges);
        container.put(Constants.DELETED_EDGES, deletedEdges);
        container.put(Constants.UPDATED_EDGES, updatedEdges);
        return container;
    }

    public List<Edge> updateSuccessors(List<Edge> successors) {
        List<Edge> sanitized = sanitizeEdges(successors);
        Map<String, List<Edge>> stateGroupedEdges = groupEdges(sanitized);
        String edgeURL = dbUrl + Constants.EDGE_PATH + "/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity(stateGroupedEdges, headers);
        ResponseEntity<Map> a = restTemplate.exchange(edgeURL, HttpMethod.PUT,  requestEntity, Map.class);
        return successors;
    }

    public Map<String, List<HierarchyFilter>> convertListToMap(List<HierarchyFilter> hierarchyFilters) {
        Map<String, List<HierarchyFilter>> hierarchyFilterMap = hierarchyFilters.stream()
                .collect(groupingBy(HierarchyFilter::getKey));
        return hierarchyFilterMap;
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    private Map<String, List<HierarchyFilter>> uniqueHierarchyFilters(Map<String, List<HierarchyFilter>> hierarchyFilterMap) {
        Map<String, List<HierarchyFilter>> uniqueHierarchyFilterMap = new HashMap<>();
        for (Map.Entry<String, List<HierarchyFilter>> hierarchyFilter : hierarchyFilterMap.entrySet()) {
            List<HierarchyFilter> uniqueHierarchyFilterList = hierarchyFilter.getValue().stream()
                    .filter(h -> Objects.nonNull(h.getValue()))
                    .filter(distinctByKey(h -> h.getValue()))
                    .collect(Collectors.toList());
            uniqueHierarchyFilterMap.put(hierarchyFilter.getKey(), uniqueHierarchyFilterList);
        }
        return uniqueHierarchyFilterMap;
    };

    public Map<String, List<HierarchyFilter>> getUniqueHierarchyFilters(String attributeKeysString) {
        List<HierarchyFilter> hierarchyFilters = !attributeKeysString.isEmpty() ? getHierarchyFiltersByKeys(attributeKeysString) : new ArrayList<>();
        Map<String, List<HierarchyFilter>> hierarchyFilterMap = convertListToMap(hierarchyFilters);
        Map<String, List<HierarchyFilter>> uniqueHierarchyFiltersMap = uniqueHierarchyFilters(hierarchyFilterMap);
        return uniqueHierarchyFiltersMap;
    }

    public List<HierarchyFilter> getHierarchyFiltersByKeys(String keys) throws RestClientException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String hierarchyFiltersByKey = dbUrl + Constants.HIERARCHY_FILTER_PATH + "/hierarchyFiltersByKey/" + keys;
            HttpEntity<Object> requestEntity = new HttpEntity(hierarchyFiltersByKey, headers);
            ResponseEntity<HierarchyFilter[]> response = restTemplate.exchange(hierarchyFiltersByKey, HttpMethod.GET,  requestEntity, HierarchyFilter[].class);
            return List.of(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

}
