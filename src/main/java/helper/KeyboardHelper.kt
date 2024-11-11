///**
// * Author: Nguyen Dinh Lam
// * Email: kiminonawa1305@gmail.com
// * Phone number: +84 855354919
// * Create at: 11:06 AM - 15/10/2024
// *  User: lam-nguyen
// **/
//
//package main.java.helper
//
//import java.io.BufferedReader
//import java.io.PrintWriter
//
//class KeyboardHelper {
//    companion object {
//        @JvmStatic
//        fun inputInt(from: Int = 0, to: Int = 0, reader: BufferedReader, writer: PrintWriter): Int {
//            while (true) {
//                var value = reader.readLine();
//                try {
//                    var value = value.toInt()
//                    if (from == to || (value >= from && value <= to)) return value;
//                } catch (_: NumberFormatException) {
//                    writer.append("Loi gia tri nhap vao\nVui long nhap lai gia tri moi")
//                    writer.flush()
//                }
//            }
//        }
//    }
//}