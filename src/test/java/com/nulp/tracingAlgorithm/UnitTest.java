package com.nulp.tracingAlgorithm;

import com.nulp.tracingAlgorithm.algrorithm.WaveAlgorithm;
import com.nulp.tracingAlgorithm.model.Scheme;
import com.nulp.tracingAlgorithm.model.cell.Node;
import org.testng.annotations.Test;

public class UnitTest {

    @Test
    public void testAlgorithm() {
        Scheme scheme = new Scheme();
        scheme.updateCell(new Node("E", 1, 2));
        scheme.updateCell(new Node("H", 1, 5));
        scheme.updateCell(new Node("B", 3, 8));
        scheme.updateCell(new Node("A", 4, 3));
        scheme.updateCell(new Node("F", 6, 6));
        scheme.updateCell(new Node("C", 6, 9));
        scheme.updateCell(new Node("D", 7, 1));
        scheme.connectNodes("B", "A");
        scheme.connectNodes("B", "A");
//        scheme.connectNodes("C", "A");
//        scheme.connectNodes("H", "A");
//        scheme.connectNodes("D", "E");
//        scheme.connectNodes("D", "E");
//        scheme.connectNodes("D", "B");
//        scheme.connectNodes("D", "B");
//        scheme.connectNodes("E", "F");
//        scheme.connectNodes("E", "C");
//        scheme.connectNodes("C", "F");
//        scheme.connectNodes("F", "H");
//        scheme.connectNodes("F", "H");
        scheme.draw();
        WaveAlgorithm waveAlgorithm = new WaveAlgorithm(scheme);
        waveAlgorithm.run();
    }

}
