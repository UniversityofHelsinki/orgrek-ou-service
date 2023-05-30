package fi.helsinki.ohtu.orgrekouservice.controller;

import fi.helsinki.ohtu.orgrekouservice.domain.SectionAttribute;
import fi.helsinki.ohtu.orgrekouservice.service.SectionAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
