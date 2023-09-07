package com.example.testwithsoot.analysis;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;

import java.util.HashMap;
import java.util.Map;

public class DataFlowAnalysisWithSoot extends ForwardFlowAnalysis<Unit, FlowSet<DataflowFact>> {

    private final DataflowFact dataflowFacts = new DataflowFact();
    public DataFlowAnalysisWithSoot(Body body) {
        super(new ExceptionalUnitGraph(body));
    }

    // Override these methods to implement your data flow analysis logic
    @Override
    protected FlowSet<DataflowFact> newInitialFlow() {
        // Initialize your initial flow set
        //System.out.println("newInitialFlow");
        FlowSet<DataflowFact> initialFlow = new ArraySparseSet<>();
        return initialFlow;
    }

    @Override
    protected void merge(FlowSet<DataflowFact> in1, FlowSet<DataflowFact> in2, FlowSet<DataflowFact> out) {
        //System.out.println("merge"+in1+in2+out);
    }

    @Override
    protected void copy(FlowSet<DataflowFact> source, FlowSet<DataflowFact> dest) {
        //System.out.println("copy"+source+dest);
    }

    @Override
    protected void flowThrough(FlowSet<DataflowFact> in, Unit unit, FlowSet<DataflowFact> out) {
        // Implement how data flow values flow through statements
        //System.out.println("flowThrough"+in+unit+out);
        //System.out.println("dataflowFacts"+dataflowFacts.getFacts());
        if (unit.branches()) {
            if (unit instanceof IfStmt) {
                IfStmt ifStmt = (IfStmt) unit;
                //System.out.println("If "+ifStmt);
                // Analyze if statement TODO
            } else if (unit instanceof SwitchStmt) {
                LookupSwitchStmt switchStmt = (LookupSwitchStmt) unit;
                if (switchStmt.branches()) {
                    dataflowFacts.add(switchStmt.getKeyBox().getValue().toString(), switchStmt.getLookupValues().toString());
                    System.out.println("Switch "+switchStmt.getKeyBox().getValue().toString()+" "+switchStmt.getLookupValues().toString());
                }
            }
        } else {
            //System.out.println("no branch");
            if (!unit.getDefBoxes().isEmpty()) {
                //System.out.println("Def "+unit);
                // Analyze definition statement TODO
            }
            if (!unit.getUseBoxes().isEmpty()) {
                //System.out.println("Use "+unit);
                if (unit instanceof AssignStmt) {
                    AssignStmt assignStmt = (AssignStmt) unit;
                    //System.out.println("Assign "+assignStmt);
                    dataflowFacts.add(assignStmt.getLeftOpBox().getValue().toString(), assignStmt.getRightOp().toString());
                    // Analyze assignment statement
                } else if (unit instanceof InvokeStmt) {
                    InvokeStmt invokeStmt = (InvokeStmt) unit;
                    //System.out.println("Invoke "+invokeStmt);
                    // Analyze method invocation statement TODO
                }
            }
        }
    }

    @Override
    public void doAnalysis() {
        super.doAnalysis();
    }

    public Map<Unit, FlowSet<DataflowFact>> getUnitToAfterFlow()
    {
        return this.unitToAfterFlow;
    }

    public HashMap<String, String> getFacts() {
        return dataflowFacts.getFacts();
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
