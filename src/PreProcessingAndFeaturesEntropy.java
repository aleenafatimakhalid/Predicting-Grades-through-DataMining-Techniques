import java.io.*;
import java.util.*;
import java.lang.*;

public class PreProcessingAndFeaturesEntropy
{
    //will process data and return it into a ll as strings
    //will also write that data into the preprocessed file jispa phir agay kaam hoga

    LinkedList<String> PreProcessData(String FilePath) throws IOException
    {
        LinkedList<String> LP = new LinkedList<>();
        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        //declaring details for dataset
        String rollNo ="";
        String sem = "";
        String cCode;
        String cTitle;
        String cCredH;
        String cGrade;
        String gpa;
        String sGpa = "";
        String cGpa = "";
        String warning = "";

        //Student class obj
        Students<String> s = new Students<>(); //of string type

        //ismay codes store kardenay, except ds bcs ds end ma alag sa ayega as target class

        //to store grades saath saath
        String[] gradesC = new String[16];
        for(int i=0; i< gradesC.length; i++)
        {
            gradesC[i] = new String();
        }

        String data = ""; //for inserting into ll
        int k = 0; //courses k lye counter
        int m = 0; //codes k lye counter
        int flag = 0; //checker k ds parha k nai yaani student rakhna k nai
        int f = 0; //counter only for inserting first heading line into ll bus
        int u = 0; //counter to check for unknown

        sc.useDelimiter(",");

        //yaani end of file tak kaam karna hai jab tak data aaraha
        while(sc.hasNext())
        {
            if(f==0) //yaani only first headings line
            {
                sc.useDelimiter(",");
                rollNo = sc.next();
                sem = sc.next();
                cCode = sc.next();
                cTitle = sc.next();
                cCredH = sc.next();
                cGrade = sc.next();
                gpa = sc.next();
                sGpa = sc.next();
                cGpa = sc.next();
                sc.useDelimiter("\n");
                warning = sc.next();
                data = "Sr.No, MT104, MT119, CS118, CL118, EE182, SL101, SS101, EE227, SS122, MT224, SS111, CS217, EL227, CL217, CS201(DATA STRUCTURES), , CGPA, , WARNING";
                LP.Insert(data);
                f++;
            }




            String rn = rollNo;
            sc.useDelimiter(",");
            rollNo = sc.next();
            sem = sc.next();
            cCode = sc.next();
            cTitle = sc.next();
            cCredH = sc.next();
            cGrade = sc.next();
            gpa = sc.next();
            sGpa = sc.next();
            cGpa = sc.next();
            sc.useDelimiter("\n");
            warning = sc.next();
            k++;
            if( k>1 && !Objects.equals(rollNo, rn))
            {
                k=0; //for new student
                //wapis courses ka grades array ko string kardain
                for( int p=0;p<gradesC.length;p++)
                    gradesC[p] = "";
            }


            //grades ka array
            if(k<=14) //yaani aik he roll no chal raha
            {
                if(cCode.equals("CS201"))
                {
                    gradesC[14] = cGrade;
                }
                else
                    gradesC[k] = cGrade;


            }


            //checking k courses pooray hain? agar ds parha hai tou yaani courses pooray hain, i.e 15
            if(Objects.equals(rollNo, rollNo))
            {
                if(Objects.equals(cCode, "CS201"))
                    flag = 1; //yaani ds parha hai, iss walay data ko consider karna

            }


            //check for unknown
            for( int p=0;p<gradesC.length;p++)
            {
                if(Objects.equals(gradesC[p], "UK"))
                {
                    u = 1;
                }
            }

            //yaani aik roll no ka sara data aagya,
            if(flag ==1 && k==14 && u!=1)
            {
                if(Objects.equals(sem, "Fall 2016") || Objects.equals(sem, "Spring 2017") || Objects.equals(sem, "Fall 2017"))
                {
                    k=0; //k ko wapis 0 for another roll no.
                    data = rollNo + ",";

                    for(int l =0; l<gradesC.length;l++)
                    {
                        data += gradesC[l] + ",";
                    }

                    data += cGpa + "," + warning;
                    LP.Insert(data);

                }

                //wapis courses ka grades array ko string kardain
                for( int p=0;p<gradesC.length;p++)
                    gradesC[p] = "";

                u = 0; //reset u
            }



        } //end of while


        FileWriter writer = new FileWriter("PreProcessed.csv");
        for(int j =0; j< LP.size; j++)
        {
            writer.write(LP.get(j));
        }
        writer.close();
        return LP; //LP contains all these strings islye wapis csv file sa beshak read na karay
    }

    //yeh fucntion for entropy and stuff
    //returns entropy for trees


    //function for ds ke entropy
    public double CalDSEntropy(String FilePath) throws FileNotFoundException {
        double dsEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String dataArr[] = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            if(dataArr[15].equals("A"))
                dsA++;
            else if(dataArr[15].equals("A+"))
                dsAP++;
            else if(dataArr[15].equals("A-"))
                dsAM++;
            else if(dataArr[15].equals("B"))
                dsB++;
            else if(dataArr[15].equals("B+"))
                dsBP++;
            else if(dataArr[15].equals("B-"))
                dsBM++;
            else if(dataArr[15].equals("C"))
                dsC++;
            else if(dataArr[15].equals("C+"))
                dsCP++;
            else if(dataArr[15].equals("C-"))
                dsCM++;
            else if(dataArr[15].equals("D"))
                dsD++;
            else if(dataArr[15].equals("D+"))
                dsDP++;
            else if(dataArr[15].equals("D-"))
                dsDM++;
            else if(dataArr[15].equals("F"))
                dsF++;
            else if(dataArr[15].equals("FA"))
                dsFA++;
            else if(dataArr[15].equals("W"))
                dsW++;

        }//end of while
        //ab sara data ds ka aagya hai and ds grades ke frequency store hogai hai

        //cal entropy by formula Pjlog2Pj
        dsEn = -( ((dsA/100)* (Math.log((dsA/100)/Math.log(2)))) + ((dsAP/100)* (Math.log((dsAP/100)/Math.log(2)))) + ((dsAM/100)* (Math.log((dsAM/100)/Math.log(2)))) + ((dsB/100)* (Math.log((dsB/100)/Math.log(2)))) + ((dsBP/100)* (Math.log((dsBP/100)/Math.log(2)))) + (dsBM/100)* (Math.log((dsBM/100)/Math.log(2))) + ((dsC/100)* (Math.log((dsC/100)/Math.log(2)))) + ((dsCP/100)* (Math.log((dsCP/100)/Math.log(2)))) + ((dsCM/100)* (Math.log((dsCM/100)/Math.log(2))))  + ((dsD/100)* (Math.log((dsD/100)/Math.log(2)))) + ((dsDP/100)* (Math.log((dsDP/100)/Math.log(2)))) + ((dsDM/100)* (Math.log((dsDM/100)/Math.log(2)))) + ((dsF/100)* (Math.log((dsF/100)/Math.log(2)))) + ((dsFA/100)* (Math.log((dsFA/100)/Math.log(2)))) + ((dsW/100)* (Math.log((dsW/100)/Math.log(2)))));

    return dsEn;
    } //end of ds entropy cal function



    //function for calculating any other feature ke entropy
    //takes processed csv file as input, jis feature ki nikalni uski index,
    public double CalFeatureEntropyBranchA(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String dataArr[] = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("A"))
            {
                dsA++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsA>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsA)* (Math.log((cA/dsA)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsA)* (Math.log((cAP/dsA)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsA)* (Math.log((cAM/dsA)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsA)* (Math.log((cB/dsA)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsA)* (Math.log((cBP/dsA)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsA)* (Math.log((cBM/dsA)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsA)* (Math.log((cD/dsA)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsA)* (Math.log((cDP/dsA)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsA)* (Math.log((cDM/dsA)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsA)* (Math.log((cF/dsA)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsA)* (Math.log((cFA/dsA)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsA)* (Math.log((cW/dsA)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function


    public double CalFeatureEntropyBranchAP(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String dataArr[] = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("A+"))
            {
                dsAP++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsAP>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsAP)* (Math.log((cA/dsAP)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsAP)* (Math.log((cAP/dsAP)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsAP)* (Math.log((cAM/dsAP)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsAP)* (Math.log((cB/dsAP)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsAP)* (Math.log((cBP/dsAP)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsAP)* (Math.log((cBM/dsAP)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsAP)* (Math.log((cD/dsAP)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsAP)* (Math.log((cDP/dsAP)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsAP)* (Math.log((cDM/dsAP)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsAP)* (Math.log((cF/dsAP)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsAP)* (Math.log((cFA/dsAP)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsAP)* (Math.log((cW/dsAP)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH A+


    public double CalFeatureEntropyBranchAM(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String dataArr[] = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("A-"))
            {
                dsAM++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsAM>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsAM)* (Math.log((cA/dsAM)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsAM)* (Math.log((cAP/dsAM)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsAM)* (Math.log((cAM/dsAM)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsAM)* (Math.log((cB/dsAM)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsAM)* (Math.log((cBP/dsAM)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsAM)* (Math.log((cBM/dsAM)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsAM)* (Math.log((cD/dsAM)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsAM)* (Math.log((cDP/dsAM)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsAM)* (Math.log((cDM/dsAM)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsAM)* (Math.log((cF/dsAM)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsAM)* (Math.log((cFA/dsAM)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsAM)* (Math.log((cW/dsAM)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH A-

    public double CalFeatureEntropyBranchB(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("B"))
            {
                dsB++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsB>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsB)* (Math.log((cA/dsB)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsB)* (Math.log((cAP/dsB)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsB)* (Math.log((cAM/dsB)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsB)* (Math.log((cB/dsB)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsB)* (Math.log((cBP/dsB)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsB)* (Math.log((cBM/dsB)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsB)* (Math.log((cD/dsB)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsB)* (Math.log((cDP/dsB)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsB)* (Math.log((cDM/dsB)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsB)* (Math.log((cF/dsB)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsB)* (Math.log((cFA/dsB)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsB)* (Math.log((cW/dsB)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH B


    public double CalFeatureEntropyBranchBP(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("B+"))
            {
                dsBP++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsBP>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsBP)* (Math.log((cA/dsBP)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsBP)* (Math.log((cAP/dsBP)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsBP)* (Math.log((cAM/dsBP)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsBP)* (Math.log((cB/dsBP)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsBP)* (Math.log((cBP/dsBP)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsBP)* (Math.log((cBM/dsBP)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsBP)* (Math.log((cD/dsBP)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsBP)* (Math.log((cDP/dsBP)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsBP)* (Math.log((cDM/dsBP)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsBP)* (Math.log((cF/dsBP)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsBP)* (Math.log((cFA/dsBP)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsBP)* (Math.log((cW/dsBP)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH B+


    public double CalFeatureEntropyBranchBM(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("B-"))
            {
                dsBM++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsBM>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsBM)* (Math.log((cA/dsBM)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsBM)* (Math.log((cAP/dsBM)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsBM)* (Math.log((cAM/dsBM)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsBM)* (Math.log((cB/dsBM)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsBM)* (Math.log((cBP/dsBM)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsBM)* (Math.log((cBM/dsBM)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsBM)* (Math.log((cD/dsBM)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsBM)* (Math.log((cDP/dsBM)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsBM)* (Math.log((cDM/dsBM)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsBM)* (Math.log((cF/dsBM)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsBM)* (Math.log((cFA/dsBM)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsBM)* (Math.log((cW/dsBM)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH B-


    public double CalFeatureEntropyBranchC(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("C"))
            {
                dsC++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsC>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsC)* (Math.log((cA/dsC)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsC)* (Math.log((cAP/dsC)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsC)* (Math.log((cAM/dsC)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsC)* (Math.log((cB/dsC)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsC)* (Math.log((cBP/dsC)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsC)* (Math.log((cBM/dsC)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsC)* (Math.log((cD/dsC)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsC)* (Math.log((cDP/dsC)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsC)* (Math.log((cDM/dsC)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsC)* (Math.log((cF/dsC)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsC)* (Math.log((cFA/dsC)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsC)* (Math.log((cW/dsC)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH C


    public double CalFeatureEntropyBranchCP(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("C+"))
            {
                dsCP++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsCP>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsCP)* (Math.log((cA/dsCP)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsCP)* (Math.log((cAP/dsCP)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsCP)* (Math.log((cAM/dsCP)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsCP)* (Math.log((cB/dsCP)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsCP)* (Math.log((cBP/dsCP)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsCP)* (Math.log((cBM/dsCP)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsCP)* (Math.log((cD/dsCP)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsCP)* (Math.log((cDP/dsCP)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsCP)* (Math.log((cDM/dsCP)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsCP)* (Math.log((cF/dsCP)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsCP)* (Math.log((cFA/dsCP)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsCP)* (Math.log((cW/dsCP)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH C+


    public double CalFeatureEntropyBranchCM(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("C-"))
            {
                dsCM++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsCM>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsCM)* (Math.log((cA/dsCM)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsCM)* (Math.log((cAP/dsCM)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsCM)* (Math.log((cAM/dsCM)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsCM)* (Math.log((cB/dsCM)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsCM)* (Math.log((cBP/dsCM)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsCM)* (Math.log((cBM/dsCM)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsCM)* (Math.log((cD/dsCM)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsCM)* (Math.log((cDP/dsCM)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsCM)* (Math.log((cDM/dsCM)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsCM)* (Math.log((cF/dsCM)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsCM)* (Math.log((cFA/dsCM)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsCM)* (Math.log((cW/dsCM)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH C-

    public double CalFeatureEntropyBranchD(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("D"))
            {
                dsD++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsD>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsD)* (Math.log((cA/dsD)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsD)* (Math.log((cAP/dsD)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsD)* (Math.log((cAM/dsCM)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsD)* (Math.log((cB/dsD)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsD)* (Math.log((cBP/dsD)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsD)* (Math.log((cBM/dsD)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsD)* (Math.log((cD/dsD)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsD)* (Math.log((cDP/dsD)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsD)* (Math.log((cDM/dsD)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsD)* (Math.log((cF/dsD)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsD)* (Math.log((cFA/dsD)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsD)* (Math.log((cW/dsD)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH D

    public double CalFeatureEntropyBranchDP(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("D+"))
            {
                dsDP++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsDP>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsDP)* (Math.log((cA/dsDP)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsDP)* (Math.log((cAP/dsDP)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsDP)* (Math.log((cAM/dsDP)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsDP)* (Math.log((cB/dsDP)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsDP)* (Math.log((cBP/dsDP)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsDP)* (Math.log((cBM/dsDP)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsDP)* (Math.log((cD/dsDP)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsDP)* (Math.log((cDP/dsDP)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsDP)* (Math.log((cDM/dsDP)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsDP)* (Math.log((cF/dsDP)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsDP)* (Math.log((cFA/dsDP)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsDP)* (Math.log((cW/dsDP)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH D+

    public double CalFeatureEntropyBranchDM(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("D-"))
            {
                dsDM++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsDM>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsDM)* (Math.log((cA/dsDM)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsDM)* (Math.log((cAP/dsDM)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsDM)* (Math.log((cAM/dsDM)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsDM)* (Math.log((cB/dsDM)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsDM)* (Math.log((cBP/dsDP)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsDM)* (Math.log((cBM/dsDM)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsDM)* (Math.log((cD/dsDM)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsDM)* (Math.log((cDP/dsDM)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsDM)* (Math.log((cDM/dsDM)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsDM)* (Math.log((cF/dsDM)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsDM)* (Math.log((cFA/dsDM)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsDM)* (Math.log((cW/dsDM)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH D-


    public double CalFeatureEntropyBranchF(String FilePath, int index) throws FileNotFoundException {

        double featEn = 0;

        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        //yeh counters store karaingey jo ds k grades thay corresponding to the sep feature grades,
        //yaani agar feature ka grade A hai tou uskay corrresponding jo ds ka grade hai, wo konsa hai and uski kya frequency, inmay wo store hogi
        int cA = 0, cAP = 0, cAM = 0, cB = 0, cBP = 0, cBM = 0, cC = 0, cCP = 0, cCM = 0, cD = 0, cDP = 0, cDM = 0, cF = 0, cFA = 0, cW = 0;

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String[] dataArr = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            //filhaal A ke branch ko dekhrahay in the required feature
            if(dataArr[index].equals("F"))
            {
                dsF++;
                //aur uskay corresponding jo grade hai wo plus
                if(dataArr[15].equals("A"))
                    cA++;
                else if(dataArr[15].equals("A+"))
                    cAP++;
                else if(dataArr[15].equals("A-"))
                    cAM++;
                else if(dataArr[15].equals("B"))
                    cB++;
                else if(dataArr[15].equals("B+"))
                    cBP++;
                else if(dataArr[15].equals("B-"))
                    cBM++;
                else if(dataArr[15].equals("C"))
                    cC++;
                else if(dataArr[15].equals("C+"))
                    cCP++;
                else if(dataArr[15].equals("C-"))
                    cCM++;
                else if(dataArr[15].equals("D"))
                    cD++;
                else if(dataArr[15].equals("D+"))
                    cDP++;
                else if(dataArr[15].equals("D-"))
                    cDM++;
                else if(dataArr[15].equals("F"))
                    cF++;
                else if(dataArr[15].equals("FA"))
                    cFA++;
                else if(dataArr[15].equals("W"))
                    cW++;
            }

        }//end of while


        //cal conditional entropies of each branch of the required feature - req feature pata chalay ga index jo ayegee hamaray pass
        int fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0; //condtional entropy k lye variables
        double conEn = 0;

        //for A abhi
        if(dsF>1) //yanni A grade hai and jitni entries hain A ke wo stored hai dsA ma tou yeh formula k denominator ma ayega
        {
            //ab checking k konsa ds ka grade hai agay, tou wo add karainga in formula of cond entropy
            if(cA>0) //yaani wo grade hai feature k A grade k against
            {
                conEn += ((cA/dsF)* (Math.log((cA/dsF)/Math.log(2))));
            }
            if(cAP>0)
            {
                conEn += ((cAP/dsF)* (Math.log((cAP/dsF)/Math.log(2))));
            }
            if(cAM>0)
            {
                conEn += ((cAM/dsF)* (Math.log((cAM/dsF)/Math.log(2))));
            }
            if(cB>0)
            {
                conEn += ((cB/dsF)* (Math.log((cB/dsF)/Math.log(2))));
            }
            if(cBP>0)
            {
                conEn += ((cBP/dsF)* (Math.log((cBP/dsF)/Math.log(2))));
            }
            if(cBM>0)
            {
                conEn += ((cBM/dsF)* (Math.log((cBM/dsF)/Math.log(2))));
            }
            if(cD>0)
            {
                conEn += ((cD/dsF)* (Math.log((cD/dsF)/Math.log(2))));
            }
            if(cDP>0)
            {
                conEn += ((cDP/dsF)* (Math.log((cDP/dsF)/Math.log(2))));
            }
            if(cDM>0)
            {
                conEn += ((cDM/dsF)* (Math.log((cDM/dsF)/Math.log(2))));
            }
            if(cF>0)
            {
                conEn += ((cF/dsF)* (Math.log((cF/dsF)/Math.log(2))));
            }
            if(cFA>0)
            {
                conEn += ((cFA/dsF)* (Math.log((cFA/dsF)/Math.log(2))));
            }
            if(cW>0)
            {
                conEn += ((cW/dsF)* (Math.log((cW/dsF)/Math.log(2))));
            }
        }


        return conEn;
    } //end of feature conditional  entropy cal function BRANCH F



    //now calculating weighted entropy of the feature
    public double CalWeiEntropyInfoGain(String FilePath, int index, double dsEn) throws FileNotFoundException {
        int dsA = 1, dsAP=1, dsAM=1, dsB=1, dsBP=1, dsBM=1, dsC=1, dsCP=1, dsCM=1, dsD=1, dsDP=1, dsDM=1, dsF=1, dsFA=1, dsW=1; //sep grades ke entropy checking
        String data = ""; //ismay store string taken from csv processed file

        File reader = new File(FilePath);
        Scanner sc = new Scanner(reader);

        int training = 0; //training counter, yani sirf shroo ka training data uthaye eg 100 out of 160


        while(training != 100)
        {
            sc.useDelimiter("\n");
            data = sc.nextLine();
            training++;

            String dataArr[] = data.split("");


            //counting grades k ds ma konsa grade kitni dafa hai, basically storing frequency of each grade in their counters
            if(dataArr[index].equals("A"))
                dsA++;
            else if(dataArr[index].equals("A+"))
                dsAP++;
            else if(dataArr[index].equals("A-"))
                dsAM++;
            else if(dataArr[index].equals("B"))
                dsB++;
            else if(dataArr[index].equals("B+"))
                dsBP++;
            else if(dataArr[index].equals("B-"))
                dsBM++;
            else if(dataArr[index].equals("C"))
                dsC++;
            else if(dataArr[index].equals("C+"))
                dsCP++;
            else if(dataArr[index].equals("C-"))
                dsCM++;
            else if(dataArr[index].equals("D"))
                dsD++;
            else if(dataArr[index].equals("D+"))
                dsDP++;
            else if(dataArr[index].equals("D-"))
                dsDM++;
            else if(dataArr[index].equals("F"))
                dsF++;
            else if(dataArr[index].equals("FA"))
                dsFA++;
            else if(dataArr[index].equals("W"))
                dsW++;
        }//end of while
        //ab sara data yaani req feature k aik aik grade ke frequence aagai hai, jo use hogi in denominators of formulas

        double fA = 0, fAP = 0, fAM = 0, fB = 0, fBP = 0, fBM = 0, fC = 0, fCP = 0, fCM = 0, fD = 0, fDP = 0, fDM = 0, fF = 0, fAF = 0, fW = 0;

        fA = CalFeatureEntropyBranchA(FilePath, index);
        fAP = CalFeatureEntropyBranchAP(FilePath, index);
        fAM= CalFeatureEntropyBranchAM(FilePath, index);
        fB= CalFeatureEntropyBranchB(FilePath, index);
        fBP = CalFeatureEntropyBranchBP(FilePath, index);
        fBM = CalFeatureEntropyBranchBM(FilePath, index);
        fC = CalFeatureEntropyBranchC(FilePath, index);
        fCP= CalFeatureEntropyBranchCP(FilePath, index);
        fCM= CalFeatureEntropyBranchCM(FilePath, index);
        fD= CalFeatureEntropyBranchD(FilePath, index);
        fDP = CalFeatureEntropyBranchDP(FilePath, index);
        fDM = CalFeatureEntropyBranchDM(FilePath, index);
        fF = CalFeatureEntropyBranchF(FilePath, index);

        double weEn = 0;

        weEn = ((dsA/100)* (fA) + ((dsAP/100)* (fAP)) + ((dsAM/100)* (fAM)) + ((dsB/100)* (fB)) + ((dsBP/100)* (fBP) ) + ((dsBM/100)* (fBM) ) + ((dsC/100)* (fC) ) + ((dsCP/100)* (fCP) ) + ((dsCM/100)* (fCM) ) + ((dsD/100)* (fD) ) + ((dsDP/100)* (fDP) ) + ((dsDM/100)* (fDM) ) + ((dsF/100)* (fF) ));



        double infoGain = 0;

        //info gain = target class entropy - weighted entropy of feature
        infoGain = dsEn - weEn;
        return infoGain;

    }
    //cal information gain for features function
    public double CalInfoGain(double dsEn, double weEn)
    {
        double infoGain = 0;

        //info gain = target class entropy - weighted entropy of feature
        infoGain = dsEn - weEn;
        return infoGain;
    } //end of infogain function

}
