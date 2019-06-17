package com.jwtsample.jwtsample;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles(value="test")
@SpringBootTest(classes = JwtsampleApplication.class)
@AutoConfigureWebMvc
public class JwtsampleApplicationTests {

	@Test
	public void contextLoads() {
		int a=1;
		assertEquals(a,1);
	}

}
