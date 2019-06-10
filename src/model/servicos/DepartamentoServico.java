package model.servicos;

import java.util.List;

import model.dao.DepartamentoDao;
import model.dao.FabricaDao;
import model.entidades.Departamento;

public class DepartamentoServico {

	
	private DepartamentoDao dao = FabricaDao.createDepartamentoDao();
	
	public List<Departamento> buscaTodos() {
		
		return dao.consultaTodos();
		 
	}

}
