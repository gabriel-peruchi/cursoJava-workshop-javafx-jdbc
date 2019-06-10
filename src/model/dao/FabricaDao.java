package model.dao;

import bd.BD;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class FabricaDao {

	public static VendedorDao createVendedorDao() {
		return new VendedorDaoJDBC(BD.getConexao());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(BD.getConexao());
	}
}
