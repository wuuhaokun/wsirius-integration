package com.wsirius.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * View
 *
 * @author bojiangzhou 2018/09/22
 */
@Controller
public class ViewController {

    @RequestMapping("/{view}")
    public String renderView(@PathVariable String view) {
        return view;
    }

}
