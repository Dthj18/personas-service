package com.imprenta.personas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 10)
    private String telefono;

    @Column(length = 13, unique = true)
    private String rfc;

    @Column(name = "razon_social", length = 255)
    private String razonSocial;

    @Column(name = "direccion_fiscal", columnDefinition = "TEXT")
    private String direccionFiscal;

    @Column(name = "id_uso_cfdi", length = 10)
    private Integer idUsoCfdi;

    @Column(name = "id_regimen_fiscal")
    private Integer idRegimenFiscal;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public Integer getIdUsoCfdi() {
        return idUsoCfdi;
    }

    public void setIdUsoCfdi(Integer idUsoCfdi) {
        this.idUsoCfdi = idUsoCfdi;
    }

    public Integer getIdRegimenFiscal() {
        return idRegimenFiscal;
    }

    public void setIdRegimenFiscal(Integer idRegimenFiscal) {
        this.idRegimenFiscal = idRegimenFiscal;
    }

}
