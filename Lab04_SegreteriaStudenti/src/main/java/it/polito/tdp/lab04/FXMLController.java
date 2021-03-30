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
    private ComboBox<String> boxCorso;

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
    	Studente s=this.controlloMatricola();
    	String nomeCorso=boxCorso.getValue();
    	
    	if(s!=null && (nomeCorso==null || nomeCorso.equals(""))) {
    		List<Corso> corsi=this.model.getCorsoDataMatricola(s);
    		if(corsi==null) {
    			txtResult.setText("Lo studente non è iscritto ad nessun corso");
    			return;
    		}
    	
    		for(Corso c:corsi) {
    			txtResult.appendText(c.toString()+"\n");
    		}
    	}
    	else {
    		if(this.verificaStudenteIscrittoACorso(s, nomeCorso))
    			txtResult.setText("Studente iscritto al corso");
    		else
    			txtResult.setText("Studente non iscritto al corso");
    	}
    	

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	String nomeCorso=boxCorso.getValue();
    	
    	if(nomeCorso==null) {
    		txtResult.setText("Errore: corso non selezionato");
    		return;
    	}
    	
    	String matricola=txtMatricola.getText();
    	
    	if(matricola.length()==0) {
    		List<Studente> studenti=this.model.getStudentiIScrittiAlCorso(nomeCorso);
    		if(studenti==null) {
    			txtResult.setText("Il corso non ha iscritti");
    			return;
    		}
    		for(Studente s:studenti)
    			txtResult.appendText(s.toString()+"\n");
    		
    	} else {
    		Studente s=this.controlloMatricola();
    		
    		if(this.verificaStudenteIscrittoACorso(s, nomeCorso))
    			txtResult.setText("Studente iscritto al corso");
    		else
    			txtResult.setText("Studente non iscritto al corso");
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	String nomeCorso=boxCorso.getValue();
    	if(nomeCorso==null) {
    		txtResult.setText("Selezionare un corso");
    		return;
    	}
    	Studente s=this.controlloMatricola();
    	if(s!=null) {
    		boolean iscritto=this.model.isriviACorso(s, nomeCorso);
    		
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
    	
    	for(Corso c:this.model.getTuttiICorsi())
    		boxCorso.getItems().add(c.getNome());
    	boxCorso.getItems().add("");
    }
    
    private Studente controlloMatricola() {
    	Integer matricola;
    	try {
    		matricola=Integer.parseInt(txtMatricola.getText());
    	} catch (NumberFormatException ne) {
    		txtResult.setText("Matricola inserita non valida");
    		return null;
    	}
    	Studente s=this.model.getStudenteDaMatricola(matricola);
    	if(s==null) {
    		txtResult.setText("Studente non trovato");
    		return null;
    	}
    	
    	return s;
    }
    
    private boolean verificaStudenteIscrittoACorso(Studente s, String nomeCorso) {
    	return this.model.verificaStudenteIscrittoACorso(s, nomeCorso);
    }
}
