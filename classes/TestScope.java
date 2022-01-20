import org.junit.Test;
import static org.junit.Assert.*;
import org.ioopm.calculator.ast.*;
import java.lang.Math;

public class TestScope extends TestSymbolicExpression 
{
    Constant constant = new Constant(2);
    Scope scope = new Scope(constant);
        
    @Override
    @Test
    public void testIsConstant()
    {
        assertFalse(scope.isConstant());
    }

    @Override
    @Test
    public void testIsCommand()
    {
        assertFalse(scope.isCommand());
    }

    @Override
    @Test
    public void testIsCondition() 
    {
        assertFalse(scope.isCondition());
    }
    
    @Override
    @Test
    public void testGetPriority()
    {
        assertEquals(scope.getPriority(), 60);        
    }
    
    @Override
    @Test
    public void testGetValue()
    {
        
        assertTrue(false);        
    }

    @Override
    @Test
    public void testAccept()
    {
        assertTrue(false);        
    }

    @Override
    @Test
    public void testToString()
    {
        assertTrue(false);        
    }
    
    @Test
    public void testGetName()
    {
        assertTrue(false);
    }
    
    @Override
    @Test
    public void testEquals()
    {
        assertTrue(false);        
    }

}