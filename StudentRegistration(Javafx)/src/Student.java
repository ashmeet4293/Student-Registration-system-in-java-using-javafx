
public class Student{
    String id,firstName,middleName,lastName,nameOfSubject,TheoreticalOrNumerical,previousLectureReview
            ,noOfStudents,EUFCalculation,date,QOD,username,password;
    Student(String id,String firstName,String lastName,String nameOfSubject, String TheoreticalOrNumerical,
            String previousLectureReview, String noOfStudents,String EUFCalculation, String date, String QOD,
            String username,String password){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.nameOfSubject=nameOfSubject;
        this.TheoreticalOrNumerical=TheoreticalOrNumerical;
        this.previousLectureReview=previousLectureReview;
        this.noOfStudents=noOfStudents;
        this.EUFCalculation=EUFCalculation;
        this.date=date;
        this.QOD=QOD;
        this.username=username;
        this.password=password;
    }

    Student(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Student() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNameOfSubject() {
        return nameOfSubject;
    }

    public void setNameOfSubject(String nameOfSubject) {
        this.nameOfSubject = nameOfSubject;
    }

    public String getTheoreticalOrNumerical() {
        return TheoreticalOrNumerical;
    }

    public void setTheoreticalOrNumerical(String TheoreticalOrNumerical) {
        this.TheoreticalOrNumerical = TheoreticalOrNumerical;
    }

    public String getPreviousLectureReview() {
        return previousLectureReview;
    }

    public void setPreviousLectureReview(String previousLectureReview) {
        this.previousLectureReview = previousLectureReview;
    }

    public String getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(String noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public String getEUFCalculation() {
        return EUFCalculation;
    }

    public void setEUFCalculation(String EUFCalculation) {
        this.EUFCalculation = EUFCalculation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQOD() {
        return QOD;
    }

    public void setQOD(String QOD) {
        this.QOD = QOD;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}