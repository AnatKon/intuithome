package com.example.intuithome.service.impl;

import com.example.intuithome.dao.impl.BatchInsert;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class RunnableBatch implements Runnable {

    private boolean started = true;
    private Queue<String> queue;
    private int batchLimit;
    protected List<String> fieldNameList;

    public RunnableBatch(int batchLimit, List<String> fieldNameList, Queue<String> queue) {
        this.batchLimit = batchLimit;
        this.fieldNameList = fieldNameList;
        this.queue = queue;
    }

    @Override
    public void run() {
        if(fieldNameList == null || fieldNameList.isEmpty()) {
            return;
        }
        try (BatchInsert batch = new BatchInsert(batchLimit)) {
            while (!queue.isEmpty() || started) {
                String s = queue.poll();
                if (s == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }
                } else {
                    String[] values = s.split(",");
                    if(values == null || values.length == 0) {
                        return;
                    }
                    Map<String, String> player = parsePlayer(values);
                    batch.insert(player);
                }
            }
        }
    }

    protected Map<String, String> parsePlayer(String[] values) {
        Map<String, String> playerMap = new LinkedHashMap<>();
        for (int i = 0; i < fieldNameList.size() && i < values.length; i++) {
            playerMap.put(fieldNameList.get(i), values[i]);
        }
        return playerMap;
    }

    public void stop() {
        started = false;
    }
}
