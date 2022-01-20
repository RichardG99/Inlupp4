package org.ioopm.calculator.ast;
import java.util.*;

public class FunctionConstants extends SymbolicExpression{
    
    public FunctionDeclaration func;
    public String name;
    private LinkedList<SymbolicExpression> constants;
    
    public FunctionConstants(FunctionDeclaration func, LinkedList<SymbolicExpression> constants){
        
        this.func = func;
        this.constants = constants;
    }
    
    public FunctionDeclaration getDec(){ 
        return this.func;
    }
    
    public String getname(){
        return this.name;
    }
    
    public void setDec(FunctionDeclaration f){
        this.func = f;
    }
    
    public LinkedList<SymbolicExpression> getConstants(){
        return this.constants;
    }
    
    @Override
    public SymbolicExpression accept(Visitor v)
    {
        return v.visit(this);
    }
    
    /*public String toString(){
        String result = "";
        for(SymbolicExpression x : constants){
            result += x.toString();
            result += "  ";
        }
        return this.func.toString() + result;
    }*/
}