package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.TextDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SteeringGroupService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<DegreeProgrammeDTO> getSteeringGroups() {
        String url = dbUrl + Constants.PUBLIC_STEERING_GROUPS_API;
        ResponseEntity<DegreeProgrammeDTO[]> response = restTemplate.getForEntity(url, DegreeProgrammeDTO[].class);
        return List.of(response.getBody());
    }

    public List<TextDTO> getDegreeTitles(){
        String url = dbUrl + Constants.PUBLIC_DEGREE_TITLE_API;
        ResponseEntity<TextDTO[]> response = restTemplate.getForEntity(url, TextDTO[].class);
        return List.of(response.getBody());
    }
}
