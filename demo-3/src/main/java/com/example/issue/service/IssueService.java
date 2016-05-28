package com.example.issue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.issue.domain.Issue;
import com.example.issue.domain.repository.IssueRepository;

@Service
public class IssueService {

	@Autowired
	private IssueRepository repository;

	@Transactional
	public Issue save(Issue issue) {
		return repository.save(issue);
	}

	@Transactional(readOnly = true)
	public Issue find(Long id) {
		return repository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Issue> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Issue modify(Long id, String title, String content) {
		Issue issue = repository.findOne(id);
		issue.setTitle(title);
		issue.setContent(content);
		return issue;
	}

	@Transactional
	public void delete(Long id) {
		repository.delete(id);
	}
}
