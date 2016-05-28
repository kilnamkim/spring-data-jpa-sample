package com.example.issue.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issue.domain.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

}