package fi.helsinki.ohtu.orgrekouservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.orgrekouservice.domain.EmptyJsonResponse;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.service.HierarchyFilterService;
import fi.helsinki.ohtu.orgrekouservice.service.SectionAttributeService;
import fi.helsinki.ohtu.orgrekouservice.service.SectionValidationService;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    private SectionAttributeService sectionAttributeService;

    @Autowired
    private HierarchyFilterService hierarchyFilterService;

    @Autowired
    private SectionValidationService sectionValidationService;

    @GetMapping("/all")
    public ResponseEntity<List<SectionAttribute>> getSectionAttributes() {
        List<SectionAttribute> sectionAttributeList = sectionAttributeService.getAllSectionAttributes();
        return new ResponseEntity<>(sectionAttributeList, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertSectionAttribute(@RequestBody SectionAttribute sectionAttribute) {
        try {
            List<String> distinctHierarchyFilterKeys = hierarchyFilterService.getDistinctHierarchyFilterKeys();
            List<SectionAttribute> sectionAttributeList = sectionAttributeService.getAllSectionAttributes();
            ResponseEntity<?> response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, sectionAttribute, Constants.NEW_SECTION_ATTRIBUTE, sectionAttributeList);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                SectionAttribute insertedSectionAttribute = sectionAttributeService.insertSectionAttribute(sectionAttribute);
                return new ResponseEntity<>(insertedSectionAttribute, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSectionAttribute(@RequestBody SectionAttribute sectionAttribute) {
        try {
            List<String> distinctHierarchyFilterKeys = hierarchyFilterService.getDistinctHierarchyFilterKeys();
            SectionAttribute foundSectionAttribute = sectionAttributeService.getSectionAttributeById(sectionAttribute.getId());
            SectionAttribute updatedSectionAttribute = sectionAttributeService.updateSectionAttributeInfo(foundSectionAttribute, sectionAttribute);
            List<SectionAttribute> sectionAttributeList = sectionAttributeService.getAllSectionAttributes();
            ResponseEntity<?> response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, updatedSectionAttribute, Constants.UPDATE_SECTION_ATTRIBUTE, sectionAttributeList);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                sectionAttributeService.updateSectionAttribute(sectionAttribute);
                return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteSectionAttribute(@PathVariable("id") int sectionId) {
        try {
            List<String> distinctHierarchyFilterKeys = hierarchyFilterService.getDistinctHierarchyFilterKeys();
            List<SectionAttribute> sectionAttributeList = sectionAttributeService.getAllSectionAttributes();
            SectionAttribute foundSectionAttribute = sectionAttributeService.getSectionAttributeById(sectionId);
            ResponseEntity<?> response = sectionValidationService.validateSectionAttributes(distinctHierarchyFilterKeys, foundSectionAttribute, Constants.DELETE_SECTION_ATTRIBUTE, sectionAttributeList);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                HttpStatus httpStatus = (HttpStatus) sectionAttributeService.deleteSectionAttribute(sectionId);
                return new ResponseEntity<>(new EmptyJsonResponse(), httpStatus);
            }  else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.BAD_REQUEST);
        }
    }

}
