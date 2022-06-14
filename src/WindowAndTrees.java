import java.io.FileNotFoundException;

public class WindowAndTrees
{

    Tree t = new Tree();
    public void SelectFeatures(int winSize, int start) throws FileNotFoundException {
        //user will send window size which can be any digit from 1-15 bcs total 15 courses hain,
        int wS = 0;
        wS = winSize;


        int index = 0; //yeh features ke index hai
        int end = wS + start; //issay wo additon hota rahay ga bcs start ko hum increment karayenge

        double[] gain = new double[20]; //yaani itnay fearures ka gain hum iss array ma store kardainga
        //takay phir ismay sa baad ma max nikalain takay wo root node bana sakain

        //initialising gain array takay null value na aye
        for(int i =0;i< gain.length;i++)
        {
            gain = new double[]{0.0};
        }


        PreProcessingAndFeaturesEntropy pF = new PreProcessingAndFeaturesEntropy();

        double dsEn = pF.CalDSEntropy("PreProcessed.csv");
        for(int k=0;k<=end;k++)
        {
           gain[k] =  pF.CalWeiEntropyInfoGain("PreProcessed.csv", k, dsEn);
        }

        //now we'll find maximum gain of the features
        double maxGain = 0.0;

        maxGain = gain[0];
        int l;
        for( l=0;l<=gain.length;l++)
        {
            if(gain[l] > maxGain)
            {
                maxGain = gain[l];
            }
        }

        //now ab max value aagayi, make that root node
        //first find k wp kis course kee thee
        int fInd = 0;
        for(l=0;l<gain.length;l++)
        {
            if(maxGain == gain[l])
                fInd = l;

        }

        //tree declared above gloabllay, takay recursive call ma masla na aye
        t.CreateTree(wS, fInd,"PreProcessed.csv" );



    }
}
