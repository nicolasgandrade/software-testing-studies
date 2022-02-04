package com.nicolasgandrade.testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestingApplicationTests {

	Calculator calcUnderTest = new Calculator();

	@Test
	void shouldAddNumbers() {
		//Given
		int a = 15;
		int b = 20;

		//When
		int result = calcUnderTest.add(a, b);

		//Then
		int expected = 35;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator {
		int add(int x, int y) {return x + y;}
	}

}
