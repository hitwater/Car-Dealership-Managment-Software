package dealership;
import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;
import static java.lang.System.out;

public class DealershipTest {			//Test class for Dealership methods
  public static Dealership dealer = null;

  @BeforeClass
  public static void createEnviroment() {       //Creates enviroment and Dealership object for tests
    try{
    dealer = new Dealership();
    }catch(IOException e) {
      assert false : "Initialization error\n";
    }
    while(dealer.getCar(0) != null) dealer.removeCar(dealer.getCar(0));
    out.println("\nSet Up Environment");
  }

  @AfterClass                                   //Clears enviroment when testing is complete
  public static void clearEnvironment() {
    dealer = null;
    out.println("\nCleared Environment"); 
  }

  @Before                                       //Initializes before every method
  public void createMethodEnviroment() {
    dealer.addCar("TEST1", "Toyota", "Camry", "2013", "10000", "1000");
    out.println("Set up method Environment\n");
  }

  @After
  public void clearMethodEnviroment() {        //Clears the database after every method
    while(dealer.getCar(0) != null) dealer.removeCar(dealer.getCar(0));
    out.println("\nCleared method Environment\n");
   
  }

  @Test                                        //Test1 tests the showCar method
  public void showCarTest() {
    out.println("Testing -- showCar Method ...\n");

    PrintStream originalOut = System.out;
    OutputStream os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
    dealer.showCars();
    String output = os.toString();

    OutputStream os2 = new ByteArrayOutputStream();
    PrintStream ps2 = new PrintStream(os2);
    System.setOut(ps2);
    dealer.showCars();    
    String expected = os2.toString();
    System.setOut(originalOut);

    dealer.showCars();

    assertEquals("Actual output does not equal expected outpuy\n",expected.toString(), os.toString());
    assertEquals("Actual output does not equal expected outpuy\n",expected.toString()+"1", os.toString());
  }

  @Test                                      //Test2 tests the showCarsRange method
  public void showCarsRangeTest() {
    out.println("Testing -- showCarsRange Method ...\n");

    dealer.addCar("TEST2", "Toyota", "Tundra", "1913", "10000", "99");
    dealer.addCar("TEST4", "Toyota", "Avalon", "1987", "10000", "2");
    dealer.addCar("TEST3", "Toyota", "Ranchero", "2111", "10000", "999999");
    PrintStream originalOut = System.out;
    OutputStream os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
    dealer.showCarsRange(1, 99999);
    String output = os.toString();

    OutputStream os2 = new ByteArrayOutputStream();
    PrintStream ps2 = new PrintStream(os2);
    System.setOut(ps2);
    dealer.showCarsRange(1, 99999);    
    String expected = os2.toString();
    System.setOut(originalOut);

    dealer.showCarsRange(1, 99999);
    assertEquals("Actual output does not equal expected outpuy\n",expected.toString(), os.toString());
    assertEquals("Actual output does not equal expected outpuy\n",expected.toString()+"1", os.toString());
  }

  @Test                                     //Test3 tests the add car method
  public void addCarTest() {
    out.println("Testing -- addCar Method ...\n");
    int i = -1;
    String vin = "11111";
    dealer.addCar("11111", "Suburu", "Hatchback", "2009", "12000", "75000");
    i = dealer.findCar(vin);
    assertEquals("Add car failed.", i, dealer.findCar(vin));
        
    vin = "111111";
    dealer.addCar("111111", "Suburu", "Hatchback", "2009", "12000", "75000");
    i = dealer.findCar(vin);
    assertEquals("Add car failed: invalid vin", 5, vin.length());
        
    vin = "FE$#0D";
    dealer.addCar("FE$#0D", "Suburu", "Hatchback", "2009", "12000", "75000");
    i = dealer.findCar(vin);
    assertEquals("Add car failed. Invalid vin characters.", -1, i);
        
    vin = "12345";
    dealer.addCar("12345", "???", "Titan", "2008", "FEDFGR", "65000");
    i = dealer.findCar(vin);
    assertEquals("Add car failed: invalid mileage.", -1, i);
  }

  @Test           //Test4 tests the remove Car method
  public void removeCarTest(){
    out.println("Testing -- removeCar Method ...\n");
    String vin = "ABCDE";
    int i = dealer.findCar(vin);
    
    dealer.removeCar(dealer.getCar(i));
    assertEquals("Car does not exist in database.", dealer.findCar(vin), i);

    vin = "TEST1";
    i = dealer.findCar(vin);
    
    dealer.removeCar(dealer.getCar(i));
    assertEquals("Car unsuccessfully removed.", dealer.findCar(vin), i);
      
  }
 
  @Test   //Test5 tests the getCar method
  public void getCarTest(){
    out.println("Testing -- getCar Method ...\n");
    int i = 0;
    dealer.getCar(i);
    assertNotNull("Car does not exist!.", dealer.getCar(i));
        
    i = 100;
    dealer.getCar(i);
    assertNull("Car does not exist!", dealer.getCar(i));
  }

  @Test  //Test6 tests the findCar method
    public void findCarTest(){
      String vin = "LCF01";
      int i = dealer.findCar(vin);
      assertEquals("Car not found.", dealer.findCar(vin), i);
        
      vin = "";
      i = dealer.findCar(vin);
      assertEquals("Car not found.", dealer.findCar(vin), i);
    }

  @Test  //Test7tests the flushTest method
  public void flushTest() throws IOException{
    dealer.flush();
    StringBuilder sb = null;
    BufferedReader br = new BufferedReader(new FileReader("cars.txt"));
    try{
      sb = new StringBuilder();
      String line = br.readLine();

      while(line != null) {
        sb.append(line);
        sb.append("\n");
        line = br.readLine();
      }
    out.println(sb.toString());
    }finally {}
  }
}
