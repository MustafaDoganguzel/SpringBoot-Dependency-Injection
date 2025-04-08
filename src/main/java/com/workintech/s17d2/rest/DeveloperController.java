package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {


    public Map<Integer, Developer> developers ;
    public Taxable taxable;
    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable = taxable;
    }

    @PostConstruct
    public void init(){
     this.developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getDeveloper(){
        return developers.values().stream().toList();


    }

    @GetMapping("/{id}")
    public Developer getDeveloperId(@PathVariable int id){
        return developers.get(id);
    }

    @PostMapping
    public void setDevelopers(int id, String name, double salary, Experience experience){

        if(experience == Experience.JUNIOR){
            salary = salary - taxable.getSimpleTaxRate();
            developers.put(id, new JuniorDeveloper(id, name , salary));
        }
        if (experience == Experience.MID){
            salary= salary - taxable.getMiddleTaxRate();
            developers.put(id, new MidDeveloper(id, name , salary));
        }
        if (experience == Experience.SENIOR){
            salary= salary - taxable.getUpperTaxRate();
            developers.put(id, new SeniorDeveloper(id, name , salary));
        }
    }
    @PutMapping("/{id}")
    public void updateDevelopers(@PathVariable int id, @RequestBody Developer developer){
      developers.put(id, developer);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        developers.remove(id);
    }

}
