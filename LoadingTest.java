/**
* Class that tests the loading of files
* @author Bruckmann C., Wagner R.
*
*/
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;


public class LoadingTest {

    @Test
    public void Sum() {
        int a = 5;
        int b = 10;
        int result = a + b;
        assertEquals(15, result);
    }
    

    
    @Test(expected = FileNotFoundException.class)
    public void notExistingFile() throws FileNotFoundException {
    	DrawPanel drawPanel = new DrawPanel();
    	drawPanel.loadFile("abc.txt");
    }
}