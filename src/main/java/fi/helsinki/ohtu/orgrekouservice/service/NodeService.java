package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
}
