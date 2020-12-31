/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.main;

import com.company.dao.inter.UserDaoInter;
import com.company.entity.Country;
import com.company.entity.User;

import java.util.List;

/**
 * @author UlviAshraf
 */
public class Main {

    public static void main(String[] args) throws Exception {
        UserDaoInter dao = Context.instanceUserDao();
        List<User> list = dao.getAllUser("", "", null);
        for(User u:list){
            System.out.println(u.getAddress());
        }
    }
}
