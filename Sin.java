package org.ioopm.calculator.ast;
import java.lang.Math;
import java.util.HashMap;

public class Sin extends Unary {
    
    public Sin(SymbolicExpression value){  
        super(value);
    }
    
    public String getName() {
        return "sin";
    }
    
    public boolean equals(Object other) {
        if (other instanceof Sin) {
            return equals((Sin) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals (Sin other){
        return super.getUnaryValue().equals(other.getUnaryValue());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}
