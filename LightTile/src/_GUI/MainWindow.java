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
	private final int W=1000,H=640;
	private final int TW,TH;
	private LightMap map;
	private Buttonbar buttonbar;
	private ScrollUI scrollui_1,scrollui_2,scrollui_3,scrollui_4;
	
	
	private JPanel map_panel,settings,grid,space,tileset_map,tileset_space,tileset_grid,buttonBar;
	private JScrollPane mapscroll,tilesetscroll;
	private ButtonGroup spritegroup;
	private JButton mode,save;
	private JComboBox<String> tilesets;
	private DefaultComboBoxModel<String> tileset_model;
	
	private Color dark,medium,light;
	private Container window;
	private Border border,lightborder;
	private Cursor pointer;
    private Spritesheet sheet;
	private Icon actualSprite;
	private boolean eraseMode,gridmode;
	
	public HashMap<Integer, String> spritesheets;
	
	//CONSTRUCTOR 
	public MainWindow(LightMap map) {
		
		this.map = map;
		TH = map.getHeight();
		TW = map.getWidth();
		
		gridmode = false;
		eraseMode = false;
		
		dark = new Color(30,30,30);
		light = new Color(50,50,50);
		medium = new Color(40,40,40);
		border = BorderFactory.createLineBorder(dark);
		lightborder = BorderFactory.createLineBorder(light);
		pointer = new Cursor(12);
		
		setTitle("LightTile v0.2");
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
		buttonbar = new Buttonbar(this);
	}

	//CREATE SETTINGS
	private void createSettings() {
		settings = new JPanel();
		settings.setLayout(new FlowLayout(FlowLayout.LEADING, 20,20));
		settings.setBackground(medium);
		
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
		tilesetscroll.getVerticalScrollBar().setUI(scrollui_3.getUi());
		tilesetscroll.getHorizontalScrollBar().setBackground(dark);
		tilesetscroll.getHorizontalScrollBar().setUI(scrollui_4.getUi());
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
		mapscroll.setBorder(null);
		mapscroll.setPreferredSize(new Dimension(680,550));
		mapscroll.getHorizontalScrollBar().setBackground(medium);
		mapscroll.getHorizontalScrollBar().setBorder(border);
		mapscroll.getHorizontalScrollBar().setUI(scrollui_1.getUi());
		mapscroll.getVerticalScrollBar().setBackground(medium);
		mapscroll.getVerticalScrollBar().setBorder(border);
		mapscroll.getVerticalScrollBar().setUI(scrollui_2.getUi());	
	
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
		  
		scrollui_1 = new ScrollUI(this);
		scrollui_2 = new ScrollUI(this);
		scrollui_3 = new ScrollUI(this);
		scrollui_4 = new ScrollUI(this);
		
	}

	//USER INTERACTIONS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(tilesets)) changeTileset(e);
		
		JButton btn = null;
		if(e.getSource() instanceof JButton) {
			btn = (JButton)e.getSource();
			String name = btn.getName();
			
			switch (name.charAt(0)) {
			case 'T':
				if(!eraseMode){
					
					actualSprite = btn.getIcon();
					buttonbar.getActualSpriteLbl().setIcon(actualSprite);
					
				}
				break;
			case 'M':
				
				if(eraseMode) btn.setIcon(new ImageIcon());
				else btn.setIcon(actualSprite);
				break;
			case 'E':
				eraseMode = !eraseMode;
				if(eraseMode) {
					
					btn.setBackground(dark);
					btn.setBorder(null);
					
				}else {
					
					btn.setBackground(light);
					btn.setBorder(lightborder);
					
				}				
				
				break;
			case 'G':
				gridmode = !gridmode;
				if(gridmode) {
					
					btn.setBackground(dark);
					btn.setBorder(null);
					for (int i = 0; i < TW*TH; i++) ((JComponent) grid.getComponent(i)).setBorder(null);
					
				}else {
					
					btn.setBackground(light);
					btn.setBorder(lightborder);
					for (int i = 0; i < TW*TH; i++) ((JComponent) grid.getComponent(i)).setBorder(border);
					
				}
				
			break;
			}
			
			System.out.println(name);	
		}	
	}
	
	//switch off all the modes except one
	private void turnOffModesExcept() {
		//array of modes []
		
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

	//GETTERS && SETTERS
	public Color getDark() {return dark;}
	public Color getLight() {return light;}
	public Border getBorder() {return border;}
	public Border getLightborder() {return lightborder;}
	public Icon getActualSprite() {return actualSprite;}
	public Cursor getPointer() {return pointer;}
	public Container getWindow() {return window;}


}
