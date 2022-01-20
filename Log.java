package org.ioopm.calculator.ast;
import java.lang.Math;
import java.util.HashMap;

public class Log extends Unary{
 
    public Log(SymbolicExpression value){          //ska det vara double? utvärdeas parantesen så tidigt?
        super(value);
    }
    
    public String getName() {
        return "log";
    }
    
    
    public boolean equals(Object other) {
        if (other instanceof Log) {
            return equals((Log) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals (Log other){
        return super.getUnaryValue().equals(other.getUnaryValue());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}