package com.coppel.polizasfaltantes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coppel.polizasfaltantes.models.EliminacionPolizaRequest;
import com.coppel.polizasfaltantes.models.Pagination;
import com.coppel.polizasfaltantes.models.Poliza;
import com.coppel.polizasfaltantes.models.PolizaRequest;
import com.coppel.polizasfaltantes.services.PolizasService;
import com.coppel.polizasfaltantes.services.UserDetailsImpl;

@RestController
@RequestMapping("/polizas")
public class PolizasController {

    @Autowired
    private PolizasService polizasService;

    @GetMapping("")
    public Pagination<Poliza> index(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        return this.polizasService.getAll(page, limit);
    }

    @GetMapping("/{idPoliza}")
    public Poliza show(
        @PathVariable int idPoliza
    ) {
        return this.polizasService.findById(idPoliza);
    }

    @PostMapping("")
    public Poliza store(
        @RequestBody PolizaRequest polizaRequest,
        Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        polizaRequest.setIdUsuario(userDetails.getId());

        return this.polizasService.store(polizaRequest);
    }

    @PutMapping("/{idPoliza}")
    public Poliza update(
        @PathVariable int idPoliza,
        @RequestBody PolizaRequest polizaRequest,
        Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        polizaRequest.setIdUsuario(userDetails.getId());

        return this.polizasService.update(idPoliza, polizaRequest);
    }

    @PutMapping("/{idPoliza}/eliminar")
    public Poliza delete(
        @PathVariable int idPoliza,
        @RequestBody EliminacionPolizaRequest motivoEliminacion
    ) {
        return this.polizasService.delete(idPoliza, motivoEliminacion.getMotivoEliminacion());
    }

}
