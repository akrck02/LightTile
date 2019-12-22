package _GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Buttonbar extends JPanel {

	private JLabel actualSpriteLbl;
	private ButtonGroup buttonBarGroup;
	private JButton copy,erase,all,margin;
	private Border lightborder;
	private Cursor pointer;
	
	
	public Buttonbar(MainWindow window) {
		
		this.lightborder = window.getLightborder();
		this.pointer = window.getPointer();
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(25,25,25));
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
		erase.setPreferredSize(new Dimension(30,30));
		erase.setFocusable(false);
		erase.setName("E");
		erase.addActionListener(window);
		
		copy = new JButton("C");
		copy.setBackground(new Color(60,60,60));
		copy.setForeground(Color.white);
		copy.setBorder(lightborder);
		copy.setCursor(pointer);
		copy.setFont(copy.getFont().deriveFont(12f));
		copy.setPreferredSize(new Dimension(30,30));
		copy.setFocusable(false);
		copy.setName("C");
		copy.addActionListener(window);
		
		all = new JButton("A");
		all.setBackground(new Color(60,60,60));
		all.setForeground(Color.white);
		all.setBorder(lightborder);
		all.setCursor(pointer);
		all.setFont(all.getFont().deriveFont(12f));
		all.setPreferredSize(new Dimension(30,30));
		all.setFocusable(false);
		all.setName("A");
		all.addActionListener(window);
		
		margin = new JButton("G");
		margin.setBackground(new Color(60,60,60));
		margin.setForeground(Color.white);
		margin.setBorder(lightborder);
		margin.setCursor(pointer);
		margin.setFont(margin.getFont().deriveFont(12f));
		margin.setPreferredSize(new Dimension(30,30));
		margin.setFocusable(false);
		margin.setName("G");
		margin.addActionListener(window);
		
		
		buttonBarGroup.add(erase);
		buttonBarGroup.add(copy);
		buttonBarGroup.add(all);
		
		this.add(actualSpriteLbl);
		this.add(erase);
		this.add(copy);
		this.add(all);
		this.add(margin);
		window.getWindow().add(this,BorderLayout.NORTH);
	}


	
	//GETTERS && SETTERS

	public JLabel getActualSpriteLbl() {return actualSpriteLbl;}
	public void setActualSpriteLbl(JLabel actualSpriteLbl) {this.actualSpriteLbl = actualSpriteLbl;}
	
}
