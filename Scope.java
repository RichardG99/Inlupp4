package org.ioopm.calculator.ast;

import org.ioopm.calculator.*;


public class Scope extends SymbolicExpression 
{
    public final SymbolicExpression arg;

    public Scope(SymbolicExpression arg)
    {
        this.arg = arg;
    }
 
    @Override
    public int getPriority() 
    {
        return 60;
    }

    @Override
    public double getValue()
    {
        return arg.getValue();
    }

    @Override
    public SymbolicExpression accept(Visitor visitor) 
    {
        return visitor.visit(this);
    }
    
    @Override
    public String toString()
    {
        return "{" + this.arg + '}';
    }
    
    @Override
    public String getName()
    {
        return "{" + this.arg + '}';
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof Scope 
            && this.equals((Scope) other);
    }

    public boolean equals(Scope other)
    {
        return this.arg.equals(other.arg);
    }
}
