package com.example.testwithsoot.analysis;

import soot.*;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;

import java.util.Map;

public class DataFlowAnalysisWithSoot extends ForwardFlowAnalysis<Unit, FlowSet<Value>> {
    public DataFlowAnalysisWithSoot(Body body) {
        super(new ExceptionalUnitGraph(body));
    }

    // Override these methods to implement your data flow analysis logic
    @Override
    protected FlowSet<Value> newInitialFlow() {
        // Initialize your initial flow set
        System.out.println("newInitialFlow");
        return null;
    }

    @Override
    protected void merge(FlowSet<Value> in1, FlowSet<Value> in2, FlowSet<Value> out) {
        System.out.println("merge"+in1+in2+out);
    }

    @Override
    protected void copy(FlowSet<Value> source, FlowSet<Value> dest) {
        System.out.println("copy"+source+dest);
    }

    @Override
    protected void flowThrough(FlowSet<Value> in, Unit unit, FlowSet<Value> out) {
        // Implement how data flow values flow through statements
        System.out.println("flowThrough"+in+unit+out);
    }

    @Override
    public void doAnalysis() {
        super.doAnalysis();
    }

    public Map<Unit, FlowSet<Value>> getUnitToAfterFlow()
    {
        return this.unitToAfterFlow;
    }


    // ... other methods

    /*public static void main(String[] args) {
        SootClass targetClass = Scene.v().getSootClass("com.example.testwithsoot.A");
        for (SootMethod method : targetClass.getMethods()) {
            if (method.isConcrete()) {
                Body body = method.retrieveActiveBody();
                UnitGraph unitGraph = new BriefUnitGraph(body);
                DataFlowAnalysisWithSoot analysis = new DataFlowAnalysisWithSoot(unitGraph);

                // Access analysis results and perform actions
            }
        }
    }*/
}
