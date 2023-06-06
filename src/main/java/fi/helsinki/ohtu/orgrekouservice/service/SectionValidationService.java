package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionValidationDTO;
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
public class SectionValidationService {
    static Logger logger = LoggerFactory.getLogger(SectionValidationService.class);
    public ResponseEntity validateSectionAttributes(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute) {
        List<SectionValidationDTO> errorMessages = new ArrayList<>();
        foundAtHierarchyFilter(distinctHierarchyFilterKeys, sectionAttribute, errorMessages);


        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private static void foundAtHierarchyFilter(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute, List<SectionValidationDTO> errorMessages) {
        boolean found = distinctHierarchyFilterKeys.stream().filter(key -> sectionAttribute.getAttr().equals(key)).findAny().isPresent();
        if (!found) {
            SectionValidationDTO validationDTO = new SectionValidationDTO();
            validationDTO.setId(sectionAttribute.getId());
            validationDTO.setSection(sectionAttribute.getSection());
            validationDTO.setAttr(sectionAttribute.getAttr());
            validationDTO.setErrorMessage(Constants.SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE);
            errorMessages.add(validationDTO);
            logger.error("Validation failed for section attribute: " + sectionAttribute.getAttr() + " message : " + Constants.SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE);
        }
    }
}
