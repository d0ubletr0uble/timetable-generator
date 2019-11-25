package odinas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Benchmark extends Application {
    private int[] iterations = new int[]{1, 10, 100, 1_000, 10_000, 20_000, 40_000, 60_000, 80_000, 100_000, 200_000, 400_000, 600_000, 800_000, 1_000_000};

    static void showGraph(String method, double[][] times, int[] iterations) {
        Stage stage = new Stage();
        new Benchmark().start(stage);
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("apimtis");
        yAxis.setLabel("sekundes");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("CBuffer " + method);
        series2.setName("ArrayBlockingQueue " + method);
        for (int i = 0; i < iterations.length; i++) {
            series1.getData().add(new XYChart.Data<>(iterations[i], times[i][0]));
            series2.getData().add(new XYChart.Data<>(iterations[i], times[i][1]));
        }

        stage.setScene(new Scene(chart));
        chart.getData().add(series1);
        chart.getData().add(series2);
    }

    void showTest() {
        double[][] times = new double[iterations.length][];
        for (int i = 0; i < iterations.length; i++) // once per test warmup
            times[i] = test(iterations[i]);

        for (int i = 0; i < iterations.length; i++) //actual benchmark
            times[i] = test(iterations[i]);

        showGraph("full remove,full add", times, iterations);
        logToConsole(times, iterations);
    }

    private void logToConsole(double[][] times, int[] iterations) {
        System.out.println("-".repeat(40));
        System.out.println(String.format("| %7s | %-10s | %8s |", "Iteracijos", "CBuffer", "ArrayQueue"));
        System.out.println("-".repeat(40));
        for (int i = 0; i < iterations.length; i++)
            System.out.println(String.format("| %10s | %9fs | %9fs |", iterations[i], times[i][0], times[i][1]));
        System.out.println("-".repeat(40));
    }

    private Integer[] randomInts(int size) {
        var ints = new Integer[size];
        for (int i = 0; i < size; i++)
            ints[i] = (int) Math.random() * 10000;

        return ints;
    }

    private double[] test(int iterations) { //1 add and then 1 remove
        double[] times = new double[2];

        var numbers = randomInts(iterations);
        var buffer = new CBuffer<Integer>(numbers);
        Queue<Integer> queue = new ArrayBlockingQueue<Integer>(numbers.length, true, Arrays.asList(numbers));

        //----------------------Once per iteration warmup
        for (int i = 0; i < iterations; i++)
            buffer.remove();
        for (int i = 0; i < iterations; i++)
            buffer.add((int) Math.random() * 10000);
        //------------------------------------warmup end

        //------------------------------actual benchmark
        long time = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            buffer.remove();
        for (int i = 0; i < iterations; i++)
            buffer.add((int) Math.random() * 10000);
        times[0] = (System.nanoTime() - time) / 1e9;
        //---------------------------------benchmark end

        //---------------------------Once per iteration warmup
        for (int i = 0; i < iterations; i++)
            queue.remove();
        for (int i = 0; i < iterations; i++)
            queue.add((int) Math.random() * 10000);
        //-----------------------------------------warmup end

        //------------------------------actual benchmark
        time = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            queue.remove();
        for (int i = 0; i < iterations; i++)
            queue.add((int) Math.random() * 10000);
        times[1] = (System.nanoTime() - time) / 1e9;
        //---------------------------------benchmark end

        return times;
    }

    @Override
    public void start(Stage stage) {
        stage.show();
    }
}
