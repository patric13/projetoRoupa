package dominio;

public enum TipoArea {
	TI("Tecnologia da informação"), DIREITO("Direito"), MEDICINA("Medicina"), Engenharia("Engenharia"), Fisica(
			"Fisica"), Biotecnologia("Biotecnologia"), Contabildiade("Contabilidade");

	private String nome;
	
	private TipoArea(String nome) {
		this.nome = nome;
	}
	
	public String toString() {
		return this.nome;
	}
}
