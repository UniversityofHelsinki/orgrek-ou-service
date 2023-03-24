package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
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
public class NodeAttributeValidationService {

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public ResponseEntity validateNodeAttributes(List<Attribute> nodeAttributes) {
        List<AttributeValidationDTO> errorMessages = new ArrayList<>();
        for (Attribute nodeAttribute : nodeAttributes) {
            validateId(errorMessages, nodeAttribute);
            validateKey(errorMessages, nodeAttribute);
            validateValue(errorMessages, nodeAttribute);
            validateValueLength(errorMessages, nodeAttribute);
            validateStartDate(errorMessages, nodeAttribute);
            validateDates(errorMessages, nodeAttribute);
        }
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    };

    private void validateId(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        validate(nodeAttribute.getId() == null, nodeAttribute, Constants.ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateValue(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        validate((nodeAttribute.getValue() == null || nodeAttribute.getValue().isEmpty() && !nodeAttribute.isDeleted()), nodeAttribute, Constants.ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateKey(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        validate((nodeAttribute.getKey() == null || nodeAttribute.getKey().isEmpty()), nodeAttribute, Constants.ATTRIBUTE_KEY_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateStartDate(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        validate((nodeAttribute.getStartDate() == null && !nodeAttribute.isDeleted()), nodeAttribute, Constants.ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateValueLength(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (nodeAttribute.getValue() != null) {
            if (nodeAttribute.getValue().length() > Constants.ATTRIBUTE_NAME_MAXIMUM_LENGTH) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setNodeId(nodeAttribute.getNodeId());
                attributeValidationDTO.setErrorMessage(Constants.ATTRIBUTE_VALUE_LENGTH_VALIDATION_MESSAGE_KEY);
                errorMessages.add(attributeValidationDTO);
            }
        }
    };

    private static void validate(boolean notValid, Attribute nodeAttribute, String attributeIdValidationMessageKey, List<AttributeValidationDTO> errorMessages) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (notValid) {
            if (nodeAttribute.getId() != null && nodeAttribute.getNodeId() != null) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setNodeId(nodeAttribute.getNodeId());
                attributeValidationDTO.setErrorMessage(attributeIdValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
            } else {
                attributeValidationDTO.setErrorMessage(attributeIdValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
            }
        }
    };

    private void validateDates(List<AttributeValidationDTO> errorMessages, Attribute nodeAttribute) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (nodeAttribute.getStartDate() != null && nodeAttribute.getEndDate() != null) {
            LocalDate convertedEndDate = convertToLocalDate(nodeAttribute.getEndDate());
            convertedEndDate = convertedEndDate.minusDays(1);
            Date convertedDate = convertToDateViaInstant(convertedEndDate);
            if (nodeAttribute.getStartDate().after(convertedDate)) {
                attributeValidationDTO.setId(nodeAttribute.getId());
                attributeValidationDTO.setNodeId(nodeAttribute.getNodeId());
                attributeValidationDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
                errorMessages.add(attributeValidationDTO);
            }
        }
    };
}
