import aes.cbc.ImageEncryptionCBCMode;
import aes.ecb.ImageEncryptionECBMode;
import aes.utils.GenerateKey;
import aes.utils.ImageRead;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

class Main extends JFrame implements ActionListener {

    private ImageRead panel;
    private ImageEncryptionCBCMode imageEncryptionCBCMode;
    private ImageEncryptionECBMode imageEncryptionECBMode;
    private GenerateKey generateKey;
    private File fileName;

    public Main() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("AES Image Encryption / Decryption");
        setLayout(new BorderLayout());
        panel = new ImageRead(); //Image Read object
        getContentPane().add(panel); //adds ImageRead object(panel)
        pack(); //fits the size
        setJMenuBar(MainMenu()); //calls the MainMenu method

        setSize(new Dimension(880, 550));


        generateKey = new GenerateKey();
        imageEncryptionCBCMode = new ImageEncryptionCBCMode();
        imageEncryptionECBMode = new ImageEncryptionECBMode();
    }

    public static void main(String args[]) {
        Main win = new Main();
        win.setVisible(true);
        //CLI for setting image file with argument parameter
        if (args.length > 0) {
            win.actionLoadImage(new File(args[0]));
        }
    }

    private JMenuBar MainMenu() {

        JMenuBar menuBar = new JMenuBar();
        //the three main menus
        JMenu file, menu, help;
        JMenuItem open, save, saveas, close,        //menu items
                setkey, EncryptECBMode, DecryptECBMode, EncryptCBCMode, DecryptCBCMode, about;

        file = new JMenu("File");
        menu = new JMenu("JCrypt");
        help = new JMenu("Help");

        open = new JMenuItem("Open  ..", new ImageIcon("./icon/folder.png"));
        save = new JMenuItem("Save", new ImageIcon("./icon/save.png"));
        saveas = new JMenuItem("Save as   ..");
        close = new JMenuItem("Close ", new ImageIcon("./icon/close.png"));

        setkey = new JMenuItem("Set Pass Key", new ImageIcon("./icon/key.png"));
        EncryptECBMode = new JMenuItem("Encrypt ECB Mode Image", new ImageIcon("./icon/lock.png"));
        DecryptECBMode = new JMenuItem("Decrypt ECB Mode Image", new ImageIcon("./icon/unlock.png"));
        EncryptCBCMode = new JMenuItem("Encrypt CBC Mode Image", new ImageIcon("./icon/lock.png"));
        DecryptCBCMode = new JMenuItem("Decrypt CBC Mode Image", new ImageIcon("./icon/unlock.png"));

        about = new JMenuItem("About", new ImageIcon("./icon/info.png"));

        //adds the items to their related menu containers
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(close);
        menu.add(setkey);
        menu.addSeparator();
        menu.add(EncryptECBMode);
        menu.add(DecryptECBMode);
        menu.add(EncryptCBCMode);
        menu.add(DecryptCBCMode);
        help.add(about);

        //Adding the jmenues to jmenu bar
        menuBar.add(file);
        menuBar.add(menu);
        menuBar.add(help);

        //Adding action listner for the menu items
        open.addActionListener(this);
        setkey.addActionListener(this);
        close.addActionListener(this);
        save.addActionListener(this);
        EncryptECBMode.addActionListener(this);
        about.addActionListener(this);
        saveas.addActionListener(this);
        DecryptECBMode.addActionListener(this);
        EncryptCBCMode.addActionListener(this);
        DecryptCBCMode.addActionListener(this);
        return menuBar;
    }

    /**
     * set File method for implementing encapsulation
     * accesing private variables with public methods
     */
    public void setFile(File file) {
        fileName = file;
    }

    /**
     * Action listener
     * overiding the actionPerformed method of the abstract class ActionListner
     **/
    public void actionPerformed(ActionEvent action) {

        String text = action.getActionCommand();

        try {

            if (text == "Open  ..") {
                actionLoadImage(null);
            } //open menu item listner
            else if (text == "Save") {
                actionSaveImage(fileName);
            } //overites the file with the new image
            else if (text == "Save as   ..") {
                actionSaveImage(null);
            } //passes null for file name to add name by user
            else if (text == "Close ") {
                System.exit(0);
            } //exits the program
            else if (text == "Set Pass Key") {
                actionKeyDialog();
            }else if (text == "Encrypt ECB Mode Image") {
                panel.setImage(imageEncryptionECBMode.encryptBufferedImage(fileName.getPath())); //Encrypt action listner
            } else if (text == "Decrypt ECB Mode Image") {
                panel.setImage(imageEncryptionECBMode.decryptedBufferedImage(fileName.getPath())); //Decrypt action listner
            }else if (text == "Encrypt CBC Mode Image") {
                panel.setImage(imageEncryptionCBCMode.encryptBufferedImage(fileName.getPath())); //Encrypt action listner
            } else if (text == "Decrypt CBC Mode Image") {
                panel.setImage(imageEncryptionCBCMode.decryptedBufferedImage(fileName.getPath())); //Decrypt action listner
            } else if (text == "About") {
                DisplayContactinfo();
            }

        } catch (Exception err) {
            System.out.println("ERROR:" + err);
        }
    }

    /**
     * Set the key
     **/
    public void actionKeyDialog() {
        String key = new String(generateKey.getKey());

        key = (String) JOptionPane.showInputDialog(this,
                "Enter a 16 byte key (current key= " +
                        key.getBytes().length + " bytes)", key);

        while (key != null && key.getBytes().length != 16) {

            key = (String) JOptionPane.showInputDialog(this,
                    "Enter a 16 byte key (current key= " +
                            key.getBytes().length + " bytes)", key);
        }

        if (key != null) {
            imageEncryptionECBMode.setKey(key.getBytes());
            imageEncryptionCBCMode.setKey(key.getBytes());
        }
    }

    /**
     * Load an image from a file
     */
    public void actionLoadImage(File imageFile) {

        if (imageFile == null) {
            JFileChooser fc = new JFileChooser(fileName);
            fc.setControlButtonsAreShown(false);
            fc.showOpenDialog(this);
            imageFile = fc.getSelectedFile();
        }
        //sets the file if it is not null
        if (imageFile != null) {

            panel.setImage(imageFromFile(imageFile));
            setFile(imageFile);
        }
    }

    /**
     * Load an image from a file
     */
    private BufferedImage imageFromFile(File file) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return img;
    }

    /**
     * Save an image from a file
     *
     * @param imageFile the name of the file to save, use "null" to access a dialog
     */
    public void actionSaveImage(File imageFile) {

        if (imageFile == null) {
            JFileChooser filechooser = new JFileChooser(fileName);
            filechooser.showSaveDialog(this);
            imageFile = filechooser.getSelectedFile();
        }

        if (imageFile != null) {
            try {
                ImageIO.write(panel.getImage(), "png", imageFile);
            } catch (Exception e) {
                System.out.println("Error:" + e);
            }
            setFile(imageFile);
        }
    }

    public void DisplayContactinfo() {

        JFrame contact = new JFrame("About");
        contact.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contact.setSize(new Dimension(500, 430));
        contact.setLayout(new BorderLayout());
        contact.setDefaultCloseOperation(3);
        contact.setResizable(false);

        String About = "\n\n" +
                "Version 1.0 \n" +
                "University of Prishtina \"Hasan Prishtina\n" +
                "Faculty of Electrical and Computer Engineering\n" +
                "Computer and Software Engineering\n" +
                "Welcome to AES Image Encryption and Decryption with Mode ECB and CBC      \n\n" +
                "CREATED BY:\n\n" +
                "Student Name : Gentrit Ibishi		\n" +
                "Email :gentritibishi@gmail.com\n"+
                "November 13,2023\n\n" +


                "This program Encrypts image files with 128 bits \nADVANCED ENCRYPTION STANDARD (AES) algorithm\n" +
                "and it can be easily convertable to AES 196 or AES 256 bit encryption. \n" +
                "It uses Java Cryptographic Extension (JCE)\njavax.crypto package API.\n " +
                "It uses Bouncy Castle a lightweight cryptography API for Java \norg.bouncycastle.crypto package API.\n ";
        JTextArea cont1 = new JTextArea(About);
        contact.add(new JScrollPane(cont1), BorderLayout.CENTER);
        cont1.setEditable(false);

        contact.setVisible(true);

    }
}
