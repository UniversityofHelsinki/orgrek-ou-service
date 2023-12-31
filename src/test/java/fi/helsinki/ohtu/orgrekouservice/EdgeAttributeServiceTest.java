package fi.helsinki.ohtu.orgrekouservice;


import fi.helsinki.ohtu.orgrekouservice.domain.Edge;
import fi.helsinki.ohtu.orgrekouservice.domain.EdgeWrapper;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class EdgeAttributeServiceTest {

    @Autowired
    private EdgeService edgeService;

    @Test
    public void testExtractEdgeWrappersToMapShouldReturnMapOfDifferenEdges() {
        List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
        EdgeWrapper edgeWrapperToBeUpdated = new EdgeWrapper(123, 42766115, 93988778, "henkilosto", null, null, false, false);
        EdgeWrapper edgeWrapperToBeAdded = new EdgeWrapper(123, 42766115, 93988778, "henkilosto", null, null, true, false);
        EdgeWrapper edgeWrapperToBeDeleted = new EdgeWrapper(123, 42766115, 93988778, "henkilosto", null, null, false, true);

        edgeWrapperList.add(edgeWrapperToBeUpdated);

        edgeWrapperList.add(edgeWrapperToBeAdded);

        edgeWrapperList.add(edgeWrapperToBeDeleted);

        Map<String, List<Edge>> map =  edgeService.extractEdgesToMap(edgeWrapperList);

        assertEquals(true, !map.entrySet().isEmpty());
        assertEquals(1, map.get(Constants.NEW_EDGES).size());
        assertEquals(1, map.get(Constants.UPDATED_EDGES).size());
        assertEquals(1, map.get(Constants.DELETED_EDGES).size());
    }
}
