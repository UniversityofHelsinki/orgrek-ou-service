package fi.helsinki.ohtu.orgrekouservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import fi.helsinki.ohtu.orgrekouservice.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.SteeringGroupTransformer;
import fi.helsinki.ohtu.orgrekouservice.domain.TextDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class SteeringGroupTransformerTest {

    private JsonNode jsonTree;

    @BeforeAll
    public void createTestDataAndDoTheTransformationAndSerializeIntoJson() {
        try {
            List<TextDTO> degreeTitles = readJsonFileToListOfObjects("degreeTitles.json", TextDTO.class);
            List<DegreeProgrammeDTO> groups = readJsonFileToListOfObjects("steering.json", DegreeProgrammeDTO.class);
            SteeringGroupTransformer.Localized<Map<String, Object>> transformation = SteeringGroupTransformer.transform(groups, degreeTitles);
            ObjectMapper objectMapper = new ObjectMapper();
            String resultAsString = objectMapper.writeValueAsString(transformation);
            jsonTree = objectMapper.readTree(resultAsString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTopLevelHas3Languages()  {
        assertThat(jsonTree.size(), is(3));
    }

    @Test
    public void testLanguagesAreFiSvEn() {
        Iterator<String> langNames = jsonTree.fieldNames();
        List<String> langCodes = new ArrayList<>();
        langNames.forEachRemaining(langCodes::add);
        assertThat(langCodes, contains("fi", "sv", "en"));
    }

    @Test
    public void testAllLanguageNodesHasSixChildElements() {
        assertThat(jsonTree.get("fi").size(), is(6));
        assertThat(jsonTree.get("sv").size(), is(6));
        assertThat(jsonTree.get("en").size(), is(6));
    }

    @Test
    public void testAllTitlesArePresent() {
        assertThat(jsonTree.get("fi").findValue("bachelorsTitle"), notNullValue());
        assertThat(jsonTree.get("sv").findValue("mastersTitle"), notNullValue());
        assertThat(jsonTree.get("fi").findValue("doctoralsTitle"), notNullValue());
    }

    @Test
    public void testProgrammeHasRightFields() {
        Iterator<String> programmeChildIterator = jsonTree.get("fi").get("bachelorsProgrammes").get(0).fieldNames();
        List<String> programmesChildNodeNames = new ArrayList<>();
        programmeChildIterator.forEachRemaining(programmesChildNodeNames::add);
        assertThat(programmesChildNodeNames, contains("iamGroup", "programmeCode", "programmeName", "steeringGroupName"));
    }

    @Test
    public void  testFourBachelorsSteeringGroupsExist() {
        JsonNode programmes = jsonTree.get("fi").get("bachelorsProgrammes");
        List<String> bachelorsSteeringGroupNames = programmes.findValues("steeringGroupName")
                .stream()
                .map(e -> e.asText())
                .collect(Collectors.toList());
        assertThat(bachelorsSteeringGroupNames, hasSize(4));
    }

    @Test
    public void  testBachelorsSteeringGroupsAreInAlphabeticalOrder() {
        JsonNode programmes = jsonTree.get("fi").get("bachelorsProgrammes");
        List<String> bachelorsSteeringGroupNames = programmes.findValues("steeringGroupName")
                .stream()
                .map(e -> e.asText())
                .collect(Collectors.toList());
        List<String> expectedNamesInOrder = Lists.newArrayList("Kasvatustieteiden kandiohjelman johtoryhmä", "Oikeusnotaarin koulutusohjelman johtoryhmä",
                "Teologian ja uskonnontutkimuksen kandiohjelman johtoryhmä",
                "Yhteiskuntatieteiden kandiohjelman johtoryhmä" );
        assertThat(bachelorsSteeringGroupNames, contains(expectedNamesInOrder.toArray()));
    }



    private List readJsonFileToListOfObjects(String filename, Class classtype) throws IOException {
        File jsonFile = new ClassPathResource(filename).getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType dtoType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, classtype);
        return objectMapper.readValue(jsonFile, dtoType);
    }
}



