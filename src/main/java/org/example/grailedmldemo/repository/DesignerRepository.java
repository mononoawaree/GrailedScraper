package org.example.grailedmldemo.repository;

import org.example.grailedmldemo.model.GrailedDesigner;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesignerRepository {

    private final DataSource dataSource;

    public DesignerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(GrailedDesigner designer) {
        String sql = "INSERT INTO designers (id, name, count) VALUES (?, ?, ?) ON CONFLICT (id) DO NOTHING";
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, designer.getId());
            pstmt.setString(2, designer.getName());
            pstmt.setInt(3, designer.getCount());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllDesignerNames() throws SQLException {
        String sql = "SELECT name FROM designers";
        List<String> names = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        }
        return names;
    }
}
