package com.trbaxter.github.fractionalcomputationapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.trbaxter.github.fractionalcomputationapi.service.ComputationService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndexController.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ComputationService computationService;

	@Test
  public void testDerivativeEndpoint() throws Exception {

    when(computationService.derivative("2x+5", 1)).thenReturn("2");

    mockMvc.perform(MockMvcRequestBuilders.get("/calculate/derivative")
		   .param("expression", "2x+5")
		   .param("order", "1"))
           .andExpect(status().isOk())
           .andExpect(content().string("2"));
  }
}
