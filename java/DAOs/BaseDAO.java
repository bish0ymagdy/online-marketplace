package DAOs;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {
	
//    the annotation only works when it inside the servlet because of the Tomcat
//    @Resource(name = "jdbc/connection")  
    private DataSource dataSource;
	
	public BaseDAO() {
        try {
            // 1. Initialize the naming context to talk to Tomcat's environment
            InitialContext ctx = new InitialContext();
            
            // 2. Look up the resource environment. "java:comp/env/" is a required prefix in Tomcat.
            // Replace "jdbc/YourResourceName" with the exact "name" attribute from your context.xml!
            this.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/connection");
            
        } catch (NamingException e) {
            System.err.println("JNDI Lookup failed! Check your context.xml resource name.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("DataSource is still null. Tomcat could not find the JNDI resource.");
        }
        // 3. Return a pre-warmed connection instantly from the Tomcat pool
        return this.dataSource.getConnection();
    }
}
