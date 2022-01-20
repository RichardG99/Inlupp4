package org.ioopm.calculator.ast;
import java.util.HashMap;
public class Negation extends Unary{
    
    public Negation(SymbolicExpression value){          //ska det vara double? utvärdeas parantesen så tidigt?
        super(value);
    }
    
    public String getName() {
        return "-";
    }
    
        
    
    public boolean equals(Object other) {
        if (other instanceof Negation) {
            return equals((Negation) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals (Negation other){
        return super.getUnaryValue().equals(other.getUnaryValue());
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}