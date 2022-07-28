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
            return listToTree.createTree(pairs);
        } else {
            Map<String, TreeNodeDTO> map = new HashMap<>();
            for (String hierarchyType : hierarchyTypes) {
                List<Pair> pairs = treeService.getTreeHierarchyData(hierarchyType, date);
                ListToTree listToTree = new ListToTree();
                map.put(hierarchyType, listToTree.createTree(pairs));
            }
            List<TreeNodeDTO> list = new ArrayList<>();
            ListToTree listToTree = new ListToTree();
            for (Map.Entry<String, TreeNodeDTO> stringTreeNodeDTOEntry : map.entrySet()) {
                list.add(stringTreeNodeDTOEntry.getValue());
                        List<TreeNodeDTO> tmpList = listToTree.iterateTree(stringTreeNodeDTOEntry.getKey(), stringTreeNodeDTOEntry.getValue().getChildren(), map);
                        list.addAll(tmpList);
            }

            return list.get(0);
        }
    }

}
