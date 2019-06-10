package model.dao;

import java.util.List;

import model.entidades.Departamento;

public interface DepartamentoDao {

	void inserir(Departamento obj);
	void update(Departamento obj);
	void deletePorId(Integer id);
	Departamento consultaPorId(Integer id);
	List<Departamento> consultaTodos();
	
}
