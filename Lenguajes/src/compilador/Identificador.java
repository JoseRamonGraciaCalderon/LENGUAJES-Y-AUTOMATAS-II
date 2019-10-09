package compilador;


public class Identificador {

	@Override
	public String toString() {
		return "Identificador [nombre=" + nombre + ", valor=" + valor + ", tipo=" + tipo +",linea"+linea+ "]";
	}
	String nombre;
	String valor;
	String tipo;
	int linea;
	
	public Identificador(String nombre, String valor, String tipo,int linea) {
		super();
		this.nombre = nombre;
		this.valor = valor;
		this.tipo = tipo;
		this.linea=linea;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	
}