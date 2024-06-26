package ssvv.example;

import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class TestAddTema {
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

    @Test
    public void tc_1_AssignmentAlreadyExists() {
        assert service.addTema(new Tema("1", "file repository", 2, 1)) != null;
    }

    @Test
    public void tc_2_InvalidAssignment() {
        try {
            service.addTema(new Tema("", "", -1, 15));
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void tc_3_InvalidDescription() {
        try {
            service.addTema(new Tema("1234", "", 2, 1));
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void tc_4_InvalidDeadline() {
        try {
            service.addTema(new Tema("1234", "fcsb", 16, 14));
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void tc_5_InvalidReceptionDate() {
        try {
            service.addTema(new Tema("4321", "ddb", 12, 15));
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void tc_6_ValidAssignment() {
        try {
            service.addTema(new Tema("1", "ddb in adn", 14, 13));
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}
