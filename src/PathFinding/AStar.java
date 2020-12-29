package PathFinding;
import java.util.ArrayList;

public class AStar {
    Square aSource;
    Square aDestination;
    ArrayList<Square> openList;
    ArrayList<Square> closedList;
    Square[][] aParents;

    public AStar(Square pSource, Square pDestination, int numColumns, int numRows) {
        //Source and destination are stored (column, row)
        aSource = pSource;
        aDestination = pDestination;
        pSource.setMovesFromStart(0);
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        aParents = new Square[numColumns][numRows];
    }

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
                    if(square.getEstimatedCostToFinish().isEmpty()) {
                        int predictedDistanceToDestination = getPredictedDistanceFromDestination(square);
                        square.setEstimatedCostToFinish(predictedDistanceToDestination);
                        square.setMovesFromStart(distanceFromStart);
                        //TODO consider a way ot show the considered squares. Maybe add a delay...
                        //square.considerSquare();
                        openList.add(square);
                        aParents[square.getColumn()][square.getRow()] = closestSquare;
                    }else{
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

    public ArrayList<Square> traceBackSolution(){
        /**
        for(int i =0; i < aParents.length; i++){
            for(int j = 0; j < aParents[0].length; j++){
                System.out.println(aParents[i][j]);
            }
        }
         **/
        ArrayList<Square> shortestPath = new ArrayList<>();
        //System.out.println(aDestination.getColumn() + " " + aDestination.getRow());
        Square current = aParents[aDestination.getColumn()][aDestination.getRow()];;
        //System.out.println(current);
        while(current != aSource){
            shortestPath.add(current);
            //System.out.println("Column: "+current.getColumn());
            //System.out.println("Row: " +current.getRow());
            current = aParents[current.getColumn()][current.getRow()];
        }
        System.out.println("               ");
        return shortestPath;
    }


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

    private int getPredictedDistanceFromDestination(Square pSquare){
        int horizontalDistance = Math.abs(aDestination.getColumn() - pSquare.getColumn());
        int verticalDistance = Math.abs(aDestination.getRow() - pSquare.getRow());
        return horizontalDistance + verticalDistance;
    }



    private boolean squareNotInList(Square pSquare){
        return openList.contains(pSquare);
    }






}
