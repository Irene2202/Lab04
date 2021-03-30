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

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	String nomeCorso=boxCorso.getValue();
    	
    	if(nomeCorso==null) {
    		txtResult.setText("Errore: corso non selezionato");
    		return;
    	}
    	
    	for(Studente s:this.model.getStudentiIScrittiAlCorso(nomeCorso)) {
    		txtResult.appendText(s.toString()+"\n");
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {

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
    	Integer matricola;
    	try {
    		matricola=Integer.parseInt(txtMatricola.getText());
    	} catch (NumberFormatException ne) {
    		txtResult.setText("Matricola inserita non valida");
    		return;
    	}
    	
    	Studente s=this.model.getStudenteDaMAtricola(matricola);
    	if(s==null) {
    		txtResult.setText("Studente non trovato");
    		return;
    	}
    	txtNome.setText(s.getNome());
    	txtCognome.setText(s.getCognome());
    	txtResult.clear();
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
    	
    	List<String> corsi=new ArrayList<>();
    	corsi.add("");
    	for(Corso c: this.model.getTuttiICorsi()) {
    		corsi.add(c.getNome());
    	}
    	boxCorso.getItems().addAll(corsi);
    }
}
