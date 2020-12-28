package PathFinding;

public class Field {
    private final int aNumRows;
    private final int aNumColumns;
    private boolean aHasSource;
    private boolean aHasDestination;
    private Square aSource;
    private Square aDestination;
    private Field aField;

    public Field(int pNumRows, int pNumColumns){
        assert pNumRows > 0 && pNumColumns > 0;
        aNumRows = pNumRows;
        aNumColumns = pNumColumns;
        aHasSource = false;
        aHasDestination = false;
        for(int i = 0; i< pNumColumns; i++){
            for(int j = 0; j < pNumRows; j++){
                Square.getSquare(i,j);
            }
        }
    }

    public void setSource(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).makeSource();
        aSource = Square.getSquare(pRow, pColumn);
        aHasSource = true;
    }

    public boolean hasSource(){
        return aHasSource;
    }

    public boolean hasDestination(){
        return aHasDestination;
    }

    public Square getDestination(){
        return aDestination;
    }

    public Square getSource(){
        return aSource;
    }

    public boolean isSource(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.SOURCE;
    }

    public boolean isDestination(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.DESTINATION;
    }

    public void setDestination(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).makeDestination();
        aDestination = Square.getSquare(pRow, pColumn);
        aHasDestination = true;
    }

    public void makeInaccessible(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).makeInaccessible();
    }

    public boolean isAccessible(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.ACCESSIBLE;
    }

    public boolean isPath(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.PATH;
    }

    public boolean isConsidered(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.CONSIDERING;
    }

    public int getNumRows(){
        return aNumRows;
    }

    public int getNumColumns(){
        return aNumColumns;
    }


}
