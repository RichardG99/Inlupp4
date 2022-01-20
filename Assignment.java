package org.ioopm.calculator.ast;


public class Assignment extends Binary {
    
     public Assignment(SymbolicExpression lhs, SymbolicExpression rhs){
        super(lhs, rhs);
    }
    
    public boolean equals(Object other) {
        if (other instanceof Assignment) {
            return equals ((Assignment) other);
        } 
        else {
            return false;
        }
    }
    
    public boolean equals(Assignment other){
        return (super.getLhs().equals(other.getLhs()) == super.getRhs().equals(other.getRhs()));
    }
    
    @Override
    public String toString(){
        return (this.getLhs() + " = " + this.getRhs());
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
