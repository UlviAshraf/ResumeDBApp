/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.main;

import com.company.dao.inter.UserDaoInter;

/**
 *
 * @author UlviAshraf
 */
public class Main {

    public static void main(String[] args) throws Exception {
      UserDaoInter dao=  Context.instanceUserDao();
        System.out.println(dao.getAllUser());
    }
}
