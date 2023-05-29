package RoomChoice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Player.Player;
import Room.Room;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import start_page.StartPage;

public class RoomChoice implements Initializable {

	@FXML
	private TableView<Room> tableView;
	@FXML
	private TableColumn<Room, Integer> people;
	@FXML
	private TableColumn<Room, String> RoomId;
	@FXML
	private TableColumn<Room, String> status;
	private String rid = "";

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		RoomId.setCellValueFactory(new PropertyValueFactory<Room, String>("id"));
		people.setCellValueFactory(new PropertyValueFactory<Room, Integer>("people"));
		status.setCellValueFactory(new PropertyValueFactory<Room, String>("status"));
		StartPage.page = "RoomChoice";
		rid = "";
		// TODO:Get Rooms from server
		StartPage.player.sendMessage("GetRooms");
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					Runnable updater = new Runnable() {
						@Override
						public void run() {
							//System.out.println("start refresh table");
							ObservableList<Room> rooms = tableView.getItems();
							rooms.removeAll(rooms);
							for (Room r : StartPage.rooms) {
								rooms.add(r);
							}
							tableView.setItems(rooms);
							tableView.refresh();
						}
					};
					while (StartPage.page.equals("RoomChoice")) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							System.out.println("roomchoice thread err");
							ex.printStackTrace();
						}
						Platform.runLater(updater);
					}
				}
			}).start();
			/*
			 * Platform.runLater(new Runnable() { // TODO: check if this can be used if the
			 * maybe solve the exception of // the thread // new Thread(new Runnable() {
			 * 
			 * @Override public void run() { int i = 0;
			 * 
			 * try { //while(i<3) { Thread.sleep(3000);
			 * System.out.println("start refresh table"); ObservableList<Room> rooms =
			 * tableView.getItems(); rooms.removeAll(rooms); for (Room r : StartPage.rooms)
			 * { rooms.add(r); } tableView.setItems(rooms); tableView.refresh(); i++; //}
			 * 
			 * } catch (Exception e) { System.out.println("RoomUIControlle1231r error");
			 * e.printStackTrace(); } } });
			 */
		} catch (Exception e) {
			System.out.println("Error out UI");
		}
	}

	@FXML
	public void createRoom(ActionEvent e) throws IOException {

		// Room room = new Room("456","running"); // modify here to set Room status
		// ObservableList<Room> rooms = tableView.getItems();
		// room.addPlayer(new Player("Charles", 1, 1, 1)); // need to be modified
		// rooms.add(room);

		// tableView.setItems(rooms);
		StartPage.player.sendMessage("CreateRoom");
		StartPage.player.sendMessage(StartPage.player);
		Parent root = FXMLLoader.load(getClass().getResource("/Room/Room.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene 2");
		System.out.println("HIHIHI");
	}

	@FXML
	public void addRoom(ActionEvent e) throws IOException {
		// int selectID = tableView.getSelectionModel().getSelectedIndex();
		// Room room = tableView.getItems().get(selectID);
		// room.addPlayer(new Player("Shang",2,2,2));

		// tableView.refresh();
		if (rid != "") {
			StartPage.player.sendMessage("AddRoom:" + rid);
			StartPage.player.sendMessage(StartPage.player);
			Parent root = FXMLLoader.load(getClass().getResource("/Room/Room.fxml"));
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			System.out.println("switch to scene 2");
		}
	}

	public void returnToMainWindow(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mainwindow_page/mainwindow.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene main");

	}

	 public void rowClicked(MouseEvent event) {
		Room r = tableView.getSelectionModel().getSelectedItem();
		rid = r.getId();
		System.out.println("rid = " + rid);
	}
}
