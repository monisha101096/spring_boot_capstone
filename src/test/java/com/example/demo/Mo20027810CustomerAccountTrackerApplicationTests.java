package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Mo20027810CustomerAccountTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Mo20027810CustomerAccountTrackerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testfindAll() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/accounts/all", HttpMethod.GET, entity,
				String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testfindById() {
		Account account = restTemplate.getForObject(getRootUrl() + "/accounts/1", Account.class);
		Assert.assertNotNull(account);
	}
	
	@Test
	public void testfindByAnyField() {
		Account account = restTemplate.getForObject(getRootUrl() + "/accounts/John", Account.class);
		Assert.assertNotNull(account);
	}

	@Test
	public void testCreateAccount() {
		Account account = new Account();
		account.setName("Paul Roberts");
		account.setBalance(new Double(500));
		ResponseEntity<Account> postResponse = restTemplate.postForEntity(getRootUrl() + "/accounts/new", account, Account.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateAmount() {
		int id = 1;
		restTemplate.put(getRootUrl() + "/accounts/" + id, new Double(100));
		com.example.demo.model.Account updatedAccount = restTemplate.getForObject(getRootUrl() + "/accounts/" + id, Account.class);
		Assert.assertNotNull(updatedAccount);
	}
	
	@Test
	public void testDeleteAccount() {
		int id = 1;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/accounts/" + id ,  HttpMethod.DELETE, entity,
				String.class);
		Assert.assertNotNull(response.ok());
	}
	
	@Test
	public void testDeleteAllAccount() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/accounts/all", HttpMethod.DELETE, entity,
				String.class);
		Assert.assertNotNull(response.ok());
	}

}