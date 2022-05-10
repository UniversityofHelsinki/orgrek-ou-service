package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
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
        String url = dbUrl + Constants.STEERING_API;
        System.out.println(url);
        ResponseEntity<DegreeProgrammeDTO[]> response = restTemplate.getForEntity(url, DegreeProgrammeDTO[].class);
        return List.of(response.getBody());
    }
}
