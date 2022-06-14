import java.io.FileNotFoundException;

public class Tree {

    TNode root = null;


    public void CreateTree(int ws, int i, String FilePath) throws FileNotFoundException //yeh i wo gain ke index hai, uss index pa wo course code para hai, jiska grade uthana
    {
        //yaani first node
        if (root == null) {
            TNode temp = new TNode();
            temp.value = i;
            temp.A = null;
            temp.AP = null;
            temp.AM = null;
            temp.B = null;
            temp.BP = null;
            temp.BM = null;
            temp.C = null;
            temp.CP = null;
            temp.CM = null;
            temp.D = null;
            temp.DP = null;
            temp.DM = null;
            temp.F = null;
            root = temp;
        }

        int index = i;
        //agar branch ke condition entropy zero hai tou wo leaf node hoga
        double fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0;

        PreProcessingAndFeaturesEntropy pF = new PreProcessingAndFeaturesEntropy();

        fA = pF.CalFeatureEntropyBranchA(FilePath, index);
        fAP = pF.CalFeatureEntropyBranchAP(FilePath, index);
        fAM = pF.CalFeatureEntropyBranchAM(FilePath, index);
        fB = pF.CalFeatureEntropyBranchB(FilePath, index);
        fBP = pF.CalFeatureEntropyBranchBP(FilePath, index);
        fBM = pF.CalFeatureEntropyBranchBM(FilePath, index);
        fC = pF.CalFeatureEntropyBranchC(FilePath, index);
        fCP = pF.CalFeatureEntropyBranchCP(FilePath, index);
        fCM = pF.CalFeatureEntropyBranchCM(FilePath, index);
        fD = pF.CalFeatureEntropyBranchD(FilePath, index);
        fDP = pF.CalFeatureEntropyBranchDP(FilePath, index);
        fDM = pF.CalFeatureEntropyBranchDM(FilePath, index);
        fF = pF.CalFeatureEntropyBranchF(FilePath, index);

        //ab agar yeh entropies conditional wali zero sa bari hongee tab he iskay agay doosra node ayega


    }


    //traversals

    static String s = "";

    public String preOrderReturn(TNode t) {
        String str = "";

        if (t != null) //yaani tree is not empty
        {
            str = (String) t.value;
            s += str + ",";
            preOrderReturn(t.A);
            preOrderReturn(t.AP);
            preOrderReturn(t.AM);
            preOrderReturn(t.B);
            preOrderReturn(t.BP);
            preOrderReturn(t.BM);
            preOrderReturn(t.C);
            preOrderReturn(t.CP);
            preOrderReturn(t.CM);
            preOrderReturn(t.D);
            preOrderReturn(t.DP);
            preOrderReturn(t.DM);
            preOrderReturn(t.F);

        }

        return s;
    }

    String PreOrderTraversal()
    {
        String p = preOrderReturn(root); //root will be null agar random hou tou
        return p;
    }

    static String m = "";
    //inorder traversal
    public String InOrderReturn(TNode t)
    {
        String str = "";

        if (t != null) //yaani tree is not empty
        {

            InOrderReturn(t.A);
            InOrderReturn(t.AP);
            InOrderReturn(t.AM);
            InOrderReturn(t.B);
            InOrderReturn(t.BP);
            InOrderReturn(t.BM);
            InOrderReturn(t.C);
            InOrderReturn(t.CP);
            InOrderReturn(t.CM);
            InOrderReturn(t.D);
            InOrderReturn(t.DP);
            InOrderReturn(t.DM);
            str = (String) t.value;
            m += str + ",";
            InOrderReturn(t.F);

        }

        return m;
    }


    String InOrderTraversal()
    {
        String i = InOrderReturn(root); //root will be null agar random hou tou
        return i;
    }



    static String o = "";
    //pist order traversal
    public String PostOrderReturn(TNode t)
    {
        String str = "";

        if (t != null) //yaani tree is not empty
        {

            PostOrderReturn(t.A);
            PostOrderReturn(t.AP);
            PostOrderReturn(t.AM);
            PostOrderReturn(t.B);
            PostOrderReturn(t.BP);
            PostOrderReturn(t.BM);
            PostOrderReturn(t.C);
            PostOrderReturn(t.CP);
            PostOrderReturn(t.CM);
            PostOrderReturn(t.D);
            PostOrderReturn(t.DP);
            PostOrderReturn(t.DM);
            PostOrderReturn(t.F);
            str = (String) t.value;
            o += str + ",";

        }

        return o;
    }


    String PostOrderTraversal()
    {
        String pO = PostOrderReturn(root); //root will be null agar random hou tou
        return pO;
    }

}
