package PathFinding;

import java.util.ArrayList;
import java.util.Optional;

public class Square {
    private Status aStatus;
    private final int aColumn;
    private final int aRow;
    private Optional<Integer> movesFromStart = Optional.empty();
    private Optional<Integer> estimatedCostToFinish = Optional.empty();

    private static ArrayList<Square> aSquares = new ArrayList<>();
    private Square(Status pStatus, int pColumn, int pRow){
        aColumn = pColumn;
        aRow = pRow;
        aStatus = pStatus;
    }
    public static Square getSquare(int pRow, int pColumn){
        for(Square square : aSquares){
            if(square.getRow() == pRow && square.getColumn() == pColumn){
                return square;
            }
        }
        Square newSquare = new Square(Status.ACCESSIBLE, pColumn, pRow);
        aSquares.add(newSquare);
        return newSquare;
    }

    public Optional<Integer> getMovesFromStart(){
        return movesFromStart;
    }

    public Optional<Integer> getEstimatedCostToFinish(){
        return estimatedCostToFinish;
    }

    public void setMovesFromStart(int pMoves){
        movesFromStart = Optional.of(pMoves);
    }

    public void setEstimatedCostToFinish(int pCost){
        estimatedCostToFinish = Optional.of(pCost);
    }

    public void makeInaccessible(){
        this.aStatus = Status.INACCESSIBLE;
    }

    public void makeSource(){
        this.aStatus = Status.SOURCE;
    }

    public void considerSquare(){
        this.aStatus = Status.CONSIDERING;
    }

    public void makePath(){
        this.aStatus = Status.PATH;
    }

    public void makeDestination(){
        this.aStatus = Status.DESTINATION;
    }

    public Status getStatus(){
        return aStatus;
    }

    public int getRow(){
        return aRow;
    }

    public int getColumn(){
        return aColumn;
    }
}
