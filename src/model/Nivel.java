package model;

public enum Nivel {
	FUNDAMENTAL("Ensino Fundamental"),MEDIO("Ensino Médio"),TECNICO("Ensino Técnico"),SUPERIOR("Ensino Superior");

	private String nome;
	
	private Nivel(String nome){
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome;
	}
	
	
}
