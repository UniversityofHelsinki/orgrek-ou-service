package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.SteeringGroupTransformer;
import fi.helsinki.ohtu.orgrekouservice.domain.TextDTO;
import fi.helsinki.ohtu.orgrekouservice.service.SteeringGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/steering")
public class SteeringGroupController {

    @Autowired
    private SteeringGroupService steeringGroupService;

    @RequestMapping(method = GET, value = "/steeringGroups")
    public Object getSteeringGroups() {
        List<DegreeProgrammeDTO> groups = steeringGroupService.getSteeringGroups();
        List<TextDTO> degreeTitles = steeringGroupService.getDegreeTitles();
        return SteeringGroupTransformer.transform(groups, degreeTitles);
    }

}
