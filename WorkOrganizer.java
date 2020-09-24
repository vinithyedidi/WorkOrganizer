package workOrganizer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.List;
import java.util.ArrayList;


public class WorkOrganizer extends JFrame implements ActionListener, WindowListener {

    private static final long serialVersionUID = 1L;
    public static final workOrganizer.AppDataReader csvReader = new AppDataReader();
    public static final Image icon = Toolkit.getDefaultToolkit().getImage(AppDataReader.class.getResource("Icon.png").getPath());
    public static final Dimension textFieldSize = new Dimension(500, 150);
    public static final Dimension notesDisplaySize = new Dimension(500, 600);
    public static final Dimension btnSize = new Dimension(250, 50);
    public static final Dimension frameSize = new Dimension(500, 660);

    private JPanel notesDisplay;

    public WorkOrganizer() {

        notesDisplay = new JPanel();
        notesDisplay.setLayout(new BoxLayout(notesDisplay, BoxLayout.Y_AXIS));
        notesDisplay.setMinimumSize(notesDisplaySize);
        for (String note : csvReader.getNotes()) {
            createJTextField(note);
        }
        JScrollPane scrollableNotesDisplay = new JScrollPane(notesDisplay, 
                                                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                                                             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableNotesDisplay.setPreferredSize(notesDisplaySize);
        scrollableNotesDisplay.getVerticalScrollBar().setUnitIncrement(10);

        JButton addBtn = new JButton("New");
        addBtn.setPreferredSize(btnSize);
        addBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createJTextField("Text");
                revalidate();
                repaint();
            }
        });
        JButton saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(btnSize);
        saveBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToPersistentData();
            }
        });
        JPanel btnDisplay = new JPanel();
        btnDisplay.setLayout(new BorderLayout());
        btnDisplay.add(addBtn, BorderLayout.EAST);
        btnDisplay.add(saveBtn, BorderLayout.WEST);

        
        setTitle("Work Organizer");
        setIconImage(icon);
        setLayout(new BorderLayout());
        add(scrollableNotesDisplay, BorderLayout.NORTH);
        add(btnDisplay, BorderLayout.SOUTH);
        getContentPane().setPreferredSize(frameSize);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    public JPanel getNotesDisplay() {
        return notesDisplay;
    }
    
    private void createJTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(textFieldSize);
        textField.setMinimumSize(textFieldSize);
        textField.setMaximumSize(textFieldSize);
        textField.setFont(new Font("Arial", Font.PLAIN, 11));
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().isEmpty()) {
                    getNotesDisplay().remove(textField);
                    revalidate();
                    repaint();
                    saveToPersistentData();
                } else {
                    saveToPersistentData();
                }
    
                }
            });
            getNotesDisplay().add(textField);
        saveToPersistentData();
    }

    private void saveToPersistentData() {
        Component[] textFields = getNotesDisplay().getComponents();
        List<String> newNotes = new ArrayList<>();
        for (int i = 0; i < textFields.length; i ++) {
            if (textFields[i].getClass().equals(JTextField.class)) {
                JTextField temp = (JTextField) textFields[i];
                newNotes.add(temp.getText());
            }
        }
        csvReader.writeToFile(newNotes);
    }

    public static void main(String[] args) {
        new WorkOrganizer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        saveToPersistentData();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        saveToPersistentData();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        saveToPersistentData();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        saveToPersistentData();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        saveToPersistentData();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        saveToPersistentData();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        saveToPersistentData();
    }
}