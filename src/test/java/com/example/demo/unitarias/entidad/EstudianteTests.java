package com.example.demo.unitarias.entidad;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.entidad.Estudiante;
import com.example.demo.repositorio.EstudianteRepositorio;
import com.example.demo.servicio.EstudianteServicio;

@SpringBootTest
public class EstudianteTests {

    @MockBean
    private EstudianteRepositorio estudianteRepository;

    @InjectMocks
    private EstudianteServicio estudianteService;

    @Test
    public void testAgregarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan Perez");
        estudiante.setEmail("juan.perez@example.com");
        estudiante.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudiante);

        Estudiante creado = estudianteService.guardarEstudiante(estudiante);

        verify(estudianteRepository).save(estudiante);
        assert(creado.getNombre().equals(estudiante.getNombre()));
    }
    }