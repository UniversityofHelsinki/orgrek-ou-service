package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NewNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
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
public class NodeValidationService {

    static Logger logger = LoggerFactory.getLogger(NodeValidationService.class);

    public ResponseEntity validateNewNode(NewNodeDTO newNodeDTO) {
        List<AttributeValidationDTO> errorMessages = new ArrayList<>();
        validateHierarchies(errorMessages, newNodeDTO);
        validateStartDate(errorMessages, newNodeDTO);
        validateNames(errorMessages, newNodeDTO);
        Node newNode = new Node();
        newNode.setStartDate(newNodeDTO.getStartDate());
        newNode.setEndDate(newNodeDTO.getEndDate());
        validateDates(errorMessages, newNode);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    }

    public ResponseEntity validateNode(Node foundNode) {
        List<AttributeValidationDTO> errorMessages = new ArrayList<>();
        validateDates(errorMessages, foundNode);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    };

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    };

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    };

    private void validateStartDate(List<AttributeValidationDTO> errorMessages, NewNodeDTO newNodeDTO) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (newNodeDTO.getStartDate() == null) {
            attributeValidationDTO.setErrorMessage(Constants.NODE_START_DATE_VALIDATION_MESSAGE_KEY);
            errorMessages.add(attributeValidationDTO);
            logger.error("Validation failed for new node with parent id: " + newNodeDTO.getParentNodeId() + " message : " + Constants.NODE_START_DATE_VALIDATION_MESSAGE_KEY);
        }
    }

    private void validateNames(List<AttributeValidationDTO> errorMessages, NewNodeDTO newNodeDTO) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (newNodeDTO.getNameFi().isEmpty() || newNodeDTO.getNameEn().isEmpty() || newNodeDTO.getNameSv().isEmpty()) {
            attributeValidationDTO.setErrorMessage(Constants.NODE_NAMES_VALIDATION_MESSAGE_KEY);
            errorMessages.add(attributeValidationDTO);
            logger.error("Validation failed for new node with parent id: " + newNodeDTO.getParentNodeId() + " message : " + Constants.NODE_NAMES_VALIDATION_MESSAGE_KEY);
        }
    };

    private void validateHierarchies(List<AttributeValidationDTO> errorMessages, NewNodeDTO newNodeDTO) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (newNodeDTO.getHierarchies() == null || newNodeDTO.getHierarchies().isEmpty()) {
            attributeValidationDTO.setErrorMessage(Constants.NODE_HIERARCHY_VALIDATION_MESSAGE_KEY);
            errorMessages.add(attributeValidationDTO);
            logger.error("Validation failed for new node with parent id: " + newNodeDTO.getParentNodeId() + " message : " + Constants.NODE_HIERARCHY_VALIDATION_MESSAGE_KEY);
        }
    };

    private void validateDates(List<AttributeValidationDTO> errorMessages, Node foundNode) {
        AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
        if (foundNode.getStartDate() != null && foundNode.getEndDate() != null) {
            LocalDate convertedEndDate = convertToLocalDate(foundNode.getEndDate());
            convertedEndDate = convertedEndDate.minusDays(1);
            Date convertedDate = convertToDateViaInstant(convertedEndDate);
            if (foundNode.getStartDate().after(convertedDate)) {
                attributeValidationDTO.setId(foundNode.getUniqueId());
                attributeValidationDTO.setNodeId(foundNode.getId());
                attributeValidationDTO.setErrorMessage(Constants.NODE_DATE_VALIDATION_MESSAGE_KEY);
                errorMessages.add(attributeValidationDTO);
                logger.error("Validation failed for node: " + foundNode.getId() + " message : " + Constants.NODE_DATE_VALIDATION_MESSAGE_KEY);
            }
        }
    };
}
