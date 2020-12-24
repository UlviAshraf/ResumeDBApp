/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author UlviAshraf
 */
public abstract class AbstractDao {


    public static Connection connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "badman001";
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }
}
