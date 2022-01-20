package org.ioopm.calculator.ast;
import java.util.*;

public class Vars extends Command {
    private static final Vars theInstance = new Vars();
    private Vars() {}
    
    public static Vars instance() {
        return theInstance;
    }
    
    /*public void run(Environment vars){
        System.out.println("All Variables:" + vars);
    }*/
    public void run(Environment vars){
        StringBuilder sb = new StringBuilder();
        sb.append("Variables: ");
        TreeSet<Variable> variables = new TreeSet<>(vars.keySet());
        for (Variable v : variables) {
            sb.append(v.getName());
            sb.append(" = ");
            sb.append(vars.get(v));
            sb.append(", ");
            }
        sb.deleteCharAt(sb.length()-1);  
        sb.deleteCharAt(sb.length()-1);  
        System.out.println(sb.toString());
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}