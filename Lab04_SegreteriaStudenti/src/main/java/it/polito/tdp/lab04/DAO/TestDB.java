package it.polito.tdp.lab04.DAO;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		cdao.getTuttiICorsi();
		
		StudenteDAO sdao=new StudenteDAO();
		sdao.getStudenteDaMAtricola(146102); //->matricola inesistente
		sdao.getStudenteDaMAtricola(146101); //->matricola esistente
		
		
	}

}
