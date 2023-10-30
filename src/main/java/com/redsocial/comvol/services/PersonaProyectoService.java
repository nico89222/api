package com.redsocial.comvol.services;

import com.redsocial.comvol.dto.personaProyecto.*;
import com.redsocial.comvol.exception.BadRequestException;
import com.redsocial.comvol.model.*;
import com.redsocial.comvol.repository.*;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class PersonaProyectoService {

    private final PostulacionPersonaRepository postulacionPersonaRepository;
    private final PersonaService personaService;
    private final ProyectoService proyectoService;
    private final RolRepository rolRepository;
    private final EstadoPersonaRepository estadoPersonaRepository;
    private final PersonaProyectoRepository personaProyectoRepository;
    private final PersonaRepository personaRepository;
    private final EstadoRepository estadoRepository;


    @Autowired
    private JavaMailSender mailSender;

    public PostulacionResponseDto crearPostulacion(Long idPersona, Long idProyecto, PersonaRolDto personaRolDto) throws MessagingException {

        Persona persona = personaService.buscarPersona(idPersona);
        Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);
        Rol rol = rolRepository.findById(personaRolDto.getIdRol()).orElseThrow(() -> new BadRequestException(Mensajes.ROL_NO_ENCONTRADO));
        EstadoPersona estadoPersona = estadoPersonaRepository.findById(1L).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO));

        Optional<PostulacionPersona> resultadoPersonaProyecto = postulacionPersonaRepository.findByIdProyectoAndIdPersona(proyecto, persona);

        if (resultadoPersonaProyecto.isPresent())
            throw new BadRequestException(Mensajes.YA_SE_ENCUENTRA_POSTULADO);

        enviarCorreo(persona.getEmail(), "Felicidades por tu postulacion", "Muchas gracias por postularte", "http://localhost:3000/");
        return postulacionResponse(guardarPostulacion(mapearPostulacion(persona, proyecto, rol, estadoPersona)));

    }

    private PostulacionPersona guardarPostulacion(PostulacionPersona postulacionPersona) {
        return postulacionPersonaRepository.save(postulacionPersona);
    }

    private PostulacionPersona mapearPostulacion(Persona persona, Proyecto proyecto, Rol rol, EstadoPersona estadoPersona) {

        return PostulacionPersona.builder()
                .idPersona(persona)
                .idProyecto(proyecto)
                .idRol(rol)
                .idEstadoPersona(estadoPersona)
                .build();
    }

    private PostulacionResponseDto postulacionResponse(PostulacionPersona postulacionPersona) {

        return PostulacionResponseDto.builder()
                .id(postulacionPersona.getIdPostulacionPersona())
                .build();
    }


    public Page<DetallePostulacionPersonaDto> detallePostulacion(Long idProyecto, Integer pagina, Integer cantidad) {

        Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);

        Page<PostulacionPersona> listaPostulacionPersona = postulacionPersonaRepository.findByIdProyecto(proyecto, PageRequest.of(pagina, cantidad));

        return listaPostulacionPersona.map(this::mapearPostulacionPersona);

    }

    private DetallePostulacionPersonaDto mapearPostulacionPersona(PostulacionPersona postulacionPersona) {


        return DetallePostulacionPersonaDto.builder()
                .idPostulacionPersona(postulacionPersona.getIdPostulacionPersona())
                .idPersona(postulacionPersona.getIdPersona().getIdPersona())
                .idProyecto(postulacionPersona.getIdProyecto().getIdProyecto())
                .imgProyecto(postulacionPersona.getIdProyecto().getUrlImagenProyecto())
                .tituloProyecto(postulacionPersona.getIdProyecto().getTituloProyecto())
                .nombre(postulacionPersona.getIdPersona().getNombre())
                .apellido(postulacionPersona.getIdPersona().getApellido())
                .email(postulacionPersona.getIdPersona().getEmail())
                .pais(postulacionPersona.getIdPersona().getPais().getDescripcionPais())
                .provincia(postulacionPersona.getIdPersona().getProvincia())
                .localidad(postulacionPersona.getIdPersona().getLocalidad())
                .perfilExterno(postulacionPersona.getIdPersona().getPerfilExterno())
                .idRol(postulacionPersona.getIdRol().getIdRol())
                .rolPostulado(postulacionPersona.getIdRol().getDescripcionRol())
                .estadoPostulacion(postulacionPersona.getIdEstadoPersona().getDescripcionEstadoPersona())
                .descripcionCategoria(postulacionPersona.getIdProyecto().getCategoriaProyecto().getDescripcionCategoria())
                .personaRoles(postulacionPersona.getIdPersona().getListaRoles().stream().map(Rol::getDescripcionRol).collect(Collectors.toList()))
                .build();

    }

    public PersonaProyectoResponseDto crearPersonaProyecto(CrearPersonaProyectoDto crearPersonaProyectoDto) {

        return personaProyectoResponseDto(altaPersonaProyecto(crearPersonaProyectoDto));

    }


    private PersonaProyecto altaPersonaProyecto(CrearPersonaProyectoDto crearPersonaProyectoDto) {

        EstadoPersona estadoPersona = estadoPersonaRepository.findById(crearPersonaProyectoDto.getIdEstadoPersona()).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO));
        PostulacionPersona postulacionPersona = postulacionPersonaRepository.findById(crearPersonaProyectoDto.getIdPostulacionPersona()).orElseThrow(() -> new BadRequestException(Mensajes.NO_SE_ENCUENTRA_POSTULADO));

        Persona persona = personaService.buscarPersona(crearPersonaProyectoDto.getIdPersona());
        Proyecto proyecto = proyectoService.buscarProyecto(crearPersonaProyectoDto.getIdProyecto());

        Optional<PersonaProyecto> resultadoPersonaProyecto = personaProyectoRepository.findByProyectoAndPersona(proyecto, persona);

        if (resultadoPersonaProyecto.isPresent())
            throw new BadRequestException(Mensajes.YA_SE_ENCUENTRA_POSTULADO);

        if (crearPersonaProyectoDto.getIdEstadoPersona() != 2)
            throw new BadRequestException(Mensajes.ESTADO_PERSONA_CANCELADO);

        if (postulacionPersona.getIdEstadoPersona().getIdEstadoPersona() != 1)
            throw new BadRequestException(Mensajes.ESTADO_PERSONA_CANCELADO);

        modificarEstadoYGuardar(postulacionPersona, estadoPersona);

        descontarYValidarCantidadIntegrantes(proyecto);

        proyectoService.guardarProyecto(proyecto);

        return guardarPersonaProyecto(mapearPersonaProyecto(crearPersonaProyectoDto));

    }

    private void descontarYValidarCantidadIntegrantes(Proyecto proyecto){
        if(proyecto.getLimitePersonasProyecto() > 0){
            proyecto.setLimitePersonasProyecto(proyecto.getLimitePersonasProyecto()-1);
            proyectoService.guardarProyecto(validarSiLlegoACero(proyecto));
        }

    }


    private Proyecto validarSiLlegoACero(Proyecto proyecto){
        if(proyecto.getLimitePersonasProyecto() == 0){
            proyecto.setEstadoProyecto(estadoRepository.findById(2L).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO)));
            return proyecto;
        }
        return proyecto;
    }

    private PostulacionPersona modificarEstadoYGuardar(PostulacionPersona postulacionPersona, EstadoPersona estadoPersona) {
        postulacionPersona.setIdEstadoPersona(estadoPersona);
        return guardarPostulacion(postulacionPersona);
    }

    private PersonaProyecto mapearPersonaProyecto(CrearPersonaProyectoDto crearPersonaProyectoDto) {

        Persona persona = personaService.buscarPersona(crearPersonaProyectoDto.getIdPersona());
        Proyecto proyecto = proyectoService.buscarProyecto(crearPersonaProyectoDto.getIdProyecto());
        Rol rol = rolRepository.findById(crearPersonaProyectoDto.getIdRol()).orElseThrow(() -> new BadRequestException(Mensajes.ROL_NO_ENCONTRADO));

        return PersonaProyecto.builder()
                .persona(persona)
                .proyecto(proyecto)
                .rol(rol)
                .fechaAltaPersonaAlProyecto(LocalDate.now())
                .build();

    }

    private PersonaProyecto guardarPersonaProyecto(PersonaProyecto crearPersonaProyecto) {
        return personaProyectoRepository.save(crearPersonaProyecto);

    }

    private PersonaProyectoResponseDto personaProyectoResponseDto(PersonaProyecto personaProyecto) {

        return PersonaProyectoResponseDto.builder()
                .id(personaProyecto.getIdPersonaProyecto())
                .build();
    }

    public PostulacionResponseDto cancelarPostulacion(Long idPersonaPostulacion, PersonaEstadolDto personaEstadolDto) {

        EstadoPersona estadoPersona = estadoPersonaRepository.findById(personaEstadolDto.getIdEstadoPersona()).orElseThrow(() -> new BadRequestException(Mensajes.ESTADO_NO_ENCONTRADO));
        PostulacionPersona postulacionPersona = postulacionPersonaRepository.findById(idPersonaPostulacion).orElseThrow(() -> new BadRequestException(Mensajes.NO_SE_ENCUENTRA_POSTULADO));

        if (personaEstadolDto.getIdEstadoPersona() != 3)
            throw new BadRequestException(Mensajes.ESTADO_PERSONA_CANCELADO);

        if (postulacionPersona.getIdEstadoPersona().getIdEstadoPersona() != 1)
            throw new BadRequestException(Mensajes.ESTADO_PERSONA_CANCELADO);

        return postulacionResponse(modificarEstadoYGuardar(postulacionPersona, estadoPersona));
    }


    public Page<DetalleProyectoPersonaDto> detalleProyectoPersona(Long idProyecto, Integer pagina, Integer cantidad) {

        Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);

        Page<PersonaProyecto> listaProyectoPersona = personaProyectoRepository.findByProyecto(proyecto, PageRequest.of(pagina, cantidad));

        return listaProyectoPersona.map(this::mapearProyectoPersona);


    }

    private DetalleProyectoPersonaDto mapearProyectoPersona(PersonaProyecto personaProyecto) {

        return DetalleProyectoPersonaDto.builder()
                .idPersonaProyecto(personaProyecto.getIdPersonaProyecto())
                .idProyecto(personaProyecto.getProyecto().getIdProyecto())
                .idPersona(personaProyecto.getPersona().getIdPersona())
                .nombre(personaProyecto.getPersona().getNombre())
                .apellido(personaProyecto.getPersona().getApellido())
                .email(personaProyecto.getPersona().getEmail())
                .rolPostulado(personaProyecto.getRol().getDescripcionRol())
                .fechaAltaPersonaAlProyecto(personaProyecto.getFechaAltaPersonaAlProyecto())
                .fechaBajaPersonaAlProyecto(personaProyecto.getFechaBajaPersonaAlProyecto())
                .descripcionCategoria(personaProyecto.getProyecto().getCategoriaProyecto().getDescripcionCategoria())
                .descripcionEstado(personaProyecto.getProyecto().getEstadoProyecto().getDescripcionEstado())
                .propietario(personaProyecto.getProyecto().getIdResponsable().getNombre() + " " + personaProyecto.getProyecto().getIdResponsable().getApellido())
                .numeroCelularPropietario(personaProyecto.getProyecto().getIdResponsable().getNumeroCelular())
                .tituloProyecto(personaProyecto.getProyecto().getTituloProyecto())
                .urlImagenProyecto(personaProyecto.getProyecto().getUrlImagenProyecto())
                .numeroCelular(personaProyecto.getPersona().getNumeroCelular())
                .build();

    }


    public PersonaProyectoResponseDto bajaPersonaEnProyecto(Long idPersonaProyecto) {

        PersonaProyecto personaProyecto = personaProyectoRepository.findById(idPersonaProyecto).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_PROYECTO_NO_ENCOTRADO));

        if (personaProyecto.getFechaBajaPersonaAlProyecto() != null)
            throw new BadRequestException(Mensajes.TIENE_BAJA_PROYECTO);

        personaProyecto.setFechaBajaPersonaAlProyecto(LocalDate.now());

        return personaProyectoResponseDto(guardarPersonaProyecto(personaProyecto));

    }

    public Page<DetalleProyectoPersonaDto> detalleProyectoPIntegrante(Long idPersona, Integer pagina, Integer cantidad) {

        Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_NO_ENCONTRADA));

        Page<PersonaProyecto> listaProyectoPersona = personaProyectoRepository.findByPersona(persona, PageRequest.of(pagina, cantidad));

        return listaProyectoPersona.map(this::mapearProyectoPersona);
    }

    public Page<DetallePostulacionPersonaDto> detallePostulacionPersona(Long idPersona, Integer pagina, Integer cantidad) {

        Persona persona = personaRepository.findById(idPersona).orElseThrow(() -> new BadRequestException(Mensajes.PERSONA_NO_ENCONTRADA));

        Page<PostulacionPersona> listaPostulacionPersona = postulacionPersonaRepository.findByIdPersona(persona, PageRequest.of(pagina, cantidad));


        return listaPostulacionPersona.map(this::mapearPostulacionPersona);
    }

    public void enviarCorreo(String email, String titulo, String texto, String enlace) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(email));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Prueba");


        String htmlContent =
                        "<h2>"+titulo+"</h2>" +
                        "<p>"+texto+"</p>" +
                        "<a  type=\"button\" href = \""+enlace+"\"> Mas proyectos </a >" ;

       /* String htmlContent = "<h1>"+titulo+"</h1>" +
                "<p>"+texto+"</p>"+
                "<a href='{enlace}'></a>";*/
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}

