package PathFinding;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application {
    int PADDING = 2;
    int NUM_ROWS = 25;
    int NUM_COLUMNS = 25;

    String ACCESSIBLE_BUTTON_STYLE = "-fx-background-radius: 0; -fx-pref-width: 30px; -fx-pref-height: 30px;" +
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-size: 12; " +
            "-fx-text-fill: red; -fx-font-weight: bold";

    String INACCESSIBLE_BUTTON_STYLE = "-fx-background-radius: 0; -fx-pref-width: 30px; -fx-pref-height: 30px;" +
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-size: 12; " +
            "-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: #528B8B";

    String PATH_BUTTON_STYLE = "-fx-background-radius: 0; -fx-pref-width: 30px; -fx-pref-height: 30px;" +
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-size: 12; " +
            "-fx-font-weight: bold; -fx-background-color: black";

    String TOP_BUTTON_STYLE = "-fx-pref-width: 100px; -fx-pref-height: 30px; -fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent; -fx-font-size: 12; -fx-background-radius: 0; -fx-font-weight: bold;"+
            "-fx-text-fill: black; -fx-faint-focus-color: transparent";


    Field aField;
    GridPane aGrid;

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pathfinding");
        primaryStage.setResizable(false);
        primaryStage.setScene(CreateScene());
        initializeField();
        render();
        primaryStage.show();
        aGrid.requestFocus();
    }

    /**
     * Method to update the buttons in the grid based on their current statuses
     */
    private void render(){
        aGrid.getChildren().clear();
        aGrid.requestFocus();
        for(int i = 0; i < aField.getNumColumns(); i++){
            for(int j = 0; j < aField.getNumRows(); j++){
                aGrid.add(createNode(j, i), i, j);
            }
        }
    }

    /**
     * A method to determine the status of a square and delegate its creation to the appropriate method.
     * @param pRow The row of the square to be created.
     * @param pColumn The column of the square to be created
     * @return The button that will be added to the GridPane
     */
    private Node createNode(int pRow, int pColumn){
        if(aField.isAccessible(pRow, pColumn)){
            return createAccessibleButton(pRow, pColumn);
        }else if(aField.isSource(pRow, pColumn)){
            return createSourceButton();
        }else if(aField.isDestination(pRow, pColumn)){
            return createDestinationButton();
        }else if(aField.isPath(pRow, pColumn)){
            return createPathButton();
        }else{
            return createInaccessibleButton();
        }
    }

    /**
     * A method to create a button representing the source of the path to be created.
     * @return The source button that has been created for this square position.
     */
    private Button createSourceButton(){
        Button button = new Button();
        button.setText("S");
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    /**
     * A method to create a button representing a square that is in the solution path.
     * @return The button representing part of the path.
     */
    private Button createPathButton(){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(PATH_BUTTON_STYLE);
        return button;
    }

    /**
     * A method to create a button representing the destination of the path to be created.
     * @return The button representing the destination of the path.
     */
    private Button createDestinationButton(){
        Button button = new Button();
        button.setText("D");
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    /**
     * A method to create a button representing a square that may not be part of the final path.
     * @return The button representing a square that may not be part of the final path.
     */
    private Button createInaccessibleButton(){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(INACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    /**
     * A method to reset the squares so that a new path may be generated.
     */
    private void reset(){
        for(int i = 0; i < aField.getNumColumns(); i++){
            for(int j = 0; j < aField.getNumRows(); j++){
                Square.getSquare(i, j).resetSquare();
            }
        }
        aField.fieldReset();
        render();
    }

    /**
     * A method to create an accessible button that may be changed into a source, destination, or inaccessible later on.
     * @param pRow The row of the button to be created.
     * @param pColumn The column of the button to be created.
     * @return
     */
    private Button createAccessibleButton(int pRow, int pColumn){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        //The first accessible button pressed will become the source, the second will be the destination, and all subsequent will be inaccessible.
        button.setOnMouseClicked( e ->{
            if(aField.getSource().isEmpty()){
                aField.setSource(pRow, pColumn);
            }else if(aField.getDestination().isEmpty()){
                aField.setDestination(pRow, pColumn);
            }else {
                aField.makeInaccessible(pRow, pColumn);
            }
            render();
        });
        return button;
    }

    /**
     * A method to initialize the field instance that this program will model.
     */
    private void initializeField(){
        aField = new Field(NUM_ROWS, NUM_COLUMNS);
    }

    /**
     * A method to create a scene with find path and reset buttons on the top.
     * @return The scene that has been created
     */
    private Scene CreateScene(){
        final BorderPane root = new BorderPane();
        Button findPathButton = new Button();
        findPathButton.setOnMouseClicked(e -> createPath());
        findPathButton.setStyle(TOP_BUTTON_STYLE);
        findPathButton.setText("Find Path");
        Button resetButton = new Button();
        resetButton.setOnMouseClicked(e -> reset());
        resetButton.setStyle(TOP_BUTTON_STYLE);
        resetButton.setText("Reset Grid");

        HBox topButtons = new HBox();
        topButtons.setPadding(new Insets(12, 12, 12, 12));
        topButtons.setSpacing(10);
        topButtons.setStyle("-fx-background-color: #63B0EF");
        topButtons.getChildren().addAll(findPathButton, resetButton);
        root.setTop(topButtons);

        aGrid = new GridPane();
        root.setCenter(aGrid);
        aGrid.setHgap(1.5);
        aGrid.setVgap(1.5);
        aGrid.setAlignment(Pos.CENTER);
        aGrid.setPadding(new Insets(PADDING));
        return new Scene(root);
    }

    /**
     * A method that will generate the shortest path as calculated by the AStar algorithm
     */
    private void createPath(){
        assert aField.getSource().isPresent() && aField.getDestination().isPresent();
        AStar fieldAStar = new AStar(aField.getSource().get(), aField.getDestination().get(), aField.getNumColumns(), aField.getNumRows());
        fieldAStar.solve();
        for(Square square : fieldAStar.traceBackSolution()){
            square.setStatus(Status.PATH);
        }
        render();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

