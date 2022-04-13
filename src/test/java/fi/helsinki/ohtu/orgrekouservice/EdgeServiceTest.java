package fi.helsinki.ohtu.orgrekouservice;


import fi.helsinki.ohtu.orgrekouservice.domain.User;
import fi.helsinki.ohtu.orgrekouservice.service.EdgeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class EdgeServiceTest {
    @Autowired
    private EdgeService edgeService;

    @Test
    public void testEconomyHierarchyShouldReturnToAllUsers() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("");
        testUser.setRoles(Collections.singletonList("ROLE_READER"));
        testUser.setPreferredLanguage("fi");
        List<String> types = List.of("talous", "tutkimus", "henkilöstö", "ohjaus", "opetus");

        List<String> filteredTypes = edgeService.filterHierarchyTypesForUser(types, testUser);

        assertEquals(1, filteredTypes.size());
        assertEquals("talous", filteredTypes.get(0));
    }

    @Test
    public void testAllHierarchiesShouldReturnToUsersWithRoleAdmin() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("grp-orgkrek-role-admin");
        testUser.setRoles(List.of("ROLE_READER", "ROLE_ADMIN"));
        testUser.setPreferredLanguage("fi");
        List<String> types = List.of("talous", "tutkimus", "henkilöstö", "ohjaus", "opetus");

        List<String> filteredTypes = edgeService.filterHierarchyTypesForUser(types, testUser);

        assertEquals(5, filteredTypes.size());
        assertEquals(types, filteredTypes);
    }

    @Test
    public void testAllHierarchiesShouldReturnToUsersWithRoleWriter() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("grp-orgkrek-role-writer");
        testUser.setRoles(List.of("ROLE_READER", "ROLE_WRITER"));
        testUser.setPreferredLanguage("fi");
        List<String> types = List.of("talous", "tutkimus", "henkilöstö", "ohjaus", "opetus");

        List<String> filteredTypes = edgeService.filterHierarchyTypesForUser(types, testUser);

        assertEquals(5, filteredTypes.size());
        assertEquals(types, filteredTypes);
    }

    @Test
    public void testAllHierarchiesShouldReturnToUsersWithRoleReaderAll() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("grp-orgkrek-role-reader-all");
        testUser.setRoles(List.of("ROLE_READER", "ROLE_READER_ALL"));
        testUser.setPreferredLanguage("fi");
        List<String> types = List.of("talous", "tutkimus", "henkilöstö", "ohjaus", "opetus");

        List<String> filteredTypes = edgeService.filterHierarchyTypesForUser(types, testUser);

        assertEquals(5, filteredTypes.size());
        assertEquals(types, filteredTypes);
    }
}
