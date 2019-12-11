package _GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import beans.LightMap;
import beans.Sprite;
import beans.Spritesheet;

public class MainWindow extends JFrame implements ActionListener {

	//attibutes 
	final int W=1000,H=640;
	final int TW,TH;
	LightMap map;
	
	BasicScrollBarUI UI, UI2 ,UI3,UI4;
	JPanel map_panel,settings,grid,space,tileset_map,tileset_space,tileset_grid,buttonBar;
	JScrollPane mapscroll,tilesetscroll;
	ButtonGroup spritegroup,buttonBarGroup;
	JButton mode, save,copy,erase,all;
	JComboBox<String> tilesets;
	JLabel actualSpriteLbl;
	DefaultComboBoxModel<String> tileset_model;
	
	Color dark,medium,light;
	Container window;
	Border border,lightborder;
	Cursor pointer;
    Spritesheet sheet;
	Icon actualSprite;
	int state;
	boolean eraseMode;
	
	public HashMap<Integer, String> spritesheets;
	
	//CONSTRUCTOR 
	public MainWindow(LightMap map) {
		
		this.map = map;
		TH = map.getHeight();
		TW = map.getWidth();
		
		state=0;
		dark = new Color(30,30,30);
		light = new Color(50,50,50);
		medium = new Color(40,40,40);
		border = BorderFactory.createLineBorder(dark);
		lightborder = BorderFactory.createLineBorder(light);
		pointer = new Cursor(12);
		
		setTitle("LightTile v0.1");
		setSize(W, H);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	//DRAW THE WINDOW
	public void draw(){		
		window = getContentPane();
		createButtonBar();
		createUI();
		createMap();
		createSettings();
		settings.setBackground(dark);
		settings.setPreferredSize(new Dimension(300,600));
		window.setBackground(medium);
		window.add(settings,BorderLayout.EAST);
		setVisible(true);
		revalidate();
		repaint();
		
	}
	
	//CREATE BUTTONBAR
	private void createButtonBar() {
		buttonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonBar.setBackground(dark);
		buttonBarGroup = new ButtonGroup();
		
		actualSpriteLbl = new JLabel();
		actualSpriteLbl.setBorder(lightborder);
		actualSpriteLbl.setPreferredSize(new Dimension(32,32));
		
		erase = new JButton("E");
		erase.setBackground(new Color(60,60,60));
		erase.setForeground(Color.white);
		erase.setBorder(lightborder);
		erase.setCursor(pointer);
		erase.setFont(erase.getFont().deriveFont(12f));
		erase.setPreferredSize(new Dimension(50,30));
		erase.setFocusable(false);
		erase.setName("EraseMode");
		erase.addActionListener(this);
		
		copy = new JButton("C");
		copy.setBackground(new Color(60,60,60));
		copy.setForeground(Color.white);
		copy.setBorder(lightborder);
		copy.setCursor(pointer);
		copy.setFont(copy.getFont().deriveFont(12f));
		copy.setPreferredSize(new Dimension(50,30));
		copy.setFocusable(false);
		copy.setName("copyMode");
		copy.addActionListener(this);
		
		all = new JButton("A");
		all.setBackground(new Color(60,60,60));
		all.setForeground(Color.white);
		all.setBorder(lightborder);
		all.setCursor(pointer);
		all.setFont(all.getFont().deriveFont(12f));
		all.setPreferredSize(new Dimension(50,30));
		all.setFocusable(false);
		all.setName("allMode");
		all.addActionListener(this);
		
		
		buttonBarGroup.add(erase);
		buttonBarGroup.add(copy);
		buttonBarGroup.add(all);
		
		buttonBar.add(actualSpriteLbl);
		buttonBar.add(erase);
		buttonBar.add(copy);
		buttonBar.add(all);
		window.add(buttonBar,BorderLayout.NORTH);
		
	}

	//CREATE SETTINGS
	private void createSettings() {
		settings = new JPanel();
		settings.setLayout(new FlowLayout(FlowLayout.LEADING, 20,20));
		
		mode = new JButton("COLLISIONS");
		mode.setBackground(new Color(60,60,60));
		mode.setForeground(Color.white);
		mode.setBorder(lightborder);
		mode.setCursor(pointer);
		mode.setFont(mode.getFont().deriveFont(18f));
		mode.setPreferredSize(new Dimension(260,60));
		mode.setFocusable(false);
		
		tileset_model = new DefaultComboBoxModel<String>();
		tilesets = new JComboBox<String>(tileset_model);
		tilesets.setForeground(light);
		tilesets.setFont(tilesets.getFont().deriveFont(16f));
		tilesets.setBorder(null);
		tilesets.setBackground(new Color(240,240,240));
		tilesets.setPreferredSize(new Dimension(260,30));
		
		tileset_map = new JPanel();
		tileset_map.setBackground(light);
		tileset_map.setPreferredSize(new Dimension(260,300));
		
		Object[] array = spritesheets.keySet().toArray();
		for (int j = 0; j < array.length; j++) {
			String str = spritesheets.get(array[j]);
			tileset_model.addElement(str);
		}			
		
			this.sheet = new Spritesheet("res/tileset/"+tilesets.getSelectedItem().toString(), 32, false);
			int numberOfTiles=(sheet.getWidth_in_sprites()*sheet.getHeight_in_sprites());
			tilesets.addActionListener(this);
		
			tileset_grid = new JPanel(new GridLayout(0,sheet.getWidth_in_sprites()));
			tileset_grid.setBackground(dark);
		
			spritegroup = new ButtonGroup();
		for (int i = 0; i < numberOfTiles; i++) {
			JButton btn = new JButton();
			btn.setBackground(medium);
			btn.setName("T"+i);
			btn.setIcon(new ImageIcon(sheet.getSprite(i).getImage()));
			btn.setPreferredSize(new Dimension(32,32));
			btn.addActionListener(this);
			btn.setBorder(border);
			spritegroup.add(btn);
			tileset_grid.add(btn);
		}
	
		tileset_space = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tileset_space.setBackground(dark);
			
		tilesetscroll = new JScrollPane(tileset_space);
		tilesetscroll.setPreferredSize(new Dimension(250,290));
		tilesetscroll.setBackground(dark);
		tilesetscroll.getVerticalScrollBar().setBackground(dark);
		tilesetscroll.getVerticalScrollBar().setUI(UI3);
		tilesetscroll.getHorizontalScrollBar().setBackground(dark);
		tilesetscroll.getHorizontalScrollBar().setUI(UI4);
		tilesetscroll.setBorder(null);
			
		tileset_space.add(tileset_grid);
		tileset_map.add(tilesetscroll,BorderLayout.CENTER);
		
		save = new JButton("SAVE");
		save.setBackground(new Color(60,60,60));
		save.setForeground(Color.white);
		save.setBorder(lightborder);
		save.setCursor(pointer);
		save.setFont(mode.getFont().deriveFont(18f));
		save.setPreferredSize(new Dimension(260,60));
		save.setFocusable(false);
		
		settings.add(mode);
		settings.add(tilesets);
		settings.add(tileset_map);
		settings.add(save);
	}

	//CREATE THE MAP
	private void createMap() {
		
		//create panels
		map_panel = new JPanel();
		space = new JPanel();
		grid = new JPanel();
		
		//crate scrollbar
		mapscroll =  new JScrollPane(space);
		mapscroll.setPreferredSize(new Dimension(680,550));
		mapscroll.getHorizontalScrollBar().setBackground(medium);
		mapscroll.getHorizontalScrollBar().setBorder(border);
		mapscroll.getHorizontalScrollBar().setUI(UI);
		mapscroll.getVerticalScrollBar().setBackground(medium);
		mapscroll.getVerticalScrollBar().setBorder(border);
		mapscroll.getVerticalScrollBar().setUI(UI2);	
	
		space.setLayout(new FlowLayout(FlowLayout.CENTER));
		grid.setLayout(new GridLayout(TH,TW));
		

		
		for (int j = 0; j < TW*TH; j++) {
			JButton btn = new JButton();
			btn.setBackground(medium);
			btn.setName("M"+j);
			btn.setPreferredSize(new Dimension(32,32));
			btn.setBorder(border);
			btn.addActionListener(this);
			grid.add(btn);
		}
			
		map_panel.setBorder(border);
		mapscroll.setBorder(border);
		
		space.add(grid);
		map_panel.add(mapscroll,BorderLayout.CENTER);
		
		map_panel.setBackground(medium);
		mapscroll.setBackground(medium);
		space.setBackground(medium);
		grid.setBackground(medium);
		map_panel.setPreferredSize(new Dimension(500,599));
		
		window.add(map_panel,BorderLayout.CENTER);
	}

	//CREATE UI
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
		UI2 = new BasicScrollBarUI() {	
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
		UI3 = new BasicScrollBarUI() {	
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
		UI4 = new BasicScrollBarUI() {	
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

	//USER INTERACTIONS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(tilesets)) changeTileset(e);

		try {
				JButton btn = (JButton) e.getSource();
				if(btn.getName().charAt(0) == 'M' && !eraseMode) state = 0;
				if(btn.getName().charAt(0) == 'T' && !eraseMode) 	state = 1;
				
				if(btn.equals(erase)) {
					eraseMode = !eraseMode;
					if(eraseMode) {
						btn.setBackground(new Color(40,40,40));
						btn.setBorder(null);
					}else {
						btn.setBackground(new Color(60,60,60));
						btn.setBorder(border);
					}
					state = 2;
				}
				
				switch (state) {
				case 0: // paint
					if(btn.getName().substring(0,1).equals("M")) btn.setIcon(actualSprite);
					break;
				case 1: // getSprite
					if(btn.getName().substring(0,1).equals("T")) actualSprite = btn.getIcon();
					actualSpriteLbl.setIcon(actualSprite);
					break;
				case 2:
					if(btn.getName().substring(0,1).equals("M"))btn.setIcon(new ImageIcon());
					break;					
				default:
					System.out.println("Other!!");
				}	
		}catch (Exception ex) {}
	}	

	//CHANGE TILESET
	private void changeTileset(ActionEvent e) {
		
		tileset_map.removeAll();
		tileset_space.removeAll();
		tileset_grid.removeAll();
		settings.removeAll();
		
		sheet = new Spritesheet("res/tileset/"+tilesets.getSelectedItem().toString(), 32, false);
		int numberOfTiles=(sheet.getWidth_in_sprites()*sheet.getHeight_in_sprites());
		
		tileset_grid = new JPanel(new GridLayout(0,sheet.getWidth_in_sprites()));
		tileset_grid.setBackground(dark);
		
		
		for (int i = 0; i < numberOfTiles; i++) {
			JButton btn = new JButton();
			btn.setBackground(medium);
			btn.setName("T"+i);
			btn.setIcon(new ImageIcon(sheet.getSprite(i).getImage()));
			btn.setPreferredSize(new Dimension(32,32));
			btn.setBorder(border);
			btn.addActionListener(this);
			spritegroup.add(btn);
			tileset_grid.add(btn);
		}
				
		tileset_space.add(tileset_grid);
		tileset_map.add(tilesetscroll,BorderLayout.CENTER);
		
		settings.add(mode);
		settings.add(tilesets);
		settings.add(tileset_map);
		settings.add(save);
		
		revalidate();
		repaint();
	}


}
