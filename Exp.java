package org.ioopm.calculator.ast;
import java.lang.Math;
import java.util.HashMap;

public class Exp extends Unary{
    
   public Exp(SymbolicExpression value){          //ska det vara double? utvärdeas parantesen så tidigt?
        super(value);
    }
    
    public String getName() {
        return "^";
    }
    
    public int getPriority(){
        return 5;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Exp) {
            return equals((Exp) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals (Exp other){
        return getUnaryValue().equals(other.getUnaryValue());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
    
}