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
	
	public List<Studente> getStudentiIScrittiAlCorso(String nome){
		List<Corso> corsi=this.corsoDao.getCorso(nome);
		List<Studente> studentiIscritti=new ArrayList<>();
		
		for(Corso c:corsi) {
			studentiIscritti.addAll(this.corsoDao.getStudentiIscrittiAlCorso(c));
		}
		return studentiIscritti;
	}
	
	public List<Corso> getCorsoDataMatricola(Studente s){
		return this.studenteDao.getCorsiDataMatricola(s);
	}
	
	public boolean verificaStudenteIscrittoACorso(Studente s, String nomeCorso) {
		List<Corso> corsi=this.corsoDao.getCorso(nomeCorso);
		boolean trovato=false;
		for(Corso c:corsi) {
			trovato=this.corsoDao.verificaStudenteIscrittoACorso(s, c);
			if(trovato)
				break;
		}
		
		return trovato;
	}
	
	public boolean isriviACorso(Studente s, String nomeCorso) {
		if(!this.verificaStudenteIscrittoACorso(s, nomeCorso)) {
			List<Corso> corsi=this.corsoDao.getCorso(nomeCorso);
			for(Corso c:corsi) {
				if(this.corsoDao.inscriviStudenteACorso(s, c))
					return true;
			}
			return false;
		} else
			return false;
		
	}

}
