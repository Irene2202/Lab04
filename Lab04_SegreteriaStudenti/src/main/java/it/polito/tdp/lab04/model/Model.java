package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao=new CorsoDAO();
		studenteDao=new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi(){
		List<Corso> corsoLista=new ArrayList<>();
		
		//corsoLista.add("Corsi");
		
		for(Corso c: corsoDao.getTuttiICorsi()) {
			
			corsoLista.add(c);
		}
		return corsoLista;
	}
	
	public Studente getStudenteDaMAtricola(Integer matricola) {
		return this.studenteDao.getStudenteDaMAtricola(matricola);
	}
	
	public List<Studente> getStudentiIScrittiAlCorso(String nome){
		Corso c=this.corsoDao.getCorso(nome);
		return this.corsoDao.getStudentiIscrittiAlCorso(c);
	}

}
