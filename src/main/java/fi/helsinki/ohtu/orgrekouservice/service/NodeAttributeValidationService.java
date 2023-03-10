package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NodeAttributeValidationService {

    public ResponseEntity validateNodeAttributes(List<Attribute> nodeAttributes) {
        List<AttributeValidationDTO> errorMessages = new ArrayList<>();

        for (Attribute nodeAttribute : nodeAttributes) {
            AttributeValidationDTO attributeValidationDTO = new AttributeValidationDTO();
            if (nodeAttribute.getStartDate() != null && nodeAttribute.getEndDate() != null) {
                if (nodeAttribute.getStartDate().after(nodeAttribute.getEndDate())) {
                    attributeValidationDTO.setId(nodeAttribute.getId());
                    attributeValidationDTO.setNodeId(nodeAttribute.getNodeId());
                    attributeValidationDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);
                    errorMessages.add(attributeValidationDTO);
                }
            }
        }

        if (!errorMessages.isEmpty()) {
            return new ResponseEntity<>(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(Arrays.asList(), HttpStatus.OK);
    };

}
