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
	public Studente getStudenteDaMatricola(Integer matricola){
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
	
	public List<Corso> getCorsiDataMatricola(Studente s){
		final String sql="SELECT c.codins, c.crediti, c.nome, c.pd "
				+"FROM studente AS s, iscrizione AS i, corso AS c "
				+"WHERE s.matricola=i.matricola && i.codins=c.codins && s.matricola=?";
		
		List<Corso> corsiDaMatricola=new ArrayList<>();
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			
			ResultSet rs=st.executeQuery();
			
			Corso c=null;
			while(rs.next()) {
				String codins = rs.getString("c.codins");
				int numeroCrediti = rs.getInt("c.crediti");
				String nome = rs.getString("c.nome");
				int periodoDidattico = rs.getInt("c.pd");
				
				c=new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsiDaMatricola.add(c);
			}
			conn.close();
			
			return corsiDaMatricola;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
	}

}
