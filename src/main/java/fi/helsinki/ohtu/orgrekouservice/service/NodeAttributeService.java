package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyFilter;
import fi.helsinki.ohtu.orgrekouservice.domain.OtherAttributeDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodeAttributeService {

    @Autowired
    private SectionAttributeService sectionAttributeService;

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

    public List<Attribute> getNodeOtherAttributesByNodeId(int nodeUniqueId) {
        try {
            String nodeCodeAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/other/attributes/" + nodeUniqueId;
            return getNodeAttributes(nodeCodeAttributesUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OtherAttributeDTO> updateOtherNodeAttributes(List<Attribute> otherNodeAttributes, Map<String, List<HierarchyFilter>> uniqueHierarchyFilterMap) {
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
            otherAttributeDTO.setValue(otherNodeAttribute.getValue());
            otherAttributeDTO.setType("text");
            for (Map.Entry<String, List<HierarchyFilter>> uniqueFilterMapEntry : uniqueHierarchyFilterMap.entrySet()) {
                if (otherNodeAttribute.getKey().equals(uniqueFilterMapEntry.getKey())) {
                    List<String> values = uniqueFilterMapEntry.getValue().stream().filter(entry -> entry.getValue() != null).map(HierarchyFilter::getValue).collect(Collectors.toList());
                    if (values != null && values.size() > 0) {
                        otherAttributeDTO.setOptionValues(values);
                        otherAttributeDTO.setType("options");
                    }
                }
            }
            otherAttributeList.add(otherAttributeDTO);
        }
        return otherAttributeList;
    };

    public List<String> getAttributeKeys(String selectedHierarchies) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nodeAttributeKeysUrl = dbUrl + Constants.HIERARCHY_FILTER_PATH + "/" + selectedHierarchies + "/" + Constants.OTHER_ATTRIBUTES + "/attributes/keys";
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


    public List<Attribute> sanitizeAttributes(List<Attribute> attributes) {
        attributes.removeIf(x -> x.isDeleted() && x.isNew());
        return attributes;
    }

}
