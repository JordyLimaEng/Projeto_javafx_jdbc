package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartamento;
	
	@FXML
	private TableColumn<Department,Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department,String> tableColumnNome;
	
	@FXML
	private Button btNovo;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		createDialogForm("/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		initializeNodes();
	}

	private void initializeNodes() {//padrão para iniciar o comportamento das colunas
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//para que a tabela fique de acordo com a janela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {//atualiza a tabela da view com os valores
		if(service == null) {
			throw new IllegalStateException("Service estava NULL!");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
	}
	
	private void createDialogForm(String absName, Stage parentStage) {//tem q iformar quem foi que criou o dialog
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);//enquanto não fechar, a janela anterior não pode mexer
			dialogStage.showAndWait();
		}catch(IOException e) {
			Alerts.showAlert("IO EXCEPTION", "Erro carregando view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
