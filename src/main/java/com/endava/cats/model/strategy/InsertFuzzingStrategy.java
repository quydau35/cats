package com.endava.cats.model.strategy;

import com.endava.cats.model.CommonWithinMethods;
import com.endava.cats.model.FuzzingStrategy;

public class InsertFuzzingStrategy extends FuzzingStrategy {
    @Override
    public Object process(Object value) {
        return CommonWithinMethods.insertInTheMiddle(String.valueOf(value), String.valueOf(data), true);
    }

    @Override
    public String name() {
        return "INSERT";
    }
}
