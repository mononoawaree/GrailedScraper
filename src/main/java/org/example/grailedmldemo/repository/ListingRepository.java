package org.example.grailedmldemo.repository;
import org.example.grailedmldemo.model.Listing;
import javax.sql.DataSource;

public class ListingRepository {
    private final DataSource dataSource;

    public ListingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Listing listing) {

    }
    public boolean existsBySourceId(String id) {
        return false;
    }
}
