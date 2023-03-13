package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class AttributeValidationServiceTest {

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @Test
    public void testAttributesWithValidDatesShouldReturnEmptyArrayWithStatusCode200() {
        Date startDate = new Date(2022,1,15);
        Date endDate = new Date(2022,1,20);

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        Attribute validAttribute2 = new Attribute();
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("moro");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        validAttribute2.setId(123);
        validAttribute2.setNodeId("1234");
        validAttribute2.setKey("moro");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(validAttribute2);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList() , response.getBody());

    }

    @Test
    public void testAttributesWithInValidDatesShouldReturnNodeArrayWithStatusCode422() {
        Date startDate = new Date(2022,1,15);
        Date endDate = new Date(2022,1,5);

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        Attribute inValidAttribute2 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("moro");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        inValidAttribute2.setId(1234);
        inValidAttribute2.setNodeId("12345");
        inValidAttribute2.setKey("moro");
        inValidAttribute2.setStartDate(startDate);
        inValidAttribute2.setEndDate(endDate);
        inValidAttribute2.setNew(false);
        inValidAttribute2.setDeleted(false);

        attributeList.add(inValidAttribute1);
        attributeList.add(inValidAttribute2);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList);

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);

        AttributeValidationDTO expectedSecondAttributeDTO = new AttributeValidationDTO();
        expectedSecondAttributeDTO.setId(1234);
        expectedSecondAttributeDTO.setNodeId("12345");
        expectedSecondAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(2, result.size());
        assertEquals(expectedFirstAttributeDTO, result.get(0));
        assertEquals(expectedSecondAttributeDTO, result.get(1));
    }

}
