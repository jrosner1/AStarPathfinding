package PathFinding;
import java.util.ArrayList;

public class AStar {
    Square aSource;
    Square aDestination;
    ArrayList<Square> openList;
    ArrayList<Square> closedList;
    Square[][] aParents;

    /**
     * A method to create an AStar object that will be capable of solving for the shortest path.
     * @param pSource The source of the field.
     * @param pDestination The destination of the field.
     * @param numColumns The number of columns that the field has.
     * @param numRows The number of rows that the field has.
     */
    public AStar(Square pSource, Square pDestination, int numColumns, int numRows) {
        //Source and destination are stored (column, row)
        aSource = pSource;
        aDestination = pDestination;
        //Destination will be put in the open list first, so it's moves from start must be set to 0.
        pSource.setMovesFromStart(0);
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        aParents = new Square[numColumns][numRows];
    }

    /**
     * A method to solve for the shortest path.
     * This method will alter the parents array so that traceBackSolution will be able to identify the path.
     */
    public void solve(){
        openList.add(aSource);
        while(!openList.isEmpty()){
            Square closestSquare = findSmallestFInOpenList();
            if(closestSquare.getRow() == aDestination.getRow() && closestSquare.getColumn() == aDestination.getColumn()){
                return;
            }
            assert closestSquare.getMovesFromStart().isPresent();
            openList.remove(closestSquare);
            closedList.add(closestSquare);
            ArrayList<Square> adjacentSquares = getAdjacentSquares(closestSquare);
            for(Square square : adjacentSquares){
                if (!square.getStatus().equals(Status.INACCESSIBLE)) {
                    int distanceFromStart = closestSquare.getMovesFromStart().get()+1;
                    //If the estimated cost has not been set, this is the first time visiting the square.
                    if(square.getEstimatedCostToFinish().isEmpty()) {
                        int predictedDistanceToDestination = getPredictedDistanceFromDestination(square);
                        square.setEstimatedCostToFinish(predictedDistanceToDestination);
                        square.setMovesFromStart(distanceFromStart);
                        openList.add(square);
                        aParents[square.getColumn()][square.getRow()] = closestSquare;
                        //If the estimated cost is not empty then the square is already in the open list
                    }else{
                        //Safe assert because the square has already been visited
                        assert square.getMovesFromStart().isPresent();
                        int existingDistanceFromStart = square.getMovesFromStart().get();
                        if(existingDistanceFromStart > distanceFromStart){
                            square.setMovesFromStart(distanceFromStart);
                            aParents[square.getColumn()][square.getRow()] = closestSquare;
                        }
                    }
                }
            }
        }
    }

    /**
     * A method to solve for the solution after aParents has been updated by solve.
     * @return An arraylist containing the path solution.
     */
    public ArrayList<Square> traceBackSolution(){
        ArrayList<Square> shortestPath = new ArrayList<>();
        Square current = aParents[aDestination.getColumn()][aDestination.getRow()];
        while(current != aSource){
            shortestPath.add(current);
            current = aParents[current.getColumn()][current.getRow()];
        }
        return shortestPath;
    }


    /**
     * A method to get all the adjacent squares of a square.
     * @param pSquare The square to get the adjacent squares of.
     * @return A list of squares adjacent to pSquare.
     */
    private ArrayList<Square> getAdjacentSquares(Square pSquare){
        int row = pSquare.getRow();
        int column = pSquare.getColumn();
        ArrayList<Square> adjList = new ArrayList<>(8);
        if(row != 0){
            adjList.add(Square.getSquare(row-1, column));
        }
        if(column != 0){
            adjList.add(Square.getSquare(row, column-1));
        }
        if(row != aParents[0].length-1){
            adjList.add(Square.getSquare(row+1, column));
        }
        if(column != aParents.length-1){
            adjList.add(Square.getSquare(row, column+1));
        }
        return adjList;
    }

    /**
     * A method to find the square with the smallest F value in the open list
     * where F is calculated as the distance from the start + the predicted distance to the finish
     * where predicted distance to the finish is calculated via the Manhattan method.
     * @return The square with the smallest F value.
     */
    private Square findSmallestFInOpenList(){
        int smallestF = Integer.MAX_VALUE;
        Square smallestFSquare = Square.getSquare(0,0);
        for(Square square : openList){
            assert aParents[square.getColumn()][square.getRow()].getMovesFromStart().isPresent();
            int movesFromStart = 0;
            int predictedMovesToDestination = 0;
            if(square.getMovesFromStart().isEmpty()){
                square.setMovesFromStart(aParents[square.getColumn()][square.getRow()].getMovesFromStart().get()+1);
            }
            movesFromStart = square.getMovesFromStart().get();
            predictedMovesToDestination = getPredictedDistanceFromDestination(square);
            if(movesFromStart + predictedMovesToDestination < smallestF){
                smallestF = movesFromStart + predictedMovesToDestination;
                smallestFSquare = square;
            }
        }
        return smallestFSquare;
    }

    /**
     * A method to calculate the predicted distance from the destination of a square
     * as calculated by the manhattan method.
     * @param pSquare The square to get the predicted distance of.
     * @return The predicted distance value.
     */
    private int getPredictedDistanceFromDestination(Square pSquare){
        int horizontalDistance = Math.abs(aDestination.getColumn() - pSquare.getColumn());
        int verticalDistance = Math.abs(aDestination.getRow() - pSquare.getRow());
        return horizontalDistance + verticalDistance;
    }
}
