package org.ioopm.calculator.ast;

public abstract class Binary extends SymbolicExpression{
    private SymbolicExpression lhs;
    private SymbolicExpression rhs;
    
    public Binary(SymbolicExpression leftHandSide, SymbolicExpression rightHandSide){
        if(leftHandSide == null || rightHandSide == null) throw new IllegalArgumentException("leftHandSide or rightHandSide can't be null"); 
        this.lhs = leftHandSide;
        this.rhs = rightHandSide;
    }
    
    public String toString() {
    /// Note how the call to toString() is not necessary
    if(lhs.getPriority() > rhs.getPriority()) return ("(" + lhs + ")" + " " + this.getName() + " " + rhs);
    
    else if(lhs.getPriority() < rhs.getPriority()) return (lhs + " " + this.getName() + " " + "(" + rhs + ")");
    
    else return ( lhs + " " + this.getName() + " " + rhs );
    }
    
    public SymbolicExpression getLhs(){
        return this.lhs;
    }
    
    public SymbolicExpression getRhs(){
        return this.rhs;
    }
    
    
}

