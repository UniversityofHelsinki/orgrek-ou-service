package fi.helsinki.ohtu.orgrekouservice.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fi.helsinki.ohtu.orgrekouservice.domain.FullName;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;

@Service
public class FullNameService {

        @Value("${server.url}")
        private String dbUrl;

        RestTemplate restTemplate = new RestTemplate();

        public List<FullName> getFullNamesByUniqueIdAndDate(int uniqueId, String date) {
                String url = dbUrl + Constants.FULL_NAME_API_PATH + "/" + uniqueId + "/" + date;
                ResponseEntity<FullName[]> names = restTemplate.getForEntity(url, FullName[].class);
                return List.of(names.getBody());
        }

        public List<FullName> getHistoryAndCurrentFullNames(int uniqueId, String date) {
                String url = dbUrl + Constants.FULL_NAME_API_PATH + "/historyandcurrent/" + uniqueId + "/" + date;
                ResponseEntity<FullName[]> names = restTemplate.getForEntity(url, FullName[].class);
                return List.of(names.getBody());
        }

        public List<FullName> getFutureAndCurrentFullNames(int uniqueId, String date) {
                String url = dbUrl + Constants.FULL_NAME_API_PATH + "/futureandcurrent/" + uniqueId + "/" + date;
                ResponseEntity<FullName[]> names = restTemplate.getForEntity(url, FullName[].class);
                return List.of(names.getBody());
        }

        public Map<String, List<FullName>> getFullNamesByUniqueIdsAndDate(List<Integer> uniqueIds, String date) {
                String url = dbUrl + Constants.FULL_NAME_API_PATH + "/" + date + "/mass";
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                try {
                        RequestEntity<List<Integer>> request = new RequestEntity<>(uniqueIds, headers, HttpMethod.POST, new URI(url));
                        ResponseEntity<FullName[]> response = restTemplate.exchange(request, FullName[].class);
                        List<FullName> fullNames = Arrays.asList(response.getBody());
                        return fullNames.stream().collect(Collectors.groupingBy(FullName::getNodeId));
                } catch (URISyntaxException use) {
                        return null;
                }
        }

        public void fillFullNames(List<NodeDTO> nodes, String date, Function<NodeDTO, Integer> uniqueIdFn, Function<NodeDTO, String> nodeIdFn) {
                List<Integer> uniqueIds = nodes.stream().map(uniqueIdFn::apply).collect(Collectors.toList());
                Map<String, List<FullName>> fullNames = getFullNamesByUniqueIdsAndDate(uniqueIds, date);
                nodes.forEach(node -> {
                        fullNames.get(nodeIdFn.apply(node)).forEach(fullName -> {
                                setFullName(node, fullName);
                        });
                });
        }

        public void fillFullNames(List<NodeDTO> nodes, Function<NodeDTO, Integer> uniqueIdFn, Function<NodeDTO, String> nodeIdFn, Function<NodeDTO, Date> dateFn) {
                nodes.forEach(node -> {
                        Integer uniqueId = uniqueIdFn.apply(node);
                        nodeIdFn.apply(node);
                        Date date = dateFn.apply(node);
                        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("fi"));
                        List<FullName> fullNames = getFullNamesByUniqueIdAndDate(uniqueId, df.format(date));
                        fullNames.forEach(fullName -> {
                                setFullName(node, fullName);
                        });
                });
        }

        private void setFullName(NodeDTO node, FullName fullName) {
                switch (fullName.getLanguage()) {
                        case "fi":
                                node.setDisplayNameFi(fullName.getName());
                                break;
                        case "en":
                                node.setDisplayNameEn(fullName.getName());
                                break;
                        case "sv":
                                node.setDisplayNameSv(fullName.getName());
                                break;
                }
        }
}
