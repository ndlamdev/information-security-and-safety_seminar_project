/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:00 AM - 29/10/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.controller.navigation;

public interface IJNavigation {
    void push(NamePage name);

    enum NamePage {
        CipherFileSymmetricalPage, GenerateKeySymmetricalPage, GenerateKeyAsymmetricalPage, CipherTextSymmetricalPage, CipherTextAsymmetricalPage, HashFilePage, SignFilePage, VerifySignatureFilePage, GenerateTraditionalKeyPage, AboutPage
    }
}