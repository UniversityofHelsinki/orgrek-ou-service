package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyFilter;
import fi.helsinki.ohtu.orgrekouservice.domain.OtherAttributeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NodeAttributeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<Attribute> updateNodeIdToAttributes(List<Attribute> nodeAttributes, String nodeId) {
        try {
            for (Attribute nodeAttribute : nodeAttributes) {
                nodeAttribute.setNodeId(nodeId);
            }
            return nodeAttributes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public Map<String, List<Attribute>> extractAttributesToMap(List<Attribute> nodeAttributes) {
        try {
            Map<String , List<Attribute>> nameAttributesMap = new HashMap();
            List<Attribute> newAttributes = new ArrayList<>();
            List<Attribute> updatedAttributes = new ArrayList<>();
            List<Attribute> deletedAttributes = new ArrayList<>();
            for (Attribute nodeAttribute : nodeAttributes) {
                if (nodeAttribute.isNew()) {
                    newAttributes.add(nodeAttribute);
                } else if (nodeAttribute.isDeleted()) {
                    deletedAttributes.add(nodeAttribute);
                } else {
                    updatedAttributes.add(nodeAttribute);
                }
            }
            nameAttributesMap.put(Constants.NEW_ATTRIBUTES , newAttributes);
            nameAttributesMap.put(Constants.UPDATED_ATTRIBUTES, updatedAttributes);
            nameAttributesMap.put(Constants.DELETED_ATTRIBUTES, deletedAttributes);
            return nameAttributesMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNodeNameAttributes(List<Attribute> nodeNameAttributes) {
        try {
            Map<String, List<Attribute>> nameAttributesMap = extractAttributesToMap(nodeNameAttributes);
            String updateNodeNameAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/name/attributes";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(nameAttributesMap, headers);
            restTemplate.exchange(updateNodeNameAttributesUrl, HttpMethod.PUT,  requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Attribute> getNodeNameAttributesByNodeId(int nodeUniqueId) {
        try {
            String nodeNameAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/name/attributes/" + nodeUniqueId;
            return getNodeAttributes(nodeNameAttributesUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNodeTypeAttributes(List<Attribute> nodeTypeAttributes) {
        try {
            Map<String, List<Attribute>> typeAttributesMap = extractAttributesToMap(nodeTypeAttributes);
            String updateNodeTypeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/type/attributes";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(typeAttributesMap, headers);
            restTemplate.exchange(updateNodeTypeAttributesUrl, HttpMethod.PUT,  requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Attribute> getNodeTypeAttributesByNodeId(int nodeUniqueId) {
        try {
            String nodeTypeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/type/attributes/" + nodeUniqueId;
            return getNodeAttributes(nodeTypeAttributesUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNodeCodeAttributes(List<Attribute> nodeCodeAttributes) {
        try {
            Map<String, List<Attribute>> codeAttributesMap = extractAttributesToMap(nodeCodeAttributes);
            String updateNodeCodeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/code/attributes";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(codeAttributesMap, headers);
            restTemplate.exchange(updateNodeCodeAttributesUrl, HttpMethod.PUT, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Attribute> getNodeCodeAttributesByNodeId(int nodeUniqueId) {
        try {
            String nodeCodeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/code/attributes/" + nodeUniqueId;
            return getNodeAttributes(nodeCodeAttributesUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNodeOtherAttributes(List<Attribute> nodeCodeAttributes) {
        try {
            Map<String, List<Attribute>> codeAttributesMap = extractAttributesToMap(nodeCodeAttributes);
            String updateNodeCodeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/other/attributes";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(codeAttributesMap, headers);
            restTemplate.exchange(updateNodeCodeAttributesUrl, HttpMethod.PUT, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OtherAttributeDTO> getNodeOtherAttributesByNodeId(int nodeUniqueId, List<String> selectedHierarchies) {
        try {
            String nodeCodeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/other/attributes/" + nodeUniqueId;
            List <Attribute> otherAttributes = getNodeAttributes(nodeCodeAttributesUrl);
            String selected = String.join(",", selectedHierarchies);
            List<String> attributeKeys = getAttributeKeys(selected);
            String attributeKeysString = String.join(",", attributeKeys);
            List<HierarchyFilter> hierarchyFilters = getHierarchyFiltersByKeys(attributeKeysString);
            Map<String, List<HierarchyFilter>> hierarchyFilterMap = convertListToMap(hierarchyFilters);
            Map<String, List<HierarchyFilter>> uniqueHierarchyFiltersMap = uniqueHierarchyFilters(hierarchyFilterMap);
            List<OtherAttributeDTO> otherAttributeList = updateOtherNodeAttributes(otherAttributes, uniqueHierarchyFiltersMap);
            return otherAttributeList;
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private List<OtherAttributeDTO> updateOtherNodeAttributes(List<Attribute> otherNodeAttributes, Map<String, List<HierarchyFilter>> uniqueHierarchyFilterMap) {
        List<OtherAttributeDTO> otherAttributeList = new ArrayList<>();
        for (Attribute otherNodeAttribute : otherNodeAttributes) {
            OtherAttributeDTO otherAttributeDTO = new OtherAttributeDTO();
            otherAttributeDTO.setId(otherNodeAttribute.getId());
            otherAttributeDTO.setKey(otherNodeAttribute.getKey());
            otherAttributeDTO.setStartDate(otherNodeAttribute.getStartDate());
            otherAttributeDTO.setEndDate(otherNodeAttribute.getEndDate());
            otherAttributeDTO.setDeleted(otherNodeAttribute.isDeleted());
            otherAttributeDTO.setNodeId(otherNodeAttribute.getNodeId());
            otherAttributeDTO.setNew(otherAttributeDTO.isNew());
            for (Map.Entry<String, List<HierarchyFilter>> uniqueFilterMapEntry : uniqueHierarchyFilterMap.entrySet()) {
                if (otherNodeAttribute.getKey().equals(uniqueFilterMapEntry.getKey())) {
                    List<String> values = uniqueFilterMapEntry.getValue().stream().map(HierarchyFilter::getValue).collect(Collectors.toList());
                    otherAttributeDTO.setOptionValues(values);
                }
            }
            otherAttributeList.add(otherAttributeDTO);
        }
        return otherAttributeList;
    };

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
                    .filter(distinctByKey(h -> h.getValue()))
                    .collect(Collectors.toList());
            uniqueHierarchyFilterMap.put(hierarchyFilter.getKey(), uniqueHierarchyFilterList);
        }
        return uniqueHierarchyFilterMap;
    };

    private List<HierarchyFilter> getHierarchyFiltersByKeys(String keys) throws RestClientException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String hierarchyFiltersByKey = dbUrl + Constants.NODE_HIERARCHY_FILTER_PATH + "/hierarchyFiltersByKey/" + keys;
            HttpEntity<Object> requestEntity = new HttpEntity(hierarchyFiltersByKey, headers);
            ResponseEntity<HierarchyFilter[]> response = restTemplate.exchange(hierarchyFiltersByKey, HttpMethod.GET,  requestEntity, HierarchyFilter[].class);
            return List.of(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getAttributeKeys(String selectedHierarchies) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nodeAttributeKeysUrl = dbUrl + Constants.NODE_HIERARCHY_FILTER_PATH + "/" + selectedHierarchies + "/" + Constants.OTHER_ATTRIBUTES + "/attributes/keys";
        HttpEntity<Object> requestEntity = new HttpEntity(nodeAttributeKeysUrl, headers);
        ResponseEntity<String[]> response = restTemplate.exchange(nodeAttributeKeysUrl, HttpMethod.GET,  requestEntity, String[].class);
        return List.of(response.getBody());
    }

    private List<Attribute> getNodeAttributes(String nodeAttributesUrl) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity(nodeAttributesUrl,headers);
        ResponseEntity<Attribute[]> response = restTemplate.exchange(nodeAttributesUrl, HttpMethod.GET,  requestEntity, Attribute[].class);
        return List.of(response.getBody());
    }

    private List<SectionAttribute> getSectionAttributes(String nodeAttributesForSectionUrl) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity(nodeAttributesForSectionUrl,headers);
        ResponseEntity<SectionAttribute[]> response = restTemplate.exchange(nodeAttributesForSectionUrl, HttpMethod.GET,  requestEntity, SectionAttribute[].class);
        return List.of(response.getBody());
    }

    public List<Attribute> sanitizeAttributes(List<Attribute> attributes) {
        attributes.removeIf(x -> x.isDeleted() && x.isNew());
        return attributes;
    }

    public List<SectionAttribute> getValidAttributesFor(String attributeType) {
        try {
            String nodeAttributesForSectionUrl = dbUrl + Constants.NODE_API_PATH + "/section/" + attributeType + "/attributes";
            return getSectionAttributes(nodeAttributesForSectionUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
