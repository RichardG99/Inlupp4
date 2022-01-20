package org.ioopm.calculator.ast;

public abstract class Unary extends SymbolicExpression{
    private final SymbolicExpression value;
    
    public Unary(SymbolicExpression value1){
        if(value1 == null) throw new IllegalArgumentException("Expected something inside sin/cos/log/exp!");
        this.value = value1;
    }
    
    public String toString() {
    /// Note how the call to toString() is not necessary
        return this.getName() + "(" + this.value.toString() + ")";
    }
    
    public SymbolicExpression getUnaryValue(){
        return this.value;
    }
}