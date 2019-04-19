package selab.mvc.controllers;

import org.json.JSONObject;
import selab.mvc.models.DataContext;
import selab.mvc.models.DataSet;
import selab.mvc.models.entities.Course;
import selab.mvc.models.entities.Course_Student;
import selab.mvc.models.entities.Student;
import selab.mvc.views.JsonView;
import selab.mvc.views.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddStudentToCourseController extends Controller {

    DataSet<Course_Student> course_students;
    public AddStudentToCourseController(DataContext dataContext) {
        super(dataContext);
        course_students = dataContext.getCourse_students();
    }

    @Override
    public View exec(String method, InputStream body) throws IOException {
        if (!method.equals("POST"))
            throw new IOException("Method not supported");

        JSONObject input = readJson(body);
        String studentNo = input.getString("studentNo");
        String courseNo = input.getString("courseNo");
        String points = input.getString("points");

        Student student = dataContext.getStudents().get(studentNo);
        Course course = dataContext.getCourses().get(courseNo);
        Course_Student course_student = new Course_Student();
        course_student.setCourse(course);
        course_student.setStudent(student);
        course_student.setPoints(Double.parseDouble(points));

        course_students.add(course_student);
        student.addCourse(course);
        course.addStudent(student);

        for (Course_Student cs: course_students.getAll()) {
            System.out.println(cs.getPrimaryKey() + " " +  cs.getPoints());
        }

        Map<String, String> result = new HashMap<>();
        result.put("success", "true");
        return new JsonView(new JSONObject(result));
    }

    @Override
    protected View getExceptionView(Exception exception) {
        Map<String, String> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return new JsonView(new JSONObject(result));
    }
}
