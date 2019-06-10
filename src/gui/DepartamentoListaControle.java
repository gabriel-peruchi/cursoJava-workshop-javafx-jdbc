package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Departamento;
import model.servicos.DepartamentoServico;

public class DepartamentoListaControle implements Initializable {

	private DepartamentoServico servico;

	@FXML
	private Button botaoNovoDepartamento;

	@FXML
	private TableView<Departamento> tabelaViewDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> tabelaColunaId;

	@FXML
	private TableColumn<Departamento, String> tabelaColunaNome;

	private ObservableList<Departamento> obsList;

	@FXML
	public void onBotaoNovoDepartamentoClick(ActionEvent evento) {
		
		Stage parentStage = Utils.palcoAtual(evento);

		criarDialogoForm(parentStage, "/gui/DepartamentoForm.fxml");
		
	}

	public void setDepartamentoServico(DepartamentoServico servico) {
		this.servico = servico;
	}

	public void initialize(URL url, ResourceBundle rb) {
		inicializaNodes();
	}

	private void inicializaNodes() {

		tabelaColunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tabelaColunaNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		Stage stage = (Stage) Main.getMainScene().getWindow();

		tabelaViewDepartamento.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTabelaView() {

		if (servico == null) {
			throw new IllegalStateException("O servico esta nulo");
		}

		List<Departamento> list = servico.buscaTodos();
		obsList = FXCollections.observableArrayList(list);
		tabelaViewDepartamento.setItems(obsList);

	}
	
	public void criarDialogoForm(Stage parentStage, String caminhoAbsoluto) {
		
		try {
			
			//carregando a nova tela
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoAbsoluto));
			
			Pane pane = loader.load();
			
			// criando um novo palco
			Stage dialagoDtage = new Stage();
			dialagoDtage.setTitle("Entre com os dados do Departamento");
			
			//sua cena vai ser o pane
			dialagoDtage.setScene(new Scene(pane));
			dialagoDtage.setResizable(false);
			
			//palco pai dessa cena
			dialagoDtage.initOwner(parentStage);
			
			//fica travada até você fechar ela
			dialagoDtage.initModality(Modality.WINDOW_MODAL);
			
			//mostra a tela
			dialagoDtage.showAndWait();
			
		} catch (IOException e) {
			Alertas.showAlert("IOException", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
		
	}

}
