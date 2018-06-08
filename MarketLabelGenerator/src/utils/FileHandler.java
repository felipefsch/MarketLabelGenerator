/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author felipe-schmidt
 */
public class FileHandler {

    private static String prevDir = "c:/";
    
    public static String displayFileSelectorDoc() {
        final JFileChooser fc = displayFileSelector();
        
        FileFilter filter = new FileNameExtensionFilter("DOC or DOCX file", "doc", "docx");        
        fc.setFileFilter(filter);

        fc.showOpenDialog(null);
        
        String filePath = "";
        if (fc.getSelectedFile() != null) {
            filePath = fc.getSelectedFile().getAbsolutePath();
            prevDir = filePath;
        }
        return filePath;
    }
    
    public static String displayFileSelectorXls() {
        final JFileChooser fc = displayFileSelector();
        
        FileFilter filter = new FileNameExtensionFilter("XLS or XLSX file", "xls", "xlsx");        
        fc.setFileFilter(filter);

        fc.showOpenDialog(null);
        
        String filePath = "";
        if (fc.getSelectedFile() != null) {
            filePath = fc.getSelectedFile().getAbsolutePath();
            prevDir = filePath;
        }
        return filePath;
    }
    
    private static JFileChooser displayFileSelector() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(new File(prevDir));
        
        return fc;
    }    
}
