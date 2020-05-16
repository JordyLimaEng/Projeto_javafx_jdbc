package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	private synchronized void loadView(String absName) {//synchronized garante que as threads da GUI nao sejam encerradas inesperadamente
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();//pega o 1o elemento da view principal
			
			Node mainMenu = mainVBox.getChildren().get(0);//pega o primeiro filho da VBox
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregara view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String absName) {//synchronized garante que as threads da GUI nao sejam encerradas inesperadamente
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();//pega o 1o elemento da view principal
			
			Node mainMenu = mainVBox.getChildren().get(0);//pega o primeiro filho da VBox
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			DepartmentListController controller = loader.getController();//pega a ref para o controller da view
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregara view", e.getMessage(), AlertType.ERROR);
		}
	}

}
