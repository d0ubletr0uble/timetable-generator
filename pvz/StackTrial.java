/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 10 16
 *
 * Tai yra klasė, kurioje atliekami realizuoto ADT bandymai
   *
   ****************************************************************************/
package lab3_pasirinktinis_adt;

import java.io.PrintStream;

public class StackTrial {
        final private static PrintStream p = System.out;

    public static void demoOperacijosSuSteku() {
//        StackLinkedBlocks<String> s1 = new StackLinkedBlocks<String>(3);
        StackFixedArray<String> s1 = new StackFixedArray<String>();
        s1.push("Labas");
        s1.push("rytas");
        s1.push("pasauli");
        s1.push("jau");
        s1.push("graži");
        s1.push("diena");
        s1.push("išaušo");
        s1.println();
        p.println("Nuimame nuo viršaus= " + s1.pop());
        p.println("Nuimame nuo viršaus= " + s1.pop());
        s1.println();

        p.println("Stekas yra "+(s1.isEmpty()? "tuščias": "dar gyvas"));
        s1.clear();
        s1.println();
//        s1.pop(); atidenkite, kad įsitikinti, jog klaida yra gaudoma
        p.println("Stekas yra "+(s1.isEmpty()? "tuščias": "dar gyvas"));
        // sukeliame steko perpildymo situaciją
        for (int i = 0; i < 66; i++) {
            s1.push("Element"+Integer.toString(i));

        }
    }
    public static void greitaveikosTyrimas() {
        int blockSize = 200;
        int stackSize = 1000000;
        StackLinkedBlocks<Integer> s1 =
                           new StackLinkedBlocks<Integer>(blockSize);
        long t0 = System.nanoTime();
        for (int k = 0; k < 8; k++) {
            for (int i = 0; i < stackSize; i++) {
                s1.push(i);
            }
//            s1.println();
            for (int i = 0; i < stackSize; i++) {
                Integer in = s1.pop();
//                System.out.println("pop="+in);
            }
//            System.gc(); System.gc(); System.gc();
        }
        long t1 = System.nanoTime();
        System.out.println("dt="+(t1-t0)*1e-9);
    }
    public static void main(String[] args) {
        // Klaidų srautą err sutapatiname su out, tikslu,
        // kad klaidų pranešimai sinchronizuotųsi su rezultatų išvedimu
        System.setErr(p);
        demoOperacijosSuSteku();
        greitaveikosTyrimas();
    }

}
