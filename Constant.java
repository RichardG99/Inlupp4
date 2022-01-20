package org.ioopm.calculator.ast;

public class Constant extends Atom{
    private double value;
    
    public Constant(double value_double) {
    /// Does it need to call super?
    this.value = value_double;
    }
    
    public boolean isConstant(){
        return true;
    }
    
    public double getValue(){
        return this.value;
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
    
    public boolean equals(Object other) {
        if (other instanceof Constant) {
            return this.equals((Constant) other);
        } 
        else {
        return false;
        }
    }

    public boolean equals(Constant other) {
    /// access a private field of other!
        return this.value == other.value;
    }
    
    
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression constant = new Constant(value);
        return constant;
    }
    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }

}