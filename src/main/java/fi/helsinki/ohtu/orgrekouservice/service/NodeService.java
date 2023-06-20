package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.NewNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class NodeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public Node getNodeByUniqueId(int nodeUniqueId) {
        try {
            String nodeNameAttributesUrl = dbUrl + Constants.NODE_API_PATH + '/' + nodeUniqueId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(nodeNameAttributesUrl,headers);
            ResponseEntity response = restTemplate.exchange(nodeNameAttributesUrl, HttpMethod.GET,  requestEntity, Node.class);
            return (Node) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public Node updateNode(Node node) {
        try {
            String updateNodePropertiesUrl = dbUrl + Constants.NODE_API_PATH + "/properties/" + node.getUniqueId();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(node, headers);
            ResponseEntity response = restTemplate.exchange(updateNodePropertiesUrl, HttpMethod.PUT,  requestEntity, Node.class);
            return (Node) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public Node updateNodeDates(Node foundNode, Node node) {
        Node updatedNode = foundNode;
        updatedNode.setStartDate(node.getStartDate());
        updatedNode.setEndDate(node.getEndDate());
        return updatedNode;
    }

    public NewNodeDTO insertNewNode(NewNodeDTO newNodeDTO) {
        try {
            String insertNodePropertiesUrl = dbUrl + Constants.NODE_API_PATH + "/insert";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(newNodeDTO, headers);
            ResponseEntity response = restTemplate.exchange(insertNodePropertiesUrl, HttpMethod.POST,  requestEntity, NewNodeDTO.class);
            return (NewNodeDTO) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public NewNodeDTO updateParentNodeId(NewNodeDTO newNodeDTO, Node parentNode) {
        newNodeDTO.setParentNodeId(parentNode.getId());
        return newNodeDTO;
    }
}
