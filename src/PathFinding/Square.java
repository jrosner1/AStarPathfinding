package PathFinding;

import java.util.ArrayList;
import java.util.Optional;

public class Square {
    private Status aStatus;
    private final int aColumn;
    private final int aRow;
    private Optional<Integer> movesFromStart = Optional.empty();
    private Optional<Integer> estimatedCostToFinish = Optional.empty();
    private final static ArrayList<Square> aSquares = new ArrayList<>();

    /**
     * A constructor that creates a square. Note that squares follow the flyweight design pattern.
     * @param pColumn The column of the square to be created.
     * @param pRow the row of the square to be created.
     */
    private Square(int pColumn, int pRow){
        aColumn = pColumn;
        aRow = pRow;
        aStatus = Status.ACCESSIBLE;
    }

    /**
     * A method to get the square at a certain position. Creates a square at that position if one does not exist.
     * @param pRow The row of the square.
     * @param pColumn The column of the square.
     * @return Teh square that has either been retrieved of created.
     */
    public static Square getSquare(int pRow, int pColumn){
        for(Square square : aSquares){
            if(square.getRow() == pRow && square.getColumn() == pColumn){
                return square;
            }
        }
        Square newSquare = new Square(pColumn, pRow);
        aSquares.add(newSquare);
        return newSquare;
    }

    /**
     * A method to get the distance of th square from the start.
     * @return The Optional value of the distance.
     */
    public Optional<Integer> getMovesFromStart(){
        return movesFromStart;
    }

    /**
     * A method to get hte distance of the square from the finish.
     * @return The Optional value of the distance.
     */
    public Optional<Integer> getEstimatedCostToFinish(){
        return estimatedCostToFinish;
    }

    /**
     * A method to set the number of moves from the start.
     * @param pMoves The number of moves from the source square.
     */
    public void setMovesFromStart(int pMoves){
        movesFromStart = Optional.of(pMoves);
    }

    /**
     * A method to reset the square to an accessible square with empty movesFromStart and
     * estimatedCostToFinish.
     */
    public void resetSquare(){
        this.setStatus(Status.ACCESSIBLE);
        movesFromStart = Optional.empty();
        estimatedCostToFinish = Optional.empty();
    }

    /**
     * A method to set the number of moves from the finish.
     * @param pCost The number of moves from the destination square.
     */
    public void setEstimatedCostToFinish(int pCost){
        estimatedCostToFinish = Optional.of(pCost);
    }

    /**
     * A method to set the status of a square.
     * @param pStatus The desired status to update the square to.
     */
    public void setStatus(Status pStatus){
        aStatus = pStatus;
    }

    /**
     * A method to get the status of a square.
     * @return The status of the implicit square.
     */
    public Status getStatus(){
        return aStatus;
    }
    //Getter for the row value
    public int getRow(){
        return aRow;
    }
    //Getter for the column value
    public int getColumn(){
        return aColumn;
    }
}
