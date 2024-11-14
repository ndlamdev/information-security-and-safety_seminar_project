/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:11 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import main.java.helper.FileChooseHelper;
import main.java.ui.fileEncrypt.component.list.ListKeyComponent;
import main.java.ui.fileEncrypt.component.menu.MainMenu;
import main.java.ui.fileEncrypt.component.tree.TreeFileComponent;
import main.java.ui.fileEncrypt.controller.SubjectSizeController;
import main.java.ui.fileEncrypt.controller.navigation.IJNavigation;
import main.java.ui.fileEncrypt.controller.navigation.impl.JNavigation;
import main.java.ui.fileEncrypt.view.CipherSymmetricalPage;
import main.java.ui.fileEncrypt.view.GenerateKeyAsymmetricalPage;
import main.java.ui.fileEncrypt.view.GenerateKeySymmetricalPage;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;

public class Application extends JFrame {
    private final Toolkit toolkit;
    private JPanel panelRight;
    private JSplitPane mainContainer, panelLeft;
    private IJNavigation navigation;
    private CipherSymmetricalPage symmetricalPage;
    private GenerateKeySymmetricalPage generateKeySymmetricalPage;
    private GenerateKeyAsymmetricalPage generateKeyAsymmetricalPage;
    private SubjectSizeController sizeController;
    private ListKeyComponent listKey;
    private String pathWorkSpace;
    private TreeFileComponent tree;
    private Preferences prefs;
    final String PREF_WORK_SPACE = "work_space";

    public Application() {
        toolkit = Toolkit.getDefaultToolkit();
        prefs = Preferences.userNodeForPackage(main.java.ui.fileEncrypt.Application.class);
        pathWorkSpace = prefs.get(PREF_WORK_SPACE, "./");
        this.init();
    }

    private void init() {


        sizeController = SubjectSizeController.getInstance();

        this.setTitle("Phần mềm mã hóa/giải mã file Lam Nguyên");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 50, 1800, 1000);
        this.setResizable(false);


        CardLayout layout = new CardLayout();
        panelRight = new JPanel(layout) {{
            setBackground(Color.WHITE);
        }};
        navigation = new JNavigation(layout, panelRight);
        this.setJMenuBar(new MainMenu(navigation, this));

        symmetricalPage = new CipherSymmetricalPage();
        panelRight.add(IJNavigation.NamePage.SymmetricalPage.name(), symmetricalPage);

        generateKeySymmetricalPage = new GenerateKeySymmetricalPage();
        panelRight.add(IJNavigation.NamePage.GenerateKeySymmetricalPage.name(), generateKeySymmetricalPage);
        sizeController.addObserver(generateKeySymmetricalPage);

        generateKeyAsymmetricalPage = new GenerateKeyAsymmetricalPage();
        panelRight.add(IJNavigation.NamePage.GenerateKeyAsymmetricalPage.name(), generateKeyAsymmetricalPage);
        sizeController.addObserver(generateKeyAsymmetricalPage);

        panelLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT) {{
            setDividerLocation(700);
            addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    int currentLocation = panelLeft.getDividerLocation();
                    if (currentLocation > 700)
                        panelLeft.setDividerLocation(700);
                    if (currentLocation <= 500)
                        panelLeft.setDividerLocation(500);

                    sizeController.onChange();
                }
            });
        }};

        tree = new TreeFileComponent(pathWorkSpace);
        panelLeft.add(tree);

        listKey = new ListKeyComponent(pathWorkSpace);
        sizeController.addObserver(listKey);
        panelLeft.add(listKey);


        mainContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight) {{
            setDividerLocation(500);
            addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    int currentLocation = mainContainer.getDividerLocation();
                    if (currentLocation > 500)
                        mainContainer.setDividerLocation(500);
                    if (currentLocation <= 300)
                        mainContainer.setDividerLocation(300);

                    sizeController.onChange();
                }
            });
        }};

        this.setContentPane(mainContainer);
        this.setVisible(true);
        sizeController.onChange();
    }

    public void encryptFile() {
        symmetricalPage.encryptMode();
    }

    public void decryptFile() {
        symmetricalPage.decryptMode();
    }

    public void selectWorkSpace() {
        var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.DIRECTORIES_ONLY);
        if (file == null) return;
        prefs.put(PREF_WORK_SPACE, file.getAbsolutePath());
        tree.setPath(file.getAbsolutePath());
        listKey.setPath(file.getAbsolutePath());
    }

    public static void setup() {
        FlatMacLightLaf.setup();

        UIManager.put("Button.arc", 10);

        UIManager.put("Button.margin", new Insets(5, 5, 5, 5));

        UIManager.put("Component.arc", 10);

        UIManager.put("ProgressBar.arc", 10);

        UIManager.put("TextComponent.arc", 10);

        UIManager.put("TextField.margin", new Insets(8, 8, 8, 8));

        UIManager.put("ComboBox.padding", new Insets(8, 8, 8, 8));

        UIManager.put("TabbedPane.selectedBackground", Color.white);

        UIManager.put("TabbedPane.selectedBackground", Color.white);

    }
}
