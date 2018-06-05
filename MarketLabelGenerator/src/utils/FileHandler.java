/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author felipe-schmidt
 */
public class FileHandler {

    private static String prevDir = "c:/";
    
    public static String displayFileSelector() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(new File(prevDir));

        fc.showOpenDialog(null);

        String filePath = "";
        if (fc.getSelectedFile() != null) {
            filePath = fc.getSelectedFile().getAbsolutePath();
            prevDir = filePath;
        }
        return filePath;
    }    
}
