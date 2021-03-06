package persistence;

import java.util.Objects;

/**
 *
 * @author Elia
 */
public class Lesson{

    private String label;
    private String teacher;
    private String start;
    private String end;
    private String classroom;
    private String lessonCode;

    public Lesson(String label, String teacher, String start, String end, String classroom, String lessonCode) {
        this.label = label;
        this.teacher = teacher;
        this.start = start;
        this.end = end;
        this.classroom = classroom;
        this.lessonCode = lessonCode;
    }

    public Lesson() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getlessonCode() {
        return lessonCode;
    }

    public void setlessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.label);
        hash = 67 * hash + Objects.hashCode(this.lessonCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Lesson){
            Lesson other = (Lesson) obj;
            if(this.lessonCode.equals(other.lessonCode)){ 
                return true;
            }  
        }
        return false;
    }

    @Override
    public String toString() {
        return label + '\n' + teacher + '\n' + start + " - " + end + '\n' + classroom + '\n' + lessonCode + '\n' + '\n';
    }
}
