package com.example.issue.domain.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.JpaEduStartApplication;
import com.example.issue.domain.Issue;
import com.example.issue.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JpaEduStartApplication.class)
@Transactional
public class IssueRepositoryTest {
	
	@Autowired
	private IssueRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void saveTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		final User user = new User("이름", "naver@navercorp.com");
		issue.setOwner(user);
		
		System.out.println("======= SAVE : " + issue.getTitle() + " ==========");
		repository.save(issue);							// 영속 상태
		
		final Long issueId = issue.getId();
		System.out.println("======= SELECT : "+ issue.getTitle() + "==========");
		final Issue newIssue = repository.findOne(issueId);	// 영속 상태
		
		assertThat(issue, is(newIssue));
		assertTrue(issue == newIssue);
		
		flushAndClear();								// issue, newIssue 준영속 상태
		
		System.out.println("======= RE SELECT : "+ issue.getTitle() + "==========");
		final Issue reIssue = repository.findOne(issueId);	// 영속 상태
		
		System.out.println("======== Check Lazy Loading Point 1 ============");
		final User reIssueOwner = reIssue.getOwner();
		
		System.out.println("======== Check Lazy Loading Point 2 ============");
		String reIssueOwnerEmail = reIssueOwner.getEmail();
		
		assertThat(reIssueOwnerEmail, is("naver@navercorp.com"));
		
		assertThat(reIssue.getId(), is(newIssue.getId()));
		assertFalse(reIssue == newIssue);
	}
	
	@Ignore
	@Test
	public void findAllTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		System.out.println("======= SAVE : " + issue.getTitle() + " ==========");
		repository.save(issue);							// 영속 상태
		
		final Issue issue2 = new Issue("제목2", "본문2");	// 비영속 상태
		System.out.println("======= SAVE : " + issue2.getTitle() + " ==========");
		repository.save(issue2);						// 영속 상태
		
		flushAndClear();								// issue, issue2 준영속 상
		
		System.out.println("======= SELECT ALL ==========");
		List<Issue> issues = repository.findAll();		// 영속 상태
	
		assertThat(issues.size(), is(2));
	}
	
	@Ignore
	@Test
	public void updateTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		System.out.println("======= SAVE : " + issue.getTitle() + " ==========");
		repository.save(issue);							// 영속 상태
		
		final String modifyContent = "수정된 본문";
		System.out.println("======= UPDATE : "+ issue.getTitle() + "==========");
		issue.setContent(modifyContent);					// 변경 감지
		
		final Long issueId = issue.getId();
		System.out.println("======= SELECT : "+ issue.getTitle() + "==========");
		final Issue updatedIssue = repository.findOne(issueId);	// 영속 상태
		
		assertThat(issue, is(updatedIssue));
		assertTrue(issue == updatedIssue);
		assertThat(issue.getContent(), is(modifyContent));
		
		flushAndClear();									// issue 준영속 상
		
		System.out.println("======= RE SELECT : "+ issue.getTitle() + "==========");
		final Issue reIssue = repository.findOne(issueId);	// 영속 상태
		
		assertThat(reIssue.getContent(), is(modifyContent));
		assertFalse(reIssue == issue);
	}
	
	@Ignore
	@Test
	public void deleteTest(){
		final Issue issue = new Issue("제목", "본문");		// 비영속 상태
		System.out.println("======= SAVE : " + issue.getTitle() + " ==========");
		repository.save(issue);							// 영속 상태
		
		flushAndClear(); 								// issue 준영속 상테
		
		final Long issueId = issue.getId();
		System.out.println("======= DELETE : " + issue.getTitle() + " ==========");
		repository.delete(issueId);						// 삭제
		
		flushAndClear();								// issue 삭제 상태
		
		System.out.println("======= SELECT : "+ issue.getTitle() + "==========");
		final Issue deletedIssue = repository.findOne(issueId);	// 이미 삭제
		
		assertNull(deletedIssue);
	}
	
	private void flushAndClear(){
		System.out.println("========   FLUSH   ==========");
		em.flush();
		em.clear();
	}

}
