package com.example.issue.domain.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.JpaEduStartApplication;
import com.example.issue.domain.Issue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JpaEduStartApplication.class)
@Transactional
public class IssueRepositoryTest {
	
	@Autowired
	private IssueRepository repository;
	
	@Test
	public void saveTest(){
		final Issue issue = new Issue("제목", "본문");		
		repository.save(issue);							
		
		final Long issueId = issue.getId();
		final Issue newIssue = repository.findOne(issueId);
		
		assertThat(issue, is(newIssue));
		assertTrue(issue == newIssue);
	}
	
	
	@Test
	public void findAllTest(){
		final Issue issue = new Issue("제목", "본문");		
		repository.save(issue);							
		
		final Issue issue2 = new Issue("제목2", "본문2");	
		repository.save(issue2);						
		
		List<Issue> issues = repository.findAll();		
		
		assertThat(issues.size(), is(2));
	}
	
	@Test
	public void updateTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		repository.save(issue);							// 영속 상태
		
		final String modifyContent = "수정된 본문";
		issue.setContent(modifyContent);					// 변경 감지
		
		final Long issueId = issue.getId();
		final Issue updatedIssue = repository.findOne(issueId);	// 영속 상태
		
		assertThat(issue, is(updatedIssue));
		assertTrue(issue == updatedIssue);
		assertThat(issue.getContent(), is(modifyContent));
	}
	
	@Test
	public void deleteTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		System.out.println("======= SAVE : " + issue.getTitle() + " ==========");
		repository.save(issue);							// 영속 상태
		
		final Long issueId = issue.getId();
		System.out.println("======= DELETE : " + issue.getTitle() + " ==========");
		repository.delete(issueId);						// 삭제
		
		
		System.out.println("======= SELECT : "+ issue.getTitle() + "==========");
		final Issue deletedIssue = repository.findOne(issueId);	// 이미 삭제
		
		assertNull(deletedIssue);
	}

}
