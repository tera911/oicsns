/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author morimoto
 */
public class Tools {
    public static String convertData(Date date){
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        return smdf.format(date);
    } 
}
