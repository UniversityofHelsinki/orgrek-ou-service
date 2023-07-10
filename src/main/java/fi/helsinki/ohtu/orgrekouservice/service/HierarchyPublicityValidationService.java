package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicityValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NewHierarchyPublicityDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HierarchyPublicityValidationService {

    static Logger logger = LoggerFactory.getLogger(HierarchyPublicityValidationService.class);

    public ResponseEntity validateHierarchyPublicity(HierarchyPublicity hierarchyPublicity, HierarchyPublicity foundHierarchyPublicity) {
        List<HierarchyPublicityValidationDTO> errorMessages = new ArrayList<>();
        validatePublicity(errorMessages, hierarchyPublicity, foundHierarchyPublicity);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validatePublicity(List<HierarchyPublicityValidationDTO> errorMessages, HierarchyPublicity hierarchyPublicity, HierarchyPublicity foundHierarchyPublicity) {
        HierarchyPublicityValidationDTO hierarchyPublicityValidationDTO = new HierarchyPublicityValidationDTO();
        if (hierarchyPublicity.getHierarchy().isEmpty()) {
            hierarchyPublicityValidationDTO.setId(hierarchyPublicity.getId());
            hierarchyPublicityValidationDTO.setErrorMessage(Constants.HIERARCHY_PUBLICITY_NAME_EMPTY);
            errorMessages.add(hierarchyPublicityValidationDTO);
            logger.error("Validation failed for hierarch publicity with id: " + hierarchyPublicity.getId() + " message : " + Constants.HIERARCHY_PUBLICITY_NAME_EMPTY);
        }
        if (!hierarchyPublicity.getHierarchy().equalsIgnoreCase(foundHierarchyPublicity.getHierarchy())) {
            hierarchyPublicityValidationDTO.setId(hierarchyPublicity.getId());
            hierarchyPublicityValidationDTO.setErrorMessage(Constants.HIERARCHY_PUBLICITY_NAME_NOT_MATCHING);
            errorMessages.add(hierarchyPublicityValidationDTO);
            logger.error("Validation failed for hierarch publicity with name: " + hierarchyPublicity.getHierarchy() + " message : " + Constants.HIERARCHY_PUBLICITY_NAME_NOT_MATCHING);
        }
    };

    public ResponseEntity validateNewHierarchyPublicity(NewHierarchyPublicityDTO hierarchyPublicityDTO) {
        List<HierarchyPublicityValidationDTO> errorMessages = new ArrayList<>();
        validateNewPublicity(errorMessages, hierarchyPublicityDTO);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validateNewPublicity(List<HierarchyPublicityValidationDTO> errorMessages, NewHierarchyPublicityDTO hierarchyPublicityDTO) {
        HierarchyPublicityValidationDTO hierarchyPublicityValidationDTO = new HierarchyPublicityValidationDTO();
        if (hierarchyPublicityDTO.getHierarchy().isEmpty()) {
            hierarchyPublicityValidationDTO.setErrorMessage(Constants.HIERARCHY_PUBLICITY_NAME_EMPTY);
            errorMessages.add(hierarchyPublicityValidationDTO);
            logger.error("Validation failed for new hierarch publicity :" + " message : " + Constants.HIERARCHY_PUBLICITY_NAME_EMPTY);
        }
        if (hierarchyPublicityDTO.getChildId().isEmpty()) {
            hierarchyPublicityValidationDTO.setErrorMessage(Constants.HIERARCHY_PUBLICITY_CHILD_ID_EMPTY);
            errorMessages.add(hierarchyPublicityValidationDTO);
            logger.error("Validation failed for new hierarch publicity :" + " message : " + Constants.HIERARCHY_PUBLICITY_CHILD_ID_EMPTY);
        }
        if (hierarchyPublicityDTO.getNames().isEmpty()) {
            hierarchyPublicityValidationDTO.setErrorMessage(Constants.HIERARCHY_PUBLICITY_NAME_LIST_EMPTY);
            errorMessages.add(hierarchyPublicityValidationDTO);
            logger.error("Validation failed for new hierarch publicity :" + " message : " + Constants.HIERARCHY_PUBLICITY_NAME_LIST_EMPTY);
        }
    }

}
