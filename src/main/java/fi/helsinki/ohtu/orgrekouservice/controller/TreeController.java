package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.*;
import fi.helsinki.ohtu.orgrekouservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;
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

    private TreeNode findRoot(TreeNode[] nodes) {
        for (TreeNode node : nodes) {
            if (node.getIsRoot()) {
                return node;
            }
        }
        return null;
    }

    private List<String> findRootNodeHierarchies(TreeNode[] nodes) {
        TreeNode rootNode = findRoot(nodes);
        Set<String> hierarchies = new HashSet<>();
        for (TreeNode node : nodes) {
            if (node.getParentNodeId().equals(rootNode.getParentNodeId())) {
                hierarchies.addAll(node.getHierarchies());
            }
        }
        return new ArrayList<>(hierarchies);
    }

    @RequestMapping(method = GET, value = "/{hierarchyTypes}/{date}")
    public List<Map<String, HierarchyNode>> getTree(@PathVariable("hierarchyTypes") String hierarchyTypes, @PathVariable("date") String date) {
        TreeNode[][] treeNodeLists = treeService.getTreeNodes(hierarchyTypes, date);
        List<Map<String, HierarchyNode>> trees = new ArrayList<>();
        for (TreeNode[] treeNodes : treeNodeLists) {
            TreeNode rootNode = findRoot(treeNodes);
            if (rootNode == null) {
                return trees;
            }
            Map<String, List<TreeNode>> byParent = List.of(treeNodes).stream().collect(Collectors.groupingBy(TreeNode::getParentNodeId));
            Map<String, HierarchyNode> byLanguage = new HashMap<>();
            String rootNodeID = rootNode.getParentNodeId();
            List<String> rootHierarchies = findRootNodeHierarchies(treeNodes);
            byLanguage.put("fi", asTree(rootNodeID, rootHierarchies, "fi", byParent));
            byLanguage.put("en", asTree(rootNodeID, rootHierarchies, "en", byParent));
            byLanguage.put("sv", asTree(rootNodeID, rootHierarchies, "sv", byParent));
            trees.add(byLanguage);
        }
        return trees;
    }



}
