package model;

public enum Nivel {
	FUNDAMENTAL("Ensino Fundamental"),MEDIO("Ensino M�dio"),TECNICO("Ensino T�cnico"),SUPERIOR("Ensino Superior");

	private String nome;
	
	private Nivel(String nome){
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome;
	}
	
	
}
