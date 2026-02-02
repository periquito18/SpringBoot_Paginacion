package com.example.demo.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidad.Curso;
import com.example.demo.repositorio.CursoRepositorio;
import com.example.demo.servicio.CursoServicio;

@Service
public class CursoServicioImpl implements CursoServicio {

    @Autowired
    private CursoRepositorio cursoRepositorio;

    /**
     * Guarda un curso en el repositorio.
     * 
     * @param curso El curso a guardar.
     * @return El curso guardado con su ID generado.
     */
    @Override
    public Curso guardarCurso(Curso curso) {
        return cursoRepositorio.save(curso);
    }

    /**
     * Elimina un curso del repositorio por su ID.
     * 
     * @param id El ID del curso a eliminar.
     */
    @Override
    public void eliminarCurso(Long id) {
        cursoRepositorio.deleteById(id);
    }

    /**
     * Obtiene una lista de todos los cursos disponibles en el repositorio.
     * 
     * @return Una lista de todos los cursos.
     */
    @Override
    public List<Curso> listarTodosLosCursos() {
        return cursoRepositorio.findAll();
    }
    /**
     * Busca cursos por su título exacto.
     * 
     * @param titulo El título del curso a buscar.
     * @return Una lista de cursos que coinciden con el título proporcionado.
     */
    @Override
    public List<Curso> buscarCursosPorTitulo(String titulo) {
        return cursoRepositorio.findByTitulo(titulo);
    }
    /**
     * Busca cursos utilizando una consulta SQL que coincide parcialmente con el título.
     * 
     * @param titulo El título parcial para buscar los cursos.
     * @return Una lista de cursos que coinciden parcialmente con el título proporcionado.
     */
    @Override
    public List<Curso> buscarPorTituloParcialSQL(String titulo) {
        return cursoRepositorio.buscarPorTituloParcialSQL(titulo);
    }
    /**
     * Busca un curso específico por su ID.
     * 
     * @param id El ID del curso a buscar.
     * @return El curso encontrado o null si no existe.
     */
	@Override
	public Curso buscarCursoPorId(Long id) {
		return cursoRepositorio.findById(id).orElse(null);
	}
	/**
     * Actualiza los detalles de un curso existente.
     * Si el curso con el ID proporcionado existe, sus detalles son actualizados.
     * 
     * @param id El ID del curso a actualizar.
     * @param curso Los nuevos detalles del curso.
     * @return El curso actualizado o null si el curso no existe.
     */
	@Override
	public Curso actualizarCurso(Long id, Curso curso) {
        if (cursoRepositorio.existsById(id)) {
        	curso.setId(id);
            return cursoRepositorio.save(curso);
        }
        return null;

	}
	
	
}