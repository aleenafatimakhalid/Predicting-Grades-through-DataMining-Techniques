public class Courses <T>
{
    T cCode, cTitle, cCredH, cGrade, gpa, gpaLabel;

    //constructor
    Courses()
    {
        cCode = cTitle = cCredH = cGrade = gpa = gpaLabel = (T) "";
    }

    //set function to set all attributes
    public void SetCoursesDetails(T cCode, T cTitle, T cCredH, T cGrade, T gpa)
    {
        this.cCode = cCode;
        this.cTitle = cTitle;
        this.cCredH = cCredH;
        this.cGrade = cGrade;
        this.gpa = gpa;
    }

    //reset ka function takay new roll no jab aye tou reset hojaye attributes, constructor ka na use hou bcs obj tou wuhi hai
    public void ResetCoursesDetails()
    {
        cCode = cTitle = cCredH = cGrade = gpa = gpaLabel = (T) "";
    }
}
