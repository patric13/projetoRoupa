package dominio;

public enum TipoVaga {
	ESTAGIO("Estágio"), TRAINEE("Trainee"), JOVEM_APRENDIZ("Jovem Aprendiz");
	
	private String nome;
	
	private TipoVaga(String nome) {
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome;
	}
	
	
	public String getNome(){
		return nome;
		
	}
}
