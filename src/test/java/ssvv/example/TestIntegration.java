package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.time.LocalDate;

public class TestIntegration {
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

    ///BIG BANGULETZ
    @Test
    public void tc_1_AddGradeStudentNonexistent() {
        try {
            service.addNota(
                    new Nota("420", "420", "420", 9.98785, LocalDate.of(2020, 2, 2)),
                    "una bucata feedback pozitiv, csz"
            );
            assert false;
        } catch (Exception eeeee) {
            assert true;
        }
    }

    @Test
    public void tc_2_AddStudentValidEmail() {
        service.addStudent(new Student(
                "SOME UNIQUE ID",
                "Gabarsolonul nambar uan",
                937,
                "gabarsolon.fcsb@gmail.com"
        ));
        assert service.findStudent("SOME UNIQUE ID") != null;
    }

    @Test
    public void tc_3_AddTemaValidAssignment() {
        try {
            service.addTema(new Tema("420", "ddb in adn", 14, 10));
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void tc_4_AddGradeValidGradeIntegrationBigBang() {
        try {
            service.addNota(
                    new Nota("3600", "SOME UNIQUE ID", "420", 7.8965123, LocalDate.of(2018, 12, 19)),
                    "a fost un feedback bun, dar din pacate nu prea a fost. Adik, in prima faza cand..., (aoki)"

            );
            assert true;
        } catch (Exception e) {
            assert false;
        } finally {
            service.deleteStudent("SOME UNIQUE ID");
            service.deleteTema("420");
            service.deleteNota("3600");
        }
    }

    //INCREMENTAL INTEGRATION, duh!
    @Test
    public void tc_5_AddStudentValidEmail() {
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
    public void tc_6_AddAssignmentAddStudent() {
        try {
            service.addStudent(new Student(
                    "SOME UNIQUE ID",
                    "Gabarsolonul nambar uan",
                    937,
                    "gabarsolon.fcsb@gmail.com"
            ));
            assert service.findStudent("SOME UNIQUE ID") != null;

            service.addTema(new Tema("420", "CINE SARE-I ROS-ALBASTRU, AY! AY!", 14, 10));
            assert service.findTema("420") != null;
        }catch (Exception e){
            assert false;
        }
        finally {
            service.deleteStudent("SOME UNIQUE ID");
            service.deleteNota("420");
        }
    }
}
