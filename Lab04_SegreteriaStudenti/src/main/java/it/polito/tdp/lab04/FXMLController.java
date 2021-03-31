package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> boxCorso;
    
    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtResult.clear();
    	Studente s=this.controlloMatricola();
    	Corso corso=boxCorso.getValue();
    	
    	if(s!=null && corso!=null) {    		
    		if(this.verificaStudenteIscrittoACorso(s, corso))
    			txtResult.setText("Studente iscritto al corso");
    		else
    			txtResult.setText("Studente non iscritto al corso");		
    	} else if(s!=null) {
    		List<Corso> corsi=this.model.getCorsoDataMatricola(s);
    		if(corsi==null) {
    			txtResult.setText("Lo studente non è iscritto ad nessun corso");
    			return;
    		}
    		for(Corso c:corsi) {
    			txtResult.appendText(c.toString()+"\n");
    		}
    	}

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	txtResult.clear();
    	Corso corso=boxCorso.getValue();
    	
    	if(corso==null) {
    		txtResult.setText("Errore: corso non selezionato");
    		return;
    	}
    	
    	String matricola=txtMatricola.getText();
    	
    	if(matricola.length()==0) {
    		List<Studente> studenti=this.model.getStudentiIScrittiAlCorso(corso);
    		if(studenti==null) {
    			txtResult.setText("Il corso non ha iscritti");
    			return;
    		}
    		for(Studente s:studenti)
    			txtResult.appendText(s.toString()+"\n");
    		
    	} else {
    		Studente s=this.controlloMatricola();
    		
    		if(this.verificaStudenteIscrittoACorso(s, corso))
    			txtResult.setText("Studente iscritto al corso");
    		else
    			txtResult.setText("Studente non iscritto al corso");
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	Corso corso=boxCorso.getValue();
    	Studente s=this.controlloMatricola();
    	if(corso==null) {
    		txtResult.setText("Selezionare un corso");
    		return;
    	}
    	
    	if(s!=null) {
    		boolean iscritto=this.model.isriviACorso(s, corso);
    		
    		if(iscritto) 
    			txtResult.setText("Studente iscritto con successo");
    		else 
    			txtResult.setText("Lo studente è già iscritto a questo corso");
    	}

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    }

    @FXML
    void doStudenteDaMatricola(ActionEvent event) {
    	Studente s=this.controlloMatricola();
    	if(s!=null) {
    		txtNome.setText(s.getNome());
    		txtCognome.setText(s.getCognome());
    		txtResult.clear();
    	}
    }

    @FXML
    void initialize() {
        assert boxCorso != null : "fx:id=\"boxCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	Corso c=null;
    	boxCorso.getItems().add(c);
    	boxCorso.getItems().addAll(model.getTuttiICorsi());
    }
    
    private Studente controlloMatricola() {
    	String matricolaS=txtMatricola.getText();
    	Studente s=null;
    	if(matricolaS.length()==0) {
    		txtResult.setText("Matricola non inserita");
    		return s;
    	}
    	Integer matricola;
    	try {
    		matricola=Integer.parseInt(matricolaS);
    	} catch (NumberFormatException ne) {
    		txtResult.setText("Matricola inserita non valida");
    		return null;
    	}
    	s=this.model.getStudenteDaMatricola(matricola);
    	if(s==null) {
    		txtResult.setText("La matricola inserita non corrisponde ad alcuno studente");
    		return null;
    	}
    	
    	return s;
    }
    
    private boolean verificaStudenteIscrittoACorso(Studente s, Corso c) {
    	return this.model.verificaStudenteIscrittoACorso(s, c);
    }
}
