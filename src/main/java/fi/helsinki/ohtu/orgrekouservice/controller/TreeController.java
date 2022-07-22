package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.Pair;
import fi.helsinki.ohtu.orgrekouservice.domain.TreeNodeDTO;
import fi.helsinki.ohtu.orgrekouservice.domain.ListToTree;
import fi.helsinki.ohtu.orgrekouservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @RequestMapping(method = GET, value = "/{hierarchyType}/{date}")
    public TreeNodeDTO getTree(@PathVariable("hierarchyType") String hierarchyType, @PathVariable String date) {
        List<Pair> pairs = treeService.getTreeHierarchyData(hierarchyType, date);
        ListToTree listToTree = new ListToTree();
        return listToTree.createTree(pairs);
    }

}
