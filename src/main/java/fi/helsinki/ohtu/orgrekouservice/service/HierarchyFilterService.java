package fi.helsinki.ohtu.orgrekouservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fi.helsinki.ohtu.orgrekouservice.util.Constants;

@Service
public class HierarchyFilterService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<String> getDistinctHierarchyFilterKeys() throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nodeAttributeKeysUrl = dbUrl + Constants.HIERARCHY_FILTER_PATH + "/distinctHierarchyFilterKeys";
        HttpEntity<Object> requestEntity = new HttpEntity<>(nodeAttributeKeysUrl, headers);
        ResponseEntity<String[]> response = restTemplate.exchange(nodeAttributeKeysUrl, HttpMethod.GET,  requestEntity, String[].class);
        return List.of(response.getBody());
    }

}
