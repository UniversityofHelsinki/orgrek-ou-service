package fi.helsinki.ohtu.orgrekouservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
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
        HttpEntity<Object> requestEntity = new HttpEntity<>(nodeAttributesForSectionUrl,headers);
        ResponseEntity<SectionAttribute[]> response = restTemplate.exchange(nodeAttributesForSectionUrl, HttpMethod.GET,  requestEntity, SectionAttribute[].class);
        return List.of(response.getBody());
    }

    public List<SectionAttribute> getAllSectionAttributes() {
        try {
            String nodeAttributesForSectionUrl = dbUrl + Constants.SECTION_API_PATH + "/all";
            return getSectionAttributes(nodeAttributesForSectionUrl);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public SectionAttribute updateSectionAttribute(SectionAttribute sectionAttribute) {
        try {
            String insertNodePropertiesUrl = dbUrl + Constants.SECTION_API_PATH + "/update";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(sectionAttribute, headers);
            ResponseEntity<SectionAttribute> response = restTemplate.exchange(insertNodePropertiesUrl, HttpMethod.PUT,  requestEntity, SectionAttribute.class);
            return (SectionAttribute) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public SectionAttribute insertSectionAttribute(SectionAttribute sectionAttribute) {
        try {
            String insertNodePropertiesUrl = dbUrl + Constants.SECTION_API_PATH + "/insert";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(sectionAttribute, headers);
            ResponseEntity<SectionAttribute> response = restTemplate.exchange(insertNodePropertiesUrl, HttpMethod.POST,  requestEntity, SectionAttribute.class);
            return (SectionAttribute) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpStatus deleteSectionAttribute(int sectionId) {
        try {
            String deleteNodePropertiesUrl = dbUrl + Constants.SECTION_API_PATH + "/" + sectionId + "/delete";
            ResponseEntity response = restTemplate.exchange(deleteNodePropertiesUrl, HttpMethod.DELETE,  null, ResponseEntity.class);
            return response.getStatusCode();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public SectionAttribute getSectionAttributeById(int sectionId) {
        try {
            String getSectionAttributeUrl = dbUrl + Constants.SECTION_API_PATH + "/" + sectionId + "/get";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity<>(getSectionAttributeUrl, headers);
            ResponseEntity<SectionAttribute> response = restTemplate.exchange(getSectionAttributeUrl, HttpMethod.GET,  requestEntity, SectionAttribute.class);
            return  (SectionAttribute) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public SectionAttribute updateSectionAttributeInfo(SectionAttribute foundSectionAttribute, SectionAttribute sectionAttribute) {
        SectionAttribute updatedSectionAttribute = new SectionAttribute();
        updatedSectionAttribute.setId(foundSectionAttribute.getId());
        updatedSectionAttribute.setSection(sectionAttribute.getSection());
        updatedSectionAttribute.setAttr(sectionAttribute.getAttr());
        return updatedSectionAttribute;
    }
}
