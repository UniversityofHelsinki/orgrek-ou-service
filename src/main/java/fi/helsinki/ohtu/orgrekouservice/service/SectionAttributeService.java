package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class SectionAttributeService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<SectionAttribute> getValidAttributesFor(String attributeType) {
        try {
            String nodeAttributesForSectionUrl = dbUrl + Constants.NODE_API_PATH + "/section/" + attributeType + "/attributes";
            return getSectionAttributes(nodeAttributesForSectionUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SectionAttribute> getSectionAttributes(String nodeAttributesForSectionUrl) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity(nodeAttributesForSectionUrl,headers);
        ResponseEntity<SectionAttribute[]> response = restTemplate.exchange(nodeAttributesForSectionUrl, HttpMethod.GET,  requestEntity, SectionAttribute[].class);
        return List.of(response.getBody());
    }

    public List<SectionAttribute> getAllSectionAttributes() {
        try {
            String nodeAttributesForSectionUrl = dbUrl + Constants.NODE_API_PATH + "/section/" + "/attributes";
            return getSectionAttributes(nodeAttributesForSectionUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
