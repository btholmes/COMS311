import org.junit.BeforeClass;
import org.junit.Test;


public class AllTests {

    static String graph1;
    static String graph2;
    static String graph3;
    static String graph4;

//    static NetworkInfluence networkInfluence;
    static NetworkInfluenceCopySoWeDontModifySamePage network;

    // ================================================================
    // SETUP
    // ================================================================
    @BeforeClass
    public static void setUp() throws Exception {
        try{

            graph1 = "4\n" +
                    "Ames  Minneapolis\n" +
                    "Ames Kansas\n" +
                    "Kansas Minneapolis\n" +
                    "Minneapolis Chicago";



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // before forcing the double
    @Test(timeout = 60000)
    public void infA() throws Exception {
        assert(network.influence("Ames") == 2.25);
    }

    @Test(timeout = 60000)
    public void infB() throws Exception {
        assert(network.influence("Ames") == 2.25);
    }



}
