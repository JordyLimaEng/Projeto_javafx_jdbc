package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Departamento;

public class DepartmentService {
	
	public List<Departamento> findAll(){
		//mock
		List<Departamento> list = new ArrayList<>();
		list.add(new Departamento(1,"Livros"));
		list.add(new Departamento(2,"DVDS"));
		list.add(new Departamento(3,"Computadores"));
		return list;
	}
}
