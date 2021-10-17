package sample;

import AI.AI;
import AI.Action;
import gridPackage.Board;
import gridPackage.Coordinate;
import gridPackage.GridStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    GridPane grid;

    Board board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Coordinate[] pits = {new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(3, 3)};

        board = new Board();
        board.generateTestBoard(new Coordinate(2, 0), new Coordinate(2, 1), pits);
        board.printBoard();

        AI ai = new AI(board);
        ai.makeMove(0, 0);

        displayBoard(ai);

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        event -> {
                            if(ai.score == 1000)
                            {
                                System.out.println("WIN");
                            }
                            else if (ai.score == -1000)
                                System.out.println("Lost");
                            else {
                                ai.playSquidBFS();
                                clearGrid();
                                displayBoard(ai);
                            }

                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

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
                    setImage(i, j, "monster.gif");
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
            }
        }
    }

    private void setImage(int i, int j, String fileName) {
        setImage(i, j, fileName, 52, 60);
    }

    private void setImage(int i, int j, String fileName, int height, int width) {
        ImageView imageView = new ImageView();
        Image ix = new Image(new File("F:\\IIT\\Projects\\Java\\onePlus\\src\\res\\"+fileName).toURI().toString());
        imageView.setImage(ix);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        (getNode(i, j)).getChildren().add(imageView);

//        if(getNode(i, j).getChildren().size() == 0) {
//            System.out.println("True");
//            HBox hBox = new HBox();
//            hBox.setMaxHeight(height);
//            hBox.setMinHeight(height);
//            hBox.setMaxWidth(width);
//            hBox.setMinWidth(width);
//            hBox.getChildren().add(imageView);
//            (getNode(i, j)).getChildren().add(hBox);
//        }
//        else
//            ((HBox) (getNode(i, j).getChildren().get(0)) ).getChildren().add(imageView);


        // addElement(imageView, i, j);
    }

    private void addToCell(Node r, int x, int y) {
        getNode(x, y).getChildren().add(r);
    }

    private void addNewElement(Node r, int x, int y) {
        grid.add(r, y, x);
        GridPane.setHalignment(r, HPos.CENTER);
        GridPane.setValignment(r, VPos.CENTER);
    }



    public StackPane getNode (final int row, final int column) {
        return (StackPane) grid.getChildren().get(board.size * row + column + 1);
    }

    /*private void shit(int x1, int y1, int x2, int y2) {




        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(3));
        translateTransition.setToX(20);
        translateTransition.setToY(20);
        translateTransition.setNode(r);
        translateTransition.play();
        //when translation is finished remove from original location
        //add to desired location and set translation to 0
        translateTransition.setOnFinished(e->{
            grid.getChildren().remove(r);
            r.setTranslateX(0); r.setTranslateY(0);
            grid.add(r, 1, 4);
        });

    }*/
}
