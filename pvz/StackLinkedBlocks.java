/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 10 16
 *
 * Tai yra klasė, realizuojanti projekte apibrėžtą interfeisą (2-as variantas)
   *
   ****************************************************************************/
 package lab3_pasirinktinis_adt;

/**
 * Linked-Array-based implementation of the stack.
 */
class Block<T>{
    T[] array;
    Block<T> previous;
    Block (int blockSize){
        array = (T[])new Object[blockSize];
    }
}
public class StackLinkedBlocks<T> implements StackClassic<T> {
    private T [ ]    currentArray;
    private Block<T> currentBlock;
    private int      topOfStack;
    private int blockSize;

    /**
     * Construct the stack.
     */
    public StackLinkedBlocks(int blockSize ) {
        currentBlock = new Block<T>( blockSize );
        currentArray = currentBlock.array;
        currentBlock.previous = null;
        topOfStack = -1;
        this.blockSize = blockSize;
    }
    public StackLinkedBlocks() {
        this(256);
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
                 "\nSteko klaida: vykdoma peek operacija tuščiam stekui");
        return currentArray[topOfStack];
    }

    /**
     * Return and remove the most recently inserted item
     * from the stack.
     * @return the most recently inserted item in the stack.
     */
    public T pop() {
        if( isEmpty( ) )
            throw new StackException(
                 "\nSteko klaida: vykdoma pop operacija tuščiam stekui");
        T obj = currentArray[topOfStack--];
        if(topOfStack < 0){
            if(currentBlock.previous != null){
                currentBlock = currentBlock.previous;
                currentArray = currentBlock.array;
                topOfStack = blockSize-1;
            }
        }
        return obj;
    }

    /**
     * Insert a new item into the stack.
     * @param obj
     */
    public void push(T obj) {
        if( ++topOfStack == blockSize ){
            Block<T> b = new Block<T>(blockSize);
            b.previous = currentBlock;
            currentArray = b.array;
            currentBlock = b;
            topOfStack = 0;
        }
        currentArray[topOfStack] = obj;
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
        currentBlock.previous = null;
        topOfStack = -1;
    }
    protected void println(){
        System.out.println("SPAUSDINAMAS STEKO TURINYS:");
        if (topOfStack < 0)
            System.out.println("STEKAS VISAI NETURI ELEMENTŲ");
        else {
            Block<T> curr = currentBlock;
            int k =topOfStack;
            do {
                for (int i = k; i>=0; i--) 
                    System.out.println(curr.array[i]);
                System.out.println("***************");
                curr = curr.previous;
                k = blockSize-1;
            }while (curr != null);
        }
    }
}
/**
 * Exception class for access in empty containers
 * such as stacks, queues, and priority queues.
 * @author Mark Allen Weiss
 */
class StackException extends RuntimeException {
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public StackException( String message ) {
        super( message );
    }
} 