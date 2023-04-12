package com.example.intuithome.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class RunnableBatchTest {

    @Test
    void parsePlayer_withFieldNamesWithValues() {
        RunnableBatch mRunnableBatch = new RunnableBatch(100, Arrays.asList("int1", "int3", "str"), null);

        String[] values = new String[3];
        values[0] = "1";
        values[1] = "2";
        values[2] = "moshe";

        Map<String, String> map =  mRunnableBatch.parsePlayer(values);

        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("int1", "1");
        expectedResult.put("int2", "2");
        expectedResult.put("str", "moshe");

        assertEquals(expectedResult.size(), map.size());
    }
}