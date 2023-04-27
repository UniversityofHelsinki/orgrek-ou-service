package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.AttributeValidationDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.service.NodeValidationService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


}
