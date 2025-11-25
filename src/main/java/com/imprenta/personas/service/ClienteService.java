package com.imprenta.personas.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.imprenta.personas.model.Cliente;
import com.imprenta.personas.repository.ClienteRepository;
import com.imprenta.personas.exception.*;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID" + id + " no encontrado"));
    }

    public Cliente save(Cliente cliente) {
        if (cliente.getRfc() != null && !cliente.getRfc().isBlank()) {

            if (clienteRepository.existsByRfc(cliente.getRfc())) {
                throw new RuntimeException("El RFC ya está registrado");
            }

            if (clienteRepository.existsByEmail(cliente.getEmail())) {
                throw new RuntimeException("El email ya está registrado");
            }
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(Integer id, Cliente datosActualizados) {

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        if (datosActualizados.getEmail() != null && !datosActualizados.getEmail().isBlank()) {
            if (!clienteExistente.getEmail().equals(datosActualizados.getEmail()) &&
                    clienteRepository.existsByEmail(datosActualizados.getEmail())) {
                throw new BadRequestException("El email ya está en uso por otro cliente");
            }
            clienteExistente.setEmail(datosActualizados.getEmail());
        }

        if (datosActualizados.getRfc() != null && !datosActualizados.getRfc().isBlank()) {
            if (clienteExistente.getRfc() == null ||
                    !clienteExistente.getRfc().equals(datosActualizados.getRfc())) {

                if (clienteRepository.existsByRfc(datosActualizados.getRfc())) {
                    throw new BadRequestException("El RFC ya está registrado por otro cliente");
                }
            }
            clienteExistente.setRfc(datosActualizados.getRfc());
        }

        clienteExistente.setNombre(datosActualizados.getNombre());
        clienteExistente.setTelefono(datosActualizados.getTelefono());
        clienteExistente.setRazonSocial(datosActualizados.getRazonSocial());
        clienteExistente.setDireccionFiscal(datosActualizados.getDireccionFiscal());
        clienteExistente.setIdUsoCfdi(datosActualizados.getIdUsoCfdi());
        clienteExistente.setIdRegimenFiscal(datosActualizados.getIdRegimenFiscal());

        return clienteRepository.save(clienteExistente);
    }

    public void delete(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, usuario con ID " + id + " no existe");
        }
        clienteRepository.deleteById(id);
    }

    public List<Cliente> buscarClientesPorTermino(String terminoBusqueda) {
        return clienteRepository.findByNombreContainingIgnoreCaseOrRazonSocialContainingIgnoreCase(terminoBusqueda,
                terminoBusqueda);
    }

}
