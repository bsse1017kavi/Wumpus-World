package AI;

import gridPackage.Board;
import gridPackage.GridCell;
import gridPackage.GridStatus;

import java.util.ArrayList;

public class AI {
    Board board = new Board();
    int x = 0, y = 0;

//    GridCell cell;
//
//    public AI(GridCell cell){
//        this.cell = cell;
//    }

    public Action makeMove(GridCell cell){
        board.board[x][y] = cell;
        cell.visit();

        ArrayList<GridCell> cells = board.getAdjacentCells(x, y);
        for(GridCell c: cells) {
            if(c.visited)
                continue;

            if(cell.breeze == GridStatus.CONFIRMED && c.pit != GridStatus.NOT_CONTAINS)
                c.pit = GridStatus.PROBABLE;
            else
                c.pit = GridStatus.NOT_CONTAINS;

            if(cell.stench == GridStatus.CONFIRMED && c.wumpus != GridStatus.NOT_CONTAINS)
                c.wumpus = GridStatus.PROBABLE;
            else
                c.wumpus = GridStatus.NOT_CONTAINS;
        }

        // count the number of cells where wumpus or pit is probable. If probable == 1 then make it confirmed.








        return null;
    }




}
