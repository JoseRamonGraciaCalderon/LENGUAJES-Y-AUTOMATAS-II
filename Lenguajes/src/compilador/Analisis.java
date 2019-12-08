package Compilador;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import Compilador.Identificador;
import Compilador.ListaDoble;
import Compilador.NodoDoble;
import Compilador.Token;
public class Analisis
{
	public String guarda12="";
	int renglon=1;
	int contador=0;
	public double resultado=0;
	ArrayList<String> impresion; //para la salida
	public String CodigoObjeto=null;;
	ArrayList<Identificador> identi = new ArrayList<Identificador>();
	ArrayList<Cuadrupla> poderoso=new ArrayList<Cuadrupla>();
	ListaDoble<Token> tokens;
	ArrayList<String> auxiliar = new ArrayList<String>();
	final Token vacio=new Token("", 9,0);
	boolean bandera=true;
	
	public ArrayList<Identificador> getIdenti() {
		return identi;
	}
	public ArrayList<Cuadrupla> getCuadrupla(){
		return poderoso;
	}
	public Analisis(String ruta) {//Recibe el nombre del archivo de texto
		analisaCodigo(ruta);
		if(bandera) {
			impresion.add("No hay errores lexicos");
			analisisSintactico(tokens.getInicio());
			analisisSemantico(tokens.getInicio());
			GeneraObjeto();
			
			
		}
		if(impresion.get(impresion.size()-1).equals("No hay errores lexicos"))
			impresion.add("No hay errores sintacticos");
		if(impresion.get(impresion.size()-1).equals("No hay errores sintacticos"))
			impresion.add("No hay errores semanticos");
		for (Identificador identificador : identi) {
			if (identificador.getTipo().equals("")) {
				String x =buscar(identificador.getNombre());
				identificador.setTipo(x);
			}
		}
			
	}
	private  boolean isNumeric(String cadena){

		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	public boolean isString(Object str) {
		
        if (str.equals("true") || str.equals("false")) {
            return true;
        } else {
            return false;
        }
 
    }
	public boolean isDecimal(String numero){
		float n;
		try {
		 n=Float.parseFloat(numero);
		 return true;
		}catch(NumberFormatException nfe) {
			return false;
		}
	   
	}
	public boolean isChar(Object str) {
        if (str.equals(str.toString())) {
            return false;
        } else {
            return true;
        }
 
    }
	public static String poderoso(String loca) {
		Pattern entero = Pattern.compile("[0-9]+");//Expresiones Regulares para enteros
		Matcher entero1 = entero.matcher(loca);
		//para char '([^']*)'
		Pattern string = Pattern.compile("[a-zA-Z]+[^true|false]");//Expresiones Regulares para string
		Matcher string1 = string.matcher(loca);
		//"/^[0-9]+([.])?([0-9]+)?$/"
		Pattern decimal = Pattern.compile("[0-9]+[.]+[0-9]+");//Expresiones Regulares para float
		Matcher decimal1 = decimal.matcher(loca);
		
		Pattern booleano = Pattern.compile("[true|false]");//Expresiones para booleanos
		Matcher booleano1 = booleano.matcher(loca);
		
		if(string1.find()) {
			return "string";
		}
		else if(decimal1.find()) {
			return "float";
		}
		else if(booleano1.find()) {
			return "boolean";
		}
		else if(entero1.find()) {
			return "int";
		}
		else {
			return "";
		}
	
	}
	public void cuadruplas(String expresion){
		 Calc cal = new Calc();

	        try {
	            Expresion e = new Expresion(expresion);
	            System.out.printf("%5s %5s %5s %5s ","| Operador |"," Operando 1 |"," Operando 2 |","  Resultado |");
	            System.out.println();
	            cal.calcular(expresion);
	            resultado=cal.resultado;
	            poderoso=cal.getdatos();
	            
	        } catch (Exception e) {
	            System.out.println(e);
	        }
		
	}
	
	public Token analisisSemantico(NodoDoble<Token> nodo) {
		Token tokensig, to;
	
		
		if(nodo!=null) {
			to =  nodo.dato;
			
			if (to.getValor().equals("=")) {
				if(nodo.siguiente.dato.getTipo()==Token.CONSTANTE&& nodo.siguiente.siguiente.dato.getTipo()==Token.OPERADOR_ARITMETICO&&nodo.siguiente.siguiente.siguiente.dato.getTipo()==Token.CONSTANTE) {
				System.out.println("Entra al primero analisissemantico y no deberia");
				if (nodo.anterior.anterior.dato.getValor().equals("int")) {	
						if(!(nodo.siguiente.siguiente.dato.getValor().equals(";"))) {
							if(!isNumeric(nodo.siguiente.siguiente.siguiente.dato.getValor())) {
								String guarda;
								System.out.println("entra al primer int alav");
								guarda=poderoso(nodo.siguiente.siguiente.siguiente.dato.getValor());
								impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nodo.siguiente.dato.getValor()+"' del tipo int"+" el valor '"+ nodo.siguiente.siguiente.siguiente.dato.getValor()+"' del tipo de dato "+guarda);				
								}
						}
		
				}
				if (nodo.anterior.anterior.dato.getValor().equals("boolean")) {	
						if(!(nodo.siguiente.siguiente.dato.getValor().contains(";"))) {
							if(!isString(nodo.siguiente.siguiente.siguiente.dato.getValor())) {
								System.out.println("Entro al primer boolean");
								String guarda;
								guarda=poderoso(nodo.siguiente.siguiente.siguiente.dato.getValor());
								impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nodo.siguiente.dato.getValor()+"' del tipo boolean"+" el valor '"+ nodo.siguiente.siguiente.siguiente.dato.getValor()+"' del tipo de dato "+guarda);
							}
						}
		
				}
				if (nodo.anterior.anterior.dato.getValor().equals("float")) {	
						if(!(nodo.siguiente.siguiente.dato.getValor().contains(";"))) {
							if(!isDecimal(nodo.siguiente.siguiente.siguiente.dato.getValor())) {
								String guarda;
								guarda=poderoso(nodo.siguiente.siguiente.siguiente.dato.getValor());
								impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nodo.siguiente.dato.getValor()+"' del tipo float"+" el valor '"+ nodo.siguiente.siguiente.siguiente.dato.getValor()+"' del tipo de dato "+guarda);
							
							}
						
		
						}
				}
			
				 //para el caso x=5;
				 if(to.getValor().equals(";")) {
					 if(nodo.anterior.anterior.dato.getValor().equals("=") && !(nodo.anterior.dato.getValor().contains("+")||nodo.anterior.dato.getValor().contains("/")||nodo.anterior.dato.getValor().contains("*")||nodo.anterior.dato.getValor().contains("-"))) {

						 
						 
						 String guarda=null;
						for(int i=0;i<identi.size();i++) {
							 String valor=nodo.anterior.dato.getValor();
							 String nombre=nodo.anterior.anterior.anterior.dato.getValor();
							 if(identi.get(i).getTipo().equals("int") && identi.get(i).getNombre().equals(nombre)) {
								 guarda=poderoso(valor);
							 if(!(identi.get(i).getTipo().equals(guarda) && identi.get(i).getNombre().equals(nombre))) {
								 if (!(impresion.contains("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo int"+" el valor '"+ valor+"' del tipo de dato "+guarda))) {
									 System.out.println("Entra al segundo int");
									 impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo int"+" el valor '"+ valor+"' del tipo de dato "+guarda);
							 	}
							 }
							 } else
							 if(identi.get(i).getTipo().equals("float") && identi.get(i).getNombre().equals(nombre) ) {
								 guarda=poderoso(valor);
								 if(!(identi.get(i).getTipo().equals(guarda) && identi.get(i).getNombre().equals(nombre))) {
									 if (!(impresion.contains("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo float"+" el valor '"+ valor+"' del tipo de dato "+guarda))) {
										 impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo float"+" el valor '"+ valor+"' del tipo de dato "+guarda);
								 	}
								 }
								}else
							 if(identi.get(i).getTipo().equals("boolean") && identi.get(i).getNombre().equals(nombre)) {
								 guarda=poderoso(valor);
								 if(!(identi.get(i).getTipo().equals(guarda) && identi.get(i).getNombre().equals(nombre))) {
									 if (!(impresion.contains("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo boolean"+" el valor '"+ valor+"' del tipo de dato "+guarda))) {
										 impresion.add("Error semantico en la linea "+to.getLinea()+" al asignar a '"+nombre+"' del tipo boolean"+" el valor '"+ valor+"' del tipo de dato "+guarda);
								 		}
								 	}
							 	}
						 	}	 
						 }
					 }
				}
			}
				if(to.getTipo()==Token.IDENTIFICADOR) {

						String auxiliar = to.getValor();
						boolean banderaM = false;
						
						for( int i=0; i<identi.size(); i++) {
							if(identi.get(i).getNombre().equals(auxiliar)) {
								banderaM=true;
							}
						  }
						if(!banderaM)
							impresion.add("Error semantico en la linea "+to.getLinea() + " se usa la variable '" + auxiliar + "' no esta declarada");
						  }
	
			/*if(to.getValor().equals("=")) {
				boolean poderosa=false;
				String guarda = null;
				for(int i=0;i<identi.size();i++) {
					if(identi.get(i).getNombre().equals(nodo.anterior.dato.getValor())){	
						guarda=identi.get(i).getValor();
						poderosa=true;
				}
				}
				if(poderosa) {
				cuadruplas(guarda);
				}
				for(int i=0;i<identi.size();i++) {
					if(identi.get(i).getNombre().equals(nodo.anterior.dato.getValor())){	
						identi.get(i).setValor(Double.toString(resultado));
				}
				
			}*/
			//}
			tokensig=analisisSemantico(nodo.siguiente);
			return to;
			
		}
		return  vacio;
	}
	public void analisaCodigo(String ruta) {
		String linea="", token="";
		StringTokenizer tokenizer;
		try{
	          FileReader file = new FileReader(ruta);
	          BufferedReader archivoEntrada = new BufferedReader(file);
	          linea = archivoEntrada.readLine();
	          impresion=new ArrayList<String>();
	          tokens = new ListaDoble<Token>();
	          while (linea != null){
	        	    linea = separaDelimitadores(linea);
	                tokenizer = new StringTokenizer(linea);
	                while(tokenizer.hasMoreTokens()) {
	                	token = tokenizer.nextToken();
	                	analisisLexico(token);
	                }
	                linea=archivoEntrada.readLine();
	                renglon++;
	          }
	          archivoEntrada.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,"No se encontro el archivo favor de checar la ruta","Alerta",JOptionPane.ERROR_MESSAGE);
		}
	}
	public String GeneraObjeto() {
		int contador=0;
		String aux="";
		CodigoObjeto=null;
		CodigoObjeto=";====================CODIGO========================\n"
				+ "		.MODEL small \n" + 
				"		.DATA \n";
		for( int i=0; i<poderoso.size(); i++) {
			//CodigoObjeto.add("Temp"+i+" db "+ "0"+"\n");
			CodigoObjeto+=poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+" 		dw 		0 \n";
		  }
		CodigoObjeto+="		.CODE\n"+
				 "MAIN 		PROC 		FAR\n" + 
				"		.STARTUP\n";
		
		for( int i=0; i<poderoso.size(); i++) {
			if(poderoso.get(i).operador.equals("*")) {
				CodigoObjeto+="		;Multiplication section(*)\n"
						+ "		MOV AX,"+poderoso.get(i).getOperandouno().replace(" ", "").replace(":", "")+"\n" + 
						"		MOV BX,"+poderoso.get(i).getOperandodos().replace(" ", "").replace(":", "")+"\n"+
						"		MUL BX \n"+
						"		MOV "+poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+",AX"+"\n";
			}
			if(poderoso.get(i).operador.equals("+")) {
				CodigoObjeto+="		;Addition section(+)\n"
						+ "		MOV AX,"+poderoso.get(i).getOperandouno().replace(" ", "").replace(":", "")+"\n" + 
						"		ADD AX,"+poderoso.get(i).getOperandodos().replace(" ", "").replace(":", "")+"\n"+
						"		MOV "+poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+",AX"+"\n";
				
			}
			if(poderoso.get(i).operador.equals("-")) {
				CodigoObjeto+=";		Addition section(-)\n"
						+ "		MOV BX,"+poderoso.get(i).getOperandouno().replace(" ", "").replace(":", "")+"\n" + 
						"		SUB BX,"+poderoso.get(i).getOperandodos().replace(" ", "").replace(":", "")+"\n"+
						"		MOV "+poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+",BX"+"\n";
			}
			if(poderoso.get(i).operador.equals("/")) {
				CodigoObjeto+="		;Divition Section(/)\n"+
						"		MOV AX,"+poderoso.get(i).getOperandouno().replace(" ", "").replace(":", "")+"\n" + 
						"		MOV BX,"+poderoso.get(i).getOperandodos().replace(" ", "").replace(":", "")+" \n"+
						"		DIV BX\n"+
						"		MOV "+poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+",AX \n"
						;
			}
			if(poderoso.get(i).operador.equals("=")) {
				CodigoObjeto+="		;Asignacion Section(=)\n"
						+ "		MOV AX,"+poderoso.get(i).getOperandouno().replace(" ", "").replace(":", "")+"\n"+
						"		MOV "+poderoso.get(i).getResultado().replace(" ", "").replace(":", "")+",AX \n";
			}	
			}
		CodigoObjeto+="MAIN 		ENDP\n"
				+ "		END";

		
		return CodigoObjeto;
	}
	public Token analisisSintactico(NodoDoble<Token> nodo) {
		Token  to;
		
		if(nodo!=null) {
			to =  nodo.dato;
			try {
			switch (to.getTipo()) {
			case Token.MODIFICADOR:
				int sig=nodo.siguiente.dato.getTipo();
				if(sig!=Token.TIPO_DATO && sig!=Token.CLASE)
					impresion.add("Error sinatactico en la linea "+to.getLinea()+" se esparaba un tipo entra a modificador de dato");
				break;
			case Token.IDENTIFICADOR:
				if(!(Arrays.asList("{","=",";").contains(nodo.siguiente.dato.getValor()))) 
					impresion.add("Error sinatactico en la linea "+to.getLinea()+" se esparaba un simbolo");
				else
					if(nodo.anterior.dato.getValor().equals("class")){
						identi.add( new Identificador(to.getValor(), " ", "class","global",to.getLinea()));
					}
				break;
			case Token.TIPO_DATO:
			case Token.CLASE:
				if(nodo.anterior.dato.getTipo()==Token.MODIFICADOR) {
					if(nodo.siguiente.dato.getTipo()!=Token.IDENTIFICADOR) 
						impresion.add("Error sinatactico en la linea "+to.getLinea()+" se esparaba un identificador");
				}else
					impresion.add("Error sinatactico en la linea "+to.getLinea()+" se esperaba un modificador");
				break;
			case Token.SIMBOLO:
				boolean banderita=false;
				if(to.getValor().equals("}")) {
					if(cuenta("{")!=cuenta("}"))
						impresion.add("Error sinatactico en la linea "+to.getLinea()+ " falta un {");
				}else if(to.getValor().equals("{")) {
					if(cuenta("{")!=cuenta("}"))
						impresion.add("Error sinatactico en la linea "+to.getLinea()+ " falta un }");
				}
				else if(to.getValor().equals("(")) {

					if(nodo.anterior.dato.getValor().equals("if")) {
						System.out.println("Por lo menos encuentra el if");
						if(!(nodo.siguiente.dato.getTipo()==Token.CONSTANTE)) {
							impresion.add("Error sinatactico en la linea "+to.getLinea()+ " se esperaba un valor");
							System.out.println("Entra  a donde se suponia");
					}
					}
					if(cuenta("(")!=cuenta(")"))
						impresion.add("Error sinatactico en la linea "+to.getLinea()+ " falta un )");
					
	
				}else if(to.getValor().equals(")")) {
					if(cuenta("(")!=cuenta(")"))
						impresion.add("Error sinatactico en la linea "+to.getLinea()+ " falta un (");
				
				}else if (to.getValor().equals(";"))
				{
					
					try
					{
		
						//para la declaracion de public int x;
						 if (nodo.anterior.anterior.dato.getTipo()==Token.TIPO_DATO && nodo.anterior.dato.getTipo()==Token.IDENTIFICADOR){
							 int x =0,auxRenglon=0;
								boolean bandera=false;
								for (int i = 0; i < identi.size(); i++) {
									if (identi.get(i).getNombre().equals(nodo.anterior.dato.getValor()) ){
										x++;
										auxRenglon=i;
									}
									
								}
								if(nodo.anterior.anterior.dato.getTipo()==Token.TIPO_DATO && x>0 && nodo.anterior.dato.getTipo()==Token.IDENTIFICADOR){
									impresion.add("Error semantico en linea "+to.getLinea()+ " la variable '"+nodo.anterior.dato.getValor()+"' ya habia sido declarada en la linea "+identi.get(auxRenglon).linea);
									System.out.println("Entra al primero");
								bandera=true;
								}
								
								if(!bandera)
									identi.add(new Identificador(nodo.anterior.dato.getValor(),"",nodo.anterior.anterior.dato.getValor(),"global",to.getLinea()));
								
							}
					
					} catch (Exception e){
						System.out.println(e.getMessage());
					}
					
				}else if (to.getValor().equals("="))
					{
					boolean bandera1=false;
					NodoDoble<Token> aux=nodo;
						while(aux!=null && !bandera1){
							aux=aux.siguiente;
							if(aux.dato.getValor().equals(";")) {
								bandera1=true;
								break;
							}
							
							guarda12+=aux.dato.getValor();
						}
					//para cualquier declaracion que lleve un = 
					//ya sea el caso de public int x=2;
					try {
						if (nodo.anterior.anterior.dato.getTipo()==Token.TIPO_DATO && nodo.anterior.dato.getTipo()==Token.IDENTIFICADOR && nodo.dato.getTipo()==Token.SIMBOLO){
					
							
								
								int x =0,auxRenglon=0;
								boolean bandera=false;
								for (int i = 0; i < identi.size(); i++) {
									if (identi.get(i).getNombre().equals(nodo.anterior.dato.getValor()) ){
										x++;
										auxRenglon=i;
									}
									
								}
								
								if(nodo.anterior.anterior.dato.getTipo()==Token.TIPO_DATO && x>0 && nodo.anterior.dato.getTipo()==Token.IDENTIFICADOR){
									System.out.println("Entra al segundo");
									impresion.add("Error semantico en linea "+to.getLinea()+ " la variable '"+nodo.anterior.dato.getValor()+"' ya habia sido declarada en la linea "+identi.get(auxRenglon).linea);
								bandera=true;
								}
								
								if(!bandera) {
									
									System.out.println("Se mete a guardar");
									
									cuadruplas(guarda12);
									for (int i=0 ;i< getCuadrupla().size(); i++) {
									Cuadrupla id =  getCuadrupla().get(i);
									if(id.resultado=="") {
										id.setResultado(nodo.anterior.dato.getValor());
									}
									
									}
									
									
									
									identi.add(new Identificador(nodo.anterior.dato.getValor(),Double.toString(resultado),nodo.anterior.anterior.dato.getValor(),"global",to.getLinea()));
									guarda12="";
									
								
								}
							

						}
						//para el caso de asignar x=2;
						else if (nodo.anterior.dato.getTipo()==Token.IDENTIFICADOR&&nodo.dato.getTipo()==Token.SIMBOLO)
						{
							cuadruplas(guarda12);
							for (int i = 0; i < identi.size(); i++) {
								if(identi.get(i).getNombre().equals(nodo.anterior.dato.getValor())){
									
									identi.get(i).setValor(Double.toString(resultado));
									banderita=true;
								}
							}
							guarda12="";
							
							if(!banderita){
								impresion.add("Error sintactico en linea "+to.getLinea()+ " se esperaba un Tipo de Dato");
							}
							
						}
					}catch (Exception e){
							System.out.println(e.getMessage());
						}
					
					}
					
					
				
				break;
			case Token.CONSTANTE:
				if(nodo.anterior.dato.getValor().equals("="))
					if(nodo.siguiente.dato.getTipo()!=Token.OPERADOR_ARITMETICO&&nodo.siguiente.dato.getTipo()!=Token.CONSTANTE&&!nodo.siguiente.dato.getValor().equals(";"))
						impresion.add("Error sinatactico en linea "+to.getLinea()+ " asignacion no valida");
				break;
			case Token.PALABRA_RESERVADA:
				if(to.getValor().equals("if"))
				{
					if(!nodo.siguiente.dato.getValor().equals("(")) {
						impresion.add("Error sinatactico en linea "+to.getLinea()+ " se esperaba un (");
					}
				}
				else {
					NodoDoble<Token> aux = nodo.anterior;
					boolean bandera=false;
					while(aux!=null&&!bandera) {
						if(aux.dato.getValor().equals("if"))
							bandera=true;
						aux =aux.anterior;
					}
					if(!bandera)
						impresion.add("Error sinatactico en linea "+to.getLinea()+ " else no valido");
				}
				break;
			case Token.OPERADOR_LOGICO:
				if (false) {
					System.out.println("Entra con false");
				
				if(nodo.anterior.dato.getTipo()!=Token.CONSTANTE) 
					
					impresion.add("Error sinatactico en linea "+to.getLinea()+ " se esperaba una constante");
				if(nodo.siguiente.dato.getTipo()!=Token.CONSTANTE) {
				impresion.add("Error sinatactico en linea "+to.getLinea()+ " se esperaba una constante");
				}
				String auxi1;
				String auxi2;
				auxi1=poderoso(nodo.anterior.dato.getValor());
				auxi2=poderoso(nodo.siguiente.dato.getValor());
				String perron1=null;
				System.out.println(auxi1);
				System.out.println(auxi2);
					if((auxi1!=auxi2)) {
						impresion.add("Error semantico en linea "+to.getLinea()+" no son del mismo tipo de dato la variable '"+nodo.anterior.dato.getValor()+"' del tipo "+auxi1+" y el valor '"+nodo.siguiente.dato.getValor()+"' del tipo " +auxi2);
					
				}
				}
				break;
			case Token.OPERADOR_ARITMETICO:
			
				
				if (false) {
				//Verificar que el nodo anterior sea una constante  
				if(!(nodo.anterior.dato.getTipo()==Token.CONSTANTE || Token.OPERADOR_ARITMETICO==nodo.dato.getTipo()))
					impresion.add("Error Semantico en linea " + to.getLinea() + "Se esperaba una constante");
				if(!(nodo.siguiente.dato.getTipo()==Token.CONSTANTE || Token.OPERADOR_ARITMETICO==nodo.dato.getTipo()))
					impresion.add("Error Semantico en linea " + to.getLinea() + "Se esperaba una constante");
				String aux1="";
				String aux2="";
				String perron = null;
				aux1=poderoso(nodo.anterior.dato.getValor());
				aux2=poderoso(nodo.siguiente.dato.getValor());
				boolean bandera1=false;
				for(int i=0;i<identi.size();i++) {
					if(identi.get(i).getNombre().equals(nodo.anterior.anterior.anterior.dato.getValor())) {
						perron=identi.get(i).getTipo();
					}
					
				}
				//para la declaracion public int x=5+5;
				if(!(perron!=null)) {
					String loca1=poderoso(nodo.anterior.dato.getValor());
					String loca2=poderoso(nodo.siguiente.dato.getValor());
					String tipo=nodo.anterior.anterior.anterior.anterior.dato.getValor();
					if((loca1!=loca2)){
						impresion.add("Error semantico en linea "+to.getLinea()+" al asignar a '"+nodo.anterior.anterior.anterior.dato.getValor()+"' del tipo "+tipo+ " no son del mismo tipo de dato el valor '"+nodo.anterior.dato.getValor()+"' del tipo "+loca1+" y el valor '"+nodo.siguiente.dato.getValor()+"' del tipo " +loca2);	
					}else {
						bandera1=true;
					}
					if(loca1.equals("boolean") && loca2.equals("boolean")) {
						impresion.add("Error semantico en linea "+to.getLinea()+" al asignar a '"+nodo.anterior.anterior.anterior.dato.getValor()+"' del tipo "+tipo
								+ " no puedes asignar el tipo de dato , valor '"+nodo.anterior.dato.getValor()+"' del tipo "+aux1+" y el valor '"+nodo.siguiente.dato.getValor()+"' del tipo " +aux2);
						bandera1=false;
					}
					if(bandera){
						int suma=0;
						float suma2=0;
						String guarda=null;
						if(tipo.equals("int")) {
							System.out.println("poquito mas");
							try {
								if(to.getValor().equals("+")) {
									System.out.println("ppquito mas mas");
								suma=Integer.parseInt(nodo.anterior.dato.getValor());
								suma+=Integer.parseInt(nodo.siguiente.dato.getValor());
								guarda=Integer.toString(suma);
								}
								if(to.getValor().equals("-")) {
									suma=Integer.parseInt(nodo.anterior.dato.getValor());
									suma-=Integer.parseInt(nodo.siguiente.dato.getValor());
									guarda=Integer.toString(suma);
								}
								if(to.getValor().equals("/")) {
									suma=Integer.parseInt(nodo.anterior.dato.getValor());
									suma/=Integer.parseInt(nodo.siguiente.dato.getValor());
									guarda=Integer.toString(suma);
								}
								if(to.getValor().equals("*")) {
									suma=Integer.parseInt(nodo.anterior.dato.getValor());
									suma*=Integer.parseInt(nodo.siguiente.dato.getValor());
									guarda=Integer.toString(suma);
								}
								
								identi.add(new Identificador(nodo.anterior.anterior.anterior.dato.getValor(),guarda,nodo.anterior.anterior.anterior.anterior.dato.getValor(),"global",to.getLinea()));
										
									
								
							} catch (NumberFormatException nfe){
								
							}
						}
						if(tipo.equals("float")) {
							try {
								if(to.getValor().equals("+")) {
								suma2=Float.parseFloat(nodo.anterior.dato.getValor());
								suma2+=Float.parseFloat(nodo.siguiente.dato.getValor());
								guarda=Float.toString(suma2);
								}
								if(to.getValor().equals("-")) {
									suma2=Float.parseFloat(nodo.anterior.dato.getValor());
									suma2-=Float.parseFloat(nodo.siguiente.dato.getValor());
									guarda=Float.toString(suma2);
								}
								if(to.getValor().equals("/")) {
									suma2=Float.parseFloat(nodo.anterior.dato.getValor());
									suma2/=Float.parseFloat(nodo.siguiente.dato.getValor());
									guarda=Float.toString(suma2);
									}
								if(to.getValor().equals("*")) {
									suma2=Float.parseFloat(nodo.anterior.dato.getValor());
									suma2*=Float.parseFloat(nodo.siguiente.dato.getValor());
									guarda=Float.toString(suma2);
									}
								identi.add(new Identificador(nodo.anterior.anterior.anterior.dato.getValor(),guarda,nodo.anterior.anterior.anterior.anterior.dato.getValor(),"global",to.getLinea()));
							} catch (NumberFormatException nfe){
								
							}
						}
					}
					break;
				}
				boolean bandera=false;
				if(!((aux1.equals(perron))&&(aux2.equals(perron) ))) {
					System.out.println("entra al menos esperado");
					impresion.add("Error semantico en linea "+to.getLinea()+" al asignar a '"+nodo.anterior.anterior.anterior.dato.getValor()+"' del tipo "+perron+ " no son del mismo tipo de dato el valor '"+nodo.anterior.dato.getValor()+"' del tipo "+aux1+" y el valor '"+nodo.siguiente.dato.getValor()+"' del tipo " +aux2);
				}else {
					bandera=true;
				}
				if(aux1.equals("boolean") && aux2.equals("boolean")) {
					impresion.add("Error semantico en linea "+to.getLinea()+" al asignar a '"+nodo.anterior.anterior.anterior.dato.getValor()+"' del tipo "+perron+ " no puedes asignar el  tipo de dato el valor '"+nodo.anterior.dato.getValor()+"' del tipo "+aux1+" y el valor '"+nodo.siguiente.dato.getValor()+"' del tipo " +aux2);
					bandera=false;
				}
				if(bandera) {
					int suma=0;
					float suma2=0;
					String guarda=null;
					if(aux1.equals("int")) {
					
						try {
							if(to.getValor().equals("+")) {
							suma=Integer.parseInt(nodo.anterior.dato.getValor());
							suma+=Integer.parseInt(nodo.siguiente.dato.getValor());
							guarda=Integer.toString(suma);
							}
							if(to.getValor().equals("-")) {
								suma=Integer.parseInt(nodo.anterior.dato.getValor());
								suma-=Integer.parseInt(nodo.siguiente.dato.getValor());
								guarda=Integer.toString(suma);
							}
							if(to.getValor().equals("/")) {
								suma=Integer.parseInt(nodo.anterior.dato.getValor());
								suma/=Integer.parseInt(nodo.siguiente.dato.getValor());
								guarda=Integer.toString(suma);
							}
							if(to.getValor().equals("*")) {
								suma=Integer.parseInt(nodo.anterior.dato.getValor());
								suma*=Integer.parseInt(nodo.siguiente.dato.getValor());
								guarda=Integer.toString(suma);
							}
							for (int i = 0; i < identi.size(); i++) {
								if(identi.get(i).getNombre().equals(nodo.anterior.anterior.anterior.dato.getValor())){
									identi.get(i).setValor(guarda);
									
								}
							}
						} catch (NumberFormatException nfe){
							
						}
					}
					if(aux1.equals("float")) {
						try {
							if(to.getValor().equals("+")) {
							suma2=Float.parseFloat(nodo.anterior.dato.getValor());
							suma2+=Float.parseFloat(nodo.siguiente.dato.getValor());
							guarda=Float.toString(suma2);
							}
							if(to.getValor().equals("-")) {
								suma2=Float.parseFloat(nodo.anterior.dato.getValor());
								suma2-=Float.parseFloat(nodo.siguiente.dato.getValor());
								guarda=Float.toString(suma2);
							}
							if(to.getValor().equals("/")) {
								suma2=Float.parseFloat(nodo.anterior.dato.getValor());
								suma2/=Float.parseFloat(nodo.siguiente.dato.getValor());
								guarda=Float.toString(suma2);
								}
							if(to.getValor().equals("*")) {
								suma2=Float.parseFloat(nodo.anterior.dato.getValor());
								suma2*=Float.parseFloat(nodo.siguiente.dato.getValor());
								guarda=Float.toString(suma2);
								}
							for (int i = 0; i < identi.size(); i++) {
								if(identi.get(i).getNombre().equals(nodo.anterior.anterior.anterior.dato.getValor())){
									identi.get(i).setValor(guarda);
									
								}
							}
						} catch (NumberFormatException nfe){
							
						}
					}
				
				}
				}
			
					break;
			}
			
			}catch(Exception e) {
				
			}
			analisisSintactico(nodo.siguiente);
			return to;
			
		}
		
		return  vacio;
		
	}
	public void analisisLexico(String token) {
		int tipo=0;
		//Se usan listas con los tipos de token
		// Esto se asemeja a un in en base de datos 
		//Ejemplo select * from Clientes where Edad in (18,17,21,44)
		System.out.println(token);
		if(Arrays.asList("public","static","private").contains(token)) 
			tipo = Token.MODIFICADOR;
		else if(Arrays.asList("if","else").contains(token)) 
			tipo = Token.PALABRA_RESERVADA;
		else if(Arrays.asList("int","char","float","boolean").contains(token))
			tipo = Token.TIPO_DATO;
		else if(Arrays.asList("(",")","{","}","=",";").contains(token))
			tipo = Token.SIMBOLO;
		else if(Arrays.asList("<","<=",">",">=","==","!=").contains(token))
			tipo = Token.OPERADOR_LOGICO;
		else if(Arrays.asList("+","-","*","/").contains(token))
			tipo = Token.OPERADOR_ARITMETICO;
		else if(Arrays.asList("true","false").contains(token)||Pattern.matches("^\\d+$",token) || Pattern.matches("[0-9]+.[0-9]+", token)) 
			tipo = Token.CONSTANTE;
		else if(token.equals("class")) 
			tipo =Token.CLASE;
		else {
			//Cadenas validas
			Pattern pat = Pattern.compile("^[a-zA -Z]+$");//Expresiones Regulares
			Matcher mat = pat.matcher(token);
			if(mat.find()) 
				tipo = Token.IDENTIFICADOR;
			else {
				impresion.add("Error en la linea "+renglon+" token "+token);
				bandera = false;
				return;
			}
		}
		tokens.insertar(new Token(token,tipo,renglon));
		impresion.add(new Token(token,tipo,renglon).toString());
	}
	private String buscar(String id) 
	{
		for (int i = identi.size()-1; i >=0; i--) {
			Identificador identificador = identi.get(i);
			if(identificador.getNombre().equals(id))
				return identificador.tipo;
		}
		return "";
	}
	public String separaDelimitadores(String linea){
		for (String string : Arrays.asList("(",")","{","}","=",";","+","<",">","else","-","*","/")) {
			if(string.equals("=")) {
				if(linea.indexOf(">=")>=0) {
					linea = linea.replace(">=", " >= ");
					break;
				}
				if(linea.indexOf("<=")>=0) {
					linea = linea.replace("<=", " <= ");
					break;
				}
				if(linea.indexOf("!=")>=0) {
					linea = linea.replace("!=", " != ");
					break;
				}
				if(linea.indexOf("==")>=0)
				{
					linea = linea.replace("==", " == ");
					break;
				}
			}
			if(linea.contains(string)) 
				linea = linea.replace(string, " "+string+" ");
		}
		return linea;
	}
	public int cuenta (String token) {
		int conta=0;
		NodoDoble<Token> Aux=tokens.getInicio();
		while(Aux !=null){
			if(Aux.dato.getValor().equals(token))
				conta++;
			Aux=Aux.siguiente;
		}	
		return conta;
	}
	public ArrayList<String> getmistokens() {
		return impresion;
	}
	public String getmisObjetos() {
		return CodigoObjeto;
	}
}
