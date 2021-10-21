package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.Node;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeWrapper;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class HierarchyServiceTest {

    @Autowired
    private HierarchyService hierarchyService;

    List<Node> parents = new ArrayList<>();

    List<NodeWrapper> parentNodesIdsWithTypes = new ArrayList<>();

    @Test
    public void testParentNodesWithTypesShouldReturnCorrectParentNodeAndThreeTypes() {

        Node parentNode = new Node();
        parentNode.setId("19");
        parentNode.setName("Uusi96");
        parentNode.setStartDate(null);
        parentNode.setEndDate(null);
        parentNode.setTimestamp(null);
        parentNode.setUnique_id(47556984);
        parents.add(parentNode);

        NodeWrapper nodeWrapper = new NodeWrapper();
        nodeWrapper.setParentNodeId("19");
        nodeWrapper.setType("tutkimus");
        parentNodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setParentNodeId("19");
        nodeWrapper.setType("talous");
        parentNodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setParentNodeId("19");
        nodeWrapper.setType("opetus");
        parentNodesIdsWithTypes.add(nodeWrapper);

        List<NodeDTO> nodeDTOList = hierarchyService.getParentNodesWithTypes(parents, parentNodesIdsWithTypes);

        assertEquals(parents.size(), nodeDTOList.size());
        assertEquals(parents.get(0).getId(), nodeDTOList.get(0).getNode().getId());
        assertEquals(3, nodeDTOList.get(0).getHierarchies().size());
        assertEquals("tutkimus", nodeDTOList.get(0).getHierarchies().get(0));
        assertEquals("talous", nodeDTOList.get(0).getHierarchies().get(1));
        assertEquals("opetus", nodeDTOList.get(0).getHierarchies().get(2));
    }


}
