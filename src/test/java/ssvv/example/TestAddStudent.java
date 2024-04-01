package ssvv.example;

import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

/**
 * Unit test for simple App.
 */
public class TestAddStudent {
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    String filenameStudent = "fisiere/Studenti.xml";
    String filenameTema = "fisiere/Teme.xml";
    String filenameNota = "fisiere/Note.xml";

    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);


    /**
     * @return the suite of tests being tested
     */
    @Test
    public void tc_1_testIdNotUnique() {
        try {
            service.addStudent(new Student(
                    "1003",
                    "T R U F A N U L",
                    420,
                    "trufanul.doardinamobuc@superbet.com"
            ));

            if (service.findStudent("1003") != null) {
                throw new ValidationException("Studentul exista!");
            }

            assert false;
        } catch (Exception exception) {
            assert true;
        }
    }

    @Test
    public void tc_2_testGroupNumberSmallerThanZero() {
        try {
            service.addStudent(new Student(
                    "ID UNIQUE",
                    "T R U F A N U L",
                    -1,
                    "trufanul.doardinamobuc@superbet.ro"
            ));
            assert false;
        } catch (Exception exception) {
            assert true;
        }
    }

    @Test
    public void tc_3_testNameNotEmpty() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Nume",
                360,
                "trufanul.ddb@superbere.com"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
        service.deleteStudent("SOME UNIQUE ID");
    }

    @Test
    public void tc_4_testNameEmpty() {
        try {
            service.addStudent(new Student(
                    "SOME UNIQUE ID",
                    "",
                    360,
                    "trufanul.ddb@superbere.com"
            ));
            assert false;
            service.deleteStudent("SOME UNIQUE ID");
        } catch (Exception exception) {
            assert true;
        }
    }

    @Test
    public void tc_5_testValidEmail() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Gabarsolonul nambar uan",
                937,
                "gabarsolon.fcsb@gmail.com"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
        service.deleteStudent("SOME UNIQUE ID");
    }

    @Test
    public void tc_6_testInvalidEmail() {
        try {
            service.addStudent(new Student(
                    "SOME UNIQUE ID",
                    "Gabarsolonul nambar uan",
                    937,
                    "email"
            ));

            assert false;
        } catch (Exception exception) {
            assert true;
        } finally {
            service.deleteStudent("SOME UNIQUE ID");
        }
    }

    @Test
    public void tc_7_testGroupNumberIsZero() {
        try {
            service.addStudent(new Student(
                    "SOME UNIQUE ID",
                    "Gabarsolonul nambar uan",
                    0,
                    "email"
            ));

            assert false;
        } catch (Exception exception) {
            assert true;
        } finally {
            service.deleteStudent("SOME UNIQUE ID");
        }
    }

    @Test
    public void tc_8_testGroupNumberIsOne() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Gabarsolonul nambar uan",
                1,
                "email"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
        service.deleteStudent("SOME UNIQUE ID");
    }

    @Test
    public void tc_9_testGroupNumberIsMaxIntPlusOne() {
        try {
            service.addStudent(new Student(
                    "SOME UNIQUE ID",
                    "Gabarsolonul nambar uan",
                    Integer.MAX_VALUE + 1,
                    "email"
            ));

            assert false;
        } catch (Exception exception) {
            assert true;
        } finally {
            service.deleteStudent("SOME UNIQUE ID");
        }
    }

    @Test
    public void tc_10_testGroupNumberIsMaxIntMinusOne() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Gabarsolonul nambar uan",
                Integer.MAX_VALUE - 1,
                "email"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
        service.deleteStudent("SOME UNIQUE ID");
    }

    @Test
    public void tc_11_testGroupNumberIsMaxInt() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Gabarsolonul nambar uan",
                Integer.MAX_VALUE,
                "email"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
        service.deleteStudent("SOME UNIQUE ID");
    }
}
