package AI;

import gridPackage.Board;
import gridPackage.Coordinate;
import gridPackage.GridCell;
import gridPackage.GridStatus;

import java.util.ArrayList;
import java.util.Stack;

public class AI {
    public Board board = new Board();
    private  Board actualBoard;
    public int x = 0, y = 0;
    Stack<Coordinate> path = new Stack<>();
    public int score = 0;

    private int winScore;

    public int wumpusNumber;
    public int goldNumber;

    boolean arrowAvailable = true;

    public AI(Board actualBoard) {
        this.actualBoard = actualBoard;
        wumpusNumber = actualBoard.numberOfWumpus;
        goldNumber = actualBoard.numberOfGold;

        winScore = 1000*goldNumber;

    }


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

    public void playSquidBFS()
    {
        if(!path.empty()){
            makeMove(path.pop());
            return;
        }

        ArrayList<WrappedCell> queue = new ArrayList<>();

        queue.add(new WrappedCell(board.board[x][y], 0));

        for(int i = 0; i<queue.size(); i++)
        {
            GridCell cell = queue.get(i).cell;

            ArrayList<GridCell> neighbours = board.getAdjacentCells(cell.coordinate.x, cell.coordinate.y);

            for(GridCell neighbour: neighbours)
            {
                if(!neighbour.visited && neighbour.safe)
                {
                    WrappedCell box = new WrappedCell(neighbour, i);

                    while (!box.cell.coordinate.equals(new Coordinate(x, y))) {
                        path.push(box.cell.coordinate);
                        box = queue.get(box.parentIndex);
                    }

                    makeMove(path.pop());
                    return;
                }

                if(neighbour.visited && !queue.contains(neighbour))
                {
                    queue.add(new WrappedCell(neighbour, i));
                }

            }

        }

        //check wumpus shootable spot
    }

    private void makeMove(GridCell neighbour) {
        makeMove(neighbour.coordinate.x, neighbour.coordinate.y);
    }

    private void makeMove(Coordinate neighbour) {
        makeMove(neighbour.x, neighbour.y);
    }

//    public void playGameBFS()
//    {
//        Queue<GridCell> queue = new ArrayDeque<>();
//
//        //makeMove(0,0);
//
//        ((ArrayDeque<GridCell>) queue).push(board.board[0][0]);
//
//        ArrayList<GridCell> unsafeMoves = new ArrayList<>();
//
//        while (queue.size() != 0)
//        {
//            int safeCounter = 0;
//
//            GridCell cell = queue.poll();
//
//            makeMove(cell.coordinate.x, cell.coordinate.y);
//
//            ArrayList<GridCell> neighbours = board.getAdjacentCells(cell.coordinate.x, cell.coordinate.y);
//
//            for(GridCell neighbour: neighbours)
//            {
//                if(!neighbour.visited && !queue.contains(neighbour) && neighbour.safe)
//                {
//                    neighbour.visit();
//                    ((ArrayDeque<GridCell>) queue).push(neighbour);
//                }
//
//                else if(!neighbour.visited && !queue.contains(neighbour))
//                {
//                    unsafeMoves.add(cell);
//                }
//            }
//
//        }
//
//        for(GridCell cell: unsafeMoves)
//        {
//            makeMove(cell.coordinate.x, cell.coordinate.y);
//        }
//    }

    public void searchAndShoot()
    {
        String direction = wumpusShootable(new Coordinate(this.x, this.y));

        if(direction.equals("No"))
        {
            return;
        }

        else
        {
            if(arrowAvailable)
            {
                shootArrow(direction);
                arrowAvailable = false;
            }

        }
    }

    public void makeMove(int x, int y)
    {
        this.x = x;
        this.y = y;

        board.board[x][y].breeze = actualBoard.board[x][y].breeze;
        board.board[x][y].stench = actualBoard.board[x][y].stench;
        board.board[x][y].wumpus = actualBoard.board[x][y].wumpus;
        board.board[x][y].pit = actualBoard.board[x][y].pit;
        board.board[x][y].gold = actualBoard.board[x][y].gold;

        updateCellInfo(board.board[x][y]);

        System.out.println(board.board[x][y].coordinate);

        printDanger();

        searchAndShoot();

        System.out.println();
    }

    public boolean checkWin()
    {
        if(score==winScore)
        {
            return true;
        }

        return false;
    }

    public void updateCellInfo(GridCell cell){
        board.board[x][y] = cell;
        score += cell.visit();

        if(cell.visit()==1000)
        {
            board.board[x][y].gold = GridStatus.NOT_CONTAINS;
            actualBoard.board[x][y].gold = GridStatus.NOT_CONTAINS;
        }

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

            if(c.pit == GridStatus.NOT_CONTAINS && c.wumpus == GridStatus.NOT_CONTAINS)
            {
                c.safe = true;
            }

        }

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
                System.out.print(board.board[i][j].wumpus);
                System.out.print("\t");
            }

            System.out.println();
        }
    }


    public void shootArrow(String direction)
    {
        Coordinate arrowPosition = new Coordinate(this.x,this.y);

        boolean hit = false;

        if(direction.equals("up"))
        {
            while(arrowPosition.x >= 0)
            {
                System.out.println("Arrow position: " + arrowPosition);

                if(wumpusCheck(arrowPosition))
                {
                    System.out.println("Wumpus Died!");
                    hit = true;

                    //make adjacent cell wumpus safe
                    board.board[x-1][y].wumpus = GridStatus.NOT_CONTAINS;

                    break;
                }

                arrowPosition.x--;
            }

            if(!hit)
            {
                arrowPosition = new Coordinate(this.x,this.y);

                while(arrowPosition.x >= 0)
                {
                    board.board[arrowPosition.x][arrowPosition.y].wumpus = GridStatus.NOT_CONTAINS;
                    arrowPosition.x--;
                }
            }
        }

        else if(direction.equals("down"))
        {
            while(arrowPosition.x < board.size)
            {
                System.out.println("Arrow position: " + arrowPosition);

                if(wumpusCheck(arrowPosition))
                {
                    System.out.println("Wumpus Died!");
                    hit = true;

                    //make adjacent cell wumpus safe
                    board.board[x+1][y].wumpus = GridStatus.NOT_CONTAINS;
                    break;
                }

                arrowPosition.x++;
            }

            if(!hit)
            {
                arrowPosition = new Coordinate(this.x,this.y);

                while(arrowPosition.x < board.size)
                {
                    board.board[arrowPosition.x][arrowPosition.y].wumpus = GridStatus.NOT_CONTAINS;
                    arrowPosition.x++;
                }
            }
        }

        else if(direction.equals("left"))
        {
            while(arrowPosition.y >= 0)
            {
                System.out.println("Arrow position: " + arrowPosition);

                if(wumpusCheck(arrowPosition))
                {
                    System.out.println("Wumpus Died!");
                    hit = true;

                    //make adjacent cell wumpus safe
                    board.board[x][y-1].wumpus = GridStatus.NOT_CONTAINS;
                    break;
                }

                arrowPosition.y--;
            }

            if(!hit)
            {
                arrowPosition = new Coordinate(this.x,this.y);

                while(arrowPosition.y >= 0)
                {
                    board.board[arrowPosition.x][arrowPosition.y].wumpus = GridStatus.NOT_CONTAINS;
                    arrowPosition.y--;
                }
            }
        }

        else if(direction.equals("right"))
        {
            while(arrowPosition.y < board.size)
            {
                System.out.println("Arrow position: " + arrowPosition);

                if(wumpusCheck(arrowPosition))
                {
                    System.out.println("Wumpus Died!");
                    hit = true;

                    //make adjacent cell wumpus safe
                    board.board[x][y+1].wumpus = GridStatus.NOT_CONTAINS;
                    break;
                }

                arrowPosition.y++;
            }

            if(!hit)
            {
                arrowPosition = new Coordinate(this.x,this.y);

                while(arrowPosition.y < board.size)
                {
                    board.board[arrowPosition.x][arrowPosition.y].wumpus = GridStatus.NOT_CONTAINS;
                    arrowPosition.y++;
                }
            }
        }
    }

    private boolean wumpusCheck(Coordinate coordinate)
    {
        if(actualBoard.board[coordinate.x][coordinate.y].wumpus == GridStatus.CONFIRMED)
        {
            actualBoard.board[coordinate.x][coordinate.y].wumpus = GridStatus.NOT_CONTAINS;
            actualBoard.generateEnvironment();
            wumpusNumber--;
            return true;
        }

        return false;
    }

    private String wumpusShootable(Coordinate coordinate)
    {
        Coordinate wumpusLocation;

        for(int i=0; i< board.size;i++)
        {
            for(int j=0;j< board.size;j++)
            {
                if(board.board[i][j].wumpus == GridStatus.CONFIRMED)
                {
                    wumpusLocation = new Coordinate(i,j);

                    if(wumpusLocation.x == coordinate.x)
                    {
                        if(wumpusLocation.y < coordinate.y)
                        {
                            return "left";
                        }

                        else
                        {
                            return "right";
                        }
                    }

                    else if(wumpusLocation.y == coordinate.y)
                    {
                        if(wumpusLocation.x < coordinate.x)
                        {
                            return "up";
                        }

                        else
                        {
                            return "down";
                        }
                    }
                }
            }

            for(int j=0;j< board.size;j++)
            {
                if(board.board[i][j].wumpus == GridStatus.PROBABLE)
                {
                    wumpusLocation = new Coordinate(i,j);

                    if(wumpusLocation.x == coordinate.x)
                    {
                        if(wumpusLocation.y < coordinate.y)
                        {
                            return "left";
                        }

                        else
                        {
                            return "right";
                        }
                    }

                    else if(wumpusLocation.y == coordinate.y)
                    {
                        if(wumpusLocation.x < coordinate.x)
                        {
                            return "up";
                        }

                        else
                        {
                            return "down";
                        }
                    }
                }
            }
        }

        return "No";
    }

}

class WrappedCell {
    GridCell cell;
    int parentIndex;

    public WrappedCell(GridCell cell, int parentIndex) {
        this.cell = cell;
        this.parentIndex = parentIndex;
    }
}
