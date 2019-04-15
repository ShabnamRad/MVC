package selab.mvc.models.entities;

import selab.mvc.models.Model;

public class Course_Student implements Model {
    private Course course;
    private Student student;
    private double points;

    @Override
    public String getPrimaryKey() {
        return course.getPrimaryKey() + "_" + student.getPrimaryKey();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
