package model.servicos;

import java.util.ArrayList;
import java.util.List;

import model.entidades.Departamento;

public class DepartamentoServico {
	
	public List<Departamento> buscaTodos() {
		
		
		List<Departamento> list = new ArrayList<Departamento>();
		
		list.add(new Departamento(1, "Livros"));
		list.add(new Departamento(2, "Computadores"));
		list.add(new Departamento(3, "Eletronicos"));
		
		return list;
		 
	}

}
