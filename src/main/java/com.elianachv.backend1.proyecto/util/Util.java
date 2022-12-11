package com.elianachv.backend1.proyecto.util;

import com.elianachv.backend1.proyecto.dto.OdontologoDto;
import com.elianachv.backend1.proyecto.dto.PacienteDto;
import com.elianachv.backend1.proyecto.dto.TurnoDto;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.entity.Turno;
import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String generarMensaje(Operaciones o, Usuarios u, boolean exitoso) {
        if (exitoso) {
            return u.getRol() + " " + o.getVerboConjugado() + " con éxito";
        } else {
            return "Error al " + o.getVerboInfinitivo() + u.getRol();
        }

    }

    public static String generarMensaje(Operaciones o, Usuarios u) {
        return generarMensaje(o, u, true);
    }

    public static List<OdontologoDto> convertirListaOdontologoADto(List<Odontologo> odontologoList) {

        List<OdontologoDto> odontologoDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        odontologoList.forEach(odontologo -> odontologoDtos.add(objectMapper.convertValue(odontologo, OdontologoDto.class)));

        return odontologoDtos;
    }

    public static List<PacienteDto> convertirListaPacienteADto(List<Paciente> pacienteList) {

        List<PacienteDto> pacienteDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        pacienteList.forEach(paciente -> {
            PacienteDto pacienteDto = objectMapper.convertValue(paciente, PacienteDto.class);
            pacienteDto.setDomicilioCompleto(paciente.getDomicilio().getDomicilioCompleto());
            pacienteDtos.add(pacienteDto);
        });

        return pacienteDtos;
    }

    public static List<TurnoDto> convertirListaTurnoADto(List<Turno> turnoList) {
        List<TurnoDto> turnoDtos = new ArrayList<>();
        ObjectMapper objectMapper = getMapper();
        turnoList.forEach(turno -> {
            TurnoDto turnoDto = objectMapper.convertValue(turno, TurnoDto.class);
            turnoDto.setDniPaciente(turno.getPaciente().getDni());
            turnoDto.setMatriculaOdontologo(turno.getOdontologo().getMatricula());
            turnoDtos.add(turnoDto);
        });
        return turnoDtos;
    }

    public static ObjectMapper getMapper(){
        return JsonMapper.builder()
                .build().findAndRegisterModules()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}