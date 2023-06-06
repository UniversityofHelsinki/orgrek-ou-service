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

        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute);
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

        ResponseEntity response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        List<SectionValidationDTO> result = (List<SectionValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.SECTION_ATTRIBUTE_VALIDATION_NOT_FOUND_AT_HIERARCHY_FILTER_TABLE, result.get(0).getErrorMessage());
    }


}
