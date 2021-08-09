package application;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mines.Mines;

public class controller implements EventHandler<MouseEvent> {
	private Mines game;
	HashMap<Button, ArrayList<Integer>> b;
	private Button[][] a;
	boolean finish = false;
	private int h, w, m, fc,wf=0;
	GridPane gp = new GridPane();
	MediaPlayer mp;

	@FXML
	private VBox vbox;

	@FXML
	private HBox hbox;

	@FXML
	private TextField Height;

	@FXML
	private TextField Width;

	@FXML
	private TextField Mines;

	@FXML
	private Label info;

	@FXML
	private Button restart;

	@FXML
	private Label counter;

	@FXML
	void clicked(ActionEvent event) {
		try {
			h = Integer.parseInt(this.Height.getText());
			w = Integer.parseInt(this.Width.getText());
			m = Integer.parseInt(this.Mines.getText());
			if (m > h * w) {
				m = (h * w) - 1;
			}
			if(h<2 || w<2 || m<1) {
				throw new Exception();
			}
		} catch (Exception e) {
			this.info.setText("invalid input");
			this.info.setTextFill(Color.RED);
			this.vbox.getChildren().add(info);
			return;
		}
		fc=m;
		this.counter.setText(String.valueOf(fc));
		game = new Mines(h, w, m);
		this.b = new HashMap<Button, ArrayList<Integer>>();
		this.a = new Button[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				Button button = new Button();
				ArrayList<Integer> coord = new ArrayList<Integer>();
				coord.add(i);
				coord.add(j);
				b.put(button, coord);
				this.a[i][j] = button;
				this.a[i][j].setStyle("-fx-background-image: url('file:images/block.png')");
				button.setMinWidth(30);
				button.setMinHeight(30);
				gp.add(button, j, i);
				button.setOnMouseClicked(this);
			}
		}
		
		gp.setAlignment(Pos.CENTER);
		this.vbox.getChildren().add(gp);
		this.vbox.setAlignment(Pos.CENTER);
	}
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (finish == true) {
				return;
			}

			int i = this.b.get((Button) (event.getSource())).get(0);
			int j = this.b.get((Button) (event.getSource())).get(1);
			if (this.game.get(i, j).equals("F")) {
				return;

			}

			if (game.getMines()[i][j].getMine()) {
				finish = true;

				game.setShowAll(true);
				seen(a);
				mp =new MediaPlayer(new Media(getClass().getClassLoader().getResource("boom.mp3").toString()));
				mp.setAutoPlay(true);
				mp.setVolume(0.5);
				this.a[i][j].setStyle("-fx-background-image: url('file:images/boom.png')");
				this.info.setText("boom! better luck next time");
				this.info.setFont(new Font(30));
				this.info.setTextFill(Color.RED);
				this.vbox.getChildren().add(info);
				return;
			}
			game.open(i, j);
			seen(a);
			if (game.isDone()&&wf==0) {
				game.setShowAll(true);
				seen(a);
				mp =new MediaPlayer(new Media(getClass().getClassLoader().getResource("win.mp3").toString()));
				mp.setAutoPlay(true);
				mp.setVolume(0.8);
				finish = true;
				this.info.setText("congrats you win");
				this.info.setFont(new Font(30));
				this.info.setTextFill(Color.BLUE);
				this.vbox.getChildren().add(info);
				return;

			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
			if (!finish) {
				int i = this.b.get((Button) (event.getSource())).get(0);
				int j = this.b.get((Button) (event.getSource())).get(1);
				game.toggleFlag(i, j);
				if(game.get(i, j).equals("F")&&game.getMines()[i][j].getMine()==false) {
					wf++;
				}else if(game.getMines()[i][j].getMine()==false && game.get(i, j).equals(".")) {
					wf--;
				}
				System.out.println(game.get(i, j));
				if (game.get(i, j).equals("F")) {
					this.a[i][j].setStyle("-fx-background-image: url('file:images/flag.png')");
					fc--;
				} else if (game.get(i, j).equals(".")) {
					fc++;
					this.a[i][j].setStyle("-fx-background-image: url('file:images/block.png')");
				}
				this.counter.setText(String.valueOf(fc));
			}
		}

	}

	@FXML
	void regame(ActionEvent event) {
		finish = false;
		gp.getChildren().clear();
		this.info.setText("");
		wf=0;
		clicked(event);
	}
	
	void seen(Button[][] a) {
		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[x].length; y++) {
				if (game.get(x, y).equals(".")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/block.png')");
				}else if (game.get(x, y).equals(" ")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/0.png')");
				}else if (game.get(x, y).equals("1")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/1.png')");
				}else if (game.get(x, y).equals("2")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/2.png')");
				}else if (game.get(x, y).equals("3")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/3.png')");
				}else if (game.get(x, y).equals("4")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/4.png')");
				}else if (game.get(x, y).equals("5")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/5.png')");
				}else if (game.get(x, y).equals("6")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/6.png')");
				}else if (game.get(x, y).equals("7")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/7.png')");
				}else if (game.get(x, y).equals("8")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/8.png')");
				}else if (game.get(x, y).equals("X")) {
					this.a[x][y].setStyle("-fx-background-image: url('file:images/mine.png')");
				}
			}
		}
	}
   
}