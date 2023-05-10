package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.EdgeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class EdgeAttributeValidationService {

    static Logger logger = LoggerFactory.getLogger(EdgeAttributeValidationService.class);

    public ResponseEntity validateEdgeAttributes(List<EdgeWrapper> nodeAttributes) {
        List<EdgeValidationDTO> errorMessages = new ArrayList<>();
        for (EdgeWrapper nodeAttribute : nodeAttributes) {
            if (!nodeAttribute.isNew()) {
                validateId(errorMessages, nodeAttribute);
            }
            validateChildUniqueId(errorMessages, nodeAttribute);
            validateParentNodeId(errorMessages, nodeAttribute);
            validateHierarchy(errorMessages, nodeAttribute);
            validateStartDate(errorMessages, nodeAttribute);
            validateDates(errorMessages, nodeAttribute);
        }
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validateId(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate(nodeAttribute.getId() == null, nodeAttribute, Constants.ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateChildUniqueId(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getChildUniqueId() % 1 != 0 || nodeAttribute.getChildUniqueId() < 0), nodeAttribute, Constants.ATTRIBUTE_CHILD_NODE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateParentNodeId(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getParentUniqueId()  % 1 != 0 || nodeAttribute.getParentUniqueId() < 0), nodeAttribute, Constants.ATTRIBUTE_PARENT_NODE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateHierarchy(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getHierarchy() == null || nodeAttribute.getHierarchy().isEmpty()), nodeAttribute, Constants.ATTRIBUTE_HIERARCHY_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateStartDate(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getStartDate() == null && !nodeAttribute.isDeleted()), nodeAttribute, Constants.ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private static void validate(boolean notValid, EdgeWrapper nodeAttribute, String attributeValidationMessageKey, List<EdgeValidationDTO> errorMessages) {
        EdgeValidationDTO attributeValidationDTO = new EdgeValidationDTO();
        if (notValid) {
            if (nodeAttribute.getId() != null && nodeAttribute.getParentUniqueId() != null && nodeAttribute.getChildUniqueId() % 1 == 0) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setParentUniqueId(nodeAttribute.getParentUniqueId());
                attributeValidationDTO.setChildUniqueId(nodeAttribute.getChildUniqueId());
                attributeValidationDTO.setErrorMessage(attributeValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed for attribute: " + nodeAttribute.getId() + " message : " + attributeValidationMessageKey);
            } else {
                attributeValidationDTO.setErrorMessage(attributeValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed, message : " + attributeValidationMessageKey);
            }
        }
    };

    private void validateDates(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        EdgeValidationDTO attributeValidationDTO = new EdgeValidationDTO();
        if (nodeAttribute.getStartDate() != null && nodeAttribute.getEndDate() != null) {
            LocalDate convertedEndDate = convertToLocalDate(nodeAttribute.getEndDate());
            convertedEndDate = convertedEndDate.minusDays(1);
            Date convertedDate = convertToDateViaInstant(convertedEndDate);
            if (nodeAttribute.getStartDate().after(convertedDate)) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setParentUniqueId(nodeAttribute.getParentUniqueId());
                attributeValidationDTO.setChildUniqueId(nodeAttribute.getChildUniqueId());
                attributeValidationDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed for attribute: " + nodeAttribute.getId() + " message : " + Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
            }
        }
    };

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}