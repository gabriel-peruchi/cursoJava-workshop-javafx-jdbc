package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bd.BdException;
import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Departamento;
import model.servicos.DepartamentoServico;

public class DepartamentoFormControle implements Initializable {

	private Departamento entidade;
	private DepartamentoServico servico;

	// lista de obejtos que "querem" receber o evento desta tela
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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
	public void onBtnSalvarClick(ActionEvent evento) {

		if (entidade == null) {
			throw new IllegalStateException("Entidade esta nula");
		}

		if (servico == null) {
			throw new IllegalStateException("Servico esta nulo");
		}

		try {

			// salva um departamento
			servico.salvarAtualizacao(getFormDados());
			
			//notifica os obejtos sobre a atualizacao(evento)
			notificaDataChangeListener();
			
			// fecha a janela atual
			Utils.palcoAtual(evento).close();

		} catch (BdException e) {
			Alertas.showAlert("Erro salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}

	}

	private void notificaDataChangeListener() {
		
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtnCancelarClick(ActionEvent evento) {
		Utils.palcoAtual(evento).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializacaoNodes();
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	private void inicializacaoNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtNome, 30);
	}

	public void atualizaFormDados() {

		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}

		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
	}

	public void setDepartamento(Departamento entidade) {
		this.entidade = entidade;
	}

	public void setDepartamentoServico(DepartamentoServico servico) {
		this.servico = servico;
	}

	// retorna um departamento com os dados da tela
	public Departamento getFormDados() {

		return new Departamento(Utils.converterParaInteiro(txtId.getText()), txtNome.getText());

	}
}
