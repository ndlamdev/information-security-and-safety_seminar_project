/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:11 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.lamnguyen.helper.DialogProgressHelper;
import com.lamnguyen.helper.FileChooseHelper;
import com.lamnguyen.helper.IconResizeHelper;
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
    private HashPage hashPage;
    private SignPage signPage;
    private VerifySignaturePage verifySignaturePage;
    private GenerateTraditionalKeyPage generateTraditionalKeyPage;
    private CipherTextTraditionalPage traditionalTextPage;
    private int width, height;
    private CipherFileAsymmetricalPage asymmetricalFilePage;

    private Application() {
        toolkit = Toolkit.getDefaultToolkit();
        width = toolkit.getScreenSize().width - 200;
        height = toolkit.getScreenSize().height - 100;
        pathWorkSpace = SettingHelper.getInstance().getWorkSpace();
        this.init();
        this.setVisible(true);
        sizeController.onChange();
        reloadWorkSpaceAsync();
    }

    public static void run() {
        setupUIManager();
        new Application();
    }

    private void init() {
        sizeController = SubjectSizeController.getInstance();

        this.setTitle("Phần mềm mã hóa/giải mã file Lam Nguyễn");
        this.setIconImage(IconResizeHelper.initImageIcon("logo.png", 50, 50).getImage());

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 50, width, height);
        this.setResizable(false);

        CardLayout layout = new CardLayout();
        panelRight = new JPanel(layout) {{
            setBackground(Color.WHITE);
        }};
        navigation = new JNavigation(layout, panelRight);
        this.setJMenuBar(new MainMenu(navigation, this));

        symmetricalFilePage = new CipherFileSymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherFileSymmetricalPage.name(), symmetricalFilePage);
        sizeController.addObserver(symmetricalFilePage);

        asymmetricalFilePage = new CipherFileAsymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherFileAsymmetricalPage.name(), asymmetricalFilePage);
        sizeController.addObserver(asymmetricalFilePage);

        symmetricalTextPage = new CipherTextSymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherTextSymmetricalPage.name(), symmetricalTextPage);
        sizeController.addObserver(symmetricalTextPage);

        asymmetricalTextPage = new CipherTextAsymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherTextAsymmetricalPage.name(), asymmetricalTextPage);
        sizeController.addObserver(asymmetricalTextPage);

        traditionalTextPage = new CipherTextTraditionalPage(this);
        panelRight.add(IJNavigation.NamePage.CipherTextTraditionalPage.name(), traditionalTextPage);
        sizeController.addObserver(traditionalTextPage);

        hashPage = new HashPage(this);
        panelRight.add(IJNavigation.NamePage.HashPage.name(), hashPage);
        sizeController.addObserver(hashPage);

        generateKeySymmetricalPage = new GenerateKeySymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.GenerateKeySymmetricalPage.name(), generateKeySymmetricalPage);
        sizeController.addObserver(generateKeySymmetricalPage);

        generateKeyAsymmetricalPage = new GenerateKeyAsymmetricalPage(this);
        panelRight.add(IJNavigation.NamePage.GenerateKeyAsymmetricalPage.name(), generateKeyAsymmetricalPage);
        sizeController.addObserver(generateKeyAsymmetricalPage);

        generateTraditionalKeyPage = new GenerateTraditionalKeyPage(this);
        panelRight.add(GenerateTraditionalKeyPage.name(), generateTraditionalKeyPage);
        sizeController.addObserver(generateTraditionalKeyPage);

        signPage = new SignPage(this);
        panelRight.add(IJNavigation.NamePage.SignPage.name(), signPage);
        sizeController.addObserver(signPage);

        verifySignaturePage = new VerifySignaturePage(this);
        panelRight.add(IJNavigation.NamePage.VerifySignaturePage.name(), verifySignaturePage);
        sizeController.addObserver(verifySignaturePage);

        panelLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        addPropertyChangeListenerHelper(panelLeft, height / 7 * 4, height / 7 * 4);

        tree = new TreeFileComponent();
        panelLeft.add(tree);

        listKey = new ListKeyComponent();
        sizeController.addObserver(listKey);
        panelLeft.add(listKey);

        mainContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        addPropertyChangeListenerHelper(mainContainer, width / 4, width / 5);

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

    public void encryptFileAsymmetrical() {
        asymmetricalFilePage.encryptMode();
    }

    public void decryptFileAsymmetrical() {
        asymmetricalFilePage.decryptMode();
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
        reloadWorkSpaceAsync();
    }

    public void reloadWorkSpaceAsync() {
        DialogProgressHelper.runProcess(process -> {
            tree.setPath(pathWorkSpace);
            listKey.setPath(pathWorkSpace);
            process.dispose();
        });
    }

    public void reloadWorkSpaceSync() {
        tree.setPath(pathWorkSpace);
        listKey.setPath(pathWorkSpace);
    }


    private static void setupUIManager() {
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
        signPage.signFileMode();
    }

    public void signText() {
        signPage.signTextMode();
    }

    public void verifySignatureFile() {
        verifySignaturePage.verifySignatureFileMode();
    }

    public void verifySignatureText() {
        verifySignaturePage.verifySignatureTextMode();
    }

    public void encryptTextTraditional() {
        traditionalTextPage.encryptMode();
    }

    public void decryptTextTraditional() {
        traditionalTextPage.decryptMode();
    }

    private void addPropertyChangeListenerHelper(JSplitPane panel, int max, int min) {
        panel.setDividerLocation(max);
        panel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int currentLocation = panelLeft.getDividerLocation();
                if (currentLocation > max)
                    panelLeft.setDividerLocation(max);
                if (currentLocation <= min)
                    panelLeft.setDividerLocation(min);

                sizeController.onChange();
            }
        });
    }

    public void hashText() {
        hashPage.hashTextMode();
    }

    public void hashFile() {
        hashPage.hashFileMode();
    }
}
