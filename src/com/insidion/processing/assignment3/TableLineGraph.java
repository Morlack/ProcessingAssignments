package com.insidion.processing.assignment3;

import com.insidion.processing.framework.Assignment;
import processing.data.Table;
import processing.data.TableRow;

/**
 * Created by mitchell on 6/5/2014.
 */
public abstract class TableLineGraph extends Assignment {

    private static final int MARGIN = 150;
    private static final int YEAR_SEP_HEIGHT = 5;
    private static final int VALUE_SEP_WIDTH = 5;
    private static final int VALUE_SCALE = 10;
    private static final int LEGENDA_WIDTH = 50;

    private Table table;
    private int maxValue;
    private int length;

    private float speed = 0.05f;
    private float currentTime = 0;

    public void setup() {
        super.setup();
        table = prepareSketchAndFillData();
        maxValue = getMaxValue();
        length = getDataLength();
    }

    @Override
    public void draw() {
        fill(0, 0, 0);
        clear();

        drawData();
        drawFrames();
        drawHorLabels();
        drawVertLabels();
        drawLegenda();

        currentTime += speed;

        if(currentTime > 14) currentTime = 0;
    }

    private void drawFrames() {
        stroke(255, 255, 255);
        fill(255, 255, 255);

        line(MARGIN, MARGIN, MARGIN, height - MARGIN);
        line(MARGIN,
                height - MARGIN,
                width - 2 * MARGIN - LEGENDA_WIDTH,
                height - MARGIN);
    }

    private void drawHorLabels() {
        int space = width - 2 * MARGIN - LEGENDA_WIDTH;
        float spacePerRow = space / length;

        for (int i = 0; i < length; i++) {
            float lineX = MARGIN + spacePerRow * i;
            float lineY = height - MARGIN;
            line(lineX, lineY, lineX, lineY + YEAR_SEP_HEIGHT);

            float labelX = round(MARGIN + (spacePerRow * i));
            float labelY = height - MARGIN + 10;
            textAlign(LEFT);
            pushMatrix();
            translate(labelX, labelY);
            rotate(radians(45));
            text(table.getRow(i).getString(0), 0, 0);
            popMatrix();


        }
    }

    private void drawVertLabels() {
        int length = maxValue / VALUE_SCALE;
        int space = height - 2 * MARGIN;
        int spacePerThing = space / length;

        for (int i = 1; i <= length; i++) {
            int lineX = MARGIN;
            int lineY = height - MARGIN - i * spacePerThing;

            line(lineX - VALUE_SEP_WIDTH, lineY, lineX, lineY);
            textAlign(CENTER);
            text(Integer.toString(i * VALUE_SCALE), lineX - 20, lineY + 5);

        }

    }

    private int getMaxValue() {
        int maxValue = 0;
        for (int i = 0; i < table.getRowCount() - 1; i++) {
            for (int j = 0; j < table.getColumnCount() - 1; j++) {
                TableRow row = table.getRow(i + 1);
                if (row.getInt(j + 1) > maxValue) {
                    maxValue = row.getInt(j + 1);
                }
            }
        }
        int roundedValue = maxValue + (VALUE_SCALE - maxValue % VALUE_SCALE);
        if (roundedValue - maxValue < 10) roundedValue += VALUE_SCALE;
        return roundedValue;
    }

    private int getDataLength() {
        return table.getRowCount();
    }

    private void drawData() {
        strokeWeight(4);

        for (int i = 1; i < round(currentTime); i++) {
            for (int j = 1; j < table.getColumnCount(); j++) {
                stroke(this.colors[j]);
                fill(this.colors[j]);
                float xLineBegin = MARGIN + (((width - 2 * MARGIN - LEGENDA_WIDTH) / length) * (i - 1));
                float xLineEnd = MARGIN + ((width - 2 * MARGIN - LEGENDA_WIDTH) / length) * (i);
                float yLineBegin = map(table.getRow(i - 1).getInt(j), 0, maxValue, height - MARGIN, MARGIN);
                float yLineEnd = map(table.getRow(i).getInt(j), 0, maxValue, height - MARGIN, MARGIN);
                line(xLineBegin, yLineBegin, xLineEnd, yLineEnd);
                ellipse(xLineEnd, yLineEnd, 3, 3);
            }
        }
    }

    private void drawLegenda() {
        textAlign(LEFT);
        for (int j = 1; j < table.getColumnCount(); j++) {
            String text = table.getColumnTitle(j);
            int color = colors[j];
            fill(255, 255, 255);
            text(text,
                    width - MARGIN - (LEGENDA_WIDTH / 2),
                    MARGIN + 30 * j);
            fill(color);
            stroke(color);
            rect(width - MARGIN - LEGENDA_WIDTH + 10,
                    MARGIN + 30 * j - 15,
                    30,
                    20);
        }
    }

    protected abstract Table prepareSketchAndFillData();

}
