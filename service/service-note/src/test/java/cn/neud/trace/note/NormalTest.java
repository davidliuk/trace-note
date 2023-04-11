package cn.neud.trace.note;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NormalTest {

    @Test
    void testTreeMap() {
        TreeMap<String, String> treeMap = new TreeMap<>(Comparator.comparingInt(a -> a.charAt(0)));
        treeMap.put("12", "1");
        treeMap.put("13", "2");
        treeMap.put("22", "3");
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println(treeMap.ceilingEntry("12").getValue());
        System.out.println(treeMap.floorEntry("12").getValue());
        System.out.println(treeMap.floorEntry("23").getValue());
        System.out.println(treeMap.ceilingEntry("23").getValue());
    }

    @Test
    void testTreeMap1() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            treeMap.put(i, String.valueOf(i));
        }
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println(treeMap.ceilingEntry(1).getValue());
        System.out.println(treeMap.floorEntry(1).getValue());
        System.out.println(treeMap.floorEntry(10).getValue());
        System.out.println(treeMap.ceilingEntry(-1).getValue());
    }

    @Test
    void testBitMap() {
        int i = 0b1110111111111111111111111;

        long t1 = System.nanoTime();
        int count = 0;
        while (true) {
            if ((i & 1) == 0) {
                break;
            } else {
                count++;
            }
            i >>>= 1;
        }
        long t2 = System.nanoTime();
        System.out.println("time1 = " + (t2 - t1));
        System.out.println("count = " + count);
        i = 0b1110111111111111111111111;
        long t3 = System.nanoTime();
        int count2 = 0;
        while (true) {
            if (i >>> 1 << 1 == i) {
                // 未签到，结束
                break;
            } else {
                // 说明签到了
                count2++;
            }

            i >>>= 1;
        }
        long t4 = System.nanoTime();
        System.out.println("time2 = " + (t4 - t3));
        System.out.println("count2 = " + count2);
    }
}