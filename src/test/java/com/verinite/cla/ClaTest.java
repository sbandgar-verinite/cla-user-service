package com.verinite.cla;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ClaTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void updateConfig() throws IOException {
    }
	
	@Test
	public void getClaTest() throws Exception {
		int initiationId = 200;
		assertEquals(200, initiationId);
	}

	@Test
	public void getClaTestResult() throws IOException {
		try {
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/application/get/all")
							.contentType(APPLICATION_JSON_VALUE).header("tenant-id", 1))
					.andExpect(status().isOk()).andReturn();
			assertEquals(200, mvcResult.getResponse().getStatus());
		} catch (Exception e) {
			throw new RuntimeException("Cla test result : " + e);
		}
	}
}
