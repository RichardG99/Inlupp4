package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Subtraction extends Binary{

    public Subtraction(SymbolicExpression lhs, SymbolicExpression rhs){
        super(lhs, rhs);
    }
    
    public String getName() {
        return "-";
    }
    
    public int getPriority(){
        return 50;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Subtraction) {
            return equals ((Subtraction) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals(Subtraction other){
        return (super.getLhs().equals(other.getLhs()) == super.getRhs().equals(other.getRhs()));
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}