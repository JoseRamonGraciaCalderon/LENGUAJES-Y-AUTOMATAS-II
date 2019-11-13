/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Compilador;

import java.util.LinkedList;

public class Expresion 
{
    private String Exp;
    
    public Expresion()
    {
        Exp = "";
    }
    
    public String getExpresion()
    {
        return Exp;
    }
    
    public String Infija()
    {
        LinkedList a = inFija();
        String aux="[ "; 
        for(int i=0; i<a.size();i++)
        {
            if((i+1)==a.size())
                aux = aux + a.get(i).toString();
            else
                aux = aux + a.get(i).toString() + "  ";
        }
        aux = aux + " ]";
        return aux;
    }
    private LinkedList inFija()
    {
        LinkedList infija = new LinkedList();
        String Expr = Clasica();
        int n = Expr.length()-1;
        for(int i=0; i<=n; i++)
            infija.addLast(Expr.charAt(i));
        return infija;
    }
    
     public String Prefija()
    {
        LinkedList a = preFija();
        String aux="[ "; 
        for(int i=0; i<a.size();i++)
        {
            if((i+1)==a.size())
                aux = aux + a.get(i).toString();
            else
                aux = aux + a.get(i).toString() + "  ";
        }
        aux = aux + " ]";
        return aux;
    }
    
    public String Completo() throws Exception
    {
        LinkedList a = CompletoPrefija();
        String aux="[ "; 
        for(int i=0; i<a.size();i++)
        {
            if((i+1)==a.size())
                aux = aux + a.get(i).toString();
            else
                aux = aux + a.get(i).toString() + "  ";
        }
        aux = aux + " ]";
        return aux;
    }
    
    public LinkedList CompletoPrefija() throws Exception
    {
        LinkedList sep = Separar();
        LinkedList completo = new LinkedList();
        int n = sep.size()-1,j=0;
        LinkedList pfija = preFija();
        for(int i=0; ((i<pfija.size())&&(j<=n)) ;i++)
        {
            Character e1 = (Character) pfija.get(i);
            if(Character.isLetter(e1))
            {
                completo.addLast(sep.get(j));
                j++;
            }else
                completo.addLast(e1);
        }
        return completo;
    }
     
    private LinkedList preFija()
    {
           
           Character c,d,e;
      	   int i,prioridadCima,prioridadOper;
      	   LinkedList expPre = new LinkedList();
           Pila aux = new Pila();
           Pila med = new Pila();
           Pila pre = new Pila();
	   String Expr = Clasica();
           for(i=0;i<Expr.length();i++)
                aux.Poner(new Character(Expr.charAt(i)));
	   
           while(!aux.vacia())
           {
		c = (Character) aux.Sacar();
		if(c.charValue() == ')')
                {
                    med.Poner(c);
		}else 
                {    
                    if(c.charValue() == '(')
                    {
			e = (Character) med.Cima();
			while(e.charValue() != ')')
                        {
                            c = (Character) med.Sacar();
                            pre.Poner(c);
			    e = (Character) med.Cima();
			}
			c = (Character) med.Sacar();
		     }else 
                         if(operador(c.charValue()))
                         {
		              e = (Character) med.Cima(); 
		              prioridadCima = prioridad(e);
                              prioridadOper = prioridad(c);
                              while(!med.vacia() && (prioridadOper < prioridadCima))
                              {
				d = (Character) med.Sacar();
				pre.Poner(d);
				e = (Character) med.Cima();
				prioridadCima = prioridad(e);
			      }
                              med.Poner(c);
			  }else
                              pre.Poner(c);
                }
	   }
	   while(!med.vacia())
           {
		c = (Character) med.Sacar();
		pre.Poner(c);
	   }
	   while(!pre.vacia())
           {
		 c = (Character) pre.Sacar(); 
		expPre.addLast(c.charValue());
	   }	   
   	   return expPre;
    }
    private boolean operador(char c)
    {
       char operadores[] = {'+','-','*','/','^'};
       boolean op = false;
       for(int i=0;((i<5) && (!op)); i++)
           if(operadores[i] == c)	    
                op = true;
       return op; 
    }
    
    private int prioridad(Character op)
    {
           int r = 4;
           if(op != null)    
           {   switch(op.charValue())
              {
                 case ')' : r = 0;
                 case '(' : r = 0; break;
                 case '+' : r = 1;
                 case '-' : r = 1; break;
                 case '*' : r = 2;
                 case '/' : r = 2; break;
                 case '^' : r = 3;
              }
           }
           return r;
    }
    
    public Expresion(String Exp)
    {
        this.Exp = Exp;
    }
    private LinkedList Separar()
    {
        int i=0;
        int n = Exp.length()-1;
        String aux1,aux2;
        aux2=Exp;
        aux1="";
        LinkedList e = new LinkedList();
        while((i<=n))
        {
          while((i<=n)&&(!Character.isDigit(aux2.charAt(i))))
              i++;
          aux1="";
          while((i<=n)&&((Character.isDigit(aux2.charAt(i)))||(aux2.charAt(i)=='.')))
          {    
              aux1=aux1+aux2.charAt(i);
              i++;
          }
          if(estaBien(aux1))
                e.add(aux1);
        }
        return e;
    }
    
    private boolean estaBien(String nro)
    {
        // Verificar que la cadena sea un numero float correcto.
        return true;
    }
    public String Valores()
    {
        LinkedList a = Separar();
        String aux="[ "; 
        for(int i=0; i<a.size();i++)
        {
            if((i+1)==a.size())
                aux = aux + a.get(i).toString();
            else
                aux = aux + a.get(i).toString() + " ";
        }
        aux = aux + " ]";
        return aux;
    }
    public String Clasica()
    {
        String Cara = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int p=-1;        
        String aux=Exp;
        int n = aux.length()-1,i=0;
        String aux1,aux2;
        aux1=aux2="";
        while(i<=n)
        {
            while((i<=n)&&(!Character.isDigit(aux.charAt(i))))
            {  
                aux1 = aux1+aux.charAt(i);
                i++;
            }
            aux2=""; boolean sw=false;
            while((i<=n)&& (Character.isDigit(aux.charAt(i)) || aux.charAt(i)=='.'))
            {    
              if(sw==false)
              {
                  sw=true;
                  p++;
              }
              aux2=aux2+aux.charAt(i);
              i++;
            }
            if((p!=-1)&&(sw))
                aux1 = aux1+Cara.charAt(p);
        }
        return aux1;
    }    
}
