package com.archivito.Documento.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Documento {
	



	private Set<Archivo> archivos;
	private LocalDateTime fecha;
	public Set<Archivo> getArchivos() {
		return archivos;
	}
	public void setArchivos(Set<Archivo> archivos) {
		this.archivos = archivos;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}









}
