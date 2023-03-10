package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NodeAttributeValidationService {

    public ResponseEntity validateNodeAttributes(List<Attribute> nodeAttributes) {

        for (Attribute nodeAttribute : nodeAttributes) {
            if (nodeAttribute.getStartDate() != null && nodeAttribute.getEndDate() != null) {
                if (nodeAttribute.getStartDate().after(nodeAttribute.getEndDate())) {
                    return new ResponseEntity<>(nodeAttributes, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
        }

        return new ResponseEntity<>(nodeAttributes, HttpStatus.OK);
    };

}
