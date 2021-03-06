package justclust.toolbar.overrepresentationanalysis;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import justclust.datastructures.Data;
import justclust.JustclustJFrame;
import justclust.datastructures.Cluster;
import justclust.datastructures.Edge;
import justclust.datastructures.Node;
import justclust.customcomponents.BrowseButton;
import justclust.plugins.configurationcontrols.CheckBoxControl;
import justclust.plugins.configurationcontrols.ComboBoxControl;
import justclust.plugins.configurationcontrols.FileSystemPathControl;
import justclust.plugins.configurationcontrols.PluginConfigurationControl;
import justclust.plugins.configurationcontrols.TextFieldControl;
import justclust.plugins.clustering.ClusteringAlgorithmPluginInterface;
import justclust.plugins.parsing.FileParserPluginInterface;
import justclust.plugins.visualisation.VisualisationLayoutPluginInterface;

public class OverrepresentationAnalysisMouseListener implements MouseListener {

    public static OverrepresentationAnalysisMouseListener classInstance;

    OverrepresentationAnalysisMouseListener() {
        classInstance = this;
    }

    public void mouseClicked(MouseEvent me) {

        if (me.getComponent() == OverrepresentationAnalysisJDialog.classInstance.overrepresentationAnalysisHelpButton) {
            new OverrepresentationAnalysisHelpJDialog();
        }

        if (me.getComponent() == OverrepresentationAnalysisJDialog.classInstance.geneOntologyBrowseButton) {

            // this code presents a JFileChooser to the user and then, if the
            // user has chosen a file, populates the
            // geneOntologyJTextField with the path of the file
            JFileChooser jFileChooser = new JFileChooser();
            // lastAccessedFile contains the file on the user's hard drive which was
            // last accessed.
            // whenever the user browses their hard drive from within JustClust, this
            // lastAccessedFile will determine the directory which the file chooser
            // opens initially.
            // the user will be taken straight to the directory they last browsed to.
            jFileChooser.setSelectedFile(Data.lastAccessedFile);
            int jFileChooserState = jFileChooser.showOpenDialog(OverrepresentationAnalysisJDialog.classInstance);
            if (jFileChooserState == JFileChooser.APPROVE_OPTION) {
                Data.lastAccessedFile = jFileChooser.getSelectedFile();
                OverrepresentationAnalysisJDialog.classInstance.geneOntologyJTextField.setText(
                        jFileChooser.getSelectedFile().getAbsolutePath());
//                OverrepresentationAnalysisJDialog.classInstance.geneOntologyFile = jFileChooser.getSelectedFile();
            }

        }

        if (me.getComponent() == OverrepresentationAnalysisJDialog.classInstance.geneOntologyAnnotationsBrowseButton) {

            // this code presents a JFileChooser to the user and then, if the
            // user has chosen a file, populates the
            // geneOntologyAnnotationsJTextField with the path of the file
            JFileChooser jFileChooser = new JFileChooser();
            // lastAccessedFile contains the file on the user's hard drive which was
            // last accessed.
            // whenever the user browses their hard drive from within JustClust, this
            // lastAccessedFile will determine the directory which the file chooser
            // opens initially.
            // the user will be taken straight to the directory they last browsed to.
            jFileChooser.setSelectedFile(Data.lastAccessedFile);
            int jFileChooserState = jFileChooser.showOpenDialog(OverrepresentationAnalysisJDialog.classInstance);
            if (jFileChooserState == JFileChooser.APPROVE_OPTION) {
                Data.lastAccessedFile = jFileChooser.getSelectedFile();
                OverrepresentationAnalysisJDialog.classInstance.geneOntologyAnnotationsJTextField.setText(
                        jFileChooser.getSelectedFile().getAbsolutePath());
//                OverrepresentationAnalysisJDialog.classInstance.geneOntologyAnnotationsFile = jFileChooser.getSelectedFile();
            }

        }

    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
}
