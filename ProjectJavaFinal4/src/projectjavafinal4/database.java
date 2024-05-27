/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjavafinal4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author LENOVO
 */
public class database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemamanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn = null;

    public database() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Kết nối đến cơ sở dữ liệu thành công.");
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Phương thức để lưu hóa đơn
    public void saveBill(String movieName, String genre, String screeningDate, double totalPrice) {
        String sql = "INSERT INTO bills (`Ten phim`, `The loai`, `Ngay chieu`, `Tong tien`) VALUES (?, ?, ?, ?)";

        try {
            // Sử dụng kết nối đã được thiết lập
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieName);
            pstmt.setString(2, genre);
            pstmt.setString(3, screeningDate);
            pstmt.setDouble(4, totalPrice);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Hóa đơn đã được lưu vào cơ sở dữ liệu.");
            } else {
                System.out.println("Không có hàng nào được thêm vào cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu hóa đơn vào cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console để debug
        }
    }

    // Đảm bảo đóng kết nối sau khi sử dụng xong
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Đã đóng kết nối đến cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Thêm phương thức finalize để đảm bảo kết nối được đóng khi đối tượng bị thu gom rác
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
