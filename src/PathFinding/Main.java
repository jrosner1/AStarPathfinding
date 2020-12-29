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

    String CONSIDERING_BUTTON_STYLE = "-fx-background-radius: 0; -fx-pref-width: 30px; -fx-pref-height: 30px;" +
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-size: 12; " +
            "-fx-font-weight: bold; -fx-background-color: green";

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

    private void render(){
        aGrid.getChildren().clear();
        aGrid.requestFocus();
        for(int i = 0; i < aField.getNumColumns(); i++){
            for(int j = 0; j < aField.getNumRows(); j++){
                aGrid.add(createNode(j, i), i, j);
            }
        }
    }

    private Node createNode(int pRow, int pColumn){
        if(aField.isAccessible(pRow, pColumn)){
            return createAccessibleButton(pRow, pColumn);
        }else if(aField.isSource(pRow, pColumn)){
            return createSourceButton();
        }else if(aField.isDestination(pRow, pColumn)){
            return createDestinationButton();
        }else if(aField.isPath(pRow, pColumn)){
            return createPathButton();
        }else if(aField.isConsidered(pRow, pColumn)){
            return consideringButton();
        }
        else{
            return createInaccessibleButton();
        }
    }

    private Button createSourceButton(){
        Button button = new Button();
        button.setText("S");
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    private Button createPathButton(){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(PATH_BUTTON_STYLE);
        return button;
    }

    private Button consideringButton(){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(CONSIDERING_BUTTON_STYLE);
        return button;
    }

    private Button createDestinationButton(){
        Button button = new Button();
        button.setText("D");
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    private Button createInaccessibleButton(){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(INACCESSIBLE_BUTTON_STYLE);
        return button;
    }

    private void reset(){
        for(int i = 0; i < aField.getNumColumns(); i++){
            for(int j = 0; j < aField.getNumRows(); j++){
                Square.getSquare(i, j).resetSquare();
            }
        }
        aField.fieldReset();
        render();
    }

    private Button createAccessibleButton(int pRow, int pColumn){
        Button button = new Button();
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setStyle(ACCESSIBLE_BUTTON_STYLE);
        button.setOnMouseClicked( e ->{
            if(!aField.hasSource()){
                aField.setSource(pRow, pColumn);
            }else if(!aField.hasDestination()){
                aField.setDestination(pRow, pColumn);
            }else {
                aField.makeInaccessible(pRow, pColumn);
            }
            render();
        });
        return button;
    }

    private void initializeField(){
        aField = new Field(NUM_ROWS, NUM_COLUMNS);
    }

    private Scene CreateScene(){
        final BorderPane root = new BorderPane();
        Button topButton = new Button();
        topButton.setOnMouseClicked(e -> createPath());
        topButton.setStyle(TOP_BUTTON_STYLE);
        topButton.setText("Find Path");
        Button resetButton = new Button();
        resetButton.setOnMouseClicked(e -> reset());
        resetButton.setStyle(TOP_BUTTON_STYLE);
        resetButton.setText("Reset Grid");

        HBox topButtons = new HBox();
        topButtons.setPadding(new Insets(12, 12, 12, 12));
        topButtons.setSpacing(10);
        topButtons.setStyle("-fx-background-color: #63B0EF");
        topButtons.getChildren().addAll(topButton, resetButton);
        root.setTop(topButtons);

        aGrid = new GridPane();
        root.setCenter(aGrid);
        aGrid.setHgap(1.5);
        aGrid.setVgap(1.5);
        aGrid.setAlignment(Pos.CENTER);
        aGrid.setPadding(new Insets(PADDING));
        return new Scene(root);
    }

    private void createPath(){
        AStar fieldAStar = new AStar(aField.getSource(), aField.getDestination(), aField.getNumColumns(), aField.getNumRows());
        fieldAStar.solve();
        for(Square square : fieldAStar.traceBackSolution()){
            square.makePath();
        }
        render();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

