package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeValidationService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class AttributeValidationServiceTest {

    @Autowired
    private NodeAttributeValidationService nodeAttributeValidationService;

    @Test
    public void testAttributesWithValidDatesShouldReturnEmptyArrayWithStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        Attribute validAttribute2 = new Attribute();
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("name_fi");
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        validAttribute2.setId(123);
        validAttribute2.setNodeId("1234");
        validAttribute2.setKey("name_fi");
        validAttribute2.setValue("morjensta pöytään");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(validAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList() , response.getBody());

    };



    @Test
    public void testTwoAttributesWithInValidDatesShouldReturnInvalidNodeArrayWithSizeOfTwoStatusCode422() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 5);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        Attribute inValidAttribute2 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue("morjensta pöytään");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        inValidAttribute2.setId(1234);
        inValidAttribute2.setNodeId("12345");
        inValidAttribute2.setKey("name_fi");
        inValidAttribute2.setValue("morjensta pöytään");
        inValidAttribute2.setStartDate(startDate);
        inValidAttribute2.setEndDate(endDate);
        inValidAttribute2.setNew(false);
        inValidAttribute2.setDeleted(false);

        attributeList.add(inValidAttribute1);
        attributeList.add(inValidAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList,sectionAttributes);

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
    };



    @Test
    public void testAttributeWithDatesSeparatedByOneDayOrLessShouldReturnInvalidNodeArrayWithSizeOfTwoStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 16);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue("morjensta pöytään");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        attributeList.add(inValidAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());
        assertEquals(expectedFirstAttributeDTO, result.get(0));
    };


    @Test
    public void testAttributeWithDatesSeparatedByTwoDaysOrMoreShouldReturnValidNodeArrayWithSizeOfZeroStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("name_fi");
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        attributeList.add(validAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, result.size());
    };


    @Test
    public void testValidNameAttributeShouldReturnNodeArrayWithSizeOfZeroStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("name_fi");
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        attributeList.add(validAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, result.size());
    };


    @Test
    public void testAttributeWithNullValueShouldReturnNodeArrayWithSizeOfOneStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue(null);
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        attributeList.add(inValidAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY);

        assertEquals(expectedFirstAttributeDTO, result.get(0));
    };

    @Test
    public void testAttributeWithEmptyNameShouldReturnNodeArrayWithSizeOfOneStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue("");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        attributeList.add(inValidAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY);

        assertEquals(expectedFirstAttributeDTO, result.get(0));
    };

    @Test
    public void testAttributeWithEmptyNameAndIncorrectDatesShouldReturnNodeArrayWithSizeOfTwoStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 16);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue("");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        attributeList.add(inValidAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(2, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_VALUE_VALIDATION_MESSAGE_KEY);

        AttributeValidationDTO expectedSecondAttributeDTO = new AttributeValidationDTO();
        expectedSecondAttributeDTO.setId(123);
        expectedSecondAttributeDTO.setNodeId("1234");
        expectedSecondAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_DATE_VALIDATION_MESSAGE_KEY);

        assertEquals(expectedFirstAttributeDTO, result.get(0));
        assertEquals(expectedSecondAttributeDTO, result.get(1));
    };

    @Test
    public void testAttributeNameUnder250MarksShouldReturnNodeArrayWithSizeOfZeroStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int tooLongString = Constants.ATTRIBUTE_NAME_MAXIMUM_LENGTH;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(tooLongString)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("name_fi");
        validAttribute1.setValue(generatedString);
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        attributeList.add(validAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, result.size());

    };

    @Test
    public void testAttributeNameTooLongShouldReturnNodeArrayWithSizeOfOneStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int tooLongString = Constants.ATTRIBUTE_NAME_MAXIMUM_LENGTH + 1;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(tooLongString)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue(generatedString);
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        attributeList.add(inValidAttribute1);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_VALUE_LENGTH_VALIDATION_MESSAGE_KEY);

        assertEquals(expectedFirstAttributeDTO, result.get(0));

    };

    @Test
    public void testAttributesWithEmptyKeyShouldReturnArraySizeOneWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        Attribute validAttribute2 = new Attribute();
        inValidAttribute1.setId(123);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("");
        inValidAttribute1.setValue("morjensta pöytään");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        validAttribute2.setId(123);
        validAttribute2.setNodeId("1234");
        validAttribute2.setKey("name_fi");
        validAttribute2.setValue("morjensta pöytään");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(inValidAttribute1);
        attributeList.add(validAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setId(123);
        expectedFirstAttributeDTO.setNodeId("1234");
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_KEY_VALIDATION_MESSAGE_KEY);
    };

    @Test
    public void testAttributesWithEmptyIDShouldReturnArraySizeOneWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute inValidAttribute1 = new Attribute();
        Attribute validAttribute2 = new Attribute();
        inValidAttribute1.setId(null);
        inValidAttribute1.setNodeId("1234");
        inValidAttribute1.setKey("name_fi");
        inValidAttribute1.setValue("morjensta pöytään");
        inValidAttribute1.setStartDate(startDate);
        inValidAttribute1.setEndDate(endDate);
        inValidAttribute1.setNew(false);
        inValidAttribute1.setDeleted(false);

        validAttribute2.setId(123);
        validAttribute2.setNodeId("1234");
        validAttribute2.setKey("name_fi");
        validAttribute2.setValue("morjensta pöytään");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(inValidAttribute1);
        attributeList.add(validAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_ID_VALIDATION_MESSAGE_KEY);
    };


    @Test
    public void testAttributesWithIncorrectTypeShouldReturnArraySizeOneWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        Attribute inValidAttribute2 = new Attribute();
        validAttribute1.setId(12345);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey(Constants.NAME_FI);
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        inValidAttribute2.setId(1234);
        inValidAttribute2.setNodeId("1234");
        inValidAttribute2.setKey(Constants.TYPE);
        inValidAttribute2.setValue("morjensta pöytään");
        inValidAttribute2.setStartDate(startDate);
        inValidAttribute2.setEndDate(endDate);
        inValidAttribute2.setNew(false);
        inValidAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(inValidAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("names");
        sectionAttribute1.setAttr("name_fi");
        SectionAttribute sectionAttribute2 = new SectionAttribute();
        sectionAttribute2.setId(1);
        sectionAttribute2.setSection("names");
        sectionAttribute2.setAttr("name_sv");
        SectionAttribute sectionAttribute3 = new SectionAttribute();
        sectionAttribute3.setId(1);
        sectionAttribute3.setSection("names");
        sectionAttribute3.setAttr("name_sv");
        sectionAttributes.add(sectionAttribute1);
        sectionAttributes.add(sectionAttribute2);
        sectionAttributes.add(sectionAttribute3);

        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, result.size());

        AttributeValidationDTO expectedFirstAttributeDTO = new AttributeValidationDTO();
        expectedFirstAttributeDTO.setErrorMessage(Constants.ATTRIBUTE_TYPE_VALIDATION_MESSAGE_KEY);
    };



    @Test
    public void testAttributesWithCorrectTypesShouldReturnEmptyArrayWithStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        List<Attribute> attributeList = new ArrayList<>();
        Attribute validAttribute1 = new Attribute();
        Attribute validAttribute2 = new Attribute();
        validAttribute1.setId(12345);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("hr-tunnus");
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        validAttribute2.setId(1234);
        validAttribute2.setNodeId("1234");
        validAttribute2.setKey("hr-tunnus");
        validAttribute2.setValue("morjensta pöytään");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(validAttribute2);

        List<SectionAttribute> sectionAttributes = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("codes");
        sectionAttribute1.setAttr("hr-tunnus");
        sectionAttributes.add(sectionAttribute1);


        ResponseEntity response =  nodeAttributeValidationService.validateNodeAttributes(attributeList, sectionAttributes);

        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, result.size());

    };


}
