package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller)->{//passa como parametro uma função de inicializaçã lambda
			controller.setSellerService(new SellerService());
			controller.updateTableView();
	});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
//		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller)->{//passa como parametro uma função de inicializaçã lambda
//				controller.setDepartmentService(new DepartmentService());
//				controller.updateTableView();
//		});
	
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml", x -> {});//como não precisa executar nada, a função lambda envia parametro vazio
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	//necessário tornar genérica para não precisar ficar criando várias loadview
	private synchronized <T> void loadView(String absName, Consumer<T> initializingAction) {//synchronized garante que as threads da GUI nao sejam encerradas inesperadamente
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();//pega o 1o elemento da view principal
			
			Node mainMenu = mainVBox.getChildren().get(0);//pega o primeiro filho da VBox
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController(); // recebe um controller genérico do tipo que receber			
			initializingAction.accept(controller);//inicializa o controller
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}	

}
