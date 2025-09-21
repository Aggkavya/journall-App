package com.kavya.journalApp;

import com.kavya.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JournalApplicationTests {

	@Autowired
	private UserRepository userRepository;




	@ParameterizedTest
	@CsvSource({
			"1, 1, 2",
			"10, 6 , 4"
	})
	public void Testadd(int a, int b, int c){
		assertEquals(a, b+c);
	}

}
