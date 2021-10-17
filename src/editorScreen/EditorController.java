package editorScreen;

import AI.AI;
import gridPackage.Board;
import gridPackage.Coordinate;
import gridPackage.GridStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    AnchorPane anchorPane;

    Board board;
    boolean popUpOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Coordinate[] pits = {new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(3, 3)};

        board = new Board();
        board.generateTestBoard(new Coordinate(2, 0), new Coordinate(2, 1), pits);

        AI ai = new AI(board);
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
            }
        }

    }

    private void loadPopUp(double x, double y) {
        try {
            Node n = FXMLLoader.load(getClass().getResource("popUp.fxml"));
            n.setOnMouseClicked(e -> {
                anchorPane.getChildren().remove(anchorPane.getChildren().size()-1);
                popUpOpen = false;
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

    private ImageView getImageView(String fileName, int height, int width) {
        ImageView imageView = new ImageView();
        Image ix = new Image(new File("F:\\IIT\\Projects\\Java\\onePlus\\src\\res\\"+fileName).toURI().toString());
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

        if(node instanceof StackPane)
            clicked = true;

        Node p = node.getParent();
        while (p != null && !(p instanceof StackPane))
            p = p.getParent();

        if(p!=null)
            clicked = true;

        if(clicked)
            {
                openClosePopUp(e);
            }


    }

    private void openClosePopUp(MouseEvent e) {
        if(!popUpOpen)
        {
            loadPopUp(processX(e), processY(e));
            // ((Node)e.getSource()).setStyle("-fx-border-color:red; -fx-border-width:2px;");
            popUpOpen = true;
        }
        else
        {
            anchorPane.getChildren().remove(anchorPane.getChildren().size()-1);
            popUpOpen = false;
        }
    }

    private double processX(MouseEvent e) {
        double x = e.getSceneX();
        x = Math.floor((x - 157) / 82.4) * 82.4 + 200;
        return x;
    }

    private double processY(MouseEvent e) {
        double y = e.getSceneY();
        y = Math.floor((y - 115) / 56.9) * 57.25 + 143;
        return y;
    }
}
