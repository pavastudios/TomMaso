package utility;

import com.pavastudios.TomMaso.storage.GlobalConnection;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class TestDBConnection {
    private static IDatabaseTester t;

    private static Connection connection;

    protected static Connection getConnection() {
        return connection;
    }

    @BeforeAll
    static void setUpAll() throws Exception {
        t = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:tommaso;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init.sql'");
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(TestDBConnection.class.getClassLoader()
                        .getResourceAsStream("db/dbData.xml"));
        t.setSetUpOperation(DatabaseOperation.REFRESH);
        t.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        t.setDataSet(initialState);

        connection = t.getConnection().getConnection();
        GlobalConnection.init(connection);
    }

    @BeforeEach
    public void setUp() throws Exception {
        t.onSetup();
    }

    @AfterEach
    public void tearDown() throws Exception {
        t.onTearDown();
    }

}
