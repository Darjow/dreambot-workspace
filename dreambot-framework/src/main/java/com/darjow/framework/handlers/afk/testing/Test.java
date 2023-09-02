package com.darjow.framework.handlers.afk.testing;

import java.awt.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.darjow.framework.handlers.afk.AFKHandler;
import com.darjow.framework.handlers.afk.DistributionType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Test {

    private static AFKHandler afkHandler;

    public static void main(String[] args){

        afkHandler = new AFKHandler();
        initializeGraph(1,60,5);
        initializeGraph(180,2000,1400);
        initializeGraph(250,700,300);
        initializeGraph(1000,3000,1100);
    }

    private static void initializeGraph(int min, int max, int mean) {
        SortedMap<Integer, Integer> uniform = new TreeMap();
        SortedMap<Integer, Integer> left = new TreeMap<>();
        SortedMap<Integer, Integer> right = new TreeMap<>();

        populateData( uniform, DistributionType.UNIFORM, min, max, mean);
        populateData( left, DistributionType.LEFT_SIDED, min, max, mean);
        populateData( right, DistributionType.RIGHT_SIDED, min, max, mean);

        drawPlot(uniform, left, right);
    }

    private static void drawPlot(SortedMap<Integer, Integer> uniform, SortedMap<Integer, Integer> left, SortedMap<Integer, Integer> right) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries uniformSeries = createSeries(uniform, "Uniform Plot");
        dataset.addSeries(uniformSeries);

        XYSeries leftSeries = createSeries(left, "Leftskewed Plot");
        dataset.addSeries(leftSeries);

        XYSeries rightSeries = createSeries(right, "Rightskewed Plot");
        dataset.addSeries(rightSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Afk test Plot",
                "Afk time",
                "Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof XYSplineRenderer) {
            ((XYSplineRenderer) renderer).setPrecision(10);
        }

        LegendTitle legend = chart.getLegend();
        legend.setVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Combined Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static XYSeries createSeries(SortedMap<Integer, Integer> map, String seriesName ) {
        XYSeries series = new XYSeries(seriesName);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int x = entry.getKey();
            int y = entry.getValue();
            series.add(x, y);
        }
        return series;
    }


    private static void populateData(SortedMap<Integer, Integer> map, DistributionType type, int min, int max, int mean) {
        for (int i = 0; i < 5_000_000; i++){
            afkHandler.startAfk(min, max, mean, type);
            int afkTime = (int) (afkHandler.getAfkUntil() - System.currentTimeMillis());
            int count = map.getOrDefault(afkTime/1000, 0);
            map.put(afkTime/1000, count +1);
        }
    }

}