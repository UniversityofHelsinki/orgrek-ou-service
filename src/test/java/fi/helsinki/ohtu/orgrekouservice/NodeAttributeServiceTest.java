package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.service.NodeAttributeService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class NodeAttributeServiceTest {
    @Autowired
    private NodeAttributeService nodeAttributeService;


    @Test
    public void testExtractAttributesToMapShouldReturnMapOfDifferentAttributes() {
        List<Attribute> attributeList = new ArrayList<>();
        Attribute nameFiAttributeToBeUpdated = new Attribute(123, "1234", "name_fi", "Muokattava nimi", null, null, false, false);
        Attribute nameEnAttributeToBeUpdated = new Attribute(124, "1234", "name_en", "Name to be modified", null, null, false, false);
        Attribute nameSvAttributeToBeUpdated = new Attribute(125, "1234", "name_sv", "Redigerbart namn", null, null, false, false);

        Attribute nameFiAttributeToBeAdded = new Attribute(555, "1235", "name_fi", "Uusi nimi", null, null, true, false);
        Attribute nameEnAttributeToBeAdded = new Attribute(666, "1235", "name_en", "New Name", null, null, true, false);
        Attribute nameSvAttributeToBeAdded = new Attribute(777, "1235", "name_sv", "Nytt Namn", null, null, true, false);

        Attribute nameFiAttributeToBeDeleted = new Attribute(555, "1235", "name_fi", "Poistettava nimi", null, null, false, true);
        Attribute nameEnAttributeToBeDeleted = new Attribute(666, "1235", "name_en", "Removable name", null, null, false, true);
        Attribute nameSvAttributeToBeDeleted = new Attribute(777, "1235", "name_sv", "Namnet som ska raderas", null, null, false, true);

        attributeList.add(nameFiAttributeToBeUpdated);
        attributeList.add(nameEnAttributeToBeUpdated);
        attributeList.add(nameSvAttributeToBeUpdated);

        attributeList.add(nameFiAttributeToBeAdded);
        attributeList.add(nameEnAttributeToBeAdded);
        attributeList.add(nameSvAttributeToBeAdded);

        attributeList.add(nameFiAttributeToBeDeleted);
        attributeList.add(nameEnAttributeToBeDeleted);
        attributeList.add(nameSvAttributeToBeDeleted);

        Map<String, List<Attribute>> map =  nodeAttributeService.extractAttributesToMap(attributeList);

        assertEquals(true, !map.entrySet().isEmpty());
        assertEquals(3, map.get(Constants.NEW_ATTRIBUTES).size());
        assertEquals(3, map.get(Constants.UPDATED_ATTRIBUTES).size());
        assertEquals(3, map.get(Constants.DELETED_ATTRIBUTES).size());

    }

}
