package PathFinding;

import java.util.Optional;

public class Field {
    private final int aNumRows;
    private final int aNumColumns;
    private Optional<Square> aSource;
    private Optional<Square> aDestination;

    /**
     * A constructor to create a field with all accessible squares and no source or destination.
     * @param pNumRows The number of rows that the field has.
     * @param pNumColumns The number of columns that the field has.
     */
    public Field(int pNumRows, int pNumColumns){
        assert pNumRows > 0 && pNumColumns > 0;
        aNumRows = pNumRows;
        aNumColumns = pNumColumns;
        aSource = Optional.empty();
        aDestination = Optional.empty();
        for(int i = 0; i< pNumColumns; i++){
            for(int j = 0; j < pNumRows; j++){
                Square.getSquare(i,j);
            }
        }
    }

    /**
     * A method to set the source square of the field.
     * @param pRow The row value of the source.
     * @param pColumn The column value of the source.
     */
    public void setSource(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).setStatus(Status.SOURCE);
        aSource = Optional.of(Square.getSquare(pRow, pColumn));
    }

    /**
     * A method to get the destination of the field.
     * @return The Optional destination square.
     */
    public Optional<Square> getDestination(){
        return aDestination;
    }

    /**
     * A method to get the source of the field.
     * @return The Optional source square.
     */
    public Optional<Square> getSource(){
        return aSource;
    }

    /**
     * A method to determine if a square is the source of the field.
     * @param pRow The row of the square to test.
     * @param pColumn The column of the square to test.
     * @return The boolean corresponding to if this square is the source or not.
     */
    public boolean isSource(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.SOURCE;
    }

    /**
     * A method to determine if a square is the destination of the field.
     * @param pRow The row value of the square to test.
     * @param pColumn The column value of the square to test.
     * @return The boolean corresponding to if this squar eis the source or not.
     */
    public boolean isDestination(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.DESTINATION;
    }

    /**
     * A method to set the destination of the field.
     * @param pRow The row of the destination square.
     * @param pColumn The column of the destination square.
     */
    public void setDestination(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).setStatus(Status.DESTINATION);
        aDestination = Optional.of(Square.getSquare(pRow, pColumn));
    }

    /**
     * A method to reset the field
     */
    public void fieldReset(){
        aDestination = Optional.empty();
        aSource = Optional.empty();
    }

    /**
     * A method to mark a square in the field as inaccessible
     * @param pRow The row of the inaccessible square
     * @param pColumn the column of inaccessible square
     */
    public void makeInaccessible(int pRow, int pColumn){
        Square.getSquare(pRow, pColumn).setStatus(Status.INACCESSIBLE);
    }

    /**
     * A method to test if a square in the field is accessible
     * @param pRow The row of the square to test.
     * @param pColumn The column of the squar eot test.
     * @return The boolean value corresponding to if the square is accessible.
     */
    public boolean isAccessible(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.ACCESSIBLE;
    }

    /**
     * A method to test if a square in the field is part of the path.
     * @param pRow The row of the square to test.
     * @param pColumn The column of the square to test.
     * @return The bolean value corresponding to if the square is accessible.
     */
    public boolean isPath(int pRow, int pColumn){
        return Square.getSquare(pRow, pColumn).getStatus() == Status.PATH;
    }

    /**
     * A method to get the number of rows in the field.
     * @return the number of rows int he field.
     */
    public int getNumRows(){
        return aNumRows;
    }

    /**
     * A method to get the number of columns in the field.
     * @return The number of columns in the field.
     */
    public int getNumColumns(){
        return aNumColumns;
    }
}
