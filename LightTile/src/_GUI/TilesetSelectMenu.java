package _GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import beans.LightMap;

public class TilesetSelectMenu extends JDialog implements ActionListener {
	
	private MainWindow owner;
	private ArrayList<JCheckBox> checkboxes;
	private JPanel flow,grid;
	private JScrollPane options;
	private JButton select;
	private BasicScrollBarUI UI;
	private Border border;
	private Cursor pointer;
	private Color light, dark;
	
	public TilesetSelectMenu(MainWindow owner) {
		
		this.owner = owner;
		this.border = owner.getBorder();
		this.pointer = owner.getPointer();
		this.dark = owner.getDark();
		this.light = owner.getLight();
		
		createUI();
		draw();
		setSize(400,500);
		setModal(true);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setTitle("Spritesheets");
		setVisible(true);
		
	}

	private void draw() {
		
		Container window = getContentPane();
		File dir = new File("res/tileset");
		String[] tilesets_paths = dir.list();
		
		flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		grid = new JPanel(new GridLayout(0, 1));
		grid.setBackground(dark);
		grid.setBorder(border);
		flow.setBackground(dark);
		flow.setBorder(border);
		
		checkboxes = new ArrayList<>();
		for (int i = 0; i < tilesets_paths.length; i++) {
			JCheckBox checkbox = new JCheckBox(tilesets_paths[i]);
			checkboxes.add(checkbox);
			checkbox.setName(i +"");
			checkbox.setBackground(null);
			checkbox.setFocusable(false);
			checkbox.setForeground(Color.white);
			checkbox.setFont(checkbox.getFont().deriveFont(18f));
			grid.add(checkbox);
		}
		
		select = new JButton("SELECT");
		select.addActionListener(this);
		select.setBackground(light);
		select.setForeground(Color.white);
		select.setFont(select.getFont().deriveFont(18f));
		select.setCursor(pointer);
		select.setFocusable(false);
		select.setBorder(border);
		select.setPreferredSize(new Dimension(390,50));
		
		flow.add(grid);
		
		options = new JScrollPane(flow);
		options.setBackground(owner.getDark());
		options.setBorder(owner.getBorder());
		options.getVerticalScrollBar().setBackground(owner.getDark());
		options.getVerticalScrollBar().setUI(UI);
				
		window.add(select,BorderLayout.SOUTH);
		window.add(options,BorderLayout.CENTER);
	}
	
	private void createUI() {
		UI = new BasicScrollBarUI() {	
			@Override protected void configureScrollBarColors(){this.thumbColor = new Color(20,20,20);}
			@Override protected JButton createIncreaseButton(int orientation) {
				JButton btn = super.createIncreaseButton(orientation);
				btn.setBackground(new Color(20,20,20));
				btn.setBorder(border);
				return btn;
			}
			@Override protected JButton createDecreaseButton(int orientation) {
				JButton btn = super.createDecreaseButton(orientation);
				btn.setBackground(new Color(20,20,20));
				btn.setBorder(border);
				return btn;
			}
		};		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(select)) {
			
			HashMap<Integer, String> spritesheets = new HashMap<Integer, String>();
			for (int i = 0; i < checkboxes.size(); i++) {
				JCheckBox box = checkboxes.get(i);
				if(box.isSelected()) {
					spritesheets.put(Integer.parseInt(box.getName()), box.getText());
				}
			}
			
			if(spritesheets.size()>0) {
				owner.spritesheets =  new HashMap<Integer, String>(spritesheets);
				owner.requestFocus();
				owner.draw();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null,"ERROR: nothing was selected.","ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	//MAIN
	public static void main(String[] args) {
		new TilesetSelectMenu(new MainWindow(new LightMap(20, 20)));
	}

	

}
