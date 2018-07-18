import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * <p>
 * Class for the choose file window for the load a file function of our software. <br>
 * </p>
 * @author C. Bruckmann, R. Wagner
 *
 */
public class OeffnenDialogClass 
{
	/**
	 * <p>
	 * Function to get the filename as string for the loading function of the GUI.
	 * </p>
	 * 
	 * @return The name of the chosen file to open from which the graph shall be read.
	 */
    String oeffnen()
    {
    	//construct an FileChooser object
        final JFileChooser chooser = new JFileChooser("Verzeichnis wählen");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        //set the chooser to looks fo files and directories
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // define start file as /home
        final File file = new File("/home");
        String inputVerzStr ="";
        
        //set start directory to /home
        chooser.setCurrentDirectory(file);

        chooser.addPropertyChangeListener(new PropertyChangeListener() 
        {
            public void propertyChange(PropertyChangeEvent e) 
            {
                if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) || e.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY))
                {
                    
                }
            }
        });

        chooser.setVisible(true);
        final int result = chooser.showOpenDialog(null);
        
        //if a file was chosen, get the filename and set the String for later return
        if (result == JFileChooser.APPROVE_OPTION) 
        {
            File inputVerzFile = chooser.getSelectedFile();
            inputVerzStr = inputVerzFile.getPath();
        }
        chooser.setVisible(false);
        // return the filename
        return inputVerzStr;
    }
} 