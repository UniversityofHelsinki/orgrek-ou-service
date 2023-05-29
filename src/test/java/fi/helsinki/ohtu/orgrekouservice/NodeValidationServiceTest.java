package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NewNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.NodeValidationService;
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
public class NodeValidationServiceTest {
    @Autowired
    private NodeValidationService nodeValidationService;

    @Test
    public void testNodeWithValidDatesShouldReturnEmptyArrayWithStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 20);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        Node node = new Node();
        node.setId("123");
        node.setUniqueId(231231);
        node.setStartDate(startDate);
        node.setEndDate(endDate);

        node.setName("test");

        ResponseEntity response = nodeValidationService.validateNode(node);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList() , response.getBody());
    }


    @Test
    public void testNodeWithInValidDatesShouldReturnInvalidArrayWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 16);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        Node node = new Node();
        node.setId("123");
        node.setUniqueId(231231);
        node.setStartDate(startDate);
        node.setEndDate(endDate);

        node.setName("test");

        ResponseEntity response = nodeValidationService.validateNode(node);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.NODE_DATE_VALIDATION_MESSAGE_KEY, result.get(0).getErrorMessage());

    }

    @Test
    public void testNewNodeWithValidDatesShouldReturnEmptyArrayWithStatusCode200() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        NewNodeDTO node = new NewNodeDTO();
        node.setParentNodeId("a1");
        node.setStartDate(startDate);
        node.setEndDate(null);

        List<String> hierarchies = new ArrayList<>();
        hierarchies.add("talous");
        hierarchies.add("tutkimus");
        node.setHierarchies(hierarchies);

        node.setNameFi("uusi yksikko");
        node.setNameEn("new organisation unit");
        node.setNameSv("ny enhet");

        ResponseEntity response = nodeValidationService.validateNewNode(node);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList() , response.getBody());
    }

    @Test
    public void testNewNodeWithInValidDatesShouldReturnEmptyArrayWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 16);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        NewNodeDTO node = new NewNodeDTO();
        node.setParentNodeId("a1");
        node.setStartDate(startDate);
        node.setEndDate(endDate);

        List<String> hierarchies = new ArrayList<>();
        hierarchies.add("talous");
        hierarchies.add("tutkimus");
        node.setHierarchies(hierarchies);

        node.setNameFi("uusi yksikko");
        node.setNameEn("new organisation unit");
        node.setNameSv("ny enhet");

        ResponseEntity response = nodeValidationService.validateNewNode(node);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.NODE_DATE_VALIDATION_MESSAGE_KEY, result.get(0).getErrorMessage());
    }


    @Test
    public void testNewNodeWithInValidHierarchiesShouldReturnEmptyArrayWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        NewNodeDTO node = new NewNodeDTO();
        node.setParentNodeId("a1");
        node.setStartDate(startDate);
        node.setEndDate(endDate);

        List<String> hierarchies = new ArrayList<>();
        node.setHierarchies(hierarchies);

        node.setNameFi("uusi yksikko");
        node.setNameEn("new organisation unit");
        node.setNameSv("ny enhet");

        ResponseEntity response = nodeValidationService.validateNewNode(node);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.NODE_HIERARCHY_VALIDATION_MESSAGE_KEY, result.get(0).getErrorMessage());
    }

    @Test
    public void testNewNodeWithInValidNamesShouldReturnEmptyArrayWithStatusCode422() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 15);
        c.set(Calendar.YEAR, 2022);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 17);
        c.set(Calendar.YEAR, 2022);
        Date endDate = c.getTime();

        NewNodeDTO node = new NewNodeDTO();
        node.setParentNodeId("a1");
        node.setStartDate(startDate);
        node.setEndDate(endDate);

        List<String> hierarchies = new ArrayList<>();
        hierarchies.add("talous");
        node.setHierarchies(hierarchies);

        node.setNameFi("");
        node.setNameEn("");
        node.setNameSv("");

        ResponseEntity response = nodeValidationService.validateNewNode(node);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        List<AttributeValidationDTO> result = (List<AttributeValidationDTO>) response.getBody();
        assertEquals(1, result.size());
        assertEquals(Constants.NODE_NAMES_VALIDATION_MESSAGE_KEY, result.get(0).getErrorMessage());
    }

}
