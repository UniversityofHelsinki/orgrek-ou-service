package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class EdgeSanitationServiceTest {

    @Autowired
    private EdgeService edgeService;

    @Test
    public void testTwoValidEdgeWrappersShouldReturnTwoEdgeWrappersFromSanitation() {
        LocalDate startDate = LocalDate.of(2022, 2, 15);
        LocalDate endDate = LocalDate.of(2022, 2, 20);

        List<EdgeWrapper> attributeList = new ArrayList<>();
        EdgeWrapper validAttribute1 = new EdgeWrapper();
        EdgeWrapper validAttribute2 = new EdgeWrapper();
        validAttribute1.setId(123);
        validAttribute1.setParentUniqueId(42766115);
        validAttribute1.setChildUniqueId(93988778);
        validAttribute1.setHierarchy("henkilosto");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        validAttribute2.setId(123);
        validAttribute1.setParentUniqueId(42766115);
        validAttribute1.setChildUniqueId(93988778);
        validAttribute1.setHierarchy("henkilosto");
        validAttribute2.setStartDate(startDate);
        validAttribute2.setEndDate(endDate);
        validAttribute2.setNew(false);
        validAttribute2.setDeleted(false);

        attributeList.add(validAttribute1);
        attributeList.add(validAttribute2);

        List<EdgeWrapper> sanitizedAttributes = edgeService.sanitizeAttributes(attributeList);
        assertEquals(2, sanitizedAttributes.size());

    };

    @Test
    public void testOneInValidAndOneValidEdgeWrapperShouldReturnOneAttributeAfterSanitation() {
        LocalDate startDate = LocalDate.of(2022, 2, 15);
        LocalDate endDate = LocalDate.of(2022, 2, 20);

        List<EdgeWrapper> attributeList = new ArrayList<>();
        EdgeWrapper validAttribute1 = new EdgeWrapper();
        EdgeWrapper inValidAttribute2 = new EdgeWrapper();
        validAttribute1.setId(123);
        validAttribute1.setParentUniqueId(42766115);
        validAttribute1.setChildUniqueId(93988778);
        validAttribute1.setHierarchy("henkilosto");
        validAttribute1.setStartDate(startDate);
        validAttribute1.setEndDate(endDate);
        validAttribute1.setNew(false);
        validAttribute1.setDeleted(false);

        inValidAttribute2.setId(123);
        validAttribute1.setParentUniqueId(42766115);
        validAttribute1.setChildUniqueId(93988778);
        validAttribute1.setHierarchy("henkilosto");
        inValidAttribute2.setStartDate(startDate);
        inValidAttribute2.setEndDate(endDate);
        inValidAttribute2.setNew(true);
        inValidAttribute2.setDeleted(true);

        attributeList.add(validAttribute1);
        attributeList.add(inValidAttribute2);

        List<EdgeWrapper> sanitizedAttributes = edgeService.sanitizeAttributes(attributeList);
        assertEquals(1, sanitizedAttributes.size());

    };
}
