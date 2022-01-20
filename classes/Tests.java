
import org.junit.Test;
import static org.junit.Assert.*;
import org.ioopm.calculator.ast.*;
import java.lang.Math;

public class Tests {
   
    /*
    @Test
    public void testAddition() {
        
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Constant c3 = new Constant(15);
        
        Environment vars = new Environment();
        Addition a1 = new Addition(c1, c2);
        
        // assert statements
        assertTrue(c3.equals(a1.eval(vars)));
        assertEquals(c3.toString(), "15.0");
        assertTrue(a1.getName() == "+");
        assertFalse(a1.isCommand());
        assertFalse(a1.isConstant());
        assertEquals(a1.getPriority(), 50);
        try{
            double result = a1.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        
    }*/
    /*
    @Test
    public void testSubtraction(){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Constant c3 = new Constant(15);
        
        Environment vars = new Environment();
        Subtraction s1 = new Subtraction(c3, c2);
        
        assertTrue(c1.equals(s1.eval(vars)));
        assertTrue(s1.getName() == "-");
        assertFalse(s1.isCommand());
        assertFalse(s1.isConstant());
        assertEquals(s1.getPriority(), 50);
        try{
            double result = s1.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
    }*/
    /*
    @Test
    public void testMultiplication(){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Constant c3 = new Constant(50);
        
        Environment vars = new Environment();
        Multiplication m1 = new Multiplication(c1, c2);
        
        assertTrue(c3.equals(m1.eval(vars)));
        assertTrue(m1.getName() == "*");
        assertFalse(m1.isCommand());
        assertFalse(m1.isConstant());
        assertEquals(m1.getPriority(), 100);
        try{
            double result = m1.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
    }*/
    /*
    @Test
    public void testDivision(){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Constant c3 = new Constant(50);
        
        Environment vars = new Environment();
        Division d1 = new Division(c3, c2);
        
        assertTrue(c1.equals(d1.eval(vars)));
        assertTrue(d1.getName() == "/");
        assertFalse(d1.isCommand());
        assertFalse(d1.isConstant());
        assertEquals(d1.getPriority(), 100);
        try{
            double result = d1.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
    } */
    @Test
    public void testConstant(){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(10);
        Constant c3 = new Constant(10);
        
        Environment vars = new Environment();
        
        assertTrue(c2.equals(c3));
        try{
            c1.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertFalse(c1.isCommand());
        assertTrue(c1.isConstant());
        assertEquals(c1.getPriority(), 1);
        assertEquals(c1.getValue(), 5.0, 0.0);
    
    }
    @Test
    public void testNamedConstant(){
        
        Environment vars = new Environment();
        SymbolicExpression pi2 = new NamedConstant("pi2", Math.PI);
        
        assertFalse(Constants.namedConstants.get("pi").equals(pi2));
        
        assertEquals(Constants.namedConstants.get("pi"), 3.14, 0.1);
     
        assertFalse(pi2.isCommand());
        assertTrue(pi2.isConstant());
        assertEquals(pi2.getPriority(), 1);
        try{
            pi2.getValue();
            assertFalse(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        
    
    }
    @Test
    public void testAssigment(){
        
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Constant c1 = new Constant(5);
        Assignment ass1 = new Assignment(x, c1);
        Assignment ass2 = new Assignment(y, c1);
        Assignment ass3 = new Assignment(x, c1);
        
        assertTrue(ass1.equals(ass3));
        assertFalse(ass1.equals(ass2));
     
        assertFalse(ass1.isCommand());
        assertFalse(ass1.isConstant());
        assertEquals(ass1.getPriority(), 1);
        try{
            ass1.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            ass1.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            ass1.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        
    }
    @Test
    public void testCos(){
        Cos cos = new Cos(new Addition(new Constant(0), new Constant(1)));
        assertTrue(cos.getName() == "cos");
        assertFalse(cos.isConstant());
        assertFalse(cos.isCommand());
        try{
            cos.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(cos.getPriority(), 1);
    }
    
    @Test
    public void testSin(){
        Sin sin = new Sin(new Addition(new Constant(0), new Constant(1)));
        assertTrue(sin.getName() == "sin");
        assertFalse(sin.isConstant());
        assertFalse(sin.isCommand());
        try{
            sin.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(sin.getPriority(), 1);
    }
    
    @Test
    public void testLog(){
        Log log = new Log(new Constant(1));
        assertTrue(log.getName() == "log");
        assertFalse(log.isConstant());
        assertFalse(log.isCommand());
        try{
            log.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(log.getPriority(), 1);
    }
    @Test
    public void testExp(){
        Exp exp = new Exp(new Constant(1));
        assertTrue(exp.getName() == "^");
        assertFalse(exp.isConstant());
        assertFalse(exp.isCommand());
        try{
            exp.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(exp.getPriority(), 5);
    }
    @Test
    public void testClear(){
        Clear clr = Clear.instance();
        assertFalse(clr.isConstant());
        assertTrue(clr.isCommand());
        try{
            clr.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            clr.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(clr.getPriority(), 1);
    }
    @Test
    public void testQuit(){
        Quit q = Quit.instance();
        assertFalse(q.isConstant());
        assertTrue(q.isCommand());
        try{
            q.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            q.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(q.getPriority(), 1);
    }
    @Test
    public void testNegation(){
        Negation neg  = new Negation(new Constant(5));
        assertFalse(neg.isConstant());
        assertFalse(neg.isCommand());
        assertEquals(neg.getName(), "-");
        try{
            neg.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(neg.getPriority(), 1);
    }
    @Test
    public void testVariable(){
        Variable x = new Variable("x");
        assertFalse(x.isConstant());
        assertFalse(x.isCommand());
        assertTrue(x.isVariable());
        assertEquals(x.getName(), "x");
        try{
            x.getValue();
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(x.getPriority(), 1);
        
    }
    @Test
    public void testVars(){
        Vars vars = Vars.instance();
        assertFalse(vars.isConstant());
        assertTrue(vars.isCommand());
        assertEquals(vars.getPriority(), 1);
        try{
            vars.getValue();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        try{
            vars.getName();
            assertFalse(true);
        }
        catch(Exception e){
            assertTrue(true);
        }
        
    }
}