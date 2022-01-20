package org.ioopm.calculator.ast;

//import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
import java.util.*;

public class Conditional extends SymbolicExpression 
{
    public final SymbolicExpression idL;
    public final SymbolicExpression idR;
    public final String operator;
    public final SymbolicExpression ifStatement;
    public final SymbolicExpression elseStatement;

    public Conditional(SymbolicExpression idL, SymbolicExpression idR, String operator, SymbolicExpression ifStatement, SymbolicExpression elseStatement)
    {
        this.idL = idL;
        this.idR = idR;
        this.operator = operator;
        this.ifStatement    = ifStatement;
        this.elseStatement   = elseStatement;
    }

    @Override
    public int getPriority() 
    {
        return 10;
    }

    @Override
    public double getValue()
    {
        throw new RuntimeException("getValue() called on expression with no value");
    }

    public String getOperator() 
    {
        return "if";
    }
    
    public String getIdOperator(){
        return this.operator;
    }
    
    public SymbolicExpression getIf(){
        return this.ifStatement;
    }
    
    public SymbolicExpression getElse(){
        return this.elseStatement;
    }
    
    public SymbolicExpression getidL(){
        return this.idL;
    }
    
    public SymbolicExpression getidR(){
        return this.idR;
    }

    @Override
    public String toString()
    {
        return getOperator() + " (" + idL + operator + idR + ") " + ifStatement + " else " + elseStatement;
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof Conditional
            && this.equals((Conditional) other);
    }

    public boolean equals(Conditional other)
    {
        return idL.equals(other.idL)
            && idR.equals(other.idR)
            && operator.equals(other.operator)
            && ifStatement.equals(other.ifStatement)
            && elseStatement.equals(other.elseStatement);
    }

    @Override
    public SymbolicExpression accept(Visitor visitor) 
    {
        return visitor.visit(this);
    }
}