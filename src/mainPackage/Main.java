package mainPackage;

import AI.AI;
import gridPackage.Board;
import gridPackage.Coordinate;

import java.util.ArrayList;


public class Main
{
    public static void main(String[] args)
    {

        Coordinate[] pits = {new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(3, 3)};

        Board board = new Board();
        board.generateTestBoard(new Coordinate(2, 0), new Coordinate(2, 1), pits);
        board.printBoard();

        // AI ai = new AI();

    }
}
