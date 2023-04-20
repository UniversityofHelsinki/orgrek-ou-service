package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
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

    static Logger logger = LoggerFactory.getLogger(NodeAttributeValidationService.class);

    public ResponseEntity validateNodeAttributes(List<EdgeWrapper> nodeAttributes) {
        List<EdgeValidationDTO> errorMessages = new ArrayList<>();
        for (EdgeWrapper nodeAttribute : nodeAttributes) {
            validateId(errorMessages, nodeAttribute);
            validateKey(errorMessages, nodeAttribute);
            validateValue(errorMessages, nodeAttribute);
            //validateValueLength(errorMessages, nodeAttribute);
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

    private void validateValue(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getParentNodeId() == null || nodeAttribute.getParentNodeId().isEmpty() && !nodeAttribute.isDeleted()), nodeAttribute, Constants.ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateKey(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getChildNodeId() == null || nodeAttribute.getChildNodeId().isEmpty()), nodeAttribute, Constants.ATTRIBUTE_KEY_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateStartDate(List<EdgeValidationDTO> errorMessages, EdgeWrapper nodeAttribute) {
        validate((nodeAttribute.getStartDate() == null && !nodeAttribute.isDeleted()), nodeAttribute, Constants.ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private static void validate(boolean notValid, EdgeWrapper nodeAttribute, String attributeValidationMessageKey, List<EdgeValidationDTO> errorMessages) {
        EdgeValidationDTO attributeValidationDTO = new EdgeValidationDTO();
        if (notValid) {
            if (nodeAttribute.getId() != null && nodeAttribute.getParentNodeId() != null && nodeAttribute.getChildNodeId() != null) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setParentNodeId(nodeAttribute.getParentNodeId());
                attributeValidationDTO.setChildNodeId(nodeAttribute.getChildNodeId());
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
                attributeValidationDTO.setParentNodeId(nodeAttribute.getParentNodeId());
                attributeValidationDTO.setChildNodeId(nodeAttribute.getChildNodeId());
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
