package com.springframework.universitycourses.bootstrap;

import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;
import com.springframework.universitycourses.model.Role;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.model.Teacher;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.repositories.TeacherRepository;
import com.springframework.universitycourses.services.AssignmentService;
import com.springframework.universitycourses.services.CourseService;
import com.springframework.universitycourses.services.EnrollmentService;
import com.springframework.universitycourses.services.RoleService;
import com.springframework.universitycourses.services.StudentService;
import com.springframework.universitycourses.services.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class DataLoader implements CommandLineRunner
{
	private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());
	private final CourseService courseService;
	private final AssignmentService assignmentService;
	private final TeacherService teacherService;
	private final StudentService studentService;
	private final EnrollmentService enrollmentService;
	private final RoleService roleService;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DataLoader(final CourseService courseService, final AssignmentService assignmentService,
			final TeacherService teacherService,
			final StudentService studentService, final EnrollmentService enrollmentService, final RoleService roleService,
			final TeacherRepository teacherRepository, final StudentRepository studentRepository,
			final BCryptPasswordEncoder passwordEncoder)
	{
		this.courseService = courseService;
		this.assignmentService = assignmentService;
		this.teacherService = teacherService;
		this.studentService = studentService;
		this.enrollmentService = enrollmentService;
		this.roleService = roleService;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(final String... args) throws Exception
	{
		var courses = getCourseService().findAll();
		if (courses.isEmpty())
		{
			loadData();
		}
	}

	private void loadData()
	{
		Course softwareEngineering =
				Course.builder()
						.title("Software Engineering")
						.description("Learn about Software Engineering")
						.credit(150L)
						.assignments(new HashSet<>())
						.build();


		Assignment dataBases =
				Assignment.builder()
						.points(16L)
						.title("Data Bases")
						.description("Learn about databases")
						.course(softwareEngineering)
						.enrollments(new HashSet<>())
						.build();

		softwareEngineering.getAssignments().add(dataBases);

		Assignment programing =
				Assignment.builder()
						.points(18L)
						.title("Programing")
						.description("Learn about programing bases")
						.course(softwareEngineering)
						.enrollments(new HashSet<>())
						.build();

		softwareEngineering.getAssignments().add(programing);

		Assignment machineLearning =
				Assignment.builder()
						.points(20L)
						.title("Machine Learning")
						.description("Learn about Machine Learning")
						.course(softwareEngineering)
						.enrollments(new HashSet<>())
						.build();

		softwareEngineering.getAssignments().add(machineLearning);

		Course electricalEngineering =
				Course.builder()
						.title("Electrical Engineering")
						.description("Learn about Electrical Engineering")
						.credit(140L)
						.assignments(new HashSet<>())
						.build();

		Assignment electronicDevices =
				Assignment.builder()
						.points(20L)
						.title("Electronic Devices")
						.description("Learn about Electronic Devices")
						.course(electricalEngineering)
						.enrollments(new HashSet<>())
						.build();

		electricalEngineering.getAssignments().add(electronicDevices);

		Assignment circuits =
				Assignment.builder()
						.points(22L)
						.title("Circuits")
						.description("Learn about Circuits")
						.course(electricalEngineering)
						.enrollments(new HashSet<>())
						.build();

		electricalEngineering.getAssignments().add(circuits);

		Assignment physics =
				Assignment.builder()
						.points(24L)
						.title("Physics")
						.description("Learn about Physics")
						.course(electricalEngineering)
						.enrollments(new HashSet<>())
						.build();

		electricalEngineering.getAssignments().add(physics);

		LOGGER.log(Level.INFO, "Courses Loaded...");

		Role teacher =
				Role.builder()
						.name("TEACHER")
						.build();

		Role student =
				Role.builder()
						.name("STUDENT")
						.build();

		Role admin =
				Role.builder()
						.name("ADMIN")
						.build();

		Teacher jacobArcila =
				Teacher.builder()
						.firstName("Jacob")
						.lastName("Arcila")
						.email("jacob@gmail.com")
						.password("123")
						.roles(new HashSet<>())
						.assignments(new HashSet<>())
						.build();

		jacobArcila.getAssignments().add(dataBases);
		jacobArcila.getAssignments().add(machineLearning);
		dataBases.setTeacher(jacobArcila);
		machineLearning.setTeacher(jacobArcila);

		Teacher stevenGonzales =
				Teacher.builder()
						.firstName("Steven")
						.lastName("Gonzales")
						.email("stevenGonzales@gmail.com")
						.password("321")
						.roles(new HashSet<>())
						.assignments(new HashSet<>())
						.build();

		stevenGonzales.getAssignments().add(electronicDevices);
		stevenGonzales.getAssignments().add(circuits);
		electronicDevices.setTeacher(stevenGonzales);
		circuits.setTeacher(stevenGonzales);

		Teacher hernandoMorales =
				Teacher.builder()
						.firstName("Hernando")
						.lastName("Morales")
						.email("hernandoMorales@gmail.com")
						.password("456")
						.roles(new HashSet<>())
						.assignments(new HashSet<>())
						.build();

		hernandoMorales.getAssignments().add(physics);
		hernandoMorales.getAssignments().add(programing);
		physics.setTeacher(hernandoMorales);
		programing.setTeacher(hernandoMorales);

		LOGGER.log(Level.INFO, "Teachers Loaded...");

		Student jefersonSalazar =
				Student.builder()
						.firstName("Jeferson")
						.lastName("Salazar")
						.email("jefersonsalazar@gmail.com")
						.password(passwordEncoder.encode("654"))
						.enrollmentYear(new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime())
						.enrollments(new HashSet<>())
						.roles(new HashSet<>())
						.build();

		Student hayderBedoya =
				Student.builder()
						.firstName("Hayder")
						.lastName("Bedoya")
						.email("hayderBedoya@gmail.com")
						.password("789")
						.enrollmentYear(new GregorianCalendar(2021, Calendar.MARCH, 20).getTime())
						.enrollments(new HashSet<>())
						.roles(new HashSet<>())
						.build();

		LOGGER.log(Level.INFO, "Students Loaded...");

		Enrollment enrollment1 =
				Enrollment.builder()
						.id(EnrollmentId.builder()
								.assignmentId(dataBases.getId())
								.studentId(jefersonSalazar.getId())
								.build())
						.student(jefersonSalazar)
						.assignment(dataBases)
						.enrollmentDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime())
						.grade(5L)
						.inProgress(false)
						.build();

		dataBases.getEnrollments().add(enrollment1);

		Enrollment enrollment2 =
				Enrollment.builder()
						.id(EnrollmentId.builder()
								.assignmentId(electronicDevices.getId())
								.studentId(hayderBedoya.getId())
								.build())
						.student(hayderBedoya)
						.assignment(electronicDevices)
						.enrollmentDate(new GregorianCalendar(2021, Calendar.MARCH, 21).getTime())
						.grade(3L)
						.inProgress(false)
						.build();

		electronicDevices.getEnrollments().add(enrollment2);

		Enrollment enrollment3 =
				Enrollment.builder()
						.id(EnrollmentId.builder()
								.assignmentId(machineLearning.getId())
								.studentId(jefersonSalazar.getId())
								.build())
						.student(jefersonSalazar)
						.assignment(machineLearning)
						.enrollmentDate(new GregorianCalendar(2020, Calendar.AUGUST, 12).getTime())
						.grade(0L)
						.inProgress(true)
						.build();

		machineLearning.getEnrollments().add(enrollment3);

		Enrollment enrollment4 =
				Enrollment.builder()
						.id(EnrollmentId.builder()
								.assignmentId(programing.getId())
								.studentId(hayderBedoya.getId())
								.build())
						.student(hayderBedoya)
						.assignment(programing)
						.enrollmentDate(new GregorianCalendar(2021, Calendar.SEPTEMBER, 21).getTime())
						.grade(0L)
						.inProgress(true)
						.build();

		programing.getEnrollments().add(enrollment4);

		getAssignmentService().save(dataBases);
		getAssignmentService().save(machineLearning);
		getAssignmentService().save(electronicDevices);
		getAssignmentService().save(circuits);
		getAssignmentService().save(physics);
		getAssignmentService().save(programing);

		getRoleService().save(teacher);
		getRoleService().save(student);
		getRoleService().save(admin);

		getStudentService().save(jefersonSalazar);
		getStudentService().save(hayderBedoya);

		getEnrollmentService().save(enrollment1);
		getEnrollmentService().save(enrollment2);
		getEnrollmentService().save(enrollment3);
		getEnrollmentService().save(enrollment4);

		Optional<Teacher> teacher1 = teacherRepository.findById(jacobArcila.getId());
		teacher1.ifPresent(t -> {
			t.getRoles().add(teacher);
			teacherRepository.save(t);
		});
		Optional<Teacher> teacher2 = teacherRepository.findById(hernandoMorales.getId());
		teacher2.ifPresent(t -> {
			t.getRoles().add(teacher);
			teacherRepository.save(t);
		});
		Optional<Teacher> teacher3 = teacherRepository.findById(stevenGonzales.getId());
		teacher3.ifPresent(t -> {
			t.getRoles().add(teacher);
			teacherRepository.save(t);
		});
		Optional<Student> student1 = studentRepository.findById(jefersonSalazar.getId());
		student1.ifPresent(s -> {
			s.getRoles().add(student);
			studentRepository.save(s);
		});
		Optional<Student> student2 = studentRepository.findById(hayderBedoya.getId());
		student2.ifPresent(s -> {
			s.getRoles().add(student);
			studentRepository.save(s);
		});
	}

	public CourseService getCourseService()
	{
		return courseService;
	}

	public AssignmentService getAssignmentService()
	{
		return assignmentService;
	}

	public TeacherService getTeacherService()
	{
		return teacherService;
	}

	public StudentService getStudentService()
	{
		return studentService;
	}

	public EnrollmentService getEnrollmentService()
	{
		return enrollmentService;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}
}
