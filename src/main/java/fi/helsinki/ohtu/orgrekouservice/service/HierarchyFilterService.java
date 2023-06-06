package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HierarchyFilterService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<String> getDistinctHierarchyFilterKeys() throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nodeAttributeKeysUrl = dbUrl + Constants.HIERARCHY_FILTER_PATH + "/distinctHierarchyFilterKeys";
        HttpEntity<Object> requestEntity = new HttpEntity(nodeAttributeKeysUrl, headers);
        ResponseEntity<String[]> response = restTemplate.exchange(nodeAttributeKeysUrl, HttpMethod.GET,  requestEntity, String[].class);
        return List.of(response.getBody());
    }

}
