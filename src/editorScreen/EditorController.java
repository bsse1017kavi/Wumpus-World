package editorScreen;

import AI.AI;
import gridPackage.Board;
import gridPackage.Coordinate;
import gridPackage.GridCell;
import gridPackage.GridStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    @FXML
    GridPane grid;

    @FXML
    AnchorPane anchorPane, pain2, banner;

    @FXML
    Button confirmButton, clearButton, playButton, pauseButton, resetButton;

    @FXML
    TextField pitN, wumpusN, goldN;

    Board board;
    AI ai;
    boolean popUpOpen = false;
    Timeline fiveSecondsWonder;
    boolean paused = false, playing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Coordinate[] pits = {new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(3, 3)};

        board = new Board();
        ai = new AI(board);
        ai.makeMove(0, 0);

        displayBoard(ai);




    }

    private void clearGrid() {
        Node n = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(n);
    }

    private void displayBoard(AI ai) {
        for(int i = 0; i<board.size; i++)
        {
            for(int j = 0; j<board.size; j++)
            {
                addNewElement(new StackPane(), i, j);
            }
        }

        for(int i = 0; i<board.size; i++)
        {
            for(int j = 0; j<board.size; j++)
            {
                if(ai.x == i && ai.y == j)
                    setImage(i, j, "ai.png");

                if(board.board[i][j].wumpus == GridStatus.CONFIRMED)
                {
                    setImage(i, j, "monster2.gif");
                }

                if(board.board[i][j].gold == GridStatus.CONFIRMED)
                {
                    setImage(i, j, "gold2.png", 52, 52);
                }

                if(board.board[i][j].pit == GridStatus.CONFIRMED)
                {
                    setImage(i, j, "hole2.png");
                }

                if(board.board[i][j].breeze == GridStatus.CONFIRMED)
                {
                    setImage(i, j, "breeze.png");
                }

                if(board.board[i][j].stench == GridStatus.CONFIRMED)
                {
                    setImage(i, j, "stench.png", 52, 40);
                }

                if(playing && !ai.board.board[i][j].visited)
                {
                    // setImage(i, j, "fow.png", 57, 84);
                    AnchorPane ap = new AnchorPane();
                    ap.setStyle("-fx-background-color: #000000");
                    ap.setOpacity(0.6);
                    (getNode(i, j)).getChildren().add(ap);
                }
            }
        }

    }

    private void loadPopUp(double x, double y) {
        try {
            int a  = (int) Math.round((y - 143) / 57.25);
            int b = (int) Math.round((x - 200) / 82.4);

            if(a == 0 && b == 0)
                return;

            System.out.println(a + ", " + b);

            AnchorPane n = FXMLLoader.load(getClass().getResource("popUp.fxml"));
            n.setOnMouseClicked(e -> {
                anchorPane.getChildren().remove(anchorPane.getChildren().size()-1);
                popUpOpen = false;
            });
            n.getChildren().get(2).setOnMouseClicked(e -> {
                board.board[a][b].setPit(GridStatus.UNCONFIRMED);
                board.board[a][b].setGold(GridStatus.UNCONFIRMED);
                board.board[a][b].setWumpus(GridStatus.CONFIRMED);
                board.numberOfWumpus+=1;
                board.generateEnvironment();
                clearGrid();
                displayBoard(ai);
            });

            n.getChildren().get(3).setOnMouseClicked(e -> {
                board.board[a][b].setWumpus(GridStatus.UNCONFIRMED);
                board.board[a][b].setGold(GridStatus.UNCONFIRMED);
                board.board[a][b].setPit(GridStatus.CONFIRMED);
                board.generateEnvironment();
                clearGrid();
                displayBoard(ai);
            });

            n.getChildren().get(4).setOnMouseClicked(e -> {
                board.board[a][b].setGold(GridStatus.CONFIRMED);
                board.board[a][b].setWumpus(GridStatus.UNCONFIRMED);
                board.board[a][b].setPit(GridStatus.UNCONFIRMED);
                board.numberOfGold+=1;
                board.generateEnvironment();
                clearGrid();
                displayBoard(ai);
            });

            n.getChildren().get(5).setOnMouseClicked(e -> {
                board.board[a][b].setWumpus(GridStatus.UNCONFIRMED);
                board.board[a][b].setPit(GridStatus.UNCONFIRMED);
                board.board[a][b].setGold(GridStatus.UNCONFIRMED);
                board.generateEnvironment();
                clearGrid();
                displayBoard(ai);
            });

            n.setTranslateX(x - 120);
            n.setTranslateY(y - 90);
            anchorPane.getChildren().add(n);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImage(int i, int j, String fileName) {
        setImage(i, j, fileName, 52, 60);
    }

    private void setImage(int i, int j, String fileName, int height, int width) {
        ImageView imageView = getImageView(fileName, height, width);

        (getNode(i, j)).getChildren().add(imageView);
    }

    private ImageView getImageView(String fileName, int height, int width) {
        ImageView imageView = new ImageView();
        Image ix = new Image(new File("src\\res\\"+fileName).toURI().toString());
        imageView.setImage(ix);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }


    private void addNewElement(Node r, int x, int y) {
        grid.add(r, y, x);
        GridPane.setHalignment(r, HPos.CENTER);
        GridPane.setValignment(r, VPos.CENTER);
    }



    public StackPane getNode (final int row, final int column) {
        return (StackPane) grid.getChildren().get(board.size * row + column + 1);
    }

    @FXML
    private void mouseEntered(MouseEvent e) {

        System.out.println(e.getSceneX());
        System.out.println(e.getSceneY());

        Node node = (Node) e.getPickResult().getIntersectedNode();

        boolean clicked = false;
        StackPane stackPane = new StackPane();

        if(node instanceof StackPane)
        {
            System.out.println("Node");
            System.out.println(node.getId());
            clicked = true;
            stackPane = (StackPane) node;
        }

        if(!clicked) {
            Node p = node.getParent();
            while (p != null && !(p instanceof StackPane))
            {
                System.out.println("Sure");
                System.out.println(p);
                p = p.getParent();
            }
            if(p!=null)
            {
                clicked = true;
                stackPane = (StackPane) p;
            }
        }

        if(clicked)
            {
                System.out.println("Clicked, I guess!");
                openClosePopUp(e, stackPane);
            }


    }

    private void openClosePopUp(MouseEvent e, StackPane pane) {
        if(!popUpOpen)
        {
            loadPopUp(processX(e, pane.getWidth()), processY(e));
            // ((Node)e.getSource()).setStyle("-fx-border-color:red; -fx-border-width:2px;");
            popUpOpen = true;
        }
        else
        {
            anchorPane.getChildren().remove(anchorPane.getChildren().size()-1);
            popUpOpen = false;
        }
    }

    private double processX(MouseEvent e, double width) {
        double x = e.getSceneX();
        x = Math.floor((x - 157) / 82.4) * 82.4 + 157 + width / 2;
        return x;
    }

    private double processY(MouseEvent e) {
        double y = e.getSceneY();
        y = Math.floor((y - 115) / 56.9) * 57.25 + 143;
        return y;
    }

    @FXML
    private void randomRun() {
        System.out.println(pitN.getText().trim() + "asdfasdfasdfasdfasdfasdfasdfasdf");
        int nP = Integer.parseInt(pitN.getText().trim());
        int nG = Integer.parseInt(goldN.getText().trim());
        int nW = Integer.parseInt(wumpusN.getText().trim());
        playing = true;

        banner.getChildren().clear();

        // board = new Board();
        // board.generateRandomBoard(nP, nW, nG);

        board = new Board();
        board.generateRandomBoard(nP, nW, nG);


        AI ai = new AI(board);
        ai.makeMove(0, 0);

        clearGrid();
        displayBoard(ai);

        fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            if(paused)
                                return;

                            if(ai.checkWin())
                            {
                                System.out.println("WIN");
                                banner.getChildren().add(getImageView("win3.gif", 160, 220)); //130, 219
                                fiveSecondsWonder.stop();
                            }
                            else if (ai.score == -1000)
                            {
                                System.out.println("LOSE");
                                banner.getChildren().add(getImageView("loss2.gif", 130, 219)); //130, 219
                                fiveSecondsWonder.stop();
                            }
                            else {
                                ai.playSquidBFS();
                                clearGrid();
                                displayBoard(ai);
                            }

                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    public void clearText(){
        pitN.clear();
        wumpusN.clear();
        goldN.clear();
    }

    @FXML
    public void customGame(){
        playing = true;
        paused = false;
        AI ai = new AI(board);
        ai.makeMove(0, 0);

        clearGrid();
        displayBoard(ai);

        fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        event -> {
                            if(paused)
                                return;

                            if(ai.checkWin())
                            {
                                System.out.println("WIN");
                                banner.getChildren().add(getImageView("win3.gif", 160, 220)); //130, 219
                                fiveSecondsWonder.stop();
                            }
                            else if (ai.score == -1000)
                            {
                                System.out.println("LOSE");
                                banner.getChildren().add(getImageView("loss2.gif", 130, 219)); //130, 219
                                fiveSecondsWonder.stop();
                            }
                            else {
                                ai.playSquidBFS();
                                clearGrid();
                                displayBoard(ai);
                            }

                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    public void pause() {
        paused = !paused;
    }

    @FXML
    public void reset() {

    }
}
