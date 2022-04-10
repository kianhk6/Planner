package com.example.Planner.Controller;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Agents.IdGenerator;
import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.EnrollmentSpace;
import com.example.Planner.Model.Label;
import com.example.Planner.Model.Semester;
import com.example.Planner.Wrappers.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlannerController {
    CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/course_data_2018.csv");
    IdGenerator idGenerator = new IdGenerator(courseInfoGenerator);
    WrapperInfoGenerator wrapperInfoGenerator = new WrapperInfoGenerator(courseInfoGenerator, idGenerator);
    List<ApiWatcherWrapper> watchers = new ArrayList<>();

    @GetMapping("/about")
    public Map<String, String> getMyName() {
        HashMap<String, String> About = new HashMap<>();
        About.put("appName", "SFU Course Planner clone");
        About.put("authorName", "Kian Hosseinkhani and Sasha Vujisic");
        return About;
    }

    @GetMapping("/dump-model")
    public void dumpModel() {
        courseInfoGenerator.listDumpCourses();
    }

    @GetMapping("/departments")
    public List<ApiDepartmentWrapper> ListDepartments() {
        return wrapperInfoGenerator.getDepartments();
    }

    @GetMapping("/departments/{id}/courses")
    public List<ApiCourseWrapper> getCourseDept(@PathVariable("id") long deptId) {
        return wrapperInfoGenerator.getCoursesBasedOnDeptId(deptId);
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getCourseDept(@PathVariable("deptId") long deptId, @PathVariable("courseId") long courseId) {
        return wrapperInfoGenerator.getCoursesBasedOnIds(deptId, courseId);
    }


    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    public List<ApiOfferingSectionWrapper> getCourseOffering(@PathVariable("deptId") long deptId,
                                                             @PathVariable("courseId") long courseId, @PathVariable("courseOfferingId") long courseOfferingId) {
        return wrapperInfoGenerator.getOfferingBasedOnCourseOfferingId(deptId, courseId, courseOfferingId);
    }

    @PostMapping("/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void AddOffering(@RequestBody ApiOfferingDataWrapper offeringData) {

        Semester semester = new Semester(offeringData.getSemester().substring(0, 3), offeringData.getSemester().charAt(3));
        String subjectStr = offeringData.getSubjectName();
        String catalogNumStr = offeringData.getCatalogNumber();
        String componentCodeStr = offeringData.getComponent();
        int capacity = offeringData.getEnrollmentCap();
        int takenSeat = offeringData.getEnrollmentTotal();
        String location = offeringData.getLocation();
        List<String> instructors = new ArrayList<>();
        instructors.add(offeringData.getInstructor());
        CourseInfo newCourse = new CourseInfo(instructors, semester,
                new Label(subjectStr, catalogNumStr, componentCodeStr),
                new EnrollmentSpace(capacity, takenSeat), location);
        courseInfoGenerator.getCourses().add(newCourse);
        courseInfoGenerator.sortCourses();
        courseInfoGenerator.setDumpCourses(new ArrayList<>());
        courseInfoGenerator.setUpDumpCourses();
        courseInfoGenerator.SortCoursesBasedOnSubject();
        courseInfoGenerator.SortDumpCourseBasedOnSubject();
        idGenerator = new IdGenerator(courseInfoGenerator);
        wrapperInfoGenerator = new WrapperInfoGenerator(courseInfoGenerator, idGenerator);

        //System.out.println(newCourse.getLabel().getDeptId());
        for (ApiWatcherWrapper watcher : watchers) {
            if (watcher.getDepartment().getDeptId() == newCourse.getLabel().getDeptId()) {
                if (watcher.getCourse().getCourseId() == newCourse.getLabel().getCourseId()) {
                    watcher.getEvents().add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                            + " added section " + newCourse.getLabel().getComponentCode() + " with enrollment ("
                            + newCourse.getEnrollmentSpace().getTakenSeat() + "/" + newCourse.getEnrollmentSpace().getCapacity() + ") to offering " +
                            newCourse.getTermString() + ' ' + newCourse.getYear());
                }
            }
        }
        //        watchers.add(new ApiWatcherWrapper(watchers.size(),
//                new ApiDepartmentWrapper(newCourse.getLabel().getDept(), newCourse.getLabel().getDeptId()),
//                new ApiCourseWrapper(newCourse.getLabel().getCatalogNum(), newCourse.getLabel().getCourseId()),
//                ));
    }


    @PostMapping("/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void AddWatcher(@RequestBody ApiWatcherInputWrapper offeringData) {
        CourseInfo course = wrapperInfoGenerator.getCourseBasedOnIds(offeringData.getDeptId(),
                offeringData.getCourseId());

        watchers.add(new ApiWatcherWrapper(watchers.size(),
                new ApiDepartmentWrapper(course.getLabel().getDept(), course.getLabel().getDeptId()),
                new ApiCourseWrapper(course.getLabel().getCatalogNum(), course.getLabel().getCourseId()),
                new ArrayList<String>()
        ));
    }


    @GetMapping("/watchers")
    public List<ApiWatcherWrapper> getWatchers() {
        return watchers;
    }

    @DeleteMapping("/watchers/{watcherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteWatcher(@PathVariable("watcherId") int watcherId) {
        watchers.removeIf(watcher -> watcher.getId() == watcherId);
    }

    @GetMapping("/stats/students-per-semester")
    public List<ApiGraphDataPointWrapper> getPoints(@RequestParam int deptId) {
        return wrapperInfoGenerator.getGraphPointsBasedOnDeptId(deptId);
    }
}
