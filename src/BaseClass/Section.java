package BaseClass;

public class Section {
    String sectionId;
    String teacherId;
    String numberOfStudent;
    String blockNumber;

    public Section(String sectionId, String teacherId, String numberOfStudent, String blockNumber) {
        this.sectionId = sectionId;
        this.teacherId = teacherId;
        this.numberOfStudent = numberOfStudent;
        this.blockNumber = blockNumber;

    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getNumberOfStudent() {
        return numberOfStudent;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public void setNumberOfStudent(String numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }
}
