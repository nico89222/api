package com.redsocial.comvol.services;

import com.redsocial.comvol.converter.PersonaToDetallePersonaDtoConverter;
import com.redsocial.comvol.dto.persona.*;
import com.redsocial.comvol.exception.BadRequestException;
import com.redsocial.comvol.model.*;
import com.redsocial.comvol.repository.CategoriaRepository;
import com.redsocial.comvol.repository.PaisRepository;
import com.redsocial.comvol.repository.PersonaRepository;
import com.redsocial.comvol.repository.RolRepository;
import com.redsocial.comvol.services.encrypt.EncryptServiceImpl;
import com.redsocial.comvol.utils.Mensajes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaToDetallePersonaDtoConverter personaToDetallePersonaDtoConverter;
    private final EncryptServiceImpl encryptServiceImpl;
    private final PaisRepository paisRepository;
    private final RolRepository rolRepository;

    private final CategoriaRepository categoriaRepository;

    @Autowired
    private JavaMailSender mailSender;


    public PersonaResponseDto crearPersona(@Valid CrearPersonaDto crearPersonaDto ) {

        Optional<Persona> emailRegistrado = personaRepository.findByEmail(crearPersonaDto.getEmail());

        if (emailRegistrado.isPresent())
            throw new BadRequestException(Mensajes.PERSONA_ENCONTRADA);

        if (validarContrasena(crearPersonaDto.getContrasena(), crearPersonaDto.getRecontrasena())) {
            throw new BadRequestException(Mensajes.ERROR_CONTRASENAS);
        }

        return personaResponse(guardarPersona(mapearPersona(crearPersonaDto)));

    }

    private boolean validarContrasena(String contrasena, String reContrasena) {
        return !contrasena.equals(reContrasena);

    }

    private Persona mapearPersona(CrearPersonaDto crearPersonaDto) {

        Pais pais = paisRepository.findById(crearPersonaDto.getPais()).orElseThrow(() -> new BadRequestException(Mensajes.PAIS_NO_ENCONTRADA));
        List<Rol> listaRoles = rolRepository.findAllById(crearPersonaDto.getPersonaRoles());
        Categoria categoria = categoriaRepository.findById(crearPersonaDto.getCategoria()).orElseThrow(()-> new BadRequestException(Mensajes.CATEGORIA_NO_ENCONTRADO));


        return Persona.builder()
                .nombre(crearPersonaDto.getNombre())
                .apellido(crearPersonaDto.getApellido())
                .email(crearPersonaDto.getEmail())
                .acercaDe(crearPersonaDto.getAcercaDe())
                .contrasena(encryptServiceImpl.encryptPassword(crearPersonaDto.getContrasena()))
                .tipoUsuario(1)
                .fechaAltaPersona(LocalDate.now())
                .suscripcion(false)
                .pais(pais)
                .provincia(crearPersonaDto.getProvincia())
                .localidad(crearPersonaDto.getLocalidad())
                .perfilExterno(crearPersonaDto.getPerfilExterno())
                .listaRoles(listaRoles)
                .categoria(categoria)
                .numeroCelular(crearPersonaDto.getNumeroCelular())
                .esEmpresa(crearPersonaDto.getEsEmpresa())
                .build();

    }

    private PersonaResponseDto personaResponse(Persona persona) {

        return PersonaResponseDto.builder()
                .id(persona.getIdPersona())
                .build();
    }

    private Persona guardarPersona(Persona crearPersona) {
        return personaRepository.save(crearPersona);

    }


    public DetallePersonaDto detallePersona(Long idPersona) {

        return personaToDetallePersonaDtoConverter.convert(buscarPersona(idPersona));


    }


    public Persona buscarPersona(Long idPersona) {

        Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_NO_ENCONTRADA));

        if (tieneFechaBaja(persona.getFechaBajaPersona()))
            throw new BadRequestException(Mensajes.TIENE_BAJA);

        return persona;
    }


    public DetallePersonaDto editarPersona(Long idPersona, EditarPersonaDto editarPersonaDto) {

        Persona persona = buscarPersona(idPersona);

        return personaToDetallePersonaDtoConverter.convert(guardarPersona(mapearEditarPersona(persona, editarPersonaDto)));


    }

    private Persona mapearEditarPersona(Persona persona, EditarPersonaDto editarPersonaDto) {

        List<Rol> listaRoles = rolRepository.findAllById(editarPersonaDto.getPersonaRoles());

        Categoria categoria = categoriaRepository.findById(editarPersonaDto.getIdCategoria()).orElseThrow(()-> new BadRequestException(Mensajes.CATEGORIA_NO_ENCONTRADO));

        Pais pais = paisRepository.findById(editarPersonaDto.getIdPais()).orElseThrow(()-> new BadRequestException(Mensajes.PAIS_NO_ENCONTRADA));

        persona.setNombre(editarPersonaDto.getNombre());
        persona.setApellido(editarPersonaDto.getApellido());
        persona.setAcercaDe(editarPersonaDto.getAcercaDe());
        persona.setProvincia(editarPersonaDto.getProvincia());
        persona.setLocalidad(editarPersonaDto.getLocalidad());
        persona.setPerfilExterno(editarPersonaDto.getPerfilExterno());
        persona.setListaRoles(listaRoles);
        persona.setNumeroCelular(editarPersonaDto.getNumeroCelular());
        persona.setCategoria(categoria);
        persona.setEsEmpresa(editarPersonaDto.getEsEmpresa());
        persona.setPais(pais);

        return persona;
    }

    public PersonaResponseDto bajaPersona(Long idPersona) {

        Persona persona = buscarPersona(idPersona);

        darDeBajaPersona(persona);

        return personaResponse(persona);

    }


    private boolean tieneFechaBaja(LocalDate fechaBaja) {
        return fechaBaja != null;
    }


    private void darDeBajaPersona(Persona persona) {
        persona.setFechaBajaPersona(LocalDate.now());
        guardarPersona(persona);
    }


    public PersonaResponseDto altaSuscripcionPersona(Long idPersona) {

        Persona persona = buscarPersona(idPersona);

        if (persona.isSuscripcion()) {
            throw new BadRequestException(Mensajes.TIENE_SUSCRIPCION);
        }

        persona.setSuscripcion(true);

        guardarPersona(persona);

        return personaResponse(persona);

    }

    public PersonaResponseDto bajaSuscripcion(Long idPersona) {

        Persona persona = buscarPersona(idPersona);

        if (!persona.isSuscripcion()) {
            throw new BadRequestException(Mensajes.NO_TIENE_SUSCRIPCION);
        }

        persona.setSuscripcion(false);

        guardarPersona(persona);

        return personaResponse(persona);

    }


    public Page<DetalleBusquedaPersonaDto> listaPersona(List<Long> listaRolId, List<Long> listaPaisId, Integer pagina, Integer cantidad) {

        listaRolId = eliminarNulosLista(listaRolId);

        listaPaisId = eliminarNulosLista(listaPaisId);

        validarCamposFiltro(listaRolId, listaPaisId);

        String esEmpresa = "NO";

        Page<Persona> resultado = personaRepository.filtroPersona(listaRolId, listaPaisId, PageRequest.of(pagina, cantidad));

        return resultado.map(this::mapearResultadoFiltro);

    }


    private DetalleBusquedaPersonaDto mapearResultadoFiltro(Persona persona) {

        String suscripcion = persona.isSuscripcion()?"Si":"No";

        return DetalleBusquedaPersonaDto.builder()
                .idPersona(persona.getIdPersona())
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .email(persona.getEmail())
                .provincia(persona.getProvincia())
                .localidad(persona.getLocalidad())
                .perfilExterno(persona.getPerfilExterno())
                .pais(persona.getPais().getDescripcionPais())
                .rol(persona.getListaRoles().stream().map(Rol::getDescripcionRol).collect(Collectors.toList()))
                .suscripcion(suscripcion)
                .numeroCelular(persona.getNumeroCelular())
                .acercaDe(persona.getAcercaDe())
                .build();

    }


    private List<Long> eliminarNulosLista(List<Long> listaRolPaisId) {

        if (listaRolPaisId.contains(null))
            return listaRolPaisId.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return listaRolPaisId;
    }

    private void validarCamposFiltro(List<Long> listaRolId, List<Long> listaPaisId) {

        List<Long> listaRolesExistente = rolRepository.findAll().stream().map(Rol::getIdRol).collect(Collectors.toList());

        List<Long> listaPaisExistente = paisRepository.findAll().stream().map(Pais::getIdPais).collect(Collectors.toList());

        validarExisteCampos(listaRolId, listaPaisId, listaRolesExistente, listaPaisExistente);

        completaListasVaciasPorDefecto(listaRolId, listaPaisId, listaRolesExistente, listaPaisExistente);
    }

    private void validarExisteCampos(List<Long> listaRolId, List<Long> listaPaisId, List<Long> listaRolesExistente, List<Long> listaPaisExistente) {

        List<Long> listaRolesNoEncontrados = new ArrayList<>();
        List<Long> listaPaisNoEncontrados = new ArrayList<>();

        if (!listaRolId.isEmpty())
            listaRolesNoEncontrados = listarDatosNoEncontrados(listaRolId, listaRolesExistente);

        if (!listaPaisId.isEmpty())
            listaPaisNoEncontrados = listarDatosNoEncontrados(listaPaisId, listaPaisExistente);

        camposNoEncontrados(listaRolesNoEncontrados, listaPaisNoEncontrados);
    }

    private List<Long> listarDatosNoEncontrados(List<Long> listaCamposId, List<Long> listaDatosExistente) {
        return listaCamposId.stream().filter(campo -> listaDatosExistente.stream().noneMatch(campo::equals)).collect(Collectors.toList());
    }

    private void camposNoEncontrados(List<Long> listaRolesNoEncontrados, List<Long> listaPaisNoEncontrados) {

        if (!listaRolesNoEncontrados.isEmpty())
            throw new BadRequestException(Mensajes.ROL_NO_ENCONTRADO + listaRolesNoEncontrados);

        if (!listaPaisNoEncontrados.isEmpty())
            throw new BadRequestException(Mensajes.PAIS_NO_ENCONTRADA + listaPaisNoEncontrados);

    }

    private void completaListasVaciasPorDefecto(List<Long> listaRolId, List<Long> listaPaisId, List<Long> listaRolesExistente, List<Long> listaPaisExistente) {

        if (listaRolId.isEmpty()) {
            listaRolId.addAll(listaRolesExistente);
        }

        if (listaPaisId.isEmpty()) {
            listaPaisId.addAll(listaPaisExistente);
        }
    }


    public PersonaResponseDto modificarContrasena(Long idPersona, CambioContrasenaDto cambioContrasenaDto) {

        Persona persona = buscarPersona(idPersona);

        if (encryptServiceImpl.verifyPassword(cambioContrasenaDto.getActualContrasena(),persona.getContrasena())){

        }else{
            throw new BadRequestException(Mensajes.ERROR_INICIO_SESION);
        }

        if (validarContrasena(cambioContrasenaDto.getNuevaContrasena(), cambioContrasenaDto.getNuevaReContrasena()))
            throw new BadRequestException(Mensajes.ERROR_CONTRASENAS);

        persona.setContrasena(encryptServiceImpl.encryptPassword(cambioContrasenaDto.getNuevaContrasena()));

        guardarPersona(persona);

        return personaResponse(persona);
    }

    public PersonaResponseDto activarUsuario(Long idPersona) {

        Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_NO_ENCONTRADA));

        if (tieneFechaBaja(persona.getFechaBajaPersona())) {
            persona.setFechaBajaPersona(null);
            guardarPersona(persona);
            return personaResponse(persona);
        }
        throw new BadRequestException(Mensajes.NO_TIENE_BAJA);

    }


    public PersonaResponseSesionDto chequeaContrasena(InicioSesionDto inicioSesionDto) {

        Persona persona = personaRepository.findByEmail(inicioSesionDto.getEmail()).orElseThrow(() -> new BadRequestException(Mensajes.ERROR_INICIO_SESION));

        if(persona.getFechaBajaPersona() != null){
            throw new BadRequestException(Mensajes.ERROR_INICIO_SESION);
        }

        if (encryptServiceImpl.verifyPassword(inicioSesionDto.getContrasena(),persona.getContrasena())){
            return personaResponseSesion(persona);
        }else{
            throw new BadRequestException(Mensajes.ERROR_INICIO_SESION);
        }

    }

    private PersonaResponseSesionDto personaResponseSesion(Persona persona) {

        return PersonaResponseSesionDto.builder()
                .id(persona.getIdPersona())
                .tipoUsuario(persona.getTipoUsuario())
                .esEmpresa(persona.getEsEmpresa())
                .build();
    }

    public void compartirProyecto(Long idPersona, String urlProyecto, Long idReferente) throws MessagingException {

        Persona postulante = buscarPersona(idPersona);
        Persona referente = buscarPersona(idReferente);

        enviarCorreo(postulante.getEmail(),"Una empresa esta interesada en vos ;)","Si estas interesado en su propuesta por favor postulate",urlProyecto);
    }

    public void enviarCorreo(String email,String titulo,String texto,String enlace) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(email));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Prueba");

        String emoji = "";

        String htmlContent = "<h3>"+titulo+" "+"<i class=\"fa-solid fa-face-smile\"></i>"+"</h3>" +
                "<p>"+texto+"</p>"+
                "<button type=\"button\""+" class=\"btn btn-primary\">"+
                "<a href=\'"+enlace+"\'+"+">"+"Ver Proyecto"+"</a>"+
        "</button>";
        message.setContent(htmlContent, "text/html; charset=utf-8");


        mailSender.send(message);
    }
}
