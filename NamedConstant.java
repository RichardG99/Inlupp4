package org.ioopm.calculator.ast;
import java.util.HashMap;

public class NamedConstant extends Atom{
    private String identifier;
    private double value;
    
    public NamedConstant(String string, double givenInt){
        this.identifier = string;
        this.value = givenInt;
    }
    
    public String getName() {
        return this.identifier;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public String toString() {
        return this.identifier + " = " + this.value;
    }
    
    public boolean isConstant(){
        return true;
    }
    
    public boolean equals(Object other) {
        if (other instanceof NamedConstant) {
            return this.equals((NamedConstant) other);
        } 
        else {
            return false;
        }
    }

    public boolean equals(NamedConstant other) {
        /// access a private field of other!
        return this.identifier == other.identifier;
    }
    
    public SymbolicExpression eval(Environment vars) {
        return new Constant(value);
    }
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}