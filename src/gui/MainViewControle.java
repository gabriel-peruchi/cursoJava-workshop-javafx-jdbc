package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.servicos.DepartamentoServico;

public class MainViewControle implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemVendedorClick() {
		System.out.println("Teste vendedor click");
	}

	@FXML
	public void onMenuItemDepartamentoClick() {
		
		//mando uma funcao como parametro (expressoes lambda)
		loadView("/gui/ListaDepartamento.fxml", (DepartamentoViewControle controle) ->{
			controle.setDepartamentoServico(new DepartamentoServico());
			controle.updateTabelaView();
			
		});
	}

	@FXML
	public void onMenuItemSobreClick() {

		loadView("/gui/Sobre.fxml",  x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	//mostrar uma tela (recebe uma funcao de parametro, e executa a aprtir do controlador(tela) que vier)
	private synchronized <T> void loadView(String caminhoAbsoluto,  Consumer<T> funcaoDeInicializacao) {

		try {
			
			//carrega a nova tela
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoAbsoluto));
			VBox newVBox = loader.load();

			//captura a cena principal
			Scene mainScene = Main.getMainScene();
			
			//captura o Vbox da tela principal
			VBox mainVBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//guarda o menu principal do vbox da tela inicial
			Node mainMenu =  mainVBox.getChildren().get(0);
			
			//limpa os filhos do VBOX da tela principal
			mainVBox.getChildren().clear();
			
			//add o menu principla no vbox principal
			mainVBox.getChildren().add(mainMenu);
			
			//add os filhos do vbox sobre no vbox da tel principal
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controle = loader.getController();
			funcaoDeInicializacao.accept(controle);
			
			

		} catch (IOException e) {
			Alertas.showAlert("IO Exception", "Erro carrgando a página", e.getMessage(), AlertType.ERROR);
		}
	}

	
	
}
