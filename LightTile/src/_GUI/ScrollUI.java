package _GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollUI {

	private BasicScrollBarUI ui;
	
	public ScrollUI(MainWindow window) {
		
		ui = new BasicScrollBarUI() {	
			@Override protected void configureScrollBarColors(){this.thumbColor = new Color(20,20,20);}
			@Override protected JButton createIncreaseButton(int orientation) {
				JButton btn = super.createIncreaseButton(orientation);
				btn.setBackground(new Color(20,20,20));
				btn.setBorder(window.getBorder());
				return btn;
			}
			@Override protected JButton createDecreaseButton(int orientation) {
				JButton btn = super.createDecreaseButton(orientation);
				btn.setBackground(new Color(20,20,20));
				btn.setBorder(window.getBorder());
				return btn;
			}
		};
	}

	
	//GETTERS && SETTERS

	public BasicScrollBarUI getUi() {return ui;}
	
	
}
