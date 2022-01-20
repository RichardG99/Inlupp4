package org.ioopm.calculator.ast;

import java.util.HashMap;

public abstract class SymbolicExpression{
    
    public boolean isConstant(){
        return false;
    }
    
    public String getName() {
        throw new RuntimeException("getName() called on expression with no operator IN SYMBOLIC");
    }
    
    public int getPriority(){
        return 1;
    }
    
    public double getValue(){
        throw new RuntimeException("getValue() called on expression with no value");
    }
    
    public boolean isVariable(){
        return false;
    }
    
    public Variable getVariable(){
        throw new RuntimeException("getVariable() called on non-Variable expression");
    }
    
    public boolean isCommand(){
        return false;
    }
    
    public abstract SymbolicExpression accept(Visitor v);
}