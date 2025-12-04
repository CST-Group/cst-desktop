/***********************************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * K. Raizer, A. L. O. Paraense, E. M. Froes, R. R. Gudwin - initial API and implementation
 ***********************************************************************************************/
package br.unicamp.cst.util.viewer;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.representation.idea.Habit;
import br.unicamp.cst.representation.idea.HabitExecutionerCodelet;
import br.unicamp.cst.representation.idea.Idea;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author du
 */
public class ChartViewerUtil {
    
     public static synchronized ChartPanel createChart(DefaultCategoryDataset dataset, String title, String categoryAxisLabel, String valueAxisLabel, PlotOrientation chartType) {

        final JFreeChart chart = ChartFactory.createBarChart(
                title,
                categoryAxisLabel,
                valueAxisLabel,
                dataset,
                chartType,
                true,
                true,
                false
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        chart.setBackgroundPaint(Color.lightGray);

        ChartPanel localChartPanel = new ChartPanel(chart);
        localChartPanel.setVisible(true);
        localChartPanel.setDomainZoomable(true);

        return localChartPanel;
    }

    public static synchronized ChartPanel createLineXYChart(XYSeriesCollection dataset, String title, String categoryAxisLabel, String valueAxisLabel, long timeRefresh) {

        final JFreeChart chart = ChartFactory.createXYLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.getDomainAxis().setFixedAutoRange(timeRefresh * 100);
        chart.setBackgroundPaint(Color.lightGray);

        ChartPanel localChartPanel = new ChartPanel(chart);
        localChartPanel.setVisible(true);
        localChartPanel.setDomainZoomable(true);

        return localChartPanel;
    }

    public static synchronized void updateValuesInChart(DefaultCategoryDataset dataset, List<? extends Codelet> codelets) {
        ArrayList<Codelet> tempCodeletsList = new ArrayList<Codelet>();
        tempCodeletsList.addAll(codelets);

        synchronized (tempCodeletsList) {
            for (Codelet co : tempCodeletsList) {
                if (co instanceof HabitExecutionerCodelet) {
                    HabitExecutionerCodelet hec = (HabitExecutionerCodelet)co;
                    for (Memory m : hec.getInputs()) {
                        if (m instanceof MemoryContainer) {
                            for (Memory mo: ((MemoryContainer)m).getAllMemories()) {
                                Object o = mo.getI();
                                if (o instanceof Idea) {
                                    Idea id = (Idea)o;
                                    Object value = id.getValue();
                                    if (m.getName().equalsIgnoreCase(hec.getName()+"Habits") && value instanceof Habit) {
                                        String habitName = id.getName();
                                        if (habitName.equalsIgnoreCase(hec.getHabitName())) {
                                            dataset.addValue(hec.getActivation(), habitName, "activation");
                                        }
                                    }    
                                }
                            }
                        }
                    }
                } else {
                    dataset.addValue(co.getActivation(), co.getName(), "activation");
                }
            }
        }
    }

    public static synchronized void updateValuesInXYLineChart(XYSeriesCollection dataset, List<? extends Codelet> codelets, long instant) {
        ArrayList<Codelet> tempCodeletsList = new ArrayList<Codelet>();
        tempCodeletsList.addAll(codelets);

        synchronized (tempCodeletsList) {
            for (Codelet co : tempCodeletsList) {
                if (co instanceof HabitExecutionerCodelet) {
                    HabitExecutionerCodelet hec = (HabitExecutionerCodelet)co;
                    for (Memory m : hec.getInputs()) {
                        if (m instanceof MemoryContainer) {
                            for (Memory mo: ((MemoryContainer)m).getAllMemories()) {
                                Object o = mo.getI();
                                if (o instanceof Idea) {
                                    Idea id = (Idea)o;
                                    Object value = id.getValue();
                                    if (m.getName().equalsIgnoreCase(hec.getName()+"Habits") && value instanceof Habit) {
                                        String habitName = id.getName();
                                        if (habitName.equalsIgnoreCase(hec.getHabitName())) {
                                            dataset.getSeries(habitName).add(instant, co.getActivation());
                                        }
                                    }    
                                }
                            }
                        }
                    }
                } else {
                    dataset.getSeries(co.getName()).add(instant, co.getActivation());
                }
            }
        }
    }

    public static synchronized void updateValueInChartByMemory(DefaultCategoryDataset dataset, List<? extends Codelet> codelets, String memoryName) {
        ArrayList<Codelet> tempCodeletsList = new ArrayList<Codelet>();
        tempCodeletsList.addAll(codelets);

        synchronized (tempCodeletsList) {
            for (Codelet co : tempCodeletsList) {
                if (co instanceof HabitExecutionerCodelet) {
                    HabitExecutionerCodelet hec = (HabitExecutionerCodelet)co;
                    for (Memory m : hec.getInputs()) {
                        if (m instanceof MemoryContainer) {
                            for (Memory mo: ((MemoryContainer)m).getAllMemories()) {
                                Object o = mo.getI();
                                if (o instanceof Idea) {
                                    Idea id = (Idea)o;
                                    Object value = id.getValue();
                                    if (m.getName().equalsIgnoreCase(hec.getName()+"Habits") && value instanceof Habit) {
                                        String habitName = id.getName();
                                        if (habitName.equalsIgnoreCase(hec.getHabitName())) {
                                            dataset.addValue(co.getOutput(memoryName).getEvaluation(), habitName, "activation");
                                        }
                                    }    
                                }
                            }
                        }
                    }
                } else {
                    dataset.addValue(co.getOutput(memoryName).getEvaluation(), co.getName(), "activation");
                }
            }
        }
    }
}