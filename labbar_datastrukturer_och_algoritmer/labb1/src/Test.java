import java.util.Random;

public class Test {
    public static void main(String[] args) {

        //Small test
        System.out.println("Hash table test for capacity 7\n");

        SymbolTable stSmall = new SymbolTable(7);
        stSmall.put("the",'c');
        stSmall.put("eht",'c');
        stSmall.put("het",'c');

        stSmall.dump();

        System.out.println("\nSize : "+stSmall.size());

        System.out.println("\nPosition of eth in the hash table is: "+stSmall.get("eht"));

        stSmall.delete("eht");

        System.out.println("\nPosition of eth after delete in the hash table is: "+stSmall.get("eht"));

        System.out.println("\nSize : "+stSmall.size());

        System.out.println("\nDump of hash table");

        stSmall.dump();

        System.out.println("\nDump after insert het with val==null");

        stSmall.put("het", null);

        stSmall.dump();

        System.out.println("\nStress test with capacity 100 and random length keys and random values");

        //Stress test for collisions
        SymbolTable st = new SymbolTable(100);

        for (int i=0; i<100; i++){
            Random rnd = new Random();
            char c = (char) ('a' + rnd.nextInt(26));

            int rndLength = rnd.nextInt(50);

            String key = "";
            for (int j=0; j < rndLength; j++){
                int rndChar = rnd.nextInt(32,126);
                key = key + (char) rndChar;
            }

            st.put(key,c);
        }

        st.dump();
        System.out.println("Size: " + st.size());
    }
}
