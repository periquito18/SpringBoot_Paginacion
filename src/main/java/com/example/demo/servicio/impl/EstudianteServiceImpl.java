package com.example.demo.servicio.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidad.Curso;
import com.example.demo.entidad.Estudiante;
import com.example.demo.repositorio.CursoRepositorio;
import com.example.demo.repositorio.EstudianteRepositorio;
import com.example.demo.servicio.EstudianteServicio;

@Service
public class EstudianteServiceImpl implements EstudianteServicio {

	@Autowired
	private EstudianteRepositorio estudianteRepositorio;
	
	@Autowired
	private CursoRepositorio cursoRepositorio;
	
	/**
	 * Guarda un estudiante en el repositorio.
	 * 
	 * @param estudiante El estudiante a guardar.
	 * @return El estudiante guardado con un ID asignado.
	 */
	@Override
	public Estudiante guardarEstudiante(Estudiante estudiante) {
		return estudianteRepositorio.save(estudiante);
	}

/**
 * Actualiza un estudiante existente en el repositorio.
 * Si el estudiante con el ID proporcionado existe, sus detalles son actualizados.
 * 
 * @param id El ID del estudiante a actualizar.
 * @param estudiante Los nuevos detalles del estudiante.
 * @return El estudiante actualizado o null si el estudiante no existe.
 */
	@Override
	public Estudiante actualizarEstudiante(Long id, Estudiante estudiante) {
	    Estudiante estudiante_ = estudianteRepositorio.findById(id).orElse(null);
	    if(estudiante_ != null) {
	        estudiante_.setNombre(estudiante.getNombre());
	        estudiante_.setEmail(estudiante.getEmail());
	        estudiante_.setFechaNacimiento(estudiante.getFechaNacimiento());
	        estudiante.setCursos(estudiante.getCursos());
	        //estudiante.setId(estudiante.getId());
	        // Asegúrate de actualizar los demás campos que necesitas.
	        return estudianteRepositorio.save(estudiante_);
	    }
	    return null;
	}

	/**
	 * Elimina un estudiante del repositorio por su ID.
	 * 
	 * @param id El ID del estudiante a eliminar.
	 */
	@Override
	public void eliminarEstudiante(Long id) {
		estudianteRepositorio.deleteById(id);
		
	}
	/**
	 * Busca un estudiante en el repositorio por su ID.
	 * 
	 * @param id El ID del estudiante a buscar.
	 * @return El estudiante encontrado o null si no existe.
	 */
	@Override
	public Estudiante buscarEstudiantePorId(Long id) {
		return estudianteRepositorio.findById(id).orElse(null);
	}
	/**
	 * Obtiene una lista de todos los estudiantes en el repositorio.
	 * 
	 * @return Una lista de todos los estudiantes.
	 */
	@Override
	public List<Estudiante> listarTodosLosEstudiantes() {
		return estudianteRepositorio.findAll();
	}
	/**
	 * Busca estudiantes por su nombre.
	 * 
	 * @param nombre El nombre a buscar en los estudiantes.
	 * @return Una lista de estudiantes que coinciden con el nombre proporcionado.
	 */
	@Override
	public List<Estudiante> buscarEstudiantesPorNombre(String nombre) {
	
		return estudianteRepositorio.findByNombre(nombre);
	}

	/**
	 * Obtiene un conjunto de estudiantes asociados a un curso específico.
	 * 
	 * @param idCurso El ID del curso para el cual buscar los estudiantes.
	 * @return Un conjunto de estudiantes inscritos en el curso.
	 */
	@Override
	public Set<Estudiante> obtenerEstudiantesPorCurso(Long idCurso) {
		Optional<Curso> curso = cursoRepositorio.findById(idCurso);
		Set<Estudiante> estudiantes = curso.get().getEstudiantes();
		return estudiantes;
	}


/**
 * Agrega un curso a un estudiante y viceversa.
 * Si tanto el estudiante como el curso existen, se asocian mutuamente.
 * 
 * @param idEstudiante El ID del estudiante al cual agregar el curso.
 * @param idCurso El ID del curso que se va a agregar al estudiante.
 * @return Verdadero si la operación fue exitosa, falso de lo contrario.
 */
	@Override
	public Boolean agregarCursoAEstudiante(Long idEstudiante, Long idCurso) {
		if(cursoRepositorio.existsById(idCurso) && estudianteRepositorio.existsById(idEstudiante) ) {
			Estudiante estudiante = estudianteRepositorio.findById(idEstudiante).get();
			Curso curso = cursoRepositorio.findById(idCurso).get();
			
			estudiante.getCursos().add(curso);
			curso.getEstudiantes().add(estudiante);
			
			estudianteRepositorio.save(estudiante);
			cursoRepositorio.save(curso);
			
			return true;
		}
		return false;
	}

}
