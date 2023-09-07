package com.example.testwithsoot;

import com.example.testwithsoot.analysis.DataFlowAnalysisWithSoot;
import soot.*;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;

import java.util.*;

public class Main {
    private static Map<SootMethod, HashMap<String, String>> outFacts;
    private static int count = 0;

    /*private static final String TARGET_CLASS = "com.example.testwithsoot.MazeFuzzer";*/
    private static final String TARGET_CLASS = "com.example.testwithsoot.A";
    /*private static final String TARGET_CLASS = "com.example.testwithsoot.DataflowTestFuzzer";*/
    /*private static final String TARGET_CLASS = "com.example.testwithsoot.DataflowTestFuzzer2";*/

    protected static Transformer createAnalysisTransformer() {

        return new BodyTransformer() {
            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                SootClass targetClass = Scene.v().getApplicationClasses().getFirst();
                /*if (Scene.v().getEntryPoints().stream().findFirst().isPresent()) {
                    SootMethod targetMethod = Scene.v().getEntryPoints().stream().findFirst().get();
                    Body mainBody = targetMethod.retrieveActiveBody();
                    DataFlowAnalysisWithSoot analysis = new DataFlowAnalysisWithSoot(mainBody);
                    analysis.doAnalysis();
                    if (outFacts == null) {
                        outFacts = new java.util.HashMap<>();
                    }
                    if (!analysis.getFacts().isEmpty()) {
                        outFacts.put(targetMethod, analysis.getFacts());
                    }
                }*/
                if (!targetClass.getMethods().isEmpty()) {
                    final SootMethod targetMethod = targetClass.getMethods().get(count++);
                    Body mainBody = targetMethod.retrieveActiveBody();
                    DataFlowAnalysisWithSoot analysis = new DataFlowAnalysisWithSoot(mainBody);
                    analysis.doAnalysis();
                    if (outFacts == null) {
                        outFacts = new java.util.HashMap<>();
                    }
                    if (!analysis.getFacts().isEmpty()) {
                        outFacts.put(targetMethod, analysis.getFacts());
                    }
                }
            }
        };
    }
    public static void main(String[] args) {

        // Set the main class and other options
        Options.v().set_soot_classpath("/Users/cashman/code/personal/projectWithSoot/esg_generator/target/classes:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jce.jar");
        Options.v().set_prepend_classpath(true);
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().process_dir();
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_whole_program(true);
        //Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb", "use-original-names:true"); // to get names of variables
        Options.v().setPhaseOption("jb", "verbose:true"); // Optional, for debugging
        Options.v().set_debug(true);
        // Set the call graph analysis options
        Options.v().setPhaseOption("cg.cha", "on");
        //TODO: generate Jimple code and check variable mappings (make ICFG work first with data flow)


        // Load target class in Scene
        SootClass c = Scene.v().forceResolve(TARGET_CLASS, SootClass.BODIES);

        if (c != null) {
            System.out.println(c);
            c.setApplicationClass();
            Scene.v().loadNecessaryClasses();

            // Contrust call graph
            CHATransformer.v().transform();
            CallGraph cg = Scene.v().getCallGraph();
            for (Edge e : cg) {
                SootMethod srcMethod = e.src();
                SootMethod tgtMethod = e.tgt();

                // Skip methods from Java standard libraries
                if (srcMethod.getDeclaringClass().isJavaLibraryClass()) {
                    continue;
                }
                if (tgtMethod.toString().contains("clinit")) {
                    continue;
                }

                // Print call graph edges
                System.out.println(srcMethod + " may call " + tgtMethod);
            }

            // Perform data flow analysis
            Transform transform2 = new Transform("jtp.analysis", createAnalysisTransformer());
            PackManager.v().getPack("jtp").add(transform2);
            PackManager.v().runBodyPacks();
        }

        // Print data flow facts
        if (outFacts != null) {
            outFacts.forEach((k,v)->{
                System.out.println("Method: "+k);
                System.out.println("Datafacts: "+v);
                System.out.println();
                System.out.println();
            });
        }

        System.exit(1);
    }
}