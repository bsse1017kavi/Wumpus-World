package mainPackage;

import AI.AI;
import gridPackage.Board;
import gridPackage.Coordinate;


public class Main
{
    public static void main(String[] args)
    {

        Coordinate[] pits = {new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(3, 3)};

        Board board = new Board();
        board.generateTestBoard(new Coordinate(2, 0), new Coordinate(2, 1), pits);
        board.printBoard();

        System.out.println();

//        ArrayList<GridCell> moves = board.getAdjacentCells(1,2);
//
//        for(GridCell move: moves)
//        {
//            System.out.println(move.coordinate);
//        }

         AI ai = new AI(board);

         ai.makeMove(0,0);

         ai.shootArrow("down");

//         ai.makeMove(0, 0);
//         ai.playSquidBFS();

         // ai.playGameBFS();
//
//         //ai.printDanger();
//
//         ai.makeMove(0,0);
//         ai.printDanger();
//
//         System.out.println();

//         ai.makeMove(0,1);
//         ai.printDanger();
//         ai.makeMove(1,1);
//         ai.makeMove(2,1);
//         ai.makeMove(3,1);
//         ai.makeMove(3,0);

        //ai.printDanger();

         //System.out.println(ai.findMove(3,0).coordinate);

         //System.out.println(ai.board.board[2][0].dangerScore);
         //System.out.println(ai.board.board[3][1].dangerScore);

        //ai.printDanger();



    }
}
