package fi.helsinki.ohtu.orgrekouservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;

@Service
public class SectionValidationService {
    static Logger logger = LoggerFactory.getLogger(SectionValidationService.class);

    public ResponseEntity<?> validateSectionAttributes(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute, String type, List<SectionAttribute> sectionAttributeList) {
        List<SectionValidationDTO> errorMessages = new ArrayList<>();
        validateId(sectionAttribute, type, errorMessages);
        validateFoundAtHierarchyFilter(distinctHierarchyFilterKeys, sectionAttribute, errorMessages);
        validateDoubleSectionAttributes(sectionAttribute, sectionAttributeList, type, errorMessages);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validateDoubleSectionAttributes(SectionAttribute sectionAttribute, List<SectionAttribute> sectionAttributeList, String type, List<SectionValidationDTO> errorMessages)  {
        boolean found = sectionAttributeList.stream().filter(sectionAttributeEntry -> sectionAttributeEntry.equals(sectionAttribute)).findAny().isPresent();
        if (type == Constants.NEW_SECTION_ATTRIBUTE && found) {
            validationMessage(sectionAttribute, Constants.SECTION_ATTRIBUTE_FOUND_AT_SECTION_ATTRIBUTE_TABLE, errorMessages);
        }
    }

    private void validateId(SectionAttribute sectionAttribute, String type, List<SectionValidationDTO> errorMessages) {
        if (sectionAttribute.getId() == null && (type.equals(Constants.UPDATE_SECTION_ATTRIBUTE) || type.equals(Constants.DELETE_SECTION_ATTRIBUTE))) {
            validationMessage(sectionAttribute, Constants.SECTION_ATTRIBUTE_ID_IS_NULL, errorMessages);
        }
    }


    private static void validateFoundAtHierarchyFilter(List<String> distinctHierarchyFilterKeys, SectionAttribute sectionAttribute, List<SectionValidationDTO> errorMessages) {
        boolean found = distinctHierarchyFilterKeys.stream().filter(key -> sectionAttribute.getAttr().equals(key)).findAny().isPresent();
        if (!found) {
            validationMessage(sectionAttribute, Constants.SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE, errorMessages);
        }
    }

    private static void validationMessage(SectionAttribute sectionAttribute, String sectionAttributeFoundAtSectionAttributeTable, List<SectionValidationDTO> errorMessages) {
        SectionValidationDTO validationDTO = new SectionValidationDTO();
        validationDTO.setId(sectionAttribute.getId());
        validationDTO.setSection(sectionAttribute.getSection());
        validationDTO.setAttr(sectionAttribute.getAttr());
        validationDTO.setErrorMessage(sectionAttributeFoundAtSectionAttributeTable);
        errorMessages.add(validationDTO);
        logger.error("Validation failed for section attribute: " + sectionAttribute.getAttr() + " message : " + sectionAttributeFoundAtSectionAttributeTable);
    }
}
