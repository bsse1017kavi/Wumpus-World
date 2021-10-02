package mainPackage;

import gridPackage.Board;


public class Main
{
    public static void main(String[] args)
    {

        Board board = new Board();
        board.generateRandomBoard(3, 1, 1);
        board.printBoard();

    }
}
