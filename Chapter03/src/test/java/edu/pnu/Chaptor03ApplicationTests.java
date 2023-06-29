package edu.pnu;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest
@WebMvcTest // 모킹하기위함
class Chaptor03ApplicationTests {
	
	@Autowired //
	 MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
			mockMvc.perform(get("/hello").param("name","둘리"))
			.andExpect(status().isOk())
//			.andExpect(content().string(" Hello : 둘리"))
			.andDo(print());
			
	}

}
