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

    List<Node> nodes = new ArrayList<>();

    List<NodeWrapper> nodesIdsWithTypes = new ArrayList<>();

    @Test
    public void testNodesWithTypesShouldReturnCorrectNodeAndThreeTypes() {

        Node node = new Node();
        node.setId("19");
        node.setName("Uusi96");
        node.setStartDate(null);
        node.setEndDate(null);
        node.setTimestamp(null);
        node.setUnique_id(47556984);
        nodes.add(node);

        NodeWrapper nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("tutkimus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        List<NodeDTO> nodeDTOList = hierarchyService.getNodesWithTypes(nodes, nodesIdsWithTypes);

        assertEquals(nodes.size(), nodeDTOList.size());
        assertEquals(nodes.get(0).getId(), nodeDTOList.get(0).getNode().getId());
        assertEquals(3, nodeDTOList.get(0).getHierarchies().size());
        assertEquals("tutkimus", nodeDTOList.get(0).getHierarchies().get(0).getType());
        assertEquals("talous", nodeDTOList.get(0).getHierarchies().get(1).getType());
        assertEquals("opetus", nodeDTOList.get(0).getHierarchies().get(2).getType());
    }


    @Test
    public void testTwoNodesWithTypesShouldReturnCorrectNodesAndTypes() {

        Node node = new Node();
        node.setId("19");
        node.setName("Uusi96");
        node.setStartDate(null);
        node.setEndDate(null);
        node.setTimestamp(null);
        node.setUnique_id(47556984);
        nodes.add(node);

        node = new Node();
        node.setId("20");
        node.setName("Uusi97");
        node.setStartDate(null);
        node.setEndDate(null);
        node.setTimestamp(null);
        node.setUnique_id(47556985);
        nodes.add(node);

        NodeWrapper nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("tutkimus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setType("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("20");
        nodeWrapper.setType("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("20");
        nodeWrapper.setType("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        List<NodeDTO> nodeDTOList = hierarchyService.getNodesWithTypes(nodes, nodesIdsWithTypes);

        assertEquals(nodes.size(), nodeDTOList.size());
        assertEquals(nodes.get(0).getId(), nodeDTOList.get(0).getNode().getId());
        assertEquals(nodes.get(1).getId(), nodeDTOList.get(1).getNode().getId());
        assertEquals(3, nodeDTOList.get(0).getHierarchies().size());
        assertEquals("tutkimus", nodeDTOList.get(0).getHierarchies().get(0).getType());
        assertEquals("talous", nodeDTOList.get(0).getHierarchies().get(1).getType());
        assertEquals("opetus", nodeDTOList.get(0).getHierarchies().get(2).getType());
        assertEquals(2, nodeDTOList.get(1).getHierarchies().size());
        assertEquals("opetus", nodeDTOList.get(1).getHierarchies().get(0).getType());
        assertEquals("talous", nodeDTOList.get(1).getHierarchies().get(1).getType());
    }

}
