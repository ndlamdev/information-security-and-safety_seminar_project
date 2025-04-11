
# Kimi Encrypt And Decrypt Tool

Kimi Encrypt And Decrypt Tool is a powerful utility for encryption and decryption, designed to provide a seamless experience for managing secure data. This tool is available for both Windows and Linux platforms.

---

## Features

- **Cross-Platform**: Supports both Windows and Linux.
- **User-Friendly**: Easy to install with shortcuts and menu integration.
- **Secure**: Reliable encryption and decryption with a simple interface.

---

## Installation

### Windows
1. Make sure Java is installed and added to your system's PATH.
2. Download the `.exe` installer package.
3. Run the installer and follow the on-screen instructions.
4. The application will be installed to `C:\Program Files\Kimi Encrypt And Decrypt Tool`.

Alternatively, you can create the installer yourself using the `jpackage` command:

```bash
jpackage --input .\target\ --name "Kimi Encrypt And Decrypt Tool" --main-jar KimiEncryptAndDecryptTool-1.0.0-jar-with-dependencies.jar --main-class com.lamnguyen.Program --type exe --icon .\nlu.ico --win-shortcut --win-menu --win-menu-group "Kimi Tools" --install-dir "C:\Program Files\Kimi Encrypt And Decrypt Tool"
```

### Linux
1. Ensure Java is installed on your system.
2. Download the `.deb` installer package.
3. Install the package using your package manager:

   ```bash
   sudo dpkg -i KimiEncryptAndDecryptTool.deb
   ```

4. The application will be installed under `/opt/Kimi Encrypt And Decrypt Tool`.

Alternatively, create the `.deb` package with the `jpackage` command:

```bash
jpackage --input ./target/ --name "Kimi Encrypt And Decrypt Tool" --main-jar KimiEncryptAndDecryptTool-1.0.0-jar-with-dependencies.jar --main-class com.lamnguyen.Program --type deb --icon ./nlu.png --linux-shortcut --linux-menu-group "Kimi Tools" --install-dir "/opt/KimiEncryptAndDecryptTool"
```

### Red Hat Enterprise Linux
1. Ensure Java is installed on your system.
2. Download the `.rpm` installer package.
3. Install the package using your package manager:

   ```bash
   sudo rpm -i KimiEncryptAndDecryptTool.rpm
   ```

4. The application will be installed under `/opt/Kimi Encrypt And Decrypt Tool`.

Alternatively, create the `.rpm` package with the `jpackage` command:

```bash
jpackage --input ./target/ --name "Kimi Encrypt And Decrypt Tool" --main-jar KimiEncryptAndDecryptTool-1.0.0-jar-with-dependencies.jar --main-class com.lamnguyen.Program --type rpm --icon ./nlu.png --linux-shortcut --linux-menu-group "Kimi Tools" --install-dir "/opt/KimiEncryptAndDecryptTool"
```

---

## Usage

1. Launch the application from the Start Menu (Windows) or Applications Menu (Linux).
2. Use the intuitive interface to encrypt or decrypt your data.
3. Follow the on-screen prompts for guidance.

---

## Requirements

- **Java**: Ensure you have a JDK installed (minimum version: 11).
- **Disk Space**: At least 100 MB free space for installation.

---

## Development Setup

To build and package the tool manually:

1. Clone the repository:

   ```bash
   git clone https://github.com/ndlamdev/information-security-and-safety_seminar_project.git
   cd KimiEncryptAndDecryptTool
   ```

2. Build the project with Maven:

   ```bash
   mvn clean package
   ```

3. Use the `jpackage` commands provided in the **Installation** section to create the installer for your platform.

---

## Troubleshooting

- **Java Not Found**: Ensure Java is installed and added to your system's PATH.
- **Installation Errors on Linux**: Run `sudo apt --fix-broken install` to resolve dependency issues.

---

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

---