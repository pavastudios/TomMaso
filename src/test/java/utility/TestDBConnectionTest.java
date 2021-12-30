package utility;

import com.pavastudios.TomMaso.storage.Queries;
import com.pavastudios.TomMaso.storage.model.Utente;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TestDBConnectionTest extends TestDBConnection{
    @Test
    public void provaTest() throws SQLException {
        ResultSet rs=getConnection().prepareStatement("SELECT * FROM utente").executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(5));
        }
        //assertTrue(1)
    }
}
