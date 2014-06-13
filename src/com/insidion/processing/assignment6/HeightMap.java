package com.insidion.processing.assignment6;

import com.insidion.processing.framework.Assignment;
import processing.data.Table;
import processing.data.TableRow;


/**
 * Created by mitchell on 6/13/2014.
 */
public class HeightMap extends Assignment {
    private static final int distance = 500;
    private static final int center_x = 92796;
    private static final int center_y = 436960;

    Table table;

    @Override
    public void setup() {
        super.setup();
        resize(1000, 1000);
        colorMode(RGB);

        table = loadTable("http://web.insidion.com/processing/o6/processed_500.csv");
    }

    @Override
    public void draw() {

        //int black = color(1);
       float blue1 = color(10, 0, 0,255);
        fill(blue1);
        for(int i = 0; i < table.getRowCount(); i++) {
            TableRow row = table.getRow(i);
            int x = row.getInt(0) - (center_x - distance);
            int y = row.getInt(1) - (center_y - distance);

            //System.out.println("X:" + x + ", Y:" + y);
             set(x, y,  i*1000000000*20);
        }




    }

    @Override
    public String toString() {
        return "Assignment Six - Height Map";
    }
}
