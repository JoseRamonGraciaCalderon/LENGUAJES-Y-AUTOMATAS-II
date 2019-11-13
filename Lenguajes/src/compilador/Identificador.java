package Compilador;


public class Identificador {

	@Override
	public String toString() {
		return "Identificador [nombre=" + nombre + ", valor=" + valor + ", tipo=" + tipo +", local="+local+",linea"+linea+ "]";
	}
	String nombre;
	String valor;
	String tipo;
	int linea;
	String local;
	
	public Identificador(String nombre, String valor, String tipo,String local,int linea) {
		super();
		this.nombre = nombre;
		this.valor = valor;
		this.tipo = tipo;
		this.linea=linea;
		this.local=local;
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
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
}