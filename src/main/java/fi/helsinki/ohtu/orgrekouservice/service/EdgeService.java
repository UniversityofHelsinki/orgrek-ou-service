package fi.helsinki.ohtu.orgrekouservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EdgeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    private User getUser(String user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User userObject = objectMapper.readValue(user, User.class);
        return userObject;
    }

    public List<String> filterHierarchyTypesForUser(List<String> types, User user){
        List<String> hierarchyTypes = new ArrayList<>();
        for (String hierarchyType : types) {
            if (hierarchyType.equals(Constants.ECONOMY_HIERARCHY) || user.getRoles().stream().anyMatch(element -> Constants.MAPPED_ROLES.contains(element))) {
                hierarchyTypes.add(hierarchyType);
            }
        }
        return hierarchyTypes;
    }

    public List<String> getHierarchyTypesForUser(String user) throws JsonProcessingException {
        User loggedUser = getUser(user);
        String getHierarchyTypes = dbUrl + Constants.EDGE_PATH + "/types";
        String[] response = restTemplate.getForObject(getHierarchyTypes, String[].class);
        List<String> hierarchyTypes = List.of(response);
        //List<String> filteredHierarchyTypes = filterHierarchyTypesForUser(hierarchyTypes, loggedUser);
        return hierarchyTypes;
    }
    public List<String> getHierarchyTypes() {
        String getHierarchyTypes = dbUrl + Constants.EDGE_PATH + "/types";
        String[] response = restTemplate.getForObject(getHierarchyTypes, String[].class);
        List<String> types = List.of(response);

        return types;
    }

    private Edge edgeMapperToEdge(EdgeWrapper edgeWrapper) {
        Edge edge = new Edge();
        edge.setId(edgeWrapper.getId());
        edge.setParentUniqueId(edgeWrapper.getParentUniqueId());
        edge.setChildUniqueId(edgeWrapper.getChildUniqueId());
        edge.setStartDate(edgeWrapper.getStartDate());
        edge.setEndDate(edgeWrapper.getEndDate());
        edge.setHierarchy(edgeWrapper.getHierarchy());
        return edge;
    }

    public Map<String, List<Edge>> extractEdgesToMap(List<EdgeWrapper> nodeAttributes) {
        try {
            Map<String , List<Edge>> upperUnitsMap = new HashMap();
            List<Edge> newAttributes = new ArrayList<>();
            List<Edge> updatedAttributes = new ArrayList<>();
            List<Edge> deletedAttributes = new ArrayList<>();
            for (EdgeWrapper nodeAttribute : nodeAttributes) {
                if (nodeAttribute.isNew()) {
                    newAttributes.add(edgeMapperToEdge(nodeAttribute));
                } else if (nodeAttribute.isDeleted()) {
                    deletedAttributes.add(edgeMapperToEdge(nodeAttribute));
                } else {
                    updatedAttributes.add(edgeMapperToEdge(nodeAttribute));
                }
            }
            upperUnitsMap.put(Constants.NEW_EDGES , newAttributes);
            upperUnitsMap.put(Constants.UPDATED_EDGES, updatedAttributes);
            upperUnitsMap.put(Constants.DELETED_EDGES, deletedAttributes);
            return upperUnitsMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateEdges(List<EdgeWrapper> nodeNameAttributes, String path) {
        try {
            Map<String, List<Edge>> upperUnitsMap = extractEdgesToMap(nodeNameAttributes);
            String updateNodeEdgesUrl = dbUrl + Constants.EDGE_PATH + path;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(upperUnitsMap, headers);
            restTemplate.exchange(updateNodeEdgesUrl, HttpMethod.PUT,  requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }


    public List<EdgeWrapper> sanitizeAttributes(List<EdgeWrapper> attributes) {
        attributes.removeIf(x -> x.isDeleted() && x.isNew());
        return attributes;
    }

}
