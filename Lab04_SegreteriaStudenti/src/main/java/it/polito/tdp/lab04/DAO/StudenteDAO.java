package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Studente;
import it.polito.tdp.lab04.model.Corso;

public class StudenteDAO {
	
	/**
	 * ottengo lo studente data la matricola
	 */
	public Studente getStudenteDaMAtricola(Integer matricola){
		final String sql="SELECT * "
				+ "FROM studente "
				+ "WHERE matricola= ?";
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet rs=st.executeQuery();
			
			Studente s=null;
			
			while(rs.next()) {
				s=new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
				//System.out.println(s.getMatricola()+" "+s.getNome()+" "+s.getCognome()+" "+s.getCds());
			}
			conn.close();
			
			return s;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
	}

}
