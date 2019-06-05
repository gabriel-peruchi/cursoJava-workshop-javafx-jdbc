package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewControle implements Initializable{

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
		System.out.println("Teste departamento click");
	}

	@FXML
	public void onMenuItemSobreClick() {
		System.out.println("Teste sobre click");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}

}
