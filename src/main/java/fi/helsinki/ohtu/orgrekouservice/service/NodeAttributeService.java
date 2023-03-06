package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
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

@Service
public class NodeAttributeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    private Map<String, List<Attribute>> extractAttributesToMap(List<Attribute> nodeNameAttributes) {
        try {
            Map<String , List<Attribute>> nameAttributesMap = new HashMap();
            List<Attribute> newAttributes = new ArrayList<>();
            List<Attribute> updatedAttributes = new ArrayList<>();
            List<Attribute> deletedAttributes = new ArrayList<>();
            for (Attribute nodeNameAttribute : nodeNameAttributes) {
                if (nodeNameAttribute.isNew()) {
                    newAttributes.add(nodeNameAttribute);
                } else if (nodeNameAttribute.isDeleted()) {
                    deletedAttributes.add(nodeNameAttribute);
                } else {
                    updatedAttributes.add(nodeNameAttribute);
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
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(nodeNameAttributesUrl,headers);
            ResponseEntity<Attribute[]> response = restTemplate.exchange(nodeNameAttributesUrl, HttpMethod.GET,  requestEntity, Attribute[].class);
            return List.of(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
