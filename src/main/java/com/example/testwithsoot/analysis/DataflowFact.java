package com.example.testwithsoot.analysis;

import fj.Hash;

import java.util.HashMap;

public class DataflowFact {

    private final HashMap<String, String> facts;

    public DataflowFact() {
        facts = new HashMap<>();
    }

    public void add(String variableName, String variableValue) {
        if (!facts.containsKey(variableName)) {
            facts.put(variableName, variableValue);
        } else {
            facts.put(variableName, facts.get(variableName) + "||" + variableValue);
        }
    }

    public HashMap<String, String> getFacts() {
        return facts;
    }
}
