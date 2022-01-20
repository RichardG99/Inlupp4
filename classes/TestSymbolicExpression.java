import org.junit.Test;
import static org.junit.Assert.*;
import org.ioopm.calculator.ast.*;
import java.lang.Math;

public abstract class TestSymbolicExpression
{
    @Test
    public abstract void testIsConstant();

    @Test
    public abstract void testIsCommand();

    @Test
    public abstract void testIsCondition();

    @Test
    public abstract void testGetPriority();

    @Test
    public abstract void testGetValue();

    @Test
    public abstract void testToString();

    @Test
    public abstract void testEquals();

    @Test
    public abstract void testAccept();
}