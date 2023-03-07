package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.service.UtilService;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class UtilServiceTest {

    @Autowired
    private UtilService utilService;

    List<NodeDTO> nodes = new ArrayList<>();

    @Test
    public void testThereIsAllThreeNameAttributes(){
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        Attribute nameEnAttribute = new Attribute(12345, "12345", "name_en", "University of Helsinki Centre for Continuing Education", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);
        attributes.add(nameEnAttribute);

        boolean isThereNameAttribute = utilService.isThereNameAttributes(attributes);

        assertTrue(isThereNameAttribute);
    }

    @Test
    public void testThereIsTwoNameAttributes(){
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);

        boolean isThereNameAttribute = utilService.isThereNameAttributes(attributes);

        assertTrue(isThereNameAttribute);
    }

    @Test
    public void testThereIsOneNameAttributes(){
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);

        boolean isThereNameAttribute = utilService.isThereNameAttributes(attributes);

        assertTrue(isThereNameAttribute);
    }


    @Test
    public void testThereAreNoNameAttributes(){
        Attribute emoAttribute = new Attribute(123, "123", "emo_lyhenne", "HY", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(emoAttribute);

        boolean isThereNameAttribute = utilService.isThereNameAttributes(attributes);

        assertFalse(isThereNameAttribute);
    }


    @Test
    public void testNodesGetDisplayNamesWithEmoAndLyhenne() {
        NodeDTO nodeDTO = new NodeDTO();
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        Attribute nameEnAttribute = new Attribute(12345, "12345", "name_en", "University of Helsinki Centre for Continuing Education", null, null, false, false);
        Attribute emoAttribute = new Attribute(123, "123","emo_lyhenne", "HY", null, null, false, false);
        Attribute lyhenneAttribute = new Attribute(123, "123", "lyhenne", "HYKK", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);
        attributes.add(nameEnAttribute);
        attributes.add(emoAttribute);
        attributes.add(lyhenneAttribute);
        nodeDTO.setAttributes(attributes);
        nodes.add(nodeDTO);
        nodes = utilService.getDisplayNames(nodes);

        assertEquals("HY, Helsingin yliopiston koulutus- ja kehittämispalvelut (HYKK)", nodes.get(0).getDisplayNameFi());
        assertEquals("HY, Helsingfors universitets utbildnings- och utvecklingstjänster (HYKK)", nodes.get(0).getDisplayNameSv());
        assertEquals("HY, University of Helsinki Centre for Continuing Education (HYKK)", nodes.get(0).getDisplayNameEn());
    }

    @Test
    public void testNodesGetDisplayNamesWithLyhenne() {
        NodeDTO nodeDTO = new NodeDTO();
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        Attribute nameEnAttribute = new Attribute(12345, "12345", "name_en", "University of Helsinki Centre for Continuing Education", null, null, false, false);
        Attribute lyhenneAttribute = new Attribute(123, "123", "lyhenne", "HYKK", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);
        attributes.add(nameEnAttribute);
        attributes.add(lyhenneAttribute);
        nodeDTO.setAttributes(attributes);
        nodes.add(nodeDTO);
        nodes = utilService.getDisplayNames(nodes);

        assertEquals("Helsingin yliopiston koulutus- ja kehittämispalvelut (HYKK)", nodes.get(0).getDisplayNameFi());
        assertEquals("Helsingfors universitets utbildnings- och utvecklingstjänster (HYKK)", nodes.get(0).getDisplayNameSv());
        assertEquals("University of Helsinki Centre for Continuing Education (HYKK)", nodes.get(0).getDisplayNameEn());
    }

    @Test
    public void testNodesGetDisplayNamesWithEmo() {
        NodeDTO nodeDTO = new NodeDTO();
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        Attribute nameEnAttribute = new Attribute(12345, "12345", "name_en", "University of Helsinki Centre for Continuing Education", null, null, false, false);
        Attribute emoAttribute = new Attribute(123, "123", "emo_lyhenne", "HY", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);
        attributes.add(nameEnAttribute);
        attributes.add(emoAttribute);
        nodeDTO.setAttributes(attributes);
        nodes.add(nodeDTO);
        nodes = utilService.getDisplayNames(nodes);

        assertEquals("HY, Helsingin yliopiston koulutus- ja kehittämispalvelut", nodes.get(0).getDisplayNameFi());
        assertEquals("HY, Helsingfors universitets utbildnings- och utvecklingstjänster", nodes.get(0).getDisplayNameSv());
        assertEquals("HY, University of Helsinki Centre for Continuing Education", nodes.get(0).getDisplayNameEn());
    }

    @Test
    public void testNodesGetDisplayNamesWithoutEmoAndLyhenne() {
        NodeDTO nodeDTO = new NodeDTO();
        Attribute nameFiAttribute = new Attribute(123, "123", "name_fi", "Helsingin yliopiston koulutus- ja kehittämispalvelut", null, null, false, false);
        Attribute nameSvAttribute = new Attribute(1234, "1234", "name_sv", "Helsingfors universitets utbildnings- och utvecklingstjänster", null, null, false, false);
        Attribute nameEnAttribute = new Attribute(12345, "12345", "name_en", "University of Helsinki Centre for Continuing Education", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(nameFiAttribute);
        attributes.add(nameSvAttribute);
        attributes.add(nameEnAttribute);
        nodeDTO.setAttributes(attributes);
        nodes.add(nodeDTO);
        nodes = utilService.getDisplayNames(nodes);

        assertEquals("Helsingin yliopiston koulutus- ja kehittämispalvelut", nodes.get(0).getDisplayNameFi());
        assertEquals("Helsingfors universitets utbildnings- och utvecklingstjänster", nodes.get(0).getDisplayNameSv());
        assertEquals("University of Helsinki Centre for Continuing Education", nodes.get(0).getDisplayNameEn());
    }

    @Test
    public void testNodesGetEmptyDisplayNamesWithoutNameAttributes() {
        NodeDTO nodeDTO = new NodeDTO();
        Attribute emoAttribute = new Attribute(123, "123", "emo_lyhenne", "HY", null, null, false, false);
        Attribute lyhenneAttribute = new Attribute(123, "123", "lyhenne", "HYKK", null, null, false, false);
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(emoAttribute);
        attributes.add(lyhenneAttribute);
        nodeDTO.setAttributes(attributes);
        nodes.add(nodeDTO);
        nodes = utilService.getDisplayNames(nodes);

        assertNull(nodes.get(0).getDisplayNameFi());
        assertNull(nodes.get(0).getDisplayNameSv());
        assertNull(nodes.get(0).getDisplayNameEn());
    }

}
