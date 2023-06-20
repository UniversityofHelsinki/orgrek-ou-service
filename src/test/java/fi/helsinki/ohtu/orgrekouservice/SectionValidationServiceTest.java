package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.service.SectionValidationService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class SectionValidationServiceTest {
    @Autowired
    private SectionValidationService sectionValidationService;

    @Test
    public void testNewSectionAttributeFoundAtDistinctHierarchyFilterKeysShouldReturn200() {
        List<String> distinctHierarchyFilterKeys = new ArrayList<>();
        distinctHierarchyFilterKeys.add("emo_lyhenne");
        distinctHierarchyFilterKeys.add("type");

        SectionAttribute sectionAttribute = new SectionAttribute();
        sectionAttribute.setSection("codes");
        sectionAttribute.setAttr("emo_lyhenne");

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("codes");
        sectionAttribute1.setAttr("hr_lyhenne");

        sectionAttributeList.add(sectionAttribute1);


        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute, Constants.NEW_SECTION_ATTRIBUTE, sectionAttributeList);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testNewSectionAttributeNotFoundAtDistinctHierarchyFilterKeysShouldReturn422() {
        List<String> distinctHierarchyFilterKeys = new ArrayList<>();
        distinctHierarchyFilterKeys.add("emo_lyhenne");
        distinctHierarchyFilterKeys.add("type");

        SectionAttribute sectionAttribute = new SectionAttribute();
        sectionAttribute.setSection("codes");
        sectionAttribute.setAttr("foobar");

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("codes");
        sectionAttribute1.setAttr("hr_lyhenne");

        sectionAttributeList.add(sectionAttribute1);

        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute, Constants.NEW_SECTION_ATTRIBUTE, sectionAttributeList);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        List<SectionValidationDTO> result = (List<SectionValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE, result.get(0).getErrorMessage());
    }

    @Test
    public void testSectionAttributeAlreadyFoundShouldReturn422() {
        List<String> distinctHierarchyFilterKeys = new ArrayList<>();
        distinctHierarchyFilterKeys.add("emo_lyhenne");
        distinctHierarchyFilterKeys.add("type");
        distinctHierarchyFilterKeys.add("foobar");

        SectionAttribute sectionAttribute = new SectionAttribute();
        sectionAttribute.setSection("codes");
        sectionAttribute.setAttr("foobar");

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("codes");
        sectionAttribute1.setAttr("foobar");

        sectionAttributeList.add(sectionAttribute1);

        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute, Constants.NEW_SECTION_ATTRIBUTE, sectionAttributeList);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        List<SectionValidationDTO> result = (List<SectionValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.SECTION_ATTRIBUTE_FOUND_AT_SECTION_ATTRIBUTE_TABLE, result.get(0).getErrorMessage());
    }

    @Test
    public void testOldSectionAttributeFoundAtDistinctHierarchyFilterKeysShouldReturn200() {
        List<String> distinctHierarchyFilterKeys = new ArrayList<>();
        distinctHierarchyFilterKeys.add("emo_lyhenne");
        distinctHierarchyFilterKeys.add("type");

        SectionAttribute sectionAttribute = new SectionAttribute();
        sectionAttribute.setId(1);
        sectionAttribute.setSection("codes");
        sectionAttribute.setAttr("type");

        List<SectionAttribute> sectionAttributeList = new ArrayList<>();
        SectionAttribute sectionAttribute1 = new SectionAttribute();
        sectionAttribute1.setId(1);
        sectionAttribute1.setSection("codes");
        sectionAttribute1.setAttr("emo_lyhenne");

        sectionAttributeList.add(sectionAttribute1);

        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute, Constants.UPDATE_SECTION_ATTRIBUTE, sectionAttributeList);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
