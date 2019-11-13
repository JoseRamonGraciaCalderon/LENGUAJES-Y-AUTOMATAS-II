/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Compilador;

public class Comprobador 
{
    public String Scan(String Exp) throws Exception
    {
        String x = limpiarEspacios(Exp);
        if(okParentesis(x))
            if(okOperadores(x))
                return x;
            else
                x="O";
        else
            x="P";
        return x;
    }
    
    private String limpiarEspacios(String Exp)
    {
        String aux = "";
        for(int i=0; i<Exp.length();i++)
        {
            if(Exp.charAt(i)!=' ')
                aux = aux + Exp.charAt(i);
        }
        return aux;
    }
    private boolean okParentesis(String Exp)
    {
        Object aux;
        Pila p = new Pila();
        for(int i=0; i<Exp.length();i++)
        {
            if(Exp.charAt(i)=='(')
                p.Poner(Exp.charAt(i));
            else
                if(Exp.charAt(i)==')')
                {
                    if(p.vacia())
                        return false;
                    else
                       aux = p.Sacar();
                }
        }
        return (p.vacia()==true);     
    }
    
    private boolean okOperadores(String Exp1)
    {
       
        String Exp = limpiarParentesis(Exp1);
        if(Operador(Exp.charAt(0))||(Operador(Exp.charAt(Exp.length()-1))))
            return false;
        else
        {
            boolean sw=true;
            for(int i=1; i<Exp.length()-1;i++)
            {
                if(Operador(Exp.charAt(i))&&(Operador(Exp.charAt(i+1))))
                    return false;
            }
            return sw;
        }
    }
    private String limpiarParentesis(String Exp)
    {
        String aux="";
        for(int i=0; i<Exp.length();i++)
        {
            if((Exp.charAt(i)!='(')&&(Exp.charAt(i)!=')'))
                aux = aux + Exp.charAt(i);
        }
        return aux;
    }
    
    private boolean Operador(char x)
    {
          boolean sw = false;
          switch(x)
          {
             case '+' : sw = true; break;
             case '-' : sw = true; break;
             case '*' : sw = true; break;
             case '/' : sw = true; break;
             case '^' : sw = true; break;
             
          }  
	  return sw;
    }

}
