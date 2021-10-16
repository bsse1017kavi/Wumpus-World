package AI;

import gridPackage.Board;
import gridPackage.GridCell;
import gridPackage.GridStatus;

import java.util.ArrayList;

public class AI {
    public Board board = new Board();
    private  Board actualBoard;
    int x = 0, y = 0;

    public AI(Board actualBoard) {
        this.actualBoard = actualBoard;
    }

    //    GridCell cell;
//
//    public AI(GridCell cell){
//        this.cell = cell;
//    }

    public GridCell findMove(int x, int y)
    {
        ArrayList<GridCell> cells = board.getAdjacentCells(x, y);

        for(GridCell cell: cells)
        {
            System.out.println(cell.coordinate);
        }

        System.out.println();

        for(GridCell cell: cells)
        {
            setDangerScore(cell);
        }

        double min = 2;

        GridCell minCell = cells.get(0);

        for(GridCell cell: cells)
        {
            if(cell.dangerScore < min)
            {
                min = cell.dangerScore;
                minCell = cell;
            }
        }

        return minCell;
    }

    public void makeMove(int x, int y)
    {
        this.x = x;
        this.y = y;

        board.board[x][y].breeze = actualBoard.board[x][y].breeze;
        board.board[x][y].stench = actualBoard.board[x][y].stench;

        updateCellInfo(board.board[x][y]);
    }

    public Action updateCellInfo(GridCell cell){
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

    public void setDangerScore(GridCell cell)
    {
        ArrayList<GridCell> neighbours = board.getAdjacentCells(cell.coordinate.x, cell.coordinate.y);

        for(GridCell neighbour: neighbours)
        {
            if(neighbour.pit == GridStatus.CONFIRMED)
            {
                cell.dangerScore += 0.25;
            }

            if(neighbour.breeze == GridStatus.CONFIRMED)
            {
                cell.dangerScore += 0.15;
            }

            if(neighbour.pit == GridStatus.PROBABLE)
            {
                cell.dangerScore += 0.15;
            }

            if(neighbour.wumpus == GridStatus.CONFIRMED)
            {
                cell.dangerScore += 0.25;
            }

            if(neighbour.wumpus == GridStatus.PROBABLE)
            {
                cell.dangerScore += 0.15;
            }
        }
    }


    public void printDanger()
    {
        for(int i=0;i<board.size;i++)
        {
            for(int j=0;j<board.size;j++)
            {
//                System.out.print(board.board[i][j].dangerScore);
                System.out.print(board.board[i][j].pit);
                System.out.print("\t");
            }

            System.out.println();
        }
    }



}
