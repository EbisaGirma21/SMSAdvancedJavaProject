package BaseClass;

public class Subject {
    String subjectName;
    String subjectCode;
    String teacherId;
    String sectionCode;

    public Subject(String subjectCode, String subjectName, String teacherId, String sectionCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.sectionCode = sectionCode;

    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Override
    public String toString() {

        return getSectionCode() + "  " + getSubjectCode();
    }

}