package fi.helsinki.ohtu.orgrekouservice.domain;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListToTree {

    public List<TreeNodeDTO> iterateTree(String hierarchy, List<TreeNodeDTO> list, Map<String, TreeNodeDTO> tmpMap) {
        for (Map.Entry<String, TreeNodeDTO> treeNodeEntry : tmpMap.entrySet()) {
            for (TreeNodeDTO treeNodeDTO : list) {
                for (TreeNodeDTO child : treeNodeDTO.getChildren()) {
                    for (TreeNodeDTO nodeDTO : treeNodeEntry.getValue().getChildren()) {
                        if (nodeDTO.getUniqueId() == treeNodeDTO.getUniqueId()) {
                            if (!nodeDTO.getHierarchies().contains(hierarchy)) {
                                nodeDTO.getHierarchies().add(hierarchy);
                            }
                        }
                        for (TreeNodeDTO nodeDTOChild : nodeDTO.getChildren()) {
                            if (nodeDTOChild.getUniqueId() == child.getUniqueId()) {
                                if (!child.getHierarchies().contains(hierarchy)) {
                                    nodeDTO.getHierarchies().add(hierarchy);
                                }
                            }
                        }
                    }
                    iterateTree(hierarchy, child.getChildren(), tmpMap);
                }
            }
        }
        return list;
    }


    public TreeNodeDTO createTree(List<Pair> pairs, String hierarchyType) {
        Map<String, TreeNodeDTO> map = new HashMap<>();

        for (Pair p:pairs) {
            TreeNodeDTO child = getChild(map, p, hierarchyType);
            updateParentChildRelation(map, p, child);
        }

        return map.get("a1");
    }

    private void updateParentChildRelation(Map<String, TreeNodeDTO> map, Pair p, TreeNodeDTO child) {
        TreeNodeDTO parent ;
        if (map.containsKey(p.getParentNodeId())) {
            parent = map.get(p.getParentNodeId());
        } else {
            parent = new TreeNodeDTO();
            map.put(p.getParentNodeId(),parent);
        }
        parent.setId(p.getParentNodeId());
        parent.addChild(child);
    }

    private TreeNodeDTO getChild(Map<String, TreeNodeDTO> map, Pair p, String hierarchyType) {
        TreeNodeDTO child ;
        if (map.containsKey(p.getChildNodeId())) {
            child = map.get(p.getChildNodeId());
        } else {
            child = new TreeNodeDTO();
            map.put(p.getChildNodeId(),child);
        }
        updateChild(p, child, hierarchyType);
        return child;
    }

    private void updateChild(Pair p, TreeNodeDTO child, String hierarchyType) {
        child.setId(p.getChildNodeId());
        child.setParentId(p.getParentNodeId());
        child.setNameFi(p.getNameFi());
        child.setNameEn(p.getNameEn());
        child.setNameSv(p.getNameSv());
        child.setUniqueId(p.getUniqueId());
        child.setCode(p.getCode());
        child.getHierarchies().add(hierarchyType);
    }
}
