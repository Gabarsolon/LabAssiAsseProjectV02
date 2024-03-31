package ssvv.example;

import domain.Student;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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
public class TestAddStudent
    extends TestCase
{
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
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestAddStudent(String testName )
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestAddStudent.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testIdNotUnique()
    {
        try{
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
        }catch (Exception exception){
            assert true;
        }
    }

    public void testGroupNumberSmallerThanZero(){
        try{
            service.addStudent(new Student(
                    "ID UNIQUE",
                    "T R U F A N U L",
                    -360,
                    "trufanul.doardinamobuc@superbet.ro"
            ));
            assert false;
        }catch(Exception exception){
            assert true;
        }
    }
}
