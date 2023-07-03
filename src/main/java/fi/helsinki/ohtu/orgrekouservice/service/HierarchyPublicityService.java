package fi.helsinki.ohtu.orgrekouservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.domain.User;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String getHierarchyPublicityUrl = dbUrl + Constants.HIERARCHY_PUBLICITY_PATH + "/all";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(getHierarchyPublicityUrl,headers);
        ResponseEntity<HierarchyPublicity[]> response = restTemplate.exchange(getHierarchyPublicityUrl, HttpMethod.GET,  requestEntity, HierarchyPublicity[].class);
        List<HierarchyPublicity> hierarchyPublicityList = List.of(Objects.requireNonNull(response.getBody()));
        return hierarchyTypes(loggedUser, hierarchyPublicityList);
    }

    private static List<String> hierarchyTypes(User loggedUser, List<HierarchyPublicity> hierarchyPublicityList) {
        List<String> hierarchyTypes = new ArrayList<>();
        for (HierarchyPublicity hierarchyPublicity :hierarchyPublicityList) {
            if (loggedUser.getRoles().stream().anyMatch(role -> Constants.MAPPED_ROLES.contains(role))) {
                hierarchyTypes.add(hierarchyPublicity.getHierarchy());
            } else if (loggedUser.getRoles().stream().anyMatch(Constants.ROLE_READER::equalsIgnoreCase) && hierarchyPublicity.isPublicity()) {
                hierarchyTypes.add(hierarchyPublicity.getHierarchy());
            }
        }
        return hierarchyTypes;
    }
}
