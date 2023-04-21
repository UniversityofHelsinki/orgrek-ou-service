package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        node.setUniqueId(47556984);
        nodes.add(node);

        NodeWrapper nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("tutkimus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        List<NodeDTO> nodeDTOList = hierarchyService.getNodesWithTypes(nodes, nodesIdsWithTypes);

        assertEquals(nodes.size(), nodeDTOList.size());
        assertEquals(nodes.get(0).getId(), nodeDTOList.get(0).getNode().getId());
        assertEquals(3, nodeDTOList.get(0).getHierarchies().size());
        assertEquals("tutkimus", nodeDTOList.get(0).getHierarchies().get(0).getHierarchy());
        assertEquals("talous", nodeDTOList.get(0).getHierarchies().get(1).getHierarchy());
        assertEquals("opetus", nodeDTOList.get(0).getHierarchies().get(2).getHierarchy());
    }


    @Test
    public void testTwoNodesWithTypesShouldReturnCorrectNodesAndTypes() {

        Node node = new Node();
        node.setId("19");
        node.setName("Uusi96");
        node.setStartDate(null);
        node.setEndDate(null);
        node.setTimestamp(null);
        node.setUniqueId(47556984);
        nodes.add(node);

        node = new Node();
        node.setId("20");
        node.setName("Uusi97");
        node.setStartDate(null);
        node.setEndDate(null);
        node.setTimestamp(null);
        node.setUniqueId(47556985);
        nodes.add(node);

        NodeWrapper nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("tutkimus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("19");
        nodeWrapper.setHierarchy("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("20");
        nodeWrapper.setHierarchy("opetus");
        nodesIdsWithTypes.add(nodeWrapper);

        nodeWrapper = new NodeWrapper();
        nodeWrapper.setNodeId("20");
        nodeWrapper.setHierarchy("talous");
        nodesIdsWithTypes.add(nodeWrapper);

        List<NodeDTO> nodeDTOList = hierarchyService.getNodesWithTypes(nodes, nodesIdsWithTypes);

        assertEquals(nodes.size(), nodeDTOList.size());
        assertEquals(nodes.get(0).getId(), nodeDTOList.get(0).getNode().getId());
        assertEquals(nodes.get(1).getId(), nodeDTOList.get(1).getNode().getId());
        assertEquals(3, nodeDTOList.get(0).getHierarchies().size());
        assertEquals("tutkimus", nodeDTOList.get(0).getHierarchies().get(0).getHierarchy());
        assertEquals("talous", nodeDTOList.get(0).getHierarchies().get(1).getHierarchy());
        assertEquals("opetus", nodeDTOList.get(0).getHierarchies().get(2).getHierarchy());
        assertEquals(2, nodeDTOList.get(1).getHierarchies().size());
        assertEquals("opetus", nodeDTOList.get(1).getHierarchies().get(0).getHierarchy());
        assertEquals("talous", nodeDTOList.get(1).getHierarchies().get(1).getHierarchy());
    }

    @Test
    public void testMergeRelativeMapsWithOneHierarchyShouldReturnCorrectRelationDTOLists() {
        Map<String, List<RelativeDTO>> relativeMaps = new HashMap<>();
        Relative relative1 = new Relative();
        relative1.setId("a1");
        relative1.setUniqueId(42785051);
        relative1.setStartDate(null);
        relative1.setEndDate(null);
        relative1.setHierarchy("talous");
        relative1.setLanguage("fi");
        relative1.setFullName("Helsingin yliopisto");

        Relative relative2 = new Relative();
        relative2.setId("a1");
        relative2.setUniqueId(42785051);
        relative2.setStartDate(null);
        relative2.setEndDate(null);
        relative2.setHierarchy("talous");
        relative2.setLanguage("sv");
        relative2.setFullName("Helsingfors Universitet");

        Relative relative3 = new Relative();
        relative3.setId("a1");
        relative3.setUniqueId(42785051);
        relative3.setStartDate(null);
        relative3.setEndDate(null);
        relative3.setHierarchy("talous");
        relative3.setLanguage("en");
        relative3.setFullName("University of Helsinki");

        RelativeDTO relativeDTO1 = new RelativeDTO(relative1);
        relativeDTO1.addHierarchy(relative1);
        RelativeDTO relativeDTO2 = new RelativeDTO(relative2);
        relativeDTO2.addHierarchy(relative2);
        RelativeDTO relativeDTO3 = new RelativeDTO(relative3);
        relativeDTO3.addHierarchy(relative3);

        List<RelativeDTO> relationListFi = new ArrayList<>();
        List<RelativeDTO> relationListSv = new ArrayList<>();
        List<RelativeDTO> relationListEn = new ArrayList<>();
        relationListFi.add(relativeDTO1);
        relationListSv.add(relativeDTO2);
        relationListEn.add(relativeDTO3);

        relativeMaps.put("fi" , relationListFi);
        relativeMaps.put("sv" , relationListSv);
        relativeMaps.put("en" , relationListEn);

        List<RelationDTO> relativeList = hierarchyService.mergeRelativeMaps(relativeMaps);

        assertEquals(1, relativeList.size());
        assertEquals("a1", relativeList.get(0).getId());
        assertEquals(42785051, relativeList.get(0).getUniqueId());
        assertEquals(1, relativeList.get(0).getHierarchies().size());
        assertEquals("talous", relativeList.get(0).getHierarchies().get(0).getHierarchy());
        assertEquals(3, relativeList.get(0).getFullNames().size());
        assertEquals("Helsingin yliopisto", relativeList.get(0).getFullNames().get(0).getName());
        assertEquals("Helsingfors Universitet", relativeList.get(0).getFullNames().get(1).getName());
        assertEquals("University of Helsinki", relativeList.get(0).getFullNames().get(2).getName());
    }

    @Test
    public void testMergeRelativeMapsWithMultipleHierarchiesShouldReturnCorrectRelationDTOLists() {
        Map<String, List<RelativeDTO>> relativeMaps = new HashMap<>();
        Relative relative1 = new Relative();
        relative1.setId("a1");
        relative1.setUniqueId(42785051);
        relative1.setStartDate(null);
        relative1.setEndDate(null);
        relative1.setHierarchy("talous");
        relative1.setLanguage("fi");
        relative1.setFullName("Helsingin yliopisto");

        Relative relative2 = new Relative();
        relative2.setId("a1");
        relative2.setUniqueId(42785051);
        relative2.setStartDate(null);
        relative2.setEndDate(null);
        relative2.setHierarchy("talous");
        relative2.setLanguage("sv");
        relative2.setFullName("Helsingfors Universitet");

        Relative relative3 = new Relative();
        relative3.setId("a1");
        relative3.setUniqueId(42785051);
        relative3.setStartDate(null);
        relative3.setEndDate(null);
        relative3.setHierarchy("talous");
        relative3.setLanguage("en");
        relative3.setFullName("University of Helsinki");

        Relative relative4 = new Relative();
        relative4.setId("a1");
        relative4.setUniqueId(42785051);
        relative4.setStartDate(null);
        relative4.setEndDate(null);
        relative4.setHierarchy("tutkimus");
        relative4.setLanguage("fi");
        relative4.setFullName("Helsingin yliopisto");

        Relative relative5 = new Relative();
        relative5.setId("a1");
        relative5.setUniqueId(42785051);
        relative5.setStartDate(null);
        relative5.setEndDate(null);
        relative5.setHierarchy("tutkimus");
        relative5.setLanguage("sv");
        relative5.setFullName("Helsingfors Universitet");

        Relative relative6 = new Relative();
        relative6.setId("a1");
        relative6.setUniqueId(42785051);
        relative6.setStartDate(null);
        relative6.setEndDate(null);
        relative6.setHierarchy("tutkimus");
        relative6.setLanguage("sv");
        relative6.setFullName("Helsingfors Universitet");

        RelativeDTO relativeDTO1 = new RelativeDTO(relative1);
        relativeDTO1.addHierarchy(relative1);
        relativeDTO1.addHierarchy(relative4);
        RelativeDTO relativeDTO2 = new RelativeDTO(relative2);
        relativeDTO2.addHierarchy(relative2);
        relativeDTO2.addHierarchy(relative5);
        RelativeDTO relativeDTO3 = new RelativeDTO(relative3);
        relativeDTO3.addHierarchy(relative3);
        relativeDTO3.addHierarchy(relative6);


        List<RelativeDTO> relationListFi = new ArrayList<>();
        relationListFi.add(relativeDTO1);
        List<RelativeDTO> relationListSv = new ArrayList<>();
        relationListSv.add(relativeDTO2);
        List<RelativeDTO> relationListEn = new ArrayList<>();
        relationListEn.add(relativeDTO3);

        relativeMaps.put("fi" , relationListFi);
        relativeMaps.put("sv" , relationListSv);
        relativeMaps.put("en" , relationListEn);

        List<RelationDTO> relativeList = hierarchyService.mergeRelativeMaps(relativeMaps);

        assertEquals(1, relativeList.size());
        assertEquals("a1", relativeList.get(0).getId());
        assertEquals(42785051, relativeList.get(0).getUniqueId());
        assertEquals(2, relativeList.get(0).getHierarchies().size());
        assertEquals("talous", relativeList.get(0).getHierarchies().get(0).getHierarchy());
        assertEquals("tutkimus", relativeList.get(0).getHierarchies().get(1).getHierarchy());
        assertEquals(3, relativeList.get(0).getFullNames().size());
        assertEquals("Helsingin yliopisto", relativeList.get(0).getFullNames().get(0).getName());
        assertEquals("Helsingfors Universitet", relativeList.get(0).getFullNames().get(1).getName());
        assertEquals("University of Helsinki", relativeList.get(0).getFullNames().get(2).getName());
    }

    @Test
    public void testMergeRelativeMapsWithMultipleRelativesShouldReturnCorrectRelationDTOLists() {
        Map<String, List<RelativeDTO>> relativeMaps = new HashMap<>();
        Relative relative1 = new Relative();
        relative1.setId("a1");
        relative1.setUniqueId(42785051);
        relative1.setStartDate(null);
        relative1.setEndDate(null);
        relative1.setHierarchy("talous");
        relative1.setLanguage("fi");
        relative1.setFullName("Helsingin yliopisto");

        Relative relative2 = new Relative();
        relative2.setId("a1");
        relative2.setUniqueId(42785051);
        relative2.setStartDate(null);
        relative2.setEndDate(null);
        relative2.setHierarchy("talous");
        relative2.setLanguage("sv");
        relative2.setFullName("Helsingfors Universitet");

        Relative relative3 = new Relative();
        relative3.setId("a1");
        relative3.setUniqueId(42785051);
        relative3.setStartDate(null);
        relative3.setEndDate(null);
        relative3.setHierarchy("talous");
        relative3.setLanguage("en");
        relative3.setFullName("University of Helsinki");

        Relative relative4 = new Relative();
        relative4.setId("a1");
        relative4.setUniqueId(42785051);
        relative4.setStartDate(null);
        relative4.setEndDate(null);
        relative4.setHierarchy("tutkimus");
        relative4.setLanguage("fi");
        relative4.setFullName("Helsingin yliopisto");

        Relative relative5 = new Relative();
        relative5.setId("a1");
        relative5.setUniqueId(42785051);
        relative5.setStartDate(null);
        relative5.setEndDate(null);
        relative5.setHierarchy("tutkimus");
        relative5.setLanguage("sv");
        relative5.setFullName("Helsingfors Universitet");

        Relative relative6 = new Relative();
        relative6.setId("a1");
        relative6.setUniqueId(42785051);
        relative6.setStartDate(null);
        relative6.setEndDate(null);
        relative6.setHierarchy("tutkimus");
        relative6.setLanguage("sv");
        relative6.setFullName("Helsingfors Universitet");

        Relative relative7 = new Relative();
        relative7.setId("3191");
        relative7.setUniqueId(98306098);
        relative7.setStartDate(null);
        relative7.setEndDate(null);
        relative7.setHierarchy("talous");
        relative7.setLanguage("fi");
        relative7.setFullName("HY, Teologinen tiedekunta");

        Relative relative8 = new Relative();
        relative8.setId("3191");
        relative8.setUniqueId(98306098);
        relative8.setStartDate(null);
        relative8.setEndDate(null);
        relative8.setHierarchy("talous");
        relative8.setLanguage("sv");
        relative8.setFullName("HY, Teologiska fakulteten");

        Relative relative9 = new Relative();
        relative9.setId("3191");
        relative9.setUniqueId(98306098);
        relative9.setStartDate(null);
        relative9.setEndDate(null);
        relative9.setHierarchy("talous");
        relative9.setLanguage("en");
        relative9.setFullName("HY, Faculty of Theology");

        RelativeDTO relativeDTO1 = new RelativeDTO(relative1);
        relativeDTO1.addHierarchy(relative1);
        relativeDTO1.addHierarchy(relative4);

        RelativeDTO relativeDTO2 = new RelativeDTO(relative2);
        relativeDTO2.addHierarchy(relative2);
        relativeDTO2.addHierarchy(relative5);

        RelativeDTO relativeDTO3 = new RelativeDTO(relative3);
        relativeDTO3.addHierarchy(relative3);
        relativeDTO3.addHierarchy(relative6);

        RelativeDTO relativeDTO4 = new RelativeDTO(relative7);
        relativeDTO4.addHierarchy(relative7);

        RelativeDTO relativeDTO5 = new RelativeDTO(relative8);
        relativeDTO5.addHierarchy(relative8);

        RelativeDTO relativeDTO6 = new RelativeDTO(relative9);
        relativeDTO6.addHierarchy(relative9);


        List<RelativeDTO> relationListFi = new ArrayList<>();
        relationListFi.add(relativeDTO1);
        relationListFi.add(relativeDTO4);
        List<RelativeDTO> relationListSv = new ArrayList<>();
        relationListSv.add(relativeDTO2);
        relationListSv.add(relativeDTO5);
        List<RelativeDTO> relationListEn = new ArrayList<>();
        relationListEn.add(relativeDTO3);
        relationListEn.add(relativeDTO6);

        relativeMaps.put("fi" , relationListFi);
        relativeMaps.put("sv" , relationListSv);
        relativeMaps.put("en" , relationListEn);

        List<RelationDTO> relativeList = hierarchyService.mergeRelativeMaps(relativeMaps);

        assertEquals(2, relativeList.size());
        assertEquals("a1", relativeList.get(0).getId());
        assertEquals("3191", relativeList.get(1).getId());
        assertEquals(42785051, relativeList.get(0).getUniqueId());
        assertEquals(98306098, relativeList.get(1).getUniqueId());
        assertEquals(2, relativeList.get(0).getHierarchies().size());
        assertEquals("talous", relativeList.get(0).getHierarchies().get(0).getHierarchy());
        assertEquals("tutkimus", relativeList.get(0).getHierarchies().get(1).getHierarchy());
        assertEquals(3, relativeList.get(0).getFullNames().size());
        assertEquals("Helsingin yliopisto", relativeList.get(0).getFullNames().get(0).getName());
        assertEquals("Helsingfors Universitet", relativeList.get(0).getFullNames().get(1).getName());
        assertEquals("University of Helsinki", relativeList.get(0).getFullNames().get(2).getName());
        assertEquals("talous", relativeList.get(1).getHierarchies().get(0).getHierarchy());
        assertEquals("HY, Teologinen tiedekunta", relativeList.get(1).getFullNames().get(0).getName());
        assertEquals("HY, Teologiska fakulteten", relativeList.get(1).getFullNames().get(1).getName());
        assertEquals("HY, Faculty of Theology", relativeList.get(1).getFullNames().get(2).getName());
    }

}
