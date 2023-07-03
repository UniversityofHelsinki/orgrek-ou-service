package fi.helsinki.ohtu.orgrekouservice;

import fi.helsinki.ohtu.orgrekouservice.domain.HierarchyPublicity;
import fi.helsinki.ohtu.orgrekouservice.domain.User;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyPublicityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class HierarchyPublicityTest {

    @Autowired
    private HierarchyPublicityService hierarchyPublicityService;

    @Test
    public void testUserWithReadAllRoleShouldReturnOnlyPublishedHierarchies() {
        User readerUser = getRoleReaderUser();
        List<HierarchyPublicity> hierarchyPublicityList = initializeHierarchyList();
        List<String> hierarchyList =  hierarchyPublicityService.hierarchyTypes(readerUser, hierarchyPublicityList);
        assertEquals(3, hierarchyList.size());
        assertEquals("talous", hierarchyList.get(0));
        assertEquals("tutkimus", hierarchyList.get(1));
        assertEquals("opetus", hierarchyList.get(2));
    }

    @Test
    public void testUserWithAdminRoleShouldReturnAllHierarchies() {
        User adminUser = getRoleAdminUser();
        List<HierarchyPublicity> hierarchyPublicityList = initializeHierarchyList();
        List<String> hierarchyList =  hierarchyPublicityService.hierarchyTypes(adminUser, hierarchyPublicityList);
        assertEquals(5, hierarchyList.size());
        assertEquals("talous", hierarchyList.get(0));
        assertEquals("tutkimus", hierarchyList.get(1));
        assertEquals("opetus", hierarchyList.get(2));
        assertEquals("toiminnanohjaus", hierarchyList.get(3));
        assertEquals("johto", hierarchyList.get(4));
    }

    private static List<HierarchyPublicity> initializeHierarchyList() {
        List<HierarchyPublicity> hierarchies = new ArrayList<>();
        HierarchyPublicity publishedHierarchy1 = new HierarchyPublicity();
        publishedHierarchy1.setId(1);
        publishedHierarchy1.setHierarchy("talous");
        publishedHierarchy1.setPublicity(true);
        hierarchies.add(publishedHierarchy1);
        HierarchyPublicity publishedHierarchy2 = new HierarchyPublicity();
        publishedHierarchy2.setId(2);
        publishedHierarchy2.setHierarchy("tutkimus");
        publishedHierarchy2.setPublicity(true);
        hierarchies.add(publishedHierarchy2);
        HierarchyPublicity publishedHierarchy3 = new HierarchyPublicity();
        publishedHierarchy3.setId(3);
        publishedHierarchy3.setHierarchy("opetus");
        publishedHierarchy3.setPublicity(true);
        hierarchies.add(publishedHierarchy3);
        HierarchyPublicity unPublishedHierarchy1 = new HierarchyPublicity();
        unPublishedHierarchy1.setId(4);
        unPublishedHierarchy1.setHierarchy("toiminnanohjaus");
        unPublishedHierarchy1.setPublicity(false);
        hierarchies.add(unPublishedHierarchy1);
        HierarchyPublicity unPublishedHierarchy2 = new HierarchyPublicity();
        unPublishedHierarchy2.setId(5);
        unPublishedHierarchy2.setHierarchy("johto");
        unPublishedHierarchy2.setPublicity(false);
        hierarchies.add(unPublishedHierarchy2);
        return hierarchies;
    }

    private static User getRoleReaderUser() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("");
        testUser.setRoles(Collections.singletonList("ROLE_READER"));
        testUser.setPreferredLanguage("fi");
        return testUser;
    }

    private static User getRoleAdminUser() {
        User testUser = new User();
        testUser.setEppn("test");
        testUser.setDisplayName("test");
        testUser.setHyGroupCn("grp-orgrek-role-admin");
        testUser.setRoles(Collections.singletonList("ROLE_ADMIN"));
        testUser.setPreferredLanguage("fi");
        return testUser;
    }
}
