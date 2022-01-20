package org.ioopm.calculator.ast;
public abstract class Command extends SymbolicExpression{
    
    public SymbolicExpression eval(Environment vars){
        throw new IllegalExpressionException("Error: Commands may not be evaluated.");
    }
    
    public boolean isCommand(){
        return true;
    }


}