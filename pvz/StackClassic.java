/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 10 16
 *
 * Tai yra projekte realizuojamos duomenų struktūros interfeisas,
 * kurį toliau realizuoja dvi klasės (du variantai)
   *
   ****************************************************************************/
//          PUBLIC OPERATIONS
// void push( x )         --> Insert x
// Object peek( )         --> Return most recently inserted item
// Object pop( )          --> Return and remove most recent item
// boolean isEmpty( )     --> Return true if empty; else false
// void clear( )          --> Remove all items and go to initianal state
// ******************ERRORS********************************
// peek, pop on empty stack

package lab3_pasirinktinis_adt;
public interface StackClassic<T> {
        /**
         * Insert a new item into the stack.
     * @param obj
         */
        void push( T obj );

        /**
         * Get the most recently inserted item in the stack.
         * Does not alter the stack.
         * @return the most recently inserted item in the stack.
         */
        T peek( );


        /**
         * Return and remove the most recently inserted item
         * from the stack.
         * @return the most recently inserted item in the stack.
         */
        T pop();

        /**
         * Test if the stack is logically empty.
         * @return true if empty, false otherwise.
         */
        boolean isEmpty( );

        /**
         * Make the stack logically empty.
         */
        void clear( );
    }