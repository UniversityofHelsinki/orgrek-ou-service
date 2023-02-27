package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NodeAttributeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<Attribute> updateNodeNameAttributes(List<Attribute> nodeNameAttributes) {
        try {
            String updateNodeNameAttributesUrl = dbUrl + Constants.NODE_API_PATH + "/name/attributes";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(nodeNameAttributes,headers);
            ResponseEntity<Attribute[]> response = restTemplate.exchange(updateNodeNameAttributesUrl, HttpMethod.PUT,  requestEntity, Attribute[].class);
            return List.of(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

}
