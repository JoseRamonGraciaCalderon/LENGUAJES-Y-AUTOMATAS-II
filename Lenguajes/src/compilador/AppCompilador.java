package compilador;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class AppCompilador extends JFrame implements ActionListener{
	// Componentes o Atributos
	private JMenuBar barraMenu;
	private JMenu menuArchivo,MenuAnalisis;
	// Menu Archivo
	private JMenuItem itemNuevo,itemAbrir,itemGuardar,itemSalir,itemAnalisLexico;
	private JFileChooser ventanaArchivos;
	private File archivo;
	private JTextArea areaTexto,areaObjeto;
	private JList<String> tokens;
	private JTabbedPane documentos,consola;
	private String [] titulos ={"Tipo","Nombre","Valor"};
	DefaultTableModel modelo = new DefaultTableModel(new Object[0][0],titulos);
	private JTable mitabla = new JTable(modelo);
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
		new AppCompilador();
	}
	public AppCompilador() {
		super("Compilador");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setIconImage(new ImageIcon("icono.png").getImage());
		setLayout(new GridLayout(2,0));
		setSize(800,700);
		setLocationRelativeTo(null);
		creaInterFaz();
	    addWindowListener((WindowListener) new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				int option = JOptionPane.showConfirmDialog(null,
					"¿Estás seguro de que quieres cerrar la aplicación?",
					"Confirmación de cierre", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setVisible(true);
	}
	private void creaInterFaz() {
		barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setIcon(new ImageIcon("archivo.png"));
		MenuAnalisis =  new JMenu("Analisis");
		MenuAnalisis.setIcon(new ImageIcon("analisis.png"));
		ventanaArchivos = new JFileChooser();
		itemNuevo = new JMenuItem("Nuevo");
		itemAbrir = new JMenuItem("Abrir...");
		itemGuardar = new JMenuItem("Guardar...");
		itemSalir = new JMenuItem("Salir");
		itemSalir.addActionListener(this);
		itemGuardar.addActionListener(this);
		itemAbrir.addActionListener(this);
		itemNuevo.addActionListener(this);
		itemAnalisLexico  = new JMenuItem("Analizar codigo");
		itemAnalisLexico.addActionListener(this);
		ventanaArchivos = new JFileChooser();
		menuArchivo.add(itemNuevo);
		menuArchivo.add(itemAbrir);
		menuArchivo.add(itemGuardar);
		menuArchivo.addSeparator();
		menuArchivo.add(itemSalir);
		MenuAnalisis.add(itemAnalisLexico);
		barraMenu.add(menuArchivo);
		barraMenu.add(MenuAnalisis);
		areaTexto = new JTextArea();
		areaObjeto = new JTextArea();
		ventanaArchivos= new JFileChooser("Guardar");
		areaTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
		documentos = new JTabbedPane();
		consola = new JTabbedPane();
		documentos.addTab("Nuevo", new JScrollPane(areaTexto));
		documentos.setToolTipText("Aqui se muestra el codigo");
		add(documentos);
		tokens=new JList<String>();
		consola.addTab("Consola",new JScrollPane(tokens));
		consola.addTab("Tabla",new JScrollPane(mitabla));
		consola.addTab("Codigo Objeto", new JScrollPane(areaObjeto));
		add(consola);
		itemNuevo.setIcon(new ImageIcon("nuevo.png"));
		itemGuardar.setIcon(new ImageIcon("guardar.png"));
		itemAbrir.setIcon(new ImageIcon("abrir.png"));
		itemSalir.setIcon( new ImageIcon("salir.png"));
		itemAnalisLexico.setIcon(new ImageIcon("lexico.png"));
		documentos.setIconAt(0, new ImageIcon("codigo.png"));
		consola.setIconAt(0, new ImageIcon("consola.png"));
		consola.setIconAt(1, new ImageIcon("tabla.png"));
		consola.setIconAt(2, new ImageIcon("Codigoobjeto.png"));
		consola.setToolTipText("Aqui se muestra el resultado del analisis");

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemAnalisLexico) {
			if(guardar()){
				Analisis analisador = new Analisis(archivo.getAbsolutePath());
				tokens.setListData(analisador.getmistokens().toArray( new String [0]));
				modelo = new DefaultTableModel(new Object[0][0],titulos);
				mitabla.setModel(modelo);
				for (int i = analisador.getIdenti().size()-1; i >=0; i--) {
					Identificador id = analisador.getIdenti().get(i);
					if(!id.tipo.equals("")) {
						Object datostabla[]= {id.tipo,id.nombre,id.valor};
						modelo.addRow(datostabla);
					}
				}

			}
		
			return;
		}
		if (e.getSource()==itemSalir) {
			System.exit(0);
			return;
		}
		if(e.getSource()==itemNuevo) {
			documentos.setTitleAt(0, "Nuevo");
			areaTexto.setText("");
			archivo=null;
			tokens.setListData(new String[0]);
			return;
		}
		if(e.getSource()==itemAbrir) {
			ventanaArchivos.setDialogTitle("Abrir..");
			ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(ventanaArchivos.showOpenDialog(this)==JFileChooser.CANCEL_OPTION) 
				return;
			archivo=ventanaArchivos.getSelectedFile();
			documentos.setTitleAt(0, archivo.getName());
			abrir();
		}
		if(e.getSource()==itemGuardar) {
			guardar();
		}
	}
	public boolean guardar() {
		try {
			if(archivo==null) {
				ventanaArchivos.setDialogTitle("Guardando..");
				ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(ventanaArchivos.showSaveDialog(this)==JFileChooser.CANCEL_OPTION) 
					return false;
				archivo=ventanaArchivos.getSelectedFile();
				documentos.setTitleAt(0, archivo.getName());
			}
			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(areaTexto.getText());
			bf.close();
			fw.close();
		}catch (Exception e) {
			System.out.println("Houston tenemos un problema?");
			return false;
		}
		return true;
	}
	public boolean abrir() {
		String texto="",linea;
		try {
			FileReader fr = new FileReader(archivo) ; 
			BufferedReader br= new BufferedReader(fr);
			while((linea=br.readLine())!=null) 
				texto+=linea+"\n";
			areaTexto.setText(texto);
			return true;
		}catch (Exception e) {
			archivo=null;
			JOptionPane.showMessageDialog(null, "Tipo de archivo incompatible", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
