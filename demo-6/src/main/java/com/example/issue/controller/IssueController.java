package com.example.issue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.issue.domain.Issue;
import com.example.issue.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
	
	@Autowired
	private IssueService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Issue> saveIssue(@RequestBody Issue issueForm) {
		Issue issue = service.save(issueForm);
		return new ResponseEntity<>(issue, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Issue> findIssue(@PathVariable Long id) {
		Issue issue = service.find(id);
		return new ResponseEntity<>(issue, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findaLL() {
		List<Issue> issues = service.findAll();
		return new ResponseEntity<>(issues, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Issue> modifyIssue(@PathVariable Long id, @RequestBody Issue issueForm) {
		Issue issue = service.modify(id, issueForm.getTitle(), issueForm.getContent());
		return new ResponseEntity<>(issue, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteIssue(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
