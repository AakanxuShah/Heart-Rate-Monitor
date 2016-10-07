package com.example.android.assignment_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final Random rand = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    public boolean isRunning =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Using the GGraphView Library
        GraphView graph = (GraphView) findViewById(R.id.graph);

        //Create the Datapoins
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);

        // customize the viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(60);
        viewport.setMaxY(100);
        viewport.setScrollable(true);

        // Creating onClickListener for the "RUN" Button
        Button button = (Button) findViewById(R.id.run);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning=true;

                // Create a thread to run the graph
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (isRunning==true) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addEntry();
                                }
                            });
                            // using sleep to slow down the addition of new points
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    }
                }).start();
            }
        });

        // Creating onClickListener for the "STOP" Button
        Button button1 = (Button) findViewById(R.id.stop);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isRunning=false;
            }
        });
    }

    // Method that adds new points to the graph
    private void addEntry() {
        series.appendData(new DataPoint(lastX++,60+ rand.nextDouble() * 40d), true, 10);
    }
}
