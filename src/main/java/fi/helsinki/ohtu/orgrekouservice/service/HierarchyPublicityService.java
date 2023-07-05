package fi.helsinki.ohtu.orgrekouservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.domain.User;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HierarchyPublicityService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    private User getUser(String user) throws  JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User userObject = objectMapper.readValue(user, User.class);
        return userObject;
    }

    public List<String> getHierarchyTypesForUser(String user) throws JsonProcessingException {
        User loggedUser = getUser(user);
        List<HierarchyPublicity> hierarchyPublicityList = getHierarchyPublicities();
        return hierarchyTypes(loggedUser, hierarchyPublicityList);
    }

    private static List<HierarchyPublicity> filterHistoryHierarchy(List<HierarchyPublicity> hierarchyPublicityList) {
        List<HierarchyPublicity> filteredHierarchyPublicityList = hierarchyPublicityList
                .stream()
                .filter(hierarchyPublicity -> !hierarchyPublicity.getHierarchy().equalsIgnoreCase(Constants.HISTORY))
                .collect(Collectors.toList());
        return filteredHierarchyPublicityList;
    }

    public static List<String> hierarchyTypes(User loggedUser, List<HierarchyPublicity> hierarchyPublicityList) {
        List<String> hierarchyTypes = new ArrayList<>();
        List<HierarchyPublicity> filteredHierarchyPublicityList = filterHistoryHierarchy(hierarchyPublicityList);
        for (HierarchyPublicity hierarchyPublicity :filteredHierarchyPublicityList) {
            if (loggedUser.getRoles().stream().anyMatch(role -> Constants.MAPPED_ROLES.contains(role))) {
                hierarchyTypes.add(hierarchyPublicity.getHierarchy());
            } else if (loggedUser.getRoles().stream().anyMatch(Constants.ROLE_READER::equalsIgnoreCase) && hierarchyPublicity.isPublicity()) {
                hierarchyTypes.add(hierarchyPublicity.getHierarchy());
            }
        }
        return hierarchyTypes;
    }

    public List<HierarchyPublicity> getHierarchyPublicityList() {
        List<HierarchyPublicity> hierarchyPublicityList = getHierarchyPublicities();
        return hierarchyPublicityList;
    }

    private List<HierarchyPublicity> getHierarchyPublicities() {
        String getHierarchyPublicityUrl = dbUrl + Constants.HIERARCHY_PUBLICITY_PATH + "/all";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(getHierarchyPublicityUrl,headers);
        ResponseEntity<HierarchyPublicity[]> response = restTemplate.exchange(getHierarchyPublicityUrl, HttpMethod.GET,  requestEntity, HierarchyPublicity[].class);
        List<HierarchyPublicity> hierarchyPublicityList = List.of(Objects.requireNonNull(response.getBody()));
        return hierarchyPublicityList;
    }

    public HierarchyPublicity getHierarchyTypeById(int id) {
        String getHierarchyPublicityByIdUrl = dbUrl + Constants.HIERARCHY_PUBLICITY_PATH + "/" + id + "/properties";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(getHierarchyPublicityByIdUrl,headers);
        ResponseEntity<HierarchyPublicity> response = restTemplate.exchange(getHierarchyPublicityByIdUrl, HttpMethod.GET,  requestEntity, HierarchyPublicity.class);
        return response.getBody();
    }

    public HierarchyPublicity updateHierarchyPublicity(HierarchyPublicity hierarchyPublicity, HierarchyPublicity foundHierarchyPublicity) {
        HierarchyPublicity updatedHierarchyPublicity = foundHierarchyPublicity;
        updatedHierarchyPublicity.setId(hierarchyPublicity.getId());
        updatedHierarchyPublicity.setPublicity(hierarchyPublicity.isPublicity());
        updatedHierarchyPublicity.setHierarchy(hierarchyPublicity.getHierarchy());
        return updatedHierarchyPublicity;
    }

    public HierarchyPublicity update(HierarchyPublicity updatedHierarchyPublicity) {
        try {
            String updateHierarchyPublicityUrl = dbUrl + Constants.HIERARCHY_PUBLICITY_PATH + "/update";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestEntity = new HttpEntity(updatedHierarchyPublicity, headers);
            ResponseEntity response = restTemplate.exchange(updateHierarchyPublicityUrl, HttpMethod.PUT,  requestEntity, HierarchyPublicity.class);
            return (HierarchyPublicity) response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
