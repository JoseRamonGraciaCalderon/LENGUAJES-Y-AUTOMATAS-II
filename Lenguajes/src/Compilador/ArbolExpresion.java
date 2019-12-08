/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Compilador;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArbolExpresion 
{ 
    private Nodo Raiz;
    private int n;
	public int i=0; 
	private int contador=0;
	private int prueba=0;
	public static  ArrayList<Cuadrupla> poderoso=new ArrayList<Cuadrupla>();
    public ArbolExpresion()
    {
        this.Raiz  = null;
        this.n     = 0;
    }

    public ArbolExpresion(LinkedList Exp)
    {
        Object aux =  Exp.get(0);
        Tupla a = new Tupla();
        a.setValor(aux.toString());
        if(operador(aux))     
            a.setOperador(true);
        else
            a.setOperador(false);
         Nodo op,q = new Nodo(a);
            Pila p = new Pila();
        boolean antesOperando = false;
        Raiz = q;
        for(int i=1;i<Exp.size(); i++ )
        {
            Object aux2 = Exp.get(i);
            //podria ser
            Tupla b = new Tupla(aux2.toString(),false);
            if(operador(aux2))
                b.setOperador(true);
            op = new Nodo(b);
            if(antesOperando)
            {
                     q = (Nodo) p.Sacar();
                     q.setHD(op);
            }else
            {
                     q.setHI(op);
                     p.Poner(q);
            }
            q = op;
            if(operador(Exp.get(i)))
                     antesOperando = false;
            else
                     antesOperando = true;
      
        }
        
    }
    
    public boolean Hoja(Nodo R)
    {
        return ((R.getHI()==null)&&(R.getHD()==null));
    }
    
    public void setRaiz(Nodo R)
    {
        if(R!=null)
        {
            this.Raiz = R;
            n++;
        }else
            this.Raiz = R;
    }
    
    public Nodo getRaiz()
    {
        return this.Raiz;
    }
    
    public int getNodos()
    {
        return n;
    }
    
    private boolean operador(Object c)
    {
        char operadores[] = {'+','-','*','/','^'};
        boolean existe = false;
        char aux =   c.toString().charAt(0);
        for(int i=0; ((i<5) && (!existe)); i++)
            if(aux == operadores[i])
                existe = true;
        return existe;
    }
    
    public double Evaluar()
    {
        return Evaluar(Raiz);
    }
    public String Evaluar2()
    {
        return EvaluarPoderoso(Raiz,contador);
    }
    public  ArrayList<Cuadrupla> obtienedatos(){
    	return poderoso;
    }
    private double Evaluar(Nodo R)
    {
  
       double res=0;
       if(R==null)
            return res=0;
       else
       {
            if(Hoja(R)) // Operando
            {
                String aux = R.getData().getValor();
                
                res = Double.parseDouble(aux);
        
                
            }else
            {
            
              double vizq = Evaluar(R.getHI());
         
             double vder = Evaluar(R.getHD());
  
             Character op = R.getData().getValor().charAt(0);
            
              
              switch(op)
              {
              	
              	
                 case '+' : res = vizq + vder;
                 System.out.printf("%1s %5s %4s %8s %4s %8s %4s %8s %4s ","|","+","|",vizq,"|",vder,"|",res,"|");             	
                 System.out.println();
                 
                 break;
                 case '-' : res = vizq - vder;
                
                 System.out.printf("%1s %5s %4s %8s %4s %8s %4s %8s %4s ","|","-","|",vizq,"|",vder,"|",res,"|");  	
                 System.out.println();
                 break;
                 case '*' : res = vizq * vder;
                 
                 System.out.printf("%1s %5s %4s %8s %4s %8s %4s %8s %4s","|","*","|",vizq,"|",vder,"|",res,"|");
                 System.out.println();
                 break;
                 case '/' : res = vizq / vder;
                 System.out.printf("%1s %5s %4s %8s %4s %8s %4s %8s %4s","|","/","|",vizq,"|",vder,"|",res,"|");
                 System.out.println();
                 break;
                 case '^' : res = Math.pow(vizq,vder);
              
                 System.out.printf("%1s %5s %4s %8s %4s %8s %4s %8s %4s","|","'","|",vizq,"|",vder,"|",res,"|");
                 System.out.println();
                 break;
                 default:
                	 ; 
                 break;
              }
            }  
       }
       return res;
    }
    private String EvaluarPoderoso(Nodo R,int j)
    {
    	
       String res="";
       if(R==null)
            return res="";
       else
       {
            if(Hoja(R)) // Operando
            {
                String aux = R.getData().getValor();
                
                res = aux;
                
            }else
            {
            j++;
            
              String vizq = EvaluarPoderoso(R.getHI(),j);
         
             String vder = EvaluarPoderoso(R.getHD(),j);
                          
              Character op = R.getData().getValor().charAt(0);
              res="T: "+j;
              
              contador=j;
              poderoso.add(new Cuadrupla(op.toString(),vizq,vder,res));
              System.out.println("Dim as Int=" +vizq);
              System.out.println("Dim as Int=" +vder);
              if(j==1) {
            	  poderoso.add(new Cuadrupla("=",res,"",""));
              }
            }
       }
       return res;
    }
    
}
