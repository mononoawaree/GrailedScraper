package org.example.grailedmldemo.repository;

import org.example.grailedmldemo.model.Designer;

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

    public void saveGrailedDesigners(Designer designer) {
        String designerSql = """
        INSERT INTO designers (canonical_name, canonical_slug)
        VALUES (?, ?)
        ON CONFLICT (canonical_name) DO UPDATE
            SET canonical_name = EXCLUDED.canonical_name
        RETURNING id
        """;

        String aliasSql = """
        INSERT INTO designer_aliases (designer_id, source, platform_id, platform_name, platform_slug, listing_count, listing_count_updated_at)
        VALUES (?, ?, ?, ?, ?, ?, NOW())
        ON CONFLICT (source, platform_id) DO UPDATE
        SET listing_count = EXCLUDED.listing_count,
        listing_count_updated_at = NOW()
        """;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long canonicalDesignerId;

                // Step 1: insert canonical designer (or get existing one's id)
                try (PreparedStatement pstmt = connection.prepareStatement(designerSql)) {
                    pstmt.setString(1, designer.getName());
                    pstmt.setString(2, designer.getSlug());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            canonicalDesignerId = rs.getLong("id");
                        } else {
                            throw new SQLException("Failed to upsert designer: " + designer.getName());
                        }
                    }
                }

                // Step 2: insert alias pointing at canonical designer
                try (PreparedStatement pstmt = connection.prepareStatement(aliasSql)) {
                    pstmt.setLong(1, canonicalDesignerId);              // designer_id (FK)
                    pstmt.setString(2, "grailed");                       // source
                    pstmt.setString(3, String.valueOf(designer.getId())); // platform_id (Grailed's id)
                    pstmt.setString(4, designer.getName());              // platform_name
                    pstmt.setString(5, designer.getSlug());              // platform_slug
                    pstmt.setInt(6, Integer.parseInt(designer.getCount()));
                    pstmt.executeUpdate();
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("DESIGNER SAVE FAILED for: " + designer.getName());
                System.err.println("SQL State: " + e.getSQLState());
                System.err.println("Error Code: " + e.getErrorCode());
                System.err.println("Message: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save Grailed designer: " + designer.getName(), e);
        }
    }

    public List<String> getAllDesignerNames() throws SQLException {
        String sql = "SELECT canonical_name FROM designers";
        List<String> names = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("canonical_name"));
            }
        }
        return names;
    }
}
