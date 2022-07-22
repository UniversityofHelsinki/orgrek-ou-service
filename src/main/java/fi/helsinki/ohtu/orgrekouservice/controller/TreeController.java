package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Pair;
import fi.helsinki.ohtu.orgrekouservice.domain.TreeNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyService;
import fi.helsinki.ohtu.orgrekouservice.service.ListToTreeService;
import fi.helsinki.ohtu.orgrekouservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.tree.TreeNode;
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

    @Autowired
    private ListToTreeService listToTreeService;

    @RequestMapping(method = GET, value = "/{hierarchyType}")
    public TreeNodeDTO getTree(@PathVariable("hierarchyType") String hierarchyType) {
        List<Pair> pairs = treeService.getTreeHierarchyData(hierarchyType);
        return listToTreeService.createTree(pairs);
    }

}
