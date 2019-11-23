/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 10 16
 *
 * Tai yra klasė, realizuojanti projekte apibrėžtą interfeisą (1-as variantas)
   *
   ****************************************************************************/
package lab3_pasirinktinis_adt;
import java.io.PrintStream;

/**
 * Array-based implementation of the stack.
 * @param <T>
 */

public class StackFixedArray<T> implements StackClassic<T> {
    private static PrintStream p = System.out;
    private T[] stackArray;
    private int topOfStack;
    private int stackSize;

    /**
     * Construct the stack.
     * @param stackSize
     */
    public StackFixedArray(int stackSize ) {
        stackArray = (T[])new Object[stackSize];
        topOfStack = -1;
        this.stackSize = stackSize;
    }
    public StackFixedArray() {
        this(64);
    }
    /**
     * Get the most recently inserted item in the stack.
     * Does not alter the stack.
     * @return the most recently inserted item in the stack.
     * @throws StackException if the stack is empty.
     */
    public T peek( ) {
        if( topOfStack < 0 )
            throw new StackException(
                 "Steko klaida: vykdoma peek operacija tuščiam stekui");
        return stackArray[topOfStack];
    }

    /**
     * Return and remove the most recently inserted item from the stack.
     * @return the most recently inserted item in the stack.
     */
    public T pop() {
        if( isEmpty( ) )
            throw new StackException(
                 "Steko klaida: vykdoma pop operacija tuščiam stekui");
        return stackArray[topOfStack--];
    }

    /**
     * Insert a new item into the stack.
     * @param obj
     */
    public void push(T obj) {
        if( ++topOfStack < stackSize )
            stackArray[topOfStack] = obj;
        else {
            p.println("Paskutinis elementas prieš klaidą yra:" +
                    stackArray[topOfStack-1]);
            throw new StackException(
                "Steko klaida: vykdant push operaciją - masyvo perpildymas");
        }
    }
    /**
     * Test if the stack is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return topOfStack == -1;
    }

    /**
     * Make the stack logically empty.
     */
    public void clear( ) {
        topOfStack = -1;
    }
    protected void println(){
        p.println("SPAUSDINAMAS STEKO TURINYS:");
        if (topOfStack < 0)
            p.println("STEKAS VISAI NETURI ELEMENTŲ");
        else {
            for (int i = topOfStack; i>=0; i--)
                p.println(stackArray[i]);
            p.println("***************");
        }
    }

}
