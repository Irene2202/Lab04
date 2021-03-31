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
		for(Corso c: corsoDao.getTuttiICorsi()) {
			
			corsoLista.add(c);
		}
		return corsoLista;
	}
	
	public Studente getStudenteDaMatricola(Integer matricola) {
		return this.studenteDao.getStudenteDaMatricola(matricola);
	}
	
	public List<Studente> getStudentiIScrittiAlCorso(Corso corso){
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsoDataMatricola(Studente s){
		return this.studenteDao.getCorsiDataMatricola(s);
	}
	
	public boolean verificaStudenteIscrittoACorso(Studente s, Corso c) {
		return corsoDao.verificaStudenteIscrittoACorso(s, c);
	}
	
	public boolean isriviACorso(Studente s, Corso c) {
		if(!this.verificaStudenteIscrittoACorso(s, c)) {
			return this.corsoDao.inscriviStudenteACorso(s, c);
		}
		
		return false;
	}

}
