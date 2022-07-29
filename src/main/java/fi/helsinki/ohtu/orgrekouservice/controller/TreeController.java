package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.ListToTree;
import fi.helsinki.ohtu.orgrekouservice.domain.Pair;
import fi.helsinki.ohtu.orgrekouservice.domain.TreeNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

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

}
