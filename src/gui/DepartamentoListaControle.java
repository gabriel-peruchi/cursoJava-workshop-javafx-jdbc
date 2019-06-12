package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import bd.BdIntegridadeException;
import gui.listeners.DataChangeListener;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Departamento;
import model.servicos.DepartamentoServico;

public class DepartamentoListaControle implements Initializable, DataChangeListener {

	private DepartamentoServico servico;

	@FXML
	private Button botaoNovoDepartamento;

	@FXML
	private TableView<Departamento> tabelaViewDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> tabelaColunaId;

	@FXML
	private TableColumn<Departamento, String> tabelaColunaNome;

	@FXML
	private TableColumn<Departamento, Departamento> tabelaColunaEDIT;

	@FXML
	private TableColumn<Departamento, Departamento> tabelaColunaREMOVE;

	private ObservableList<Departamento> obsList;

	@FXML
	public void onBotaoNovoDepartamentoClick(ActionEvent evento) {

		Stage parentStage = Utils.palcoAtual(evento);

		Departamento departamento = new Departamento();

		criarDialogoForm(parentStage, "/gui/DepartamentoForm.fxml", departamento);

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
		inicializaBotoesEditar();
		inicializaBotoesRemover();

	}

	public void criarDialogoForm(Stage parentStage, String caminhoAbsoluto, Departamento departamento) {

		try {

			// carregando a nova tela
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoAbsoluto));

			Pane pane = loader.load();

			DepartamentoFormControle controlador = loader.getController();
			controlador.setDepartamento(departamento);
			controlador.setDepartamentoServico(new DepartamentoServico());

			// se inscreve para receber os eventos do departamentoForm
			controlador.subscribeDataChangeListener(this);

			controlador.atualizaFormDados();

			// criando um novo palco
			Stage dialagoDtage = new Stage();
			dialagoDtage.setTitle("Entre com os dados do Departamento");

			// sua cena vai ser o pane
			dialagoDtage.setScene(new Scene(pane));
			dialagoDtage.setResizable(false);

			// palco pai dessa cena
			dialagoDtage.initOwner(parentStage);

			// fica travada até você fechar ela
			dialagoDtage.initModality(Modality.WINDOW_MODAL);

			// mostra a tela
			dialagoDtage.showAndWait();

			setDepartamentoServico(servico);

		} catch (IOException e) {
			Alertas.showAlert("IOException", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	public void onDataChange() {

		updateTabelaView();

	}

	private void inicializaBotoesEditar() {

		tabelaColunaEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		tabelaColunaEDIT.setCellFactory(param -> new TableCell<Departamento, Departamento>() {

			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);

				button.setOnAction(
						event -> criarDialogoForm(Utils.palcoAtual(event), "/gui/DepartamentoForm.fxml", obj));
			}
		});
	}

	private void inicializaBotoesRemover() {

		tabelaColunaREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tabelaColunaREMOVE.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}

	private void removeEntity(Departamento departamento) {

		Optional<ButtonType> resultado = Alertas.showConfirmacao("Confirmação", "Deseja deletar o departamento? ");

		if (resultado.get() == ButtonType.OK) {

			if (servico == null) {
				throw new IllegalStateException("O servico esta nulo");
			}

			try {
				servico.remove(departamento);
				updateTabelaView();
			} catch (BdIntegridadeException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}

		}

	}

}
