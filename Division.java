package org.ioopm.calculator.ast;
import java.util.HashMap;
public class Division extends Binary{
    
    public Division(SymbolicExpression lhs, SymbolicExpression rhs){
        super(lhs, rhs);
    }
    
    public String getName() {
        return "/";
    }
    
    public int getPriority(){
        return 100;
    }
    
        
    public boolean equals(Object other) {
        if (other instanceof Division) {
            return equals ((Division) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals(Division other){
        return (super.getLhs().equals(other.getLhs()) == super.getRhs().equals(other.getRhs()));
    }
    
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}