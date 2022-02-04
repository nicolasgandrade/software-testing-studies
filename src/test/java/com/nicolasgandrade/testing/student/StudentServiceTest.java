package com.nicolasgandrade.testing.student;

import com.nicolasgandrade.testing.student.exception.BadRequestException;
import com.nicolasgandrade.testing.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp() {
        studentServiceUnderTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        // When
        studentServiceUnderTest.getAllStudents();
        // Then
        // Verifica se o studentRepository foi invocado com o método findAll();
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // Given
        Student student = new Student("Joao", "joao@gmail.com", Gender.MALE);
        // When
        studentServiceUnderTest.addStudent(student);
        //Then
        // Esse argumentCaptor captura o student que foi passado no verify().save()
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        // Verifica se o student que capturamos que foi salvo anteriormente é igual ao student
        // instanciado e que solicitamos que fosse salvo
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowExceptionWhenEmailIsTaken() {
        // Given
        Student student = new Student("Joao", "joao@gmail.com", Gender.MALE);

        given(studentRepository.selectExistsEmail(student.getEmail()))
                .willReturn(true);
        // When
        // Then
        // Garante que uma exeção foi enviada
        assertThatThrownBy(() -> studentServiceUnderTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        // Verifica se o repositório NUNCA foi chamado com o método save
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteExistentStudent() {
        // Given
        long id = 123;
        given(studentRepository.existsById(id)).willReturn(true);

        // When
        studentServiceUnderTest.deleteStudent(id);

        // Then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionIfUserIdToBeDeletedDoesNotExists() {
        // Given
        long id = 123;
        given(studentRepository.existsById(id)).willReturn(false);

        // When
        // Then
        assertThatThrownBy(() -> studentServiceUnderTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exists");

        verify(studentRepository, never()).deleteById(any());
    }
}