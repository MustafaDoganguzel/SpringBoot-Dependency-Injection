package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {


    public Map<Integer, Developer> developers ;
    private final Taxable taxable;
    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable = taxable;
    }

    @PostConstruct
    public void init(){

        this.developers = new HashMap<>();
        developers.put(1, new Developer(1, "A", 1.1 , Experience.MID));


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
    @ResponseStatus(HttpStatus.CREATED)
    public void setDevelopers(@RequestBody Developer developer){

        if(developer.getExperience() == Experience.JUNIOR){

            developers.put(developer.getId(),
                    new JuniorDeveloper(developer.getId(), developer.getName() , developer.getSalary()));
        }
        if (developer.getExperience() == Experience.MID){
            developers.put(developer.getId(),
                    new MidDeveloper(developer.getId(), developer.getName() , developer.getSalary()));
        }
        if (developer.getExperience() == Experience.SENIOR){
            developers.put(developer.getId(),
                    new SeniorDeveloper(developer.getId(), developer.getName() , developer.getSalary()));
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
