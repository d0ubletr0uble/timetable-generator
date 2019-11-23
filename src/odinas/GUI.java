package odinas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.PrintStream;
import java.util.Locale;

/**
 * @author Tomas Odinas <IF-8/1>
 */
public class GUI extends Application {

    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, 650, 360);
    private ToggleButton themeButton = new ToggleButton("a");
    private TextArea console = new TextArea();
    private HBox bottom = new HBox();
    private FlowPane panel1 = new FlowPane();
    private FlowPane panel2 = new FlowPane();
    private FlowPane panel3 = new FlowPane();
    private HBox menu = new HBox();
    private Label bookCount = new Label();
    private Button pollLastButton = new Button("a");
    private Button addButton = new Button("a");
    private TextField delimiter = new TextField(" by");

    @Override
    public void start(Stage primaryStage) {
        initConsole();
        initMenu();
        initBottom();
        scene.getStylesheets().add("odinas/black-theme.css");
        primaryStage.setTitle("a");
        primaryStage.setMaximized(true);
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        primaryStage.setScene(scene);
        primaryStage.show();

        test();//todo

    }

    private void initBottom() {
        root.setBottom(bottom);
        bottom.getChildren().add(panel1);
        bottom.getChildren().add(panel2);
        bottom.getChildren().add(panel3);

        initPanel1();
        initPanel2();
        initPanel3();
    }

    private void initPanel3() {
        Button btn1 = new Button("a");
        Button btn2 = new Button("a");
        Button clear = new Button("a");
        btn2.getStyleClass().add("orange");
        clear.getStyleClass().add("green");

        panel3.getChildren().add(btn2);
        panel3.getChildren().add(btn1);
        panel3.getChildren().add(clear);
    }

    private void initPanel1() {
        Button generateSetButton = new Button("aet");
        Button iterator1 = new Button("a");
        Button iterator2 = new Button("a");
        Button heightButton = new Button("a");


        panel1.getChildren().add(generateSetButton);
        panel1.getChildren().add(iterator1);
        panel1.getChildren().add(iterator2);
        panel1.getChildren().add(heightButton);
        panel1.getChildren().add(pollLastButton);
        panel1.getChildren().add(addButton);

        flipButton(pollLastButton);
        flipButton(addButton);
    }

    private void initPanel2() {
        HBox splitterField = new HBox();
        Label label = new Label("a");
        label.setId("splitter");
        splitterField.getChildren().add(label);
        splitterField.getChildren().add(delimiter);

        panel2.getChildren().add(bookCount);
        panel2.getChildren().add(splitterField);
    }

    private void flipButton(Button button) {
        if (button.getStyleClass().contains("disabled")) {
            button.getStyleClass().remove("disabled");
            button.setManaged(true);
        } else {
            button.getStyleClass().add("disabled");
            button.setManaged(false);
        }
    }

    private void initMenu() {
        root.setTop(menu);
        themeButton.setOnMouseClicked(this::toggleTheme);
        menu.getChildren().add(themeButton);
    }

    private void toggleTheme(MouseEvent mouseEvent) {
        if (themeButton.isSelected())
            scene.getStylesheets().add("odinas/white-theme.css");
        else
            scene.getStylesheets().remove(1);
    }

    private void initConsole() {
        root.setCenter(console);
        console.setEditable(false);
        redirectConsole();
    }

    /**
     * We redirect system.out to our UI console by overiding print method
     * from now System.out.println will write to our UI console
     */
    private void redirectConsole() {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void print(String s) {
                console.appendText(s);
            }

            @Override
            public void println(String x) {
                print(x);
                print(System.lineSeparator());
            }

            @Override
            public void println(Object x) {
                println(String.valueOf(x));
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void test() {

    }
}
