package com.example.demo.unitarias.repositorio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.entidad.Curso;
import com.example.demo.entidad.Estudiante;
import com.example.demo.repositorio.CursoRepositorio;
import com.example.demo.repositorio.EstudianteRepositorio;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DataJpaTest
public class EstudianteCursoPruebas {

	    private Estudiante estudiante;
	    private Curso curso;
	    
	    @Autowired
	    private Validator validator;
	    
	    @BeforeEach
	    void init() {
	        // Inicializar un estudiante y un curso con datos válidos
	        estudiante = new Estudiante();
	        estudiante.setNombre("Ana López");
	        estudiante.setEmail("ana.lopez@example.com");
	        estudiante.setFechaNacimiento(LocalDate.of(1995, 5, 20));

	        curso = new Curso();
	        curso.setTitulo("Introducción a la Programación");
	        curso.setDescripcion("Curso básico de programación en Java.");
	    }

	    @Test
	    public void validarEstudianteSinErrores() {
	        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);
	        assertTrue(violations.isEmpty()); // Verifica que no hay violaciones de las restricciones
	    }

	    @Test
	    public void validarCursoDescripcionLongitud() {
	        curso.setDescripcion("Descripción corta.");
	        Set<ConstraintViolation<Curso>> violations = validator.validate(curso);
	        assertTrue(violations.isEmpty()); // Verifica que no hay violaciones de las restricciones

	        // Establecer una descripción que exceda los 200 caracteres
	        curso.setDescripcion("Descripción larga que supera los doscientos caracteres permitidos por la validación de la entidad Curso. " +
	                "Este texto es solo para fines de prueba y debe resultar en una violación de las restricciones de validación cuando se ejecute el validador.");
	        violations = validator.validate(curso);
	        assertFalse(violations.isEmpty()); // Debe haber una violación debido a la longitud de la descripción
	        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descripcion")));
	    }

}
