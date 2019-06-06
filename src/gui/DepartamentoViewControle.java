package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Departamento;

public class DepartamentoViewControle implements Initializable {

	@FXML
	private Button botaoNovoDepartamento;
	
	@FXML
	private TableView<Departamento> tabelaViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tabelaColunaId;
	
	@FXML
	private TableColumn<Departamento, String> tabelaColunaNome;
	
	@FXML
	public void onBotaoNovoDepartamentoClick(){
	
		System.out.println("Novo departamento!");
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

	
	
}
