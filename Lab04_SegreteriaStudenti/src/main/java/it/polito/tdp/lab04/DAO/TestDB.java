package it.polito.tdp.lab04.DAO;

import java.util.List;

import it.polito.tdp.lab04.model.Corso;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		cdao.getTuttiICorsi();
		
		StudenteDAO sdao=new StudenteDAO();
		sdao.getStudenteDaMatricola(146102); //->matricola inesistente
		sdao.getStudenteDaMatricola(146101); //->matricola esistente
		
		List<Corso> corsi=cdao.getCorso("Economia Aziendale");
		for(Corso c:corsi) {
			System.out.println(c.getCodins()+" "+c.getNome());
		}
	}

}
