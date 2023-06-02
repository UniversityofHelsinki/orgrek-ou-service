package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.EmptyJsonResponse;
import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.service.SectionAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    private SectionAttributeService sectionAttributeService;

    @GetMapping("/all")
    public ResponseEntity<List<SectionAttribute>> getSectionAttributes() {
        List<SectionAttribute> sectionAttributeList = sectionAttributeService.getAllSectionAttributes();
        return new ResponseEntity<>(sectionAttributeList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateSectionAttribute(@RequestBody SectionAttribute sectionAttribute) {
        try {
            sectionAttributeService.updateSectionAttribute(sectionAttribute);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyJsonResponse(),HttpStatus.BAD_REQUEST);
        }
    }

}
