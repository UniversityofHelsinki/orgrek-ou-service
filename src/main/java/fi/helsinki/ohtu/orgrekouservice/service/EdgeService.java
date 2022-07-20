package fi.helsinki.ohtu.orgrekouservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.User;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EdgeService {
    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    private User getUser(String user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User userObject = objectMapper.readValue(user, User.class);
        return userObject;
    }

    public List<String> filterHierarchyTypesForUser(List<String> types, User user){
        List<String> hierarchyTypes = new ArrayList<>();
        for (String hierarchyType : types) {
            if (hierarchyType.equals(Constants.ECONOMY_HIERARCHY) || user.getRoles().stream().anyMatch(element -> Constants.MAPPED_ROLES.contains(element))) {
                hierarchyTypes.add(hierarchyType);
            }
        }
        return hierarchyTypes;
    }

    public List<HierarchyDTO> getHierarchyTypesForUser(String user) throws JsonProcessingException {
        User loggedUser = getUser(user);
        String getHierarchyTypes = dbUrl + Constants.EDGE_PATH + "/types";
        String[] response = restTemplate.getForObject(getHierarchyTypes, String[].class);
        List<String> types = List.of(response);
        List<String> filteredHierarchyTypes = filterHierarchyTypesForUser(types, loggedUser);


        List<HierarchyDTO> hierarchyDTOList = new ArrayList<HierarchyDTO>();

        for (String filteredHierarchyType : filteredHierarchyTypes) {
            HierarchyDTO hierarchyDTO = new HierarchyDTO();
            hierarchyDTO.setLabel(filteredHierarchyType);
            hierarchyDTO.setValue(filteredHierarchyType);
            hierarchyDTOList.add(hierarchyDTO);
        }

        return hierarchyDTOList;
    }
}
