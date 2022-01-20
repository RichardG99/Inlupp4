package org.ioopm.calculator.ast;
import java.util.*;

public class Sequence extends SymbolicExpression
{
    public LinkedList<SymbolicExpression> body;
    
    public Sequence(LinkedList<SymbolicExpression> body)
    {
        this.body = body;
    }
    
    public LinkedList<SymbolicExpression> getBody(){
        return this.body;
    }
    
    @Override
    public SymbolicExpression accept(Visitor v)
    {
        return v.visit(this);
    }    
}