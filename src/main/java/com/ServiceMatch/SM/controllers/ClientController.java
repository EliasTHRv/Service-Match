package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

//    @GetMapping
//    public List<Cliente> getAllClientes() {
//        return clienteRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
//        Optional<Cliente> cliente = clienteRepository.findById(id);
//        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public Cliente createCliente(@RequestBody Cliente cliente) {
//        return clienteRepository.save(cliente);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
//        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
//        if (optionalCliente.isPresent()) {
//            Cliente updatedCliente = optionalCliente.get();
//            updatedCliente.setNombre(clienteDetails.getNombre());
//            // Actualizar otros atributos según sea necesario
//
//            return ResponseEntity.ok(clienteRepository.save(updatedCliente));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
//        clienteRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

    @PostMapping("/save")
    public String save(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam String password2, Model model) {
        try {
            clientService.registrar(name, email, password, password2);
            return "redirect:/user/list";
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registerOLD.html";
        }
    }
}

