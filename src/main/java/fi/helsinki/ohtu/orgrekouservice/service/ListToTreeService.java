package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Pair;
import fi.helsinki.ohtu.orgrekouservice.domain.TreeNodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListToTreeService {

    @Autowired
    private HierarchyService hierarchyService;

    public TreeNodeDTO createTree(List<Pair> pairs) {
        Map<String, TreeNodeDTO> map = new HashMap<>();

        for (Pair p:pairs) {
            TreeNodeDTO child ;
            if (map.containsKey(p.getChildNodeId())) {
                child = map.get(p.getChildNodeId());
            }
            else {
                child = new TreeNodeDTO();
                map.put(p.getChildNodeId(),child);
            }
            child.setId(p.getChildNodeId());
            child.setParentId(p.getParentNodeId());
            child.setNameFi(p.getNameFi());
            child.setNameEn(p.getNameEn());
            child.setNameSv(p.getNameSv());

            TreeNodeDTO parent ;
            if (map.containsKey(p.getParentNodeId())) {
                parent = map.get(p.getParentNodeId());
            }
            else {
                parent = new TreeNodeDTO();
                map.put(p.getParentNodeId(),parent);
            }
            parent.setId(p.getParentNodeId());
            parent.addChild(child);
        }

        map.get("a1").setNameFi("Helsingin yliopisto");
        map.get("a1").setNameEn("University of Helsinki");
        map.get("a1").setNameSv("Universitet");
        return map.get("a1");
    }
}
