package org.ioopm.calculator.ast;
import java.util.*;

public class FunctionCall extends SymbolicExpression
{
    public LinkedList<FunctionDeclaration> list;
    
    public FunctionCall(){
        this.list = new LinkedList<FunctionDeclaration>();
    }
    
    public LinkedList<FunctionDeclaration> getList(){
        return this.list;
    }
    
    @Override
    public SymbolicExpression accept(Visitor v)
    {
        return v.visit(this);
    }
    
    public void insertFunc(FunctionDeclaration func){
        this.list.add(func);
    }
}