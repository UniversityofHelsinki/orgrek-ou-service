package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Pair;
import fi.helsinki.ohtu.orgrekouservice.domain.TreeNode;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TreeService {

    @Value("${server.url}")
    private String dbUrl;

    RestTemplate restTemplate = new RestTemplate();

    public List<Pair> getTreeHierarchyData(String hierarchy, String date) {
        String url = dbUrl + Constants.TREE_API_HIERARCHY + '/' + hierarchy + '/' + date;
        ResponseEntity<Pair[]> response = restTemplate.getForEntity(url, Pair[].class);
        return List.of(response.getBody());
    }

    public TreeNode[][] getTreeNodes(String hierarchies, String date) {
        String url = dbUrl + Constants.TREE_API_HIERARCHY + '/' + hierarchies + '/' + date;
        ResponseEntity<TreeNode[][]> response = restTemplate.getForEntity(url, TreeNode[][].class);
        return response.getBody();
    }

}
