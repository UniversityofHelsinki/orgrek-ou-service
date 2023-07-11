package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.NameLanguageWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.NewHierarchyPublicityDTO;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityValidationService;
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
public class HierarchyPublicityValidationServiceTest {

    @Autowired
    private HierarchyPublicityValidationService hierarchyPublicityValidationService;

    @Test
    public void testValidNewHierarchyPublicityShouldReturn200() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("foobarFi");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("foobarEn");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("foobarSv");
        nameLanguageWrapperSv.setLanguage("sv");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyNameMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("foobarFi");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("foobarEn");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("foobarSv");
        nameLanguageWrapperSv.setLanguage("sv");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyChildIdMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("foobarFi");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("foobarEn");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("foobarSv");
        nameLanguageWrapperSv.setLanguage("sv");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyNamesMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyLanguageFinnishNameMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("foobarEn");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("foobarSv");
        nameLanguageWrapperSv.setLanguage("sv");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyLanguageSwedishNameMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("foobarFi");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("foobarEn");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("");
        nameLanguageWrapperSv.setLanguage("fi");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testInValidHierarchyLanguageEnglishNameMissingShouldReturn422() {
        NewHierarchyPublicityDTO newHierarchyPublicityDTO = new NewHierarchyPublicityDTO();
        newHierarchyPublicityDTO.setHierarchy("foobar");
        newHierarchyPublicityDTO.setPublicity(false);
        newHierarchyPublicityDTO.setChildId("a1");
        List<NameLanguageWrapper> names = new ArrayList<>();
        NameLanguageWrapper nameLanguageWrapperFi = new NameLanguageWrapper();
        nameLanguageWrapperFi.setName("foobarFi");
        nameLanguageWrapperFi.setLanguage("fi");
        names.add(nameLanguageWrapperFi);
        NameLanguageWrapper nameLanguageWrapperEn = new NameLanguageWrapper();
        nameLanguageWrapperEn.setName("");
        nameLanguageWrapperEn.setLanguage("en");
        names.add(nameLanguageWrapperEn);
        NameLanguageWrapper nameLanguageWrapperSv = new NameLanguageWrapper();
        nameLanguageWrapperSv.setName("foobarFi");
        nameLanguageWrapperSv.setLanguage("fi");
        names.add(nameLanguageWrapperSv);
        newHierarchyPublicityDTO.setNames(names);
        ResponseEntity response = hierarchyPublicityValidationService.validateNewHierarchyPublicity(newHierarchyPublicityDTO);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

}
