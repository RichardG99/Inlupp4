package org.ioopm.calculator.ast;
import java.util.HashMap;
import java.util.*;

public class Variable extends Atom implements Comparable<Variable>{
    private String identifier;
    
    public Variable(String string){
        this.identifier = string;
    }
    
    public String getName() {
        return this.identifier;
    }
    
    public Variable getVariable(){
        return this;
    }
    
    public String toString() {
        return this.identifier;
    }
    
    public boolean isVariable(){
        return true;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return this.equals((Variable) other);
        } 
        else {
            return false;
        }
    }

    public boolean equals(Variable other) {
        /// access a private field of other!
        return this.identifier.hashCode() == other.identifier.hashCode();
        // return (0 == this.identifier.compareTo(other.identifier));
    }
    
    public int hashCode(){
        return this.identifier.hashCode();
    }
    @Override
    public int compareTo(Variable v2){
        return this.getName().compareTo(v2.getName());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}