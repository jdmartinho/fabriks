package com.goblinfire.controller;

import com.goblinfire.model.Fabric;
import com.goblinfire.repository.FabricRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class FabricController {
    @Autowired
    private FabricRepository _fabricRepository;

    @RequestMapping(value = "fabrics", method = RequestMethod.GET)
    public List<Fabric> list() {
        return _fabricRepository.findAll();
    }

    @RequestMapping(value = "fabrics", method = RequestMethod.POST)
    public Fabric create(@RequestBody Fabric fabric) {
        return _fabricRepository.saveAndFlush(fabric);
    }

    @RequestMapping(value = "fabrics/{id}", method = RequestMethod.GET)
    public Fabric get(@PathVariable Long id) {
        return _fabricRepository.getOne(id);
    }

    @RequestMapping(value = "fabrics/{id}", method = RequestMethod.PUT)
    public Fabric update(@PathVariable Long id, @RequestBody Fabric fabric) {
        Fabric existingFabric = _fabricRepository.getOne(id);
        BeanUtils.copyProperties(fabric, existingFabric);
        return _fabricRepository.saveAndFlush(existingFabric);
    }

    @RequestMapping(value = "fabrics/{id}", method = RequestMethod.DELETE)
    public Fabric delete(@PathVariable Long id) {
        try{
            Fabric existingFabric = _fabricRepository.getOne(id);
            _fabricRepository.delete(existingFabric);
            return existingFabric;
        } catch (Exception ex){
            System.out.println("WWWWWWWWWWWWWWWWWWEEEEEEEEEEEEEEEEEEEEE CAAAAAAAAAAAAARUGSFSDDGHKYKH");
            return null;
        }
    }
}

