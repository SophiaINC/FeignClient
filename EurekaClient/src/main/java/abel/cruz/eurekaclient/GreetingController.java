/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abel.cruz.eurekaclient;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author acruzb
 */
public interface GreetingController {
    @RequestMapping("/greeting")
    String greeting();
}
