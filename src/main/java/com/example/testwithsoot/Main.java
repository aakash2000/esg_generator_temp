package com.example.testwithsoot;

import com.example.testwithsoot.analysis.DataFlowAnalysisWithSoot;
import soot.*;
import soot.options.Options;
import soot.toolkits.scalar.FlowSet;

import java.util.Map;

public class Main {
    private static Map<SootMethod, Map<Unit, FlowSet<Value>>> outFacts;

    protected static Transformer createAnalysisTransformer() {

        return new BodyTransformer() {
            @Override
            protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
                System.out.println("Internal Transform");
                DataFlowAnalysisWithSoot analysis = new DataFlowAnalysisWithSoot(body);
                analysis.doAnalysis();
                outFacts.put(body.getMethod(), analysis.getUnitToAfterFlow());
                System.out.print("outFacts"+outFacts);
            }
        };
    }
    public static void main(String[] args) {
        /*String javapath = System.getProperty("java.class.path");
        String jredir = System.getProperty("java.home") + "/lib/rt.jar";
        String path = javapath + File.pathSeparator + jredir;
        System.setProperty("sun.boot.class.path", path);
        System.setProperty("java.ext.dirs", "");
        System.out.println(System.getProperty("sun.boot.class.path"));
        Scene.v().setSootClassPath(jredir);
        Scene.v().extendSootClassPath("/Users/cashman/code/personal/projectWithSoot/esg_generator/target/classes/");
        //Scene.v().setSootClassPath(System.getProperty("user.dir")+"/rt.jar");
        *//*Scene.v().setSootClassPath(System.getProperty("user.dir")+"/target/classes/com/example/testwithsoot");*//*
        System.out.println(Scene.v().getSootClassPath());
        Scene.v().loadNecessaryClasses();
        //Scene.v().addBasicClass("com.example.testwithsoot.A",SootClass.SIGNATURES);
        Scene.v().loadClass("com.example.testwithsoot.A",SootClass.HIERARCHY);
        //Scene.v().loadBasicClasses();
        System.out.println(Scene.v().getClasses());
        *//*CHATransformer.v().transform();
        System.out.println("Hii"+Scene.v().getSootClassPath());
        Scene.v().getSootClass("A");
        SootMethod src=Scene.v().getMainClass().getMethodByName("doStuff");
        CallGraph cg=Scene.v().getCallGraph();*/





        /*Scene.v().loadNecessaryClasses();
        PackManager.v().getPack("wjtp").apply(); // Whole-JTP Pack
        Options.v().setPhaseOption("cg", "enabled:true");
        Options.v().setPhaseOption("cg", "verbose:true"); // Optional, for debugging
        Options.v().setPhaseOption("cg.spark", "on");*/
        /*CallGraph callGraph = scene.getCallGraph();*/
        //SootClass targetClass = Scene.v().getSootClass("com.example.testwithsoot.A"); // Replace with the class you're interested in

        // Print target class
        //System.out.println(targetClass);

        // Print all the methods that call the target method
        /*JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG();
        System.out.println(icfg.getCallersOf(targetClass.getMethodByName("doStuff2")));

        // Print all the methods that call the target method
        Iterator<Edge> edgeIterator = callGraph.edgesInto(targetClass.getMethodByName("doStuff2"));
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            // Process the edge or target method as needed
            System.out.println(edge);
        }*/

        // Set the main class and other options
        String mainClass = "com.example.testwithsoot.A"; // Replace with your main class
        String[] args1 = {
                "-cp",
                ".:/Users/cashman/code/personal/projectWithSoot/esg_generator/src/main/java/:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jce.jar",
                "-debug",
                "-v",
                "-w",
                "-interactive-mode",
                mainClass
        };
        // Initialize Soot
        soot.Main.main(args1);

        Options.v().set_whole_program(true);
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().process_dir();
        Options.v().set_allow_phantom_refs(true);
        //Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb", "verbose:true"); // Optional, for debugging
        Options.v().set_debug(true);

        SootClass c = Scene.v().forceResolve("com.example.testwithsoot.A", SootClass.BODIES);
        System.out.println(c);

        c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
        Transform transform = new Transform("jtp.analysis", createAnalysisTransformer());
        PackManager.v().getPack("jtp").add(transform);
        PackManager.v().runBodyPacks();
        /*for (SootMethod method : c.getMethods()) {
            System.out.println(method.getName());
            if (method.isConcrete()) {
                Body body = method.retrieveActiveBody();
                UnitGraph unitGraph = new BriefUnitGraph(body);
                DataFlowAnalysisWithSoot analysis = new DataFlowAnalysisWithSoot(unitGraph);

                // Now you have an instance of your analysis for this method
                // You can access analysis results or perform other actions here
            }
        }*/

        System.exit(1);
    }
}