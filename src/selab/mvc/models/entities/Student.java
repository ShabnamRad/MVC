package selab.mvc.models.entities;

import selab.mvc.models.DataSet;
import selab.mvc.models.Model;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Student implements Model {
    private String name;
    private String studentNo;
    private float average;
    private DataSet<Course> courses;

    public Student() {
        courses = new DataSet<>();
    }

    @Override
    public String getPrimaryKey() {
        return this.studentNo;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setStudentNo(String value) {
        if (!validateStudentNo(value))
            throw new IllegalArgumentException("The format is not correct");

        this.studentNo = value;
    }

    public String getStudentNo() {
        return this.studentNo;
    }

    public float getAverage() {
        return average;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public String getCourses() {
        if (courses.getAll().size() == 0)
            return "-";
        ArrayList<String> names = new ArrayList<>();
        for (Course c : courses.getAll()) {
            names.add(c.getTitle());
        }
        return String.join(", ", names);
    }

    /**
     * @param studentNo Student number to be checeked
     * @return true, if the format of the student number is correct
     */
    private boolean validateStudentNo(String studentNo) {
        Pattern pattern = Pattern.compile("^[8-9]\\d{7}$");
        return pattern.matcher(studentNo).find();
    }

    public void updateAverage(float v) {
        average = (average * (courses.getAll().size() - 1) + v) / courses.getAll().size();
    }
}
