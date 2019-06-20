package com.pdclientmanager.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDao {

    @Autowired
    private Environment environment;
    
    public void resetData() {
        try {
            executeSqlScript("/resetDataScript.sql");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void executeSqlScript(String resourceFileName) throws IOException, SQLException {
        InputStream input = null;
        BufferedReader reader = null;
        Connection conn = null;
        Statement statement = null;
        
        try {
            Class.forName(environment.getRequiredProperty("jdbc.driverClassName"));
            conn = DriverManager.getConnection(environment.getRequiredProperty("jdbc.url"),
                    environment.getRequiredProperty("jdbc.username"),
                    environment.getRequiredProperty("jdbc.password"));
            statement = conn.createStatement();
            input = getClass().getResourceAsStream(resourceFileName);
            reader = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
                statement.execute(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                reader.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
    }
    
}