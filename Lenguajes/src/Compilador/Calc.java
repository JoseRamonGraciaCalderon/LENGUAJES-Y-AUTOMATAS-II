/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Compilador;

import java.util.ArrayList;
import java.util.LinkedList;
 class Calc 
{
    public Calc()
    { }
    public double resultado;
    public ArrayList<Cuadrupla> guarda=new ArrayList<Cuadrupla>();
    public ArrayList<Cuadrupla> getdatos(){
    	return guarda;
    }
    public ArrayList<Cuadrupla> calcular(String Expre) throws Exception
    {
    	
        String aux = Expre;
        Comprobador scanner = new Comprobador();
        aux = scanner.Scan(aux);        
        if(aux.equals("P")||aux.equals("O"))
        {     
            if(aux.equals("P"))
              throw new Exception("Verifique los parentesis");
            else
              throw new Exception("El numero de parentesis no es correcto");
        }else
        {
            
            Expresion e;
            e = new Expresion(aux);
            LinkedList exp = e.CompletoPrefija();
            if((exp.size()!=0)&&(exp.size()!=2))
            {
                ArbolExpresion ar = new ArbolExpresion(exp);
                resultado = ar.Evaluar();  
                ar.Evaluar2(); 
                guarda=ar.obtienedatos();
               
        
            }else
                throw new Exception("La expresion es incorrecta");
        }
        return guarda;   
    }
}
