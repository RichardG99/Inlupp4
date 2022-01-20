package org.ioopm.calculator.ast;
import java.util.*;

public class FunctionDeclaration extends SymbolicExpression
{
    public final String name;
    public final Variable[] arguments; 
    public Sequence body;
    
    public FunctionDeclaration(String name, Variable[] arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    public void setBody(Sequence body){
        this.body = body;
    }
    
    public Variable[] getArgs(){
        return this.arguments;
    }
    
    public LinkedList<SymbolicExpression> getSequences(){
        return this.body.getBody();
    }
    @Override
    public String toString(){
        String result = "";
        
        for(Variable x : this.arguments){
            result += x.getName();
        }
        
        return (name + result);
    }
    
    
    @Override
    public SymbolicExpression accept(Visitor v)
    {
        return v.visit(this);
    }    
}