package sample;

import gridPackage.Board;
import gridPackage.Coordinate;
import gridPackage.GridStatus;
import javafx.animation.TranslateTransition;
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

        System.out.println();

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
}
