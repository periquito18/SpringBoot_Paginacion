package com.example.demo.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entidad.Curso;
import com.example.demo.entidad.Estudiante;
import com.example.demo.repositorio.CursoRepositorio;
import com.example.demo.repositorio.EstudianteRepositorio;
import com.example.demo.servicio.impl.EstudianteServiceImpl;

@SpringBootTest
public class EstudianteServicioTest {

	@Mock 
	private CursoRepositorio cursoRepositorio;
	
    @Mock
    private EstudianteRepositorio estudianteRepositorio;

    @InjectMocks
    private EstudianteServiceImpl estudianteServicio;

    private Estudiante estudiante;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        estudiante = new Estudiante();
        // Configura el estudiante con datos de prueba
    }

    /**
     *     Objetivo: Verificar que el método guardarEstudiante del servicio guarda correctamente un estudiante en el repositorio.
     *     Comportamiento esperado: Cuando se invoca guardarEstudiante, se espera que el repositorio guarde el estudiante y luego se verifica si el estudiante guardado no es null.
     */
    @Test
    public void testGuardarEstudiante() {
        when(estudianteRepositorio.save(any(Estudiante.class))).thenReturn(estudiante);
        Estudiante guardado = estudianteServicio.guardarEstudiante(new Estudiante());
        assertNotNull(guardado);
        verify(estudianteRepositorio).save(any(Estudiante.class));
    }

    /**
     *     Objetivo: Comprobar que el método actualizarEstudiante actualiza los detalles de un estudiante existente.
     *     Comportamiento esperado: Si el estudiante existe (simulado por findById devolviendo un Optional con estudiante), se actualiza y guarda. La prueba verifica si el estudiante retornado tiene los detalles actualizados.
     */
    @Test
    public void testActualizarEstudiante() {
        Long estudianteId = 1L;
        Estudiante estudianteParaActualizar = new Estudiante();
        estudianteParaActualizar.setNombre("Nombre Actualizado");
        
        // Inicializa 'estudiante' con algunos datos si aún no lo has hecho
        estudiante = new Estudiante();
        estudiante.setNombre("Nombre Original");
        
        when(estudianteRepositorio.findById(estudianteId)).thenReturn(Optional.of(estudiante));
        when(estudianteRepositorio.save(any(Estudiante.class))).thenReturn(estudianteParaActualizar);

        Estudiante actualizado = estudianteServicio.actualizarEstudiante(estudianteId, estudianteParaActualizar);

        assertNotNull(actualizado);
        assertEquals("Nombre Actualizado", actualizado.getNombre());
        verify(estudianteRepositorio).save(any(Estudiante.class));
    }
    /**
     *     Objetivo: Confirmar que el método eliminarEstudiante elimina un estudiante del repositorio.
     *     Comportamiento esperado: Se simula la eliminación del estudiante (sin ninguna acción real gracias a doNothing()) y se verifica si deleteById del repositorio fue invocado con el ID correcto.
     */
    @Test
    public void testEliminarEstudiante() {
        doNothing().when(estudianteRepositorio).deleteById(anyLong());
        estudianteServicio.eliminarEstudiante(1L);
        verify(estudianteRepositorio).deleteById(anyLong());
    }

    /**
     * Objetivo: Asegurar que se pueda buscar un estudiante por su ID a través del método buscarEstudiantePorId.
     * Comportamiento esperado: Al buscar un estudiante, se espera que findById del repositorio sea llamado y que el estudiante retornado no sea null.
     */
    @Test
    public void testBuscarEstudiantePorId() {
        when(estudianteRepositorio.findById(anyLong())).thenReturn(Optional.of(estudiante));
        Estudiante encontrado = estudianteServicio.buscarEstudiantePorId(1L);
        assertNotNull(encontrado);
        verify(estudianteRepositorio).findById(anyLong());
    }

    /**
     *     Objetivo: Verificar que el método listarTodosLosEstudiantes recupere una lista de todos los estudiantes.
     *     Comportamiento esperado: Se espera que el método findAll del repositorio sea llamado y que la lista retornada no esté vacía.
     */
    @Test
    public void testListarTodosLosEstudiantes() {
        when(estudianteRepositorio.findAll()).thenReturn(Collections.singletonList(estudiante));
        List<Estudiante> estudiantes = estudianteServicio.listarTodosLosEstudiantes();
        assertFalse(estudiantes.isEmpty());
        verify(estudianteRepositorio).findAll();
    }

/**
 *         Objetivo: Evaluar si el método agregarCursoAEstudiante asocia correctamente un curso a un estudiante.
 *         Comportamiento esperado: Si tanto el estudiante como el curso existen (simulado por existsById), se espera que se agregue el curso al estudiante y viceversa, y luego se verifica si el curso y el estudiante están asociados correctamente.
 */
    @Test
    public void testAgregarCursoAEstudiante() {
        Long estudianteId = 1L;
        Long cursoId = 1L;

        Estudiante estudiante = new Estudiante();
        estudiante.setId(estudianteId);
        estudiante.setCursos(new HashSet<>());

        Curso curso = new Curso();
        curso.setId(cursoId);
        curso.setEstudiantes(new HashSet<>());

        when(estudianteRepositorio.existsById(estudianteId)).thenReturn(true);
        when(cursoRepositorio.existsById(cursoId)).thenReturn(true);
        when(estudianteRepositorio.findById(estudianteId)).thenReturn(Optional.of(estudiante));
        when(cursoRepositorio.findById(cursoId)).thenReturn(Optional.of(curso));
        when(estudianteRepositorio.save(estudiante)).thenReturn(estudiante);
        when(cursoRepositorio.save(curso)).thenReturn(curso);

        Boolean resultado = estudianteServicio.agregarCursoAEstudiante(estudianteId, cursoId);

        assertTrue(resultado);
        assertTrue(estudiante.getCursos().contains(curso));
        assertTrue(curso.getEstudiantes().contains(estudiante));
        
        verify(estudianteRepositorio).save(estudiante);
        verify(cursoRepositorio).save(curso);
    }

}