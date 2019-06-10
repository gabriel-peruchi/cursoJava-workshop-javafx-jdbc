package model.dao;

import java.util.List;

import model.entidades.Departamento;
import model.entidades.Vendedor;

public interface VendedorDao {
	
	void inserir(Vendedor obj);
	void update(Vendedor obj);
	void deletePorId(Integer id);
	Vendedor consultaPorId(Integer id);
	List<Vendedor> consultaTodos();
	List<Vendedor> consultaPorDepartamento(Departamento departamento);
}
