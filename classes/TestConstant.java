import org.junit.Test;
import static org.junit.Assert.*;
import org.ioopm.calculator.ast.Constant;
import java.lang.Math;

public class TestConstant extends TestSymbolicExpression
{
    @Override
    @Test
    public void testIsConstant()
    {
        double value = 42;
        String msg   = "testing isConstant() on double";
        Constant const1 = new Constant(value);
        assertTrue(msg,  const1.isConstant());
    }

    @Override
    @Test
    public void testIsCommand()
    {
        String msg   = "testing isCommand()";
        double value = 42;
        assertFalse(msg, new Constant(value).isCommand());
    }

    @Override
    @Test
    public void testIsCondition() 
    {
        double value = 42;
        assertFalse(false);
    }

    @Override
    @Test
    public void testGetPriority()
    {
        double value = 42;
        int priority = 1;
        assertEquals(new Constant(value).getPriority(), priority);
    }

    @Override
    @Test
    public void testGetValue()
    {
        double value = 42;
        double delta = 0;
        assertEquals(new Constant(value).getValue(), value, delta);
    }

    @Override
    @Test
    public void testToString()
    {
        double value = 42;
        assertEquals(new Constant(value).toString(), Double.toString(value));
    }

    @Override
    @Test
    public void testEquals()
    {
        double value1 = 42;
        double value2 = value1 / 2;

        assertEquals(new Constant(value1), new Constant(value1));
        assertFalse(new Constant(value1).equals(new Constant(value2)));
    }

    @Override
    @Test
    public void testAccept()
    {
        double value = 42;

       assertFalse(false);
    }
}