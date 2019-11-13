/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Compilador;

import java.util.LinkedList;


public class Recorridos 
{
    
    public static LinkedList preOrden(Nodo r)
    {
        LinkedList l= new LinkedList();
        preOrden(r,l);
        return l;
    }
    private static void preOrden(Nodo r, LinkedList l)
    {
       if(r!=null)
       {
           Tupla aux = r.getData();  
           l.add(aux.getValor());
           preOrden(r.getHI(),l);
           preOrden(r.getHD(),l);           
       } 
    }
    public static LinkedList inOrden(Nodo r)
    {
        LinkedList l= new LinkedList();
        inOrden(r,l);
        return l;
    }
    private static void inOrden(Nodo r,LinkedList l)
    {
        if(r!=null)
        {
           inOrden(r.getHI(),l);
           Tupla aux = r.getData();  
           l.add(aux.getValor());
           inOrden(r.getHD(),l);
        }

    }
    
    public static LinkedList postOrden(Nodo r)
    {
        LinkedList l= new LinkedList();
        postOrden(r,l);
        return l;
    }
    private static void postOrden(Nodo r,LinkedList l)
    {
        if(r!=null)
        {
            postOrden(r.getHI(),l);
            postOrden(r.getHD(),l);
            Tupla aux = r.getData();  
            l.add(aux.getValor());
        }

    }
    
    public static String Mostrar(LinkedList l)
    {
        LinkedList a = l;
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
    
    private boolean Hoja(Nodo R)
    {
        return ((R.getHI()==null)&&(R.getHD()==null));
    }
    
}
