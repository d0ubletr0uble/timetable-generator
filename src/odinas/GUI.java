package odinas;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author Tomas Odinas <IF-8/1>
 */
public class GUI extends Application {
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, 650, 360);
    private ToggleButton themeButton = new ToggleButton("Keisti temą");
    private TextArea console = new TextArea();
    private HBox bottom = new HBox();
    private FlowPane panel1 = new FlowPane();
    private FlowPane panel2 = new FlowPane();
    private FlowPane panel3 = new FlowPane();
    private HBox menu = new HBox();

    private Button generateButton = new Button("Sudaryti tvarkaraštį");
    private Button get1 = new Button("Rodyti sekantį");
    private Button get2 = new Button("Rodyti sekančius: 1");
    private Button save = new Button("Išsaugoti");
    ChoiceBox<? extends String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Diena", "Mėnuo", "Metai"));

    private Label label = new Label("Elementus skirkite ;");
    private TextField textInput = new TextField();

    private Iterator<String> elementsIterator;
    private LocalDate time = LocalDate.now();

    @Override
    public void start(Stage primaryStage) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        initConsole();
        initMenu();
        initBottom();
        scene.getStylesheets().add("odinas/black-theme.css");
        primaryStage.setTitle("Tvarkaraščiai");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
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
        save.setOnMouseClicked(s -> save(console.getText()));
        save.getStyleClass().add("orange");

        choiceBox.getSelectionModel().select(0);
        panel3.getChildren().add(choiceBox);
        panel3.getChildren().add(save);
        setVisibility(save, false);
    }

    private void save(String text) {
        text = text.substring(24).replace(' ', ';');
        FileChooser chooser= new FileChooser();
        chooser.setInitialFileName("tvarkaraštis.csv");
        chooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        File output = chooser.showSaveDialog(new Stage());
        try {
            Files.write(Paths.get(output.getPath()),text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPanel1() {

        generateButton.setOnMouseClicked(s -> generateTimetable(textInput.getText()));
        get1.setOnMouseClicked(s -> showNext());
        get2.setOnMouseClicked(s -> showNext(Integer.parseInt(textInput.getText())));
        panel1.getChildren().add(generateButton);
        panel1.getChildren().add(get1);
        panel1.getChildren().add(get2);

        setVisibility(get1, false);
        setVisibility(get2, false);
    }

    private void showNext(int times) {
        for (int i = 0; i < times; i++) {
            showNext();
        }
    }

    private void showNext() {
        System.out.println(getNextTime(choiceBox.getSelectionModel().getSelectedIndex()) + " " + elementsIterator.next());
    }

    private void generateTimetable(String elements) {
        CBuffer<String> timetable = new CBuffer<>(elements.split(";"));
        System.out.println("Tvarkaraštis sudarytas" + System.lineSeparator());
        label.setText("Įveskite kiekį");
        textInput.setText("1");
        textInput.setOnKeyTyped(s -> get2.setText("Rodyti sekančius: " + textInput.getText()));

        setVisibility(get1, true);
        setVisibility(get2, true);
        setVisibility(save, true);
        setVisibility(generateButton, false);

        elementsIterator = timetable.iterator();

    }

    private String getNextTime(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                time = time.plusDays(1);
                return time.toString();
            case 1:
                time = time.plusMonths(1);
                return time.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            default:
                time = time.plusYears(1);
                return time.format(DateTimeFormatter.ofPattern("yyyy"));
        }
    }

    private void initPanel2() {
        HBox textInput = new HBox();
        label.setId("label");
        textInput.getChildren().add(label);
        textInput.getChildren().add(this.textInput);
        panel2.getChildren().add(textInput);
    }

    private void setVisibility(Button button, boolean enable) {
        if (enable) {
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

}
