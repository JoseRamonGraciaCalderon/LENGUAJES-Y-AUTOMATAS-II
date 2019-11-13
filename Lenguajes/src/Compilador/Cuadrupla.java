package Compilador;

public class Cuadrupla {
	public String operador;
	public String operandouno;
	public String operandodos;
	public String resultado;
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public String getOperandouno() {
		return operandouno;
	}
	public void setOperandouno(String operandouno) {
		this.operandouno = operandouno;
	}
	public String getOperandodos() {
		return operandodos;
	}
	public void setOperandodos(String operandodos) {
		this.operandodos = operandodos;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Cuadrupla(String operador, String operandouno, String operandodos, String resultado) {
		super();
		this.operador = operador;
		this.operandouno = operandouno;
		this.operandodos = operandodos;
		this.resultado = resultado;
	}

	

		

}
