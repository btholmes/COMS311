import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class NetworkCopyTest {

     static String graph1 = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph1";
     static String graph2 = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/inf(S)";
     static String graph3;
     static String graph4;

    //     NetworkInfluence networkInfluence;
     static NetworkInfluenceCopySoWeDontModifySamePage network;


    @BeforeClass
    public static void setUp() throws Exception {
        try{
            network = new NetworkInfluenceCopySoWeDontModifySamePage(graph1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // before forcing the double
    @Test(timeout = 60000)
    public  void infA()  {
        assert(network.influence("Ames") == 2.25);
    }

    @Test
    public  void outDegree() {
        assert (network.outDegree("Ames") == 2);
    }

    @Test
    public void outDegree2(){
        assert (network.outDegree("Kansas") == 1);
    }
    @Test
    public void outDegree3(){
        assert (network.outDegree("Minneapolis") == 1);
    }
    @Test
    public void outDegree4(){
        assert (network.outDegree("Chicago") == 0);
    }

    @Test
    public  void inDegree() {
    }

    @Test
    public void shortestPath() throws Exception {
        try{
            ArrayList<String> answer = new ArrayList<>(Arrays.asList("Ames", "Minneapolis"));
            ArrayList<String> path = network.shortestPath("Ames", "Minneapolis");
            for(int i = 0; i < answer.size(); i++){
                assert (answer.get(i).equals(path.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void shortestPath1(){

    }

    @Test
    public void shortestPath2(){

    }

    @Test
    public void shortestPath3(){

    }

    @Test
    public void shortestPath4(){

    }

    @Test
    public void shortestPath5(){

    }

    @Test
    public void influenceSet(){
        try{
            ArrayList<String> set = new ArrayList<>(Arrays.asList("A", "C"));
            assert(network.influence(set) == 3.25);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void influcenceSet2(){
//        try{
//            ArrayList<String> set = new ArrayList<>(Arrays.asList("A", "C"));
//            assert(network.influence(set) == 3.25);
//        }catch(Exception e){
//
//        }
    }

    @Test
    public void influenceSet3(){
//        try{
//            ArrayList<String> set = new ArrayList<>(Arrays.asList("A", "C"));
//            assert(network.influence(set) == 3.25);
//        }catch (Exception e){
//
//        }
    }

    @Test
    public  void distance() {
    }

    @Test
    public  void distance1() {
    }

    @Test
    public  void influence() {
    }

    @Test
    public  void influence1() {
    }

    @Test
    public  void mostInfluentialDegree() {
    }

    @Test
    public  void mostInfluentialModular() {
    }

    @Test
    public  void mostInfluentialSubModular() {
    }

    @Test
    public  void outDegree1() {
    }


    @Test
    public  void distance2() {
    }

    @Test
    public  void distance3() {
    }

    @Test
    public  void influence2() {
    }

    @Test
    public  void influence3() {
    }

    @Test
    public  void mostInfluentialDegree1() {
    }

    @Test
    public  void mostInfluentialModular1() {
    }

    @Test
    public  void mostInfluentialSubModular1() {
    }
}