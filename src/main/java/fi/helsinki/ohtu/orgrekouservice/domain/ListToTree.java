package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListToTree {

    public TreeNodeDTO createTree(List<Pair> pairs) {
        Map<String, TreeNodeDTO> map = new HashMap<>();

        for (Pair p:pairs) {
            TreeNodeDTO child = getChild(map, p);
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

    private TreeNodeDTO getChild(Map<String, TreeNodeDTO> map, Pair p) {
        TreeNodeDTO child ;
        if (map.containsKey(p.getChildNodeId())) {
            child = map.get(p.getChildNodeId());
        } else {
            child = new TreeNodeDTO();
            map.put(p.getChildNodeId(),child);
        }
        updateChild(p, child);
        return child;
    }

    private void updateChild(Pair p, TreeNodeDTO child) {
        child.setId(p.getChildNodeId());
        child.setParentId(p.getParentNodeId());
        child.setNameFi(p.getNameFi());
        child.setNameEn(p.getNameEn());
        child.setNameSv(p.getNameSv());
        child.setUniqueId(p.getUniqueId());
        child.setCode(p.getCode());
    }
}
