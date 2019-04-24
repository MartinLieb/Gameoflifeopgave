package game.of.life;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLife extends Application {

    private Cell[][] matrix;
    private Label label;
    private Game world;
    private Pane root;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        Game world = Game.intialize();
        matrix = world.getArrayH();
        updateScene(matrix, root);

        Scene scene = new Scene(root, 430, 450);

        Button btn = new Button();
        btn.setText("Start game with 100 iterations");
        btn.setOnAction((ActionEvent event) -> {
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(300),
                    ae -> evolve(world, root)));
            timeline.setCycleCount(100);
            timeline.play();

        });

        // her er den label som tæller iterations

        label = new Label("0");
        label.setLayoutX(200);
        root.getChildren().add(label);
        root.getChildren().add(btn);

        primaryStage.setTitle("Game of life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void evolve(Game world, Pane root) {

        matrix = world.getArrayH();
        world.update();
        updateScene(matrix, root);
        String iterations = String.valueOf(world.getnIteration());
        label.setText(iterations);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void updateScene(Cell[][] matrix, Pane root) {
        root.getChildren().removeAll();

        int yMax = matrix.length;
        int xMax = matrix[0].length;
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                double xVal = x * 20 + 20;
                double yVal = y * 20 + 50;

                Paint color;
                if (matrix[x][y].isAlive()) {
                    color = Paint.valueOf("BLUE");
                } else {
                    color = Paint.valueOf("WHITE");
                }

                Circle circ = new Circle(xVal, yVal, 6, color);

                if (matrix[x][y].isAlive()) {
                    circ.setStrokeType(StrokeType.OUTSIDE);
                    circ.setStroke(Color.web("blue", 1));
                    circ.setStrokeWidth(2);
                } else {
                    circ.setStrokeType(StrokeType.OUTSIDE);
                    circ.setStroke(Color.web("black", 1));
                    circ.setStrokeWidth(2);
                }

                String toolTip = matrix[x][y].toString();
                Tooltip.install(
                        circ,
                        new Tooltip(toolTip)
                );
                root.getChildren().add(circ);

            }
        }
    }

}
