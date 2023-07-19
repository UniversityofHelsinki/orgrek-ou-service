package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeAttributeValidationService;
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
public class EdgeValidationServiceTest {

    @Autowired
    EdgeAttributeValidationService edgeAttributeValidationService;

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

        List<EdgeWrapper> attributeList = new ArrayList<>();
        EdgeWrapper validAttribute1 = new EdgeWrapper();
        validAttribute1.setId(123);
        validAttribute1.setParentUniqueId(42766115);
        validAttribute1.setChildUniqueId(93988778);
        validAttribute1.setHierarchy("henkilosto");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        EdgeWrapper validAttribute2 = new EdgeWrapper();
        validAttribute2.setId(123);
        validAttribute2.setParentUniqueId(42766115);
        validAttribute2.setChildUniqueId(93988778);
        validAttribute2.setHierarchy("henkilosto");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(validAttribute2);

        ResponseEntity response =  edgeAttributeValidationService.validateEdges(attributeList, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList() , response.getBody());
    };

}
