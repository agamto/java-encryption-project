package projectEncryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UI extends JFrame
{


    private File selectedFile = null;
    private ArrayList<String> EncryptionStrings = new ArrayList<>();
    private ArrayList<String> DecryptionStrings = new ArrayList<>();
    public  UI()
    {
        JLabel Title_For_File_Project = new JLabel();
        JLabel labelForAdding = new JLabel();
        JButton ButtonForEncryption = new JButton();
        JButton ButtonForDecryption = new JButton();
        JButton buttonForAddEncryption = new JButton();
        JButton buttonForClearingEncryptionCheck = new JButton();
        Title_For_File_Project.setText("Encryption Project");
        Title_For_File_Project.setFont(new Font("Ariel",Font.BOLD,30));
        buttonForClearingEncryptionCheck.setText("clear Encryption");
        buttonForAddEncryption.setText("Add Encryption");
        ButtonForEncryption.setText("Encrypt");
        ButtonForDecryption.setText("Decrypt");
        JButton buttonToChooseFile = new JButton();
        DefaultListModel<String> l = new DefaultListModel<>();
        l.addElement("Sudoku Encryption");
        l.addElement("Modulo 10 Encryption");
        l.addElement("Euler phi Encryption");
        l.addElement("Hamilton Circuit Encryption");
        JList<String> list = new JList<>(l);
        ButtonForDecryption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getSelectedFile() == null)
                {
                    JLabel s= new JLabel("File Not Found");
                    add(s);
                }
                else
                {
                    Decrypt(selectedFile);
                    System.gc();

                    deleteFiles();
                }
            }
        });
        buttonForClearingEncryptionCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelForAdding.setText("no encryption selected");
                EncryptionStrings.clear();
            }
        });
        ButtonForEncryption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getSelectedFile() == null)
                {
                    JLabel lab = new JLabel("File not Found");
                    add(lab);
                    lab.setBounds(50,50,100,100);
                }
                else
                {
                    File f = getSelectedFile();
                    File fileForDelete;
                    for(String s : EncryptionStrings)
                    {
                        fileForDelete = new File(f.getAbsolutePath());
                        switch (s)
                        {
                            case "Sudoku Encryption":
                                SudokuEncryption su = new SudokuEncryption();
                                f = new File(su.encrypt(f));
                                fileForDelete.delete();
                                break;
                            case "Modulo 10 Encryption":
                                Modulo10Encryption m = new Modulo10Encryption();
                                f = new File(m.encrypt(f));
                                fileForDelete.delete();

                                break;
                            case "Euler phi Encryption":
                                EulerPhiToitenEncryption ep = new EulerPhiToitenEncryption();
                                f = new File(ep.encrypt(f));
                                fileForDelete.delete();

                                break;
                            case "Hamilton Circuit Encryption":
                                HammiltonCircuitEncryption hm = new HammiltonCircuitEncryption();
                                f = new File(hm.encrypt(f));
                                fileForDelete.delete();

                                break;
                            default:
                                JLabel lab = new JLabel("why");
                                add(lab);
                                break;
                          }
                        labelForAdding.setText("no encryption selected");
                    }
                    EncryptionStrings.clear();
                }
            }
        });
        buttonForAddEncryption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedValue() == null)
                    return;
                if(labelForAdding.getText().equals("no encryption selected"))
                    labelForAdding.setText("");
                EncryptionStrings.add(list.getSelectedValue());
                labelForAdding.setText(labelForAdding.getText()+list.getSelectedValue() + "  ");
            }
        });
        buttonToChooseFile.setBounds(50,125,150,50);
        buttonToChooseFile.setText("choose File...");
        JLabel label = new JLabel("no file chosen");
        buttonToChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser f = new JFileChooser();
                int returnValue = f.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // update the label with the selected file
                    setSelectedFile( f.getSelectedFile());
                    label.setText(getSelectedFile().getName());
                }
            }
        });
        add(ButtonForDecryption);
        add(label);
        add(buttonToChooseFile);
        add(labelForAdding);
        add(buttonForAddEncryption);
        add(ButtonForEncryption);
        JScrollPane s = new JScrollPane(list);
        add(s);
        add(Title_For_File_Project);
        add(buttonForClearingEncryptionCheck);
        labelForAdding.setText("no encryption selected");
        Title_For_File_Project.setBounds(325,0,400,200);
        ButtonForDecryption.setBounds(200,125,100,50);
        buttonForAddEncryption.setBounds(300,125,150,50);
        ButtonForEncryption.setBounds(450,125,100,50);
        label.setBounds(570,125,100,50);
        buttonForClearingEncryptionCheck.setBounds(670,125,150,50);
        s.setBounds(300,200,300,200);
        labelForAdding.setBounds(200,300,300,300);
        setTitle("Encryption project");
        setSize(900,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }
    private void Decrypt(File f)
    {
        char first = f.getName().charAt(0);
        File newFile = f;
        File fileForDelete;
        while(first == '1' || first == '2' || first == '3' || first == '4')
        {
            first = newFile.getName().charAt(0);
            switch (first) {
                case '1':
                    SudokuEncryption s = new SudokuEncryption();
                    DecryptionStrings.add(newFile.getAbsolutePath());
                    newFile = new File(s.decrypt(newFile));
                    break;
                case '2':
                    Modulo10Encryption md = new Modulo10Encryption();
                    DecryptionStrings.add(newFile.getAbsolutePath());
                    newFile = (new File(md.decrypt(newFile)));
                    break;
                case '3':
                    EulerPhiToitenEncryption er = new EulerPhiToitenEncryption();
                    DecryptionStrings.add(newFile.getAbsolutePath());
                    newFile = (new File(er.decrypt(newFile)));
                    break;
                case '4':
                    HammiltonCircuitEncryption hm = new HammiltonCircuitEncryption();
                    DecryptionStrings.add(newFile.getAbsolutePath());
                    newFile = (new File(hm.decrypt(newFile)));
                    break;
                default:
                    Label l = new Label("no need For new file");
                    add(l);
                    break;
            }
        }

    }
    private void  deleteFiles() {
        for (String s : DecryptionStrings)
        {
            File f = new File(s);
            f.delete();
        }
        DecryptionStrings.clear();
    }
    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }
}
