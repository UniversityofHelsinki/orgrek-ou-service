package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    /*
    @RequestMapping(method = GET, value = "/{hierarchyTypes}/{date}")
    public TreeNodeDTO getTree(@PathVariable List<String> hierarchyTypes, @PathVariable String date) {
        if (hierarchyTypes.size() == 1) {
            List<Pair> pairs = treeService.getTreeHierarchyData(hierarchyTypes.get(0), date);
            ListToTree listToTree = new ListToTree();
            return listToTree.createTree(pairs, hierarchyTypes.get(0));
        } else {
            Map<String, TreeNodeDTO> map = new HashMap<>();
            for (String hierarchyType : hierarchyTypes) {
                List<Pair> pairs = treeService.getTreeHierarchyData(hierarchyType, date);
                ListToTree listToTree = new ListToTree();
                map.put(hierarchyType, listToTree.createTree(pairs, hierarchyType));
            }
            List<TreeNodeDTO> list = new ArrayList<>();
            ListToTree listToTree = new ListToTree();
            for (Map.Entry<String, TreeNodeDTO> treeEntry : map.entrySet()) {
                listToTree.iterateTree(treeEntry.getKey(), treeEntry.getValue().getChildren(), map);
                list.add(treeEntry.getValue());
            }
            // list contains all trees, this needs to be merged into single list
            return list.get(0);
        }
    }

     */
    private Comparator<TreeNode> byCodeAndName = (a, b) -> {
        if (a.getChildCode() == null && b.getChildCode() == null) {
            return a.getChildName().compareTo(b.getChildName());
        } else if (a.getChildCode() == null) {
            return 1;
        } else if (b.getChildCode() == null) {
            return -1;
        }
        int byCode = a.getChildCode().compareTo(b.getChildCode());
        if (byCode == 0) {
            return a.getChildName().compareTo(b.getChildName());
        }
        return byCode;
    };

    private HierarchyNode asTree(String current, List<String> currentsHierarchies, String language, Map<String, List<TreeNode>> treeNodes) {
        HierarchyNode node = new HierarchyNode();
        node.setId(current);
        node.setHierarchies(currentsHierarchies);
        if (treeNodes.containsKey(current)) {
            List<TreeNode> children = treeNodes.get(current).stream().filter(c -> c.getLanguage().equals(language))
                    .collect(Collectors.toList());
            node.setUniqueId(children.get(0).getParentNodeUniqueId());
            node.setName(children.get(0).getParentName());
            if (children.get(0).getParentCode() != null) {
                node.setName(children.get(0).getParentCode() + ", " + children.get(0).getParentName());
            }
            children.sort(byCodeAndName);
            children.forEach(c -> {
                HierarchyNode child = asTree(c.getChildNodeId(), c.getHierarchies(), language, treeNodes);
                child.setName(c.getChildName());
                if (c.getChildCode() != null) {
                    child.setName(c.getChildCode() + ", " + c.getChildName());
                }
                child.setId(c.getChildNodeId());
                child.setUniqueId(c.getChildNodeUniqueId());
                node.getChildren().add(child);
            });
        }
        return node;
    }

    @RequestMapping(method = GET, value = "/{hierarchyTypes:(?:[a-z]+,)*(?:[a-z]+)+}/{date}")
    public Map<String, HierarchyNode> getTree(@PathVariable("hierarchyTypes") String hierarchyTypes, @PathVariable("date") String date) {
        List<TreeNode> treeNodes = treeService.getTreeNodes(hierarchyTypes, date);
        Map<String, List<TreeNode>> byParent = treeNodes.stream().collect(Collectors.groupingBy(TreeNode::getParentNodeId));
        Map<String, HierarchyNode> byLanguage = new HashMap<>();
        byLanguage.put("fi", asTree("a1", Arrays.asList(hierarchyTypes.split(",")), "fi", byParent));
        byLanguage.put("en", asTree("a1", Arrays.asList(hierarchyTypes.split(",")), "en", byParent));
        byLanguage.put("sv", asTree("a1", Arrays.asList(hierarchyTypes.split(",")), "sv", byParent));
        return byLanguage;
    }



}
