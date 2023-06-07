package com.wetterwidget.backend.facade;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wetterwidget.backend.entities.*;
import com.wetterwidget.backend.facade.dto.*;

@RestController
@RequestMapping("/wetterdata")
@CrossOrigin
public class WetterDataController {
	
    @GetMapping("/preview")
    public String preview() {
      return String.format("Hello Test");
    }
    
    @GetMapping("/city")
    public String city(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
}