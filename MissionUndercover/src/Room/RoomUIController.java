package Room;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Player.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import start_page.StartPage;

public class RoomUIController implements Initializable {
	@FXML
	private Label P1Name;
	@FXML
	public Label P1Des;
	@FXML
	private Label P2Name;
	@FXML
	public Label P2Des;
	@FXML
	private Label P3Name;
	@FXML
	public Label P3Des;
	@FXML
	private Label P4Name;
	@FXML
	public Label P4Des;

	@FXML
	private TextField chatRoomInput;
	@FXML
	private TextArea chatRoom;
	@FXML
	private TextField desInput;
	@FXML
	private Button desInputOK;
	@FXML
	private Label timer;
	@FXML
	private Button readyBtn;
	@FXML
	private Label question_label;
	@FXML
	private Button choiceBtn1;
	@FXML
	private Button choiceBtn2;
	@FXML
	private Button choiceBtn3;
	@FXML
	private Button choiceBtn4;
	@FXML
	private Button finishBtn;
	@FXML
	private Label descriptionLabel;
	ArrayList<Label> nameLabels = new ArrayList<Label>();
	ArrayList<Label> desLabels = new ArrayList<Label>();
	ArrayList<Button> choiceBtns = new ArrayList<Button>();

	private boolean btnShow = false;
    @FXML
    private AnchorPane roomImg;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
   	 	roomImg.getStylesheets().add(getClass().getResource("Room.css").toExternalForm());

		System.out.println("initialize");
		StartPage.page = "Room";
		nameLabels.add(P1Name);
		nameLabels.add(P2Name);
		nameLabels.add(P3Name);
		nameLabels.add(P4Name);

		desLabels.add(P1Des);
		desLabels.add(P2Des);
		desLabels.add(P3Des);
		desLabels.add(P4Des);

		choiceBtns.add(choiceBtn1);
		choiceBtns.add(choiceBtn2);
		choiceBtns.add(choiceBtn3);
		choiceBtns.add(choiceBtn4);
		finishBtn.setVisible(false);
		finishBtn.setDisable(true);

		// P1Name.setText("Charles");
		// P1Des.setText("灰色的");
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Runnable updater = new Runnable() {
						@Override
						public void run() {
							chatRoom.setText(StartPage.player.getChatRoom());
							timer.setText(String.valueOf(StartPage.room.getTime()));
							playerSetting();
							btnSetting();
							descriptionSetting();
							desInputSetting();
							question_label.setText(StartPage.player.getCard());
							if (StartPage.player.getAlive()) {
								if (StartPage.room.getGameStatus().equals("description")) {
									readyBtn.setVisible(false);
									readyBtn.setDisable(true);
									if (!StartPage.player.getDescription().equals("")) {
										descriptionLabel.setVisible(true);
										desInputOK.setVisible(true);
										desInput.setVisible(true);
										descriptionLabel.setDisable(false);
										desInputOK.setDisable(false);
										desInput.setDisable(false);
									}
								} else if (StartPage.room.getGameStatus().contains("WIN")) {
									finishBtn.setVisible(true);
									finishBtn.setDisable(false);
									closeAllActiveObject();
								}
								if (StartPage.room.getGameStatus().equals("vote")) {
									btnShow = true;
								} else
									btnShow = false;
								if (StartPage.room.getGameStatus().equals("finish")) {
									/*
									 * Parent root =
									 * FXMLLoader.load(getClass().getResource("/settlement_Page/SettlementPage.fxml"
									 * )); Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
									 * Scene scene = new Scene(root); stage.setScene(scene); stage.show();
									 */
								}
							} else {

							}

							// System.out.print("running");
						}
					};

					while (StartPage.page.equals("Room")) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException ex) {
							System.out.println("RoomUIController err");
							ex.printStackTrace();
						}
						Platform.runLater(updater);
					}
				}

			}).start();
			;
			/*
			 * Platform.runLater(new Runnable() { //TODO: check if this can be used if the
			 * maybe solve the exception of the thread //new Thread(new Runnable() {
			 * 
			 * @Override public void run() { try { System.out.println("Runlater");
			 * chatRoom.setText(StartPage.player.getChatRoom()); } catch (Exception e) {
			 * System.out.println("RoomUIController error"); } } });//.start();
			 */
		} catch (Exception e) {
			System.out.println("Error out UI");
		}
	}

	private void closeAllActiveObject() {
		desInput.setDisable(true);
		desInput.setVisible(false);
		desInputOK.setDisable(true);
		desInputOK.setVisible(false);
		descriptionLabel.setDisable(true);
		descriptionLabel.setVisible(false);
		for (Button b : choiceBtns) {
			b.setDisable(true);
			b.setVisible(false);
		}
		readyBtn.setDisable(true);
		readyBtn.setVisible(false);
	}

	private void desInputSetting() {
		if (StartPage.room.getGameStatus().equals("description")) {
			desInput.setDisable(false);
			desInputOK.setDisable(false);
			desInput.setVisible(true);
			desInputOK.setVisible(true);
			descriptionLabel.setDisable(false);
			descriptionLabel.setVisible(true);
		}
	}

	@FXML
	private void btnSetting() {
		ArrayList<Boolean> a = StartPage.room.getAlive();
		for (int i = 0; i < choiceBtns.size(); i++) {
			choiceBtns.get(i).setVisible(btnShow && a.get(i));
		}
	}

	@FXML
	private void descriptionSetting() {
		for (int i = 0; i < StartPage.room.getPlayers().size(); i++) {
			desLabels.get(i).setText(StartPage.room.getPlayers().get(i).getDescription());
		}
	}

	@FXML
	private void playerSetting() {
		ArrayList<Player> roomPlayers = StartPage.room.getPlayers();

		for (int i = 0; i < nameLabels.size(); i++) {
			nameLabels.get(i).setText("等待玩家加入");
			if (i < roomPlayers.size()) {
				nameLabels.get(i).setText(roomPlayers.get(i).getName());
			}
		}
	}

	public void chatInput(ActionEvent e) throws IOException {
		System.out.println("ChatInput");
		String input = chatRoomInput.getText();
		// readline from system.in
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// print message to socket
		if (!input.equals("")) {
			chatRoomInput.setText("");
			StartPage.player.sendMessage("Chat/" + StartPage.room.getId());
			StartPage.player.sendMessage(StartPage.player.getName() + ":" + input);
		} else
			chatRoomInput.setText("");

		System.out.println("input : " + input);
	}

	public void desInput(ActionEvent e) {
		if (!desInput.getText().equals("")) {

			StartPage.player.sendMessage("Description/" + StartPage.room.getId());// StartPage.player.getName() + "/" +
																					// desInput.getText());
			StartPage.player.sendMessage(StartPage.player.getName() + ":" + desInput.getText());
			StartPage.player.setDescription(desInput.getText());
			desInput.setText("");
			desInput.setDisable(true);
			desInputOK.setDisable(true);
			desInput.setVisible(false);
			desInputOK.setVisible(false);
			descriptionLabel.setDisable(true);
			descriptionLabel.setVisible(false);
		}

	}

	public void goBackScene(ActionEvent event) throws IOException {
		/*
		 * for(Player i : players) { if(i.getName().equals("Charles")) {
		 * players.remove(i); // check if it is removed correctly break; } }
		 */
		System.out.println("go back");
		Parent root = FXMLLoader.load(getClass().getResource("/RoomChoice/RoomChoice.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		System.out.println("switch to scene RoomList");
		StartPage.player.sendMessage("LeaveRoom/" + StartPage.room.getId());
		// StartPage.player.sendMessage("GetRooms");
	}

	public void ready(ActionEvent e) {
		/*
		 * boolean r = false; if (StartPage.player.getReady()) r = false; else r = true;
		 */
		System.out.print("readybtn push");
		StartPage.player.ready();
		if (StartPage.player.getReady()) {
			System.out.println(":ready");
			readyBtn.setText("取消準備");
			StartPage.player.sendMessage("ready/" + StartPage.room.getId() + "/" + StartPage.player.getName());

		} else {
			System.out.println(":unready");
			readyBtn.setText("準備");
			StartPage.player.sendMessage("cancelready/" + StartPage.room.getId() + "/" + StartPage.player.getName());

		}
		return;
	}

	public void useItemHunter(ActionEvent e) {
		int[] items = StartPage.player.getItems();
		StartPage.player.useItem("hunter");
		StartPage.player.sendMessage("UseItem:hunter" + String.valueOf(items[0]) + StartPage.player.getAccount());
	}

	public void useItemTime(ActionEvent e) {
		int[] items = StartPage.player.getItems();
		StartPage.player.useItem("timer");
		StartPage.player.sendMessage("UseItem:hunter" + String.valueOf(items[1]) + StartPage.player.getAccount());
	}

	public void vote1(ActionEvent e) {
		StartPage.player.sendMessage("vote:1:" + StartPage.room.getId());
		for (Button b : choiceBtns) {
			b.setVisible(false);
			b.setDisable(true);
		}
	}

	public void vote2(ActionEvent e) {
		StartPage.player.sendMessage("vote:2:" + StartPage.room.getId());
		for (Button b : choiceBtns) {
			b.setVisible(false);
			b.setDisable(true);
		}
	}

	public void vote3(ActionEvent e) {
		StartPage.player.sendMessage("vote:3:" + StartPage.room.getId());
		for (Button b : choiceBtns) {
			b.setVisible(false);
			b.setDisable(true);
		}
	}

	public void vote4(ActionEvent e) {
		StartPage.player.sendMessage("vote:4:" + StartPage.room.getId());
		for (Button b : choiceBtns) {
			b.setVisible(false);
			b.setDisable(true);
		}
	}

	public void finish(ActionEvent e) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/settlement_page/SettlementPage.fxml"));
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
