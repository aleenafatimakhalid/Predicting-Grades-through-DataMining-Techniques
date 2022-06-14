import java.lang.*;
public class Students <T>
{
        T rollNo, sem,sGpa, cGpa, warning, cGpaGrade, warningGrade;
        Courses<T> [] courses = new Courses[16];//bcs total 16 columns banay ga in the preprocessed file, features k yaani, including warning and gpa, and not sr no
        String[] gradesC = new String[16];
        {for(int i=0; i< gradesC.length; i++)
        {
                gradesC[i] = new String();
        }}

        {for(int i=0; i< courses.length; i++)
        {
                courses[i] = new Courses<>();
        }}
        //constructor
        Students()
        {
                rollNo = sem = sGpa = cGpa = warning= cGpaGrade = warningGrade = (T) "";
        }

        //courses sort karnay k lye for the file takay apnay course code k hissab sa wo ayen in the tuple
        public void SortCourses()
        {
                //two loops for rows and columns
                for(int i =0; i<15; i++)
                {
                        for(int j =i+1; j<15; j++)
                        {
                                //condition simple min max check between codes
                                int compare;
                                String str1 = (String) courses[i].cCode;
                                String str2 = (String) courses[j].cCode;
                                compare = str1.compareTo(str2);
                                if(compare > 0)
                                {
                                        Courses<T> temp = courses[i];
                                        courses[i] = courses[j];
                                        courses[j] = temp;
                                } //end of if
                        }//end of inner for
                }// end of outer for
        }//end of function sort


        //function for generating grades for courses ke gpa
        public void GenerateGrades()
        {
                //for grade k labels yaani grade ke gpa ko grade ma likhna inn tuple

                for(int i=0;i<16;i++)
                {
                        if(courses[i].cGrade != null);
                        {
                                if(courses[i].cGrade == "UK")//yanni unknown hai, ds nai parha
                                        courses[i].gpaLabel = (T) "Unknown";
                        }

                }

                //for cgpa grade

        }

}
