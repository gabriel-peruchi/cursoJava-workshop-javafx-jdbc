package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoFormControle implements Initializable {

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private Label labelErroNome;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	@FXML
	public void onBtnSalvarClick() {
		System.out.println("Salvo com sucesso");
	}

	@FXML
	public void onBtnCancelarClick() {
		System.out.println("Cancelado");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializacaoNodes();
	}

	private void inicializacaoNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtNome, 30);
	}

}
