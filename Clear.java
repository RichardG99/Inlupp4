package org.ioopm.calculator.ast;

public class Clear extends Command {
    private static final Clear theInstance = new Clear();
    private Clear() {}
    
    public static Clear instance() {
        return theInstance;
    }
    
    public void run(Environment vars){
        vars.clear();
    }
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}