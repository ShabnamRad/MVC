package selab.mvc.models.entities;

import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.Model;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Student implements Model {
    private String name;
    private String studentNo;
    private double average;
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

    public double getAverage() {
        return average;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course_Student cs) {
        courses.remove(cs.getCourse());
        removeFromAverage(cs.getPoints());
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

    public void updateAverage(double points) {
        average = (average * (courses.getAll().size() - 1) + points) / courses.getAll().size();
    }

    private void removeFromAverage(double points) {
        if (courses.getAll().size() == 0)
            average = 0;
        else
            average = (average * (courses.getAll().size() + 1) - points) / courses.getAll().size();
    }

    public void dispose(DataContext dataContext) {
        for (Course c: courses.getAll()) {
            Course_Student cs = dataContext.getCourse_students().get(c.getPrimaryKey() + "_" + getPrimaryKey());
            c.removeStudent(cs);
            dataContext.getCourse_students().remove(cs);
        }
    }
}
