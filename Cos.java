package org.ioopm.calculator.ast;
import java.lang.Math;
import java.util.HashMap;
public class Cos extends Unary{
    
    public Cos(SymbolicExpression value){          //ska det vara double? utvärdeas parantesen så tidigt?
        super(value);
    }
    
    public String getName() {
        return "cos";
    }
    
    public boolean equals(Object other) {
        if (other instanceof Cos) {
            return equals((Cos) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals (Cos other){
        return super.getUnaryValue().equals(other.getUnaryValue());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}
