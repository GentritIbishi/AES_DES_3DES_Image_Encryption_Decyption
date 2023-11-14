import aes.cbc.AESImageEncryptionCBCMode;
import aes.ecb.AESImageEncryptionECBMode;
import aes.utils.GenerateKey;
import aes.utils.ImageRead;
import des.cbc.DESImageEncryptionCBCMode;
import des.ecb.DESImageEncryptionECBMode;
import tripledes.cbc.DES3ImageEncryptionCBCMode;
import tripledes.ecb.DES3ImageEncryptionECBMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

class Main extends JFrame implements ActionListener {

    private ImageRead panel;
    private AESImageEncryptionCBCMode AESImageEncryptionCBCMode;
    private AESImageEncryptionECBMode AESImageEncryptionECBMode;
    private DESImageEncryptionECBMode DESImageEncryptionECBMode;
    private DESImageEncryptionCBCMode DESImageEncryptionCBCMode;
    private DES3ImageEncryptionECBMode DES3ImageEncryptionECBMode;
    private DES3ImageEncryptionCBCMode DES3ImageEncryptionCBCMode;
    private GenerateKey generateKey;
    private File fileName;

    public Main() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("AES / DES / Triple DES Image Encryption / Decryption");
        setLayout(new BorderLayout());
        panel = new ImageRead(); //Image Read object
        getContentPane().add(panel); //adds ImageRead object(panel)
        pack(); //fits the size
        setJMenuBar(MainMenu()); //calls the MainMenu method

        setSize(new Dimension(880, 550));

        generateKey = new GenerateKey();

        AESImageEncryptionCBCMode = new AESImageEncryptionCBCMode();
        AESImageEncryptionECBMode = new AESImageEncryptionECBMode();

        DESImageEncryptionECBMode = new DESImageEncryptionECBMode();
        DESImageEncryptionCBCMode = new DESImageEncryptionCBCMode();

        DES3ImageEncryptionECBMode = new DES3ImageEncryptionECBMode();
        DES3ImageEncryptionCBCMode = new DES3ImageEncryptionCBCMode();
        centerFrameOnScreen(this);
    }

    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
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
                setkey,
                AESEncryptECBMode, AESDecryptECBMode,
                AESEncryptCBCMode, AESDecryptCBCMode,
                DES_EncryptECBMode, DES_DecryptEBCMode,
                DES_EncryptCBCMode, DES_DecryptCBCMode,
                TRIPLEDES_EncryptECBMode, TRIPLEDES_DecryptEBCMode,
                TRIPLEDES_EncryptCBCMode, TRIPLEDES_DecryptCBCMode, about;

        file = new JMenu("File");
        menu = new JMenu("JCrypt");
        help = new JMenu("Help");

        open = new JMenuItem("Open  ..", new ImageIcon("./icon/folder.png"));
        save = new JMenuItem("Save", new ImageIcon("./icon/save.png"));
        saveas = new JMenuItem("Save as   ..");
        close = new JMenuItem("Close ", new ImageIcon("./icon/close.png"));

        setkey = new JMenuItem("Set Pass Key", new ImageIcon("./icon/key.png"));
        AESEncryptECBMode = new JMenuItem("AES Encrypt ECB Mode Image", new ImageIcon("./icon/lock.png"));
        AESDecryptECBMode = new JMenuItem("AES Decrypt ECB Mode Image", new ImageIcon("./icon/unlock.png"));

        AESEncryptCBCMode = new JMenuItem("AES Encrypt CBC Mode Image", new ImageIcon("./icon/lock.png"));
        AESDecryptCBCMode = new JMenuItem("AES Decrypt CBC Mode Image", new ImageIcon("./icon/unlock.png"));

        DES_EncryptECBMode = new JMenuItem("DES Encrypt ECB Mode Image", new ImageIcon("./icon/lock.png"));
        DES_DecryptEBCMode = new JMenuItem("DES Decrypt ECB Mode Image", new ImageIcon("./icon/unlock.png"));

        DES_EncryptCBCMode = new JMenuItem("DES Encrypt CBC Mode Image", new ImageIcon("./icon/lock.png"));
        DES_DecryptCBCMode = new JMenuItem("DES Decrypt CBC Mode Image", new ImageIcon("./icon/unlock.png"));

        TRIPLEDES_EncryptECBMode = new JMenuItem("TRIPLE DES Encrypt ECB Mode Image", new ImageIcon("./icon/lock.png"));
        TRIPLEDES_DecryptEBCMode = new JMenuItem("TRIPLE DES Decrypt ECB Mode Image", new ImageIcon("./icon/unlock.png"));

        TRIPLEDES_EncryptCBCMode = new JMenuItem("TRIPLE DES Encrypt CBC Mode Image", new ImageIcon("./icon/lock.png"));
        TRIPLEDES_DecryptCBCMode = new JMenuItem("TRIPLE DES Decrypt CBC Mode Image", new ImageIcon("./icon/unlock.png"));

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

        menu.add(AESEncryptECBMode);
        menu.add(AESDecryptECBMode);
        menu.add(AESEncryptCBCMode);
        menu.add(AESDecryptCBCMode);

        menu.add(DES_EncryptECBMode);
        menu.add(DES_DecryptEBCMode);
        menu.add(DES_EncryptCBCMode);
        menu.add(DES_DecryptCBCMode);

        menu.add(TRIPLEDES_EncryptECBMode);
        menu.add(TRIPLEDES_DecryptEBCMode);
        menu.add(TRIPLEDES_EncryptCBCMode);
        menu.add(TRIPLEDES_DecryptCBCMode);
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
        about.addActionListener(this);
        saveas.addActionListener(this);

        AESEncryptECBMode.addActionListener(this);
        AESDecryptECBMode.addActionListener(this);
        AESEncryptCBCMode.addActionListener(this);
        AESDecryptCBCMode.addActionListener(this);

        DES_EncryptECBMode.addActionListener(this);
        DES_DecryptEBCMode.addActionListener(this);
        DES_EncryptCBCMode.addActionListener(this);
        DES_DecryptCBCMode.addActionListener(this);

        TRIPLEDES_EncryptECBMode.addActionListener(this);
        TRIPLEDES_DecryptEBCMode.addActionListener(this);
        TRIPLEDES_EncryptCBCMode.addActionListener(this);
        TRIPLEDES_DecryptCBCMode.addActionListener(this);
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
            }else if (text == "AES Encrypt ECB Mode Image") {
                panel.setImage(AESImageEncryptionECBMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "AES Decrypt ECB Mode Image") {
                panel.setImage(AESImageEncryptionECBMode.decryptedBufferedImage(fileName.getPath()));
            }else if (text == "AES Encrypt CBC Mode Image") {
                panel.setImage(AESImageEncryptionCBCMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "AES Decrypt CBC Mode Image") {
                panel.setImage(AESImageEncryptionCBCMode.decryptedBufferedImage(fileName.getPath()));
            }else if (text == "DES Encrypt ECB Mode Image") {
                panel.setImage(DESImageEncryptionECBMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "DES Decrypt ECB Mode Image") {
                panel.setImage(DESImageEncryptionECBMode.decryptedBufferedImage(fileName.getPath()));
            }else if (text == "DES Encrypt CBC Mode Image") {
                panel.setImage(DESImageEncryptionCBCMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "DES Decrypt CBC Mode Image") {
                panel.setImage(DESImageEncryptionCBCMode.decryptedBufferedImage(fileName.getPath()));
            }else if (text == "TRIPLE DES Encrypt ECB Mode Image") {
                panel.setImage(DES3ImageEncryptionECBMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "TRIPLE DES Decrypt ECB Mode Image") {
                panel.setImage(DES3ImageEncryptionECBMode.decryptedBufferedImage(fileName.getPath()));
            }else if (text == "TRIPLE DES Encrypt CBC Mode Image") {
                panel.setImage(DES3ImageEncryptionCBCMode.encryptBufferedImage(fileName.getPath()));
            } else if (text == "TRIPLE DES Decrypt CBC Mode Image") {
                panel.setImage(DES3ImageEncryptionCBCMode.decryptedBufferedImage(fileName.getPath()));
            }  else if (text == "About") {
                displayContactInfo();
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
        String keySizesInfo =
                "Information\n"+
                "DES:\n" +
                        "  Key size: 56 bits (7 bytes)\n\n" +

                        "AES:\n" +
                        "  128-bit key: 16 bytes\n" +
                        "  192-bit key: 24 bytes\n" +
                        "  256-bit key: 32 bytes\n\n" +

                        "3DES:\n" +
                        "  56-bit key: 7 bytes\n" +
                        "  112-bit key: 14 bytes\n" +
                        "  168-bit key: 21 bytes\n";

        key = (String) JOptionPane.showInputDialog(this,
                keySizesInfo+"(current key= " +
                        key.getBytes().length + " bytes)\n\n", key);

        while (key != null && key.getBytes().length != 16)
        {
            key = (String) JOptionPane.showInputDialog(this,
                    keySizesInfo+"(current key= " +
                            key.getBytes().length + " bytes)\n\n", key);
        }

        if (key != null)
        {
            if(key.length() == 8)
            {
                DESImageEncryptionECBMode.setKey(key.getBytes());
                DESImageEncryptionCBCMode.setKey(key.getBytes());
            }

            if(key.length() >= 8)
            {
                AESImageEncryptionECBMode.setKey(key.getBytes());
                AESImageEncryptionCBCMode.setKey(key.getBytes());

                DES3ImageEncryptionECBMode.setKey(key.getBytes());
                DES3ImageEncryptionCBCMode.setKey(key.getBytes());
            }

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

    public static void displayContactInfo() {
        JFrame contact = new JFrame("About");
        contact.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contact.setSize(new Dimension(500, 430));
        contact.setLayout(new BorderLayout());
        contact.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contact.setResizable(false);

        String keySizesInfo =
                "Information\n" +
                        "DES:\n" +
                        "  Key size: 56 bits (7 bytes)\n\n" +

                        "AES:\n" +
                        "  128-bit key: 16 bytes\n" +
                        "  192-bit key: 24 bytes\n" +
                        "  256-bit key: 32 bytes\n\n" +

                        "3DES:\n" +
                        "  56-bit key: 7 bytes\n" +
                        "  112-bit key: 14 bytes\n" +
                        "  168-bit key: 21 bytes\n";

        String aboutText = "\n\n" +
                "Version 1.0 \n" +
                "University of Prishtina \"Hasan Prishtina\"\n" +
                "Faculty of Electrical and Computer Engineering\n" +
                "Computer and Software Engineering\n" +
                "Welcome to AES / DES / 3DES Image Encryption and Decryption with Mode ECB and CBC\n\n" +
                "CREATED BY:\n\n" +
                "Student Name: Gentrit Ibishi\n" +
                "Email: gentritibishi@gmail.com\n" +
                "November 13, 2023\n\n" +

                "This program encrypts image files:\n" +
                keySizesInfo +
                "It uses Java Cryptographic Extension (JCE)\n" +
                "javax.crypto package API.\n " +
                "It uses Bouncy Castle, a lightweight cryptography API for Java,\n" +
                "org.bouncycastle.crypto package API.\n ";

        JTextArea aboutTextArea = new JTextArea(aboutText);
        aboutTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);

        JLabel titleLabel = new JLabel("AES / DES / 3DES Image Encryption and Decryption with Mode ECB and CBC");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        contact.add(titleLabel, BorderLayout.NORTH);
        contact.add(new JScrollPane(aboutTextArea), BorderLayout.CENTER);

        contact.setVisible(true);
    }
}
