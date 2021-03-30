package it.polito.tdp.lab04.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		/*
		 * 	Write here your test model
		 */
		
		List<Studente> studenti=model.getStudentiIScrittiAlCorso("Diritto commerciale");

	}

}
