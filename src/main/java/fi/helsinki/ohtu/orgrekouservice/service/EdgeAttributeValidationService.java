package fi.helsinki.ohtu.orgrekouservice.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.orgrekouservice.domain.EdgeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;

@Service
public class EdgeAttributeValidationService {

    @Autowired
    private EdgeService edgeService;

    static Logger logger = LoggerFactory.getLogger(EdgeAttributeValidationService.class);

    public ResponseEntity<?> validateEdges(List<EdgeWrapper> edges) {
        return validateEdges(edges, true);
    }

    public ResponseEntity<?> validateEdges(List<EdgeWrapper> edges, boolean checkCyclicity) {
        List<EdgeValidationDTO> errorMessages = new ArrayList<>();
        for (EdgeWrapper edgeWrapper : edges) {
            if (!edgeWrapper.isNew()) {
                validateId(errorMessages, edgeWrapper);
            }
            validateChildUniqueId(errorMessages, edgeWrapper);
            validateParentNodeId(errorMessages, edgeWrapper);
            validateHierarchy(errorMessages, edgeWrapper);
            validateStartDate(errorMessages, edgeWrapper);
            validateDates(errorMessages, edgeWrapper);
            if (checkCyclicity) {
                validateCyclicity(errorMessages, edgeWrapper);
            }
        }
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    private void validateCyclicity(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate(containsCycle(edgeWrapper.getParentUniqueId(), edgeWrapper.getChildUniqueId(), edgeWrapper.getHierarchy()), edgeWrapper, Constants.EDGE_CYCLE_DETECTED_MESSAGE_KEY, errorMessages);
    }

    private boolean containsCycle(Integer parentUniqueId, Integer childUniqueId, String hierarchy) {
        List<EdgeWrapper> edges = edgeService.getPathsFrom(childUniqueId, hierarchy);
        for (EdgeWrapper edge : edges) {
            if (edge.getParentUniqueId().equals(parentUniqueId) || 
            edge.getChildUniqueId().equals(parentUniqueId)) {
                return true;
            }
        }
        return false;
    }

    private void validateId(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate(edgeWrapper.getId() == null, edgeWrapper, Constants.ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateChildUniqueId(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate((edgeWrapper.getChildUniqueId() % 1 != 0 || edgeWrapper.getChildUniqueId() < 0), edgeWrapper, Constants.ATTRIBUTE_CHILD_NODE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateParentNodeId(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate((edgeWrapper.getParentUniqueId()  % 1 != 0 || edgeWrapper.getParentUniqueId() < 0), edgeWrapper, Constants.ATTRIBUTE_PARENT_NODE_ID_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateHierarchy(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate((edgeWrapper.getHierarchy() == null || edgeWrapper.getHierarchy().isEmpty()), edgeWrapper, Constants.ATTRIBUTE_HIERARCHY_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private void validateStartDate(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        validate((edgeWrapper.getStartDate() == null && !edgeWrapper.isDeleted()), edgeWrapper, Constants.ATTRIBUTE_START_DATE_VALIDATION_MESSAGE_KEY, errorMessages);
    };

    private static void validate(boolean notValid, EdgeWrapper edgeWrapper, String attributeValidationMessageKey, List<EdgeValidationDTO> errorMessages) {
        EdgeValidationDTO attributeValidationDTO = new EdgeValidationDTO();
        if (notValid) {
            if (edgeWrapper.getId() != null && edgeWrapper.getParentUniqueId() != null && edgeWrapper.getChildUniqueId() % 1 == 0) {
                attributeValidationDTO.setId(edgeWrapper.getId());
                attributeValidationDTO.setParentUniqueId(edgeWrapper.getParentUniqueId());
                attributeValidationDTO.setChildUniqueId(edgeWrapper.getChildUniqueId());
                attributeValidationDTO.setErrorMessage(attributeValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed for attribute: " + edgeWrapper.getId() + " message : " + attributeValidationMessageKey);
            } else {
                attributeValidationDTO.setErrorMessage(attributeValidationMessageKey);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed, message : " + attributeValidationMessageKey);
            }
        }
    };

    private void validateDates(List<EdgeValidationDTO> errorMessages, EdgeWrapper edgeWrapper) {
        EdgeValidationDTO attributeValidationDTO = new EdgeValidationDTO();
        if (edgeWrapper.getStartDate() != null && edgeWrapper.getEndDate() != null) {
            LocalDate convertedEndDate = convertToLocalDate(edgeWrapper.getEndDate());
            convertedEndDate = convertedEndDate.minusDays(1);
            Date convertedDate = convertToDateViaInstant(convertedEndDate);
            if (edgeWrapper.getStartDate().after(convertedDate)) {
                attributeValidationDTO.setId(edgeWrapper.getId());
                attributeValidationDTO.setParentUniqueId(edgeWrapper.getParentUniqueId());
                attributeValidationDTO.setChildUniqueId(edgeWrapper.getChildUniqueId());
                attributeValidationDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed for attribute: " + edgeWrapper.getId() + " message : " + Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
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
