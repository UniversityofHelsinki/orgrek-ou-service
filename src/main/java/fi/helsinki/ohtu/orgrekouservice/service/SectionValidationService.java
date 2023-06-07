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
    public ResponseEntity validateSectionAttributes(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute, String type, List<SectionAttribute> sectionAttributeList) {
        List<SectionValidationDTO> errorMessages = new ArrayList<>();
        validateId(sectionAttribute, type, errorMessages);
        validateFoundAtHierarchyFilter(distinctHierarchyFilterKeys, sectionAttribute, errorMessages);
        validateDoubleSectionAttributes(sectionAttribute, sectionAttributeList, errorMessages);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validateDoubleSectionAttributes(SectionAttribute sectionAttribute, List<SectionAttribute> sectionAttributeList, List<SectionValidationDTO> errorMessages)  {
        boolean found = sectionAttributeList.stream().filter(sectionAttributeEntry -> sectionAttributeEntry.equals(sectionAttribute)).findAny().isPresent();
        if (found) {
            SectionValidationDTO validationDTO = new SectionValidationDTO();
            validationDTO.setId(sectionAttribute.getId());
            validationDTO.setSection(sectionAttribute.getSection());
            validationDTO.setAttr(sectionAttribute.getAttr());
            validationDTO.setErrorMessage(Constants.SECTION_ATTRIBUTE_FOUND_AT_SECTION_ATTRIBUTE_TABLE);
            errorMessages.add(validationDTO);
            logger.error("Validation failed for section attribute: " + sectionAttribute.getAttr() + " message : " + Constants.SECTION_ATTRIBUTE_FOUND_AT_SECTION_ATTRIBUTE_TABLE);
        }
    }

    private void validateId(SectionAttribute sectionAttribute, String type, List<SectionValidationDTO> errorMessages) {
        if (sectionAttribute.getId() == null && type.equals(Constants.UPDATE_SECTION_ATTRIBUTE)) {
            SectionValidationDTO validationDTO = new SectionValidationDTO();
            validationDTO.setId(sectionAttribute.getId());
            validationDTO.setSection(sectionAttribute.getSection());
            validationDTO.setAttr(sectionAttribute.getAttr());
            errorMessages.add(validationDTO);
            logger.error("Validation failed on update for section attribute: " + sectionAttribute.getAttr() + " message : " + Constants.SECTION_ATTRIBUTE_ID_IS_NULL);
        }
    }


    private static void validateFoundAtHierarchyFilter(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute, List<SectionValidationDTO> errorMessages) {
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
