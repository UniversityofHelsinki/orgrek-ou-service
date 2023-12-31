package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class AttributeSanitationServiceTest {

    @Autowired
    private NodeAttributeService nodeAttributeService;

    @Test
    public void testTwoValidAttributesShouldReturnTwoAttributesFromSanitation() {
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

        List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributeList);
        assertEquals(2, sanitizedAttributes.size());

    };

    @Test
    public void testOneInValidAndOneValidAttributesShouldReturnOneAttributeAfterSanitation() {
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
        validAttribute1.setId(123);
        validAttribute1.setNodeId("1234");
        validAttribute1.setKey("name_fi");
        validAttribute1.setValue("morjensta pöytään");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        inValidAttribute2.setId(123);
        inValidAttribute2.setNodeId("1234");
        inValidAttribute2.setKey("name_fi");
        inValidAttribute2.setValue("morjensta pöytään");
        inValidAttribute2.setStartDate(startDate);
        inValidAttribute2.setEndDate(endDate);
        inValidAttribute2.setNew(true);
        inValidAttribute2.setDeleted(true);

        attributeList.add(validAttribute1);
        attributeList.add(inValidAttribute2);

        List<Attribute> sanitizedAttributes = nodeAttributeService.sanitizeAttributes(attributeList);
        assertEquals(1, sanitizedAttributes.size());

    };
}
