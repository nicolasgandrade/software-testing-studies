package com.nicolasgandrade.testing.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepositoryUnderTest;

    @AfterEach
    void tearDown() {
        studentRepositoryUnderTest.deleteAll();
    }

    @Test
    void shouldCheckIfStudentEmailExists() {
        //Given
        Student student = new Student("Joao", "joao@gmail.com", Gender.MALE);
        studentRepositoryUnderTest.save(student);

        //When
        boolean expected = studentRepositoryUnderTest.selectExistsEmail("joao@gmail.com");

        //Then
        assertThat(expected).isEqualTo(true);
    }

    @Test
    void shouldCheckIfStudentEmailDoesNotExists() {
        //Given
        String email = "nonexistent@gmail.com";

        //When
        boolean expected = studentRepositoryUnderTest.selectExistsEmail(email);

        //Then
        assertThat(expected).isFalse();
    }
}