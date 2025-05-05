
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

## 📦 Giải thích các tham số `jpackage`

Lệnh `jpackage` được dùng để đóng gói ứng dụng Java thành một gói cài đặt riêng biệt cho từng hệ điều hành (Windows, Linux, macOS). Các tham số thường dùng:

| Tham số | Giải thích |
|--------|------------|
| `--input` | Thư mục chứa các tệp JAR và tài nguyên cần thiết cho ứng dụng. |
| `--name` | Tên ứng dụng hiển thị cho người dùng. |
| `--main-jar` | Tên của file JAR chính chứa entry point của ứng dụng (file JAR đã được đóng gói với dependencies). |
| `--main-class` | Lớp Java chính có chứa phương thức `main` để chạy ứng dụng. |
| `--type` | Loại gói cài đặt cần tạo. Có thể là `exe` (Windows), `deb` (Debian/Ubuntu), `rpm` (RHEL/Fedora), `pkg` (macOS), hoặc `app-image` (cross-platform). |
| `--icon` | Đường dẫn đến file icon đại diện cho ứng dụng. Dùng `.ico` cho Windows, `.png` cho Linux. |
| `--install-dir` | Thư mục đích để cài đặt ứng dụng. |
| `--win-shortcut` | (Windows) Tạo shortcut trên desktop hoặc Start Menu. |
| `--win-menu` | (Windows) Thêm ứng dụng vào Start Menu. |
| `--win-menu-group` | (Windows) Nhóm menu để ứng dụng xuất hiện trong Start Menu. |
| `--linux-shortcut` | (Linux) Tạo shortcut trên menu ứng dụng (Application Menu). |
| `--linux-menu-group` | (Linux) Nhóm phân loại ứng dụng trong Application Menu. |

---

## 🔧 Đóng gói bằng Inno Setup (chỉ dành cho Windows)

Ngoài `jpackage`, bạn có thể sử dụng **Inno Setup** để tạo trình cài đặt `.exe` cho ứng dụng Java của mình.

### Yêu cầu
- File `JAR` đã được đóng gói (bao gồm dependencies).
- Bộ `JRE` hoặc `JDK` để chạy ứng dụng (có thể đóng gói sẵn).
- [Inno Setup](https://jrsoftware.org/isinfo.php) đã được cài đặt trên Windows.

### Cấu trúc thư mục đề xuất
```
KimiEncryptAndDecryptTool/
├── app/
│   ├── KimiEncryptAndDecryptTool.jar
│   └── jre/                # JRE tương ứng (nếu đóng gói)
├── nlu.ico
└── setup.iss              # File script Inno Setup
```

### Ví dụ file `setup.iss`

```ini
[Setup]
AppName=Kimi Encrypt And Decrypt Tool
AppVersion=1.0
DefaultDirName={pf}\Kimi Encrypt And Decrypt Tool
DefaultGroupName=Kimi Tools
OutputBaseFilename=KimiEncryptAndDecryptToolInstaller
SetupIconFile=nlu.ico
Compression=lzma
SolidCompression=yes

[Files]
Source: "app\*"; DestDir: "{app}"; Flags: recursesubdirs

[Icons]
Name: "{group}\Kimi Encrypt And Decrypt Tool"; Filename: "{app}\jre\bin\javaw.exe"; Parameters: "-jar \"{app}\KimiEncryptAndDecryptTool.jar\""; IconFilename: "{app}\nlu.ico"

[Run]
Filename: "{app}\jre\bin\javaw.exe"; Parameters: "-jar \"{app}\KimiEncryptAndDecryptTool.jar\""; Description: "Launch Kimi Tool"; Flags: nowait postinstall skipifsilent
```

### Cách biên dịch
1. Mở Inno Setup Compiler.
2. Chọn `File > Open` và mở file `setup.iss`.
3. Nhấn `Compile` để tạo file `.exe` cài đặt.

Sau khi cài đặt, ứng dụng có thể chạy được ngay cả khi máy người dùng không có sẵn Java.


---

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
