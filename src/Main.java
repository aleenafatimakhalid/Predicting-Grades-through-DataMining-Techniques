import java.io.IOException;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException {

        PreProcessingAndFeaturesEntropy p = new PreProcessingAndFeaturesEntropy();
        //dataset preprocess hogaya
        p.PreProcessData("Dataset.csv");
        WindowAndTrees w = new WindowAndTrees();
        Scanner sc = new Scanner(System.in);

        int wS = 0;
        System.out.println("Give window size: "); //ws ka array out of bounds aaraha
        wS = sc.nextInt();


        Tree t = new Tree();
        w.SelectFeatures(wS,0);
        t.preOrderReturn(t.root);
        t.InOrderReturn(t.root);
        t.PostOrderReturn(t.root);


        System.out.println("done");


    }
}
