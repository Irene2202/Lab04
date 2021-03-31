package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso c=new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql="SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+"FROM corso AS c, studente AS s, iscrizione AS i "
				+"WHERE c.nome=? && c.codins=i.codins && i.matricola=s.matricola";
		
		List<Studente> studentiIscritti=new LinkedList<>();
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, corso.getNome());
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Integer matricola=rs.getInt("s.matricola");
				String cognome=rs.getString("s.cognome");
				String nome=rs.getString("s.nome");
				String cds=rs.getString("s.CDS");
				
				Studente s=new Studente(matricola, cognome, nome, cds);
				studentiIscritti.add(s);
				//System.out.println(s.getMatricola()+" "+s.getNome()+" "+s.getCognome()+" "+s.getCds());
			}
			
			conn.close();
			
			return studentiIscritti;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erroe DB", e);
		}
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		try {
			Connection conn=ConnectDB.getConnection();
			Statement statement=conn.createStatement();
			
			statement.executeUpdate("INSERT INTO iscrizione VALUES ("+studente.getMatricola()+", '"+corso.getCodins()+"')");
			
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}

		// ritorna true se l'iscrizione e' avvenuta con successo
		return true;
	}
	
	public boolean verificaStudenteIscrittoACorso(Studente s, Corso c) {
		final String sql="SELECT COUNT(*) AS result "
				+"FROM iscrizione "
				+"WHERE matricola=? && codins=?";
		Integer result;
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			st.setString(2, c.getCodins());
			
			ResultSet rs=st.executeQuery();
			
			rs.next();
			result=rs.getInt("result");
			//System.out.println(result+" "+c.getCodins()+" "+s.getMatricola());
			
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
		
		if(result==0) {
			return false;
		}
		else
			return true;
	}

}
