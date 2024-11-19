/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:11 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.lamnguyen.helper.FileChooseHelper;
import com.lamnguyen.helper.SettingHelper;
import com.lamnguyen.ui.component.list.ListKeyComponent;
import com.lamnguyen.ui.component.menu.MainMenu;
import com.lamnguyen.ui.component.tree.TreeFileComponent;
import com.lamnguyen.ui.controller.SubjectSizeController;
import com.lamnguyen.ui.controller.navigation.IJNavigation;
import com.lamnguyen.ui.controller.navigation.impl.JNavigation;
import com.lamnguyen.ui.view.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static com.lamnguyen.ui.controller.navigation.IJNavigation.NamePage.GenerateTraditionalKeyPage;

public class Application extends JFrame {
    private final Toolkit toolkit;
    private JPanel panelRight;
    private JSplitPane mainContainer, panelLeft;
    private IJNavigation navigation;
    private CipherFileSymmetricalPage symmetricalFilePage;
    private GenerateKeySymmetricalPage generateKeySymmetricalPage;
    private GenerateKeyAsymmetricalPage generateKeyAsymmetricalPage;
    private SubjectSizeController sizeController;
    private ListKeyComponent listKey;
    private String pathWorkSpace;
    private TreeFileComponent tree;
    private CipherTextSymmetricalPage symmetricalTextPage;
    private CipherTextAsymmetricalPage asymmetricalTextPage;
    private HashFilePage hashFile;
    private SignFilePage signFilePage;
    private VerifySignatureFilePage verifySignatureFilePage;
    private GenerateTraditionalKeyPage generateTraditionalKeyPage;

    public Application() {
        toolkit = Toolkit.getDefaultToolkit();
        pathWorkSpace = SettingHelper.getInstance().getWorkSpace();
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

        symmetricalFilePage = new CipherFileSymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherFileSymmetricalPage.name(), symmetricalFilePage);

        symmetricalTextPage = new CipherTextSymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherTextSymmetricalPage.name(), symmetricalTextPage);

        asymmetricalTextPage = new CipherTextAsymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherTextAsymmetricalPage.name(), asymmetricalTextPage);

        hashFile = new HashFilePage(this);
        panelRight.add(IJNavigation.NamePage.HashFilePage.name(), hashFile);

        generateKeySymmetricalPage = new GenerateKeySymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.GenerateKeySymmetricalPage.name(), generateKeySymmetricalPage);
        sizeController.addObserver(generateKeySymmetricalPage);

        generateKeyAsymmetricalPage = new GenerateKeyAsymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.GenerateKeyAsymmetricalPage.name(), generateKeyAsymmetricalPage);
        sizeController.addObserver(generateKeyAsymmetricalPage);

        generateTraditionalKeyPage = new GenerateTraditionalKeyPage(this);
        panelRight.add(GenerateTraditionalKeyPage.name(), generateTraditionalKeyPage);
        sizeController.addObserver(generateTraditionalKeyPage);

        signFilePage = new SignFilePage(this);
        panelRight.add(IJNavigation.NamePage.SignFilePage.name(), signFilePage);

        verifySignatureFilePage = new VerifySignatureFilePage(this);
        panelRight.add(IJNavigation.NamePage.VerifySignatureFilePage.name(), verifySignatureFilePage);

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

    public void encryptFileSymmetrical() {
        symmetricalFilePage.encryptMode();
    }

    public void decryptFileSymmetrical() {
        symmetricalFilePage.decryptMode();
    }

    public void encryptTextSymmetrical() {
        symmetricalTextPage.encryptMode();
    }

    public void decryptTextSymmetrical() {
        symmetricalTextPage.decryptMode();

    }

    public void encryptTextAsymmetrical() {
        asymmetricalTextPage.encryptMode();
    }

    public void decryptTextAsymmetrical() {
        asymmetricalTextPage.decryptMode();
    }

    public void selectWorkSpace() {
        var file = FileChooseHelper.selectFile(FileChooseHelper.SelectFileMode.DIRECTORIES_ONLY);
        if (file == null) return;
        SettingHelper.getInstance().setWorkSpace(file.getAbsolutePath());
        pathWorkSpace = file.getAbsolutePath();
        reloadWorkSpace();
    }

    public void reloadWorkSpace() {
        tree.setPath(pathWorkSpace);
        listKey.setPath(pathWorkSpace);
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

    public void signFile() {
    }
}
