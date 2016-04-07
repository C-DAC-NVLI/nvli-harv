/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vootla
 */
@Controller
public class harvesterController {
    
    
  
    
    @RequestMapping("/")
    public String exampleMethod()
    {
        return "example";
    }
    
}
