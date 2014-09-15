package bc.diffusion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

public class DiffusionGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Grids grids;
	private JSplitPane splitH, splitVU, splitVV;
	private ChemicalPanel uPanel, vPanel;
	private Plotter uPlotter, vPlotter;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DiffusionGUI () {
		this.setTitle("Chemical Diffusion CA Model");
		this.setBackground(Color.black);
	    
	    // set bottom panel
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout( new BorderLayout() );
	    getContentPane().add( topPanel );
	    
	    screenSize.width = 600;
	    screenSize.height = 600;
	    
	    int panelWidth = screenSize.width / 2;
	    int panelHeight = screenSize.height / 2;
	    
	    createField(panelWidth, panelHeight);
	    setFrameComponents(topPanel, panelWidth, panelHeight);
	    
	    timer = new Timer(45, this);
	    timer.start();
	}
	
	public void createField(int panelWidth, int panelHeight) {
		grids = new Grids(panelWidth, panelHeight); 
	    
	    // set diffusion panels
	    uPanel = new UPanel(grids, panelWidth, panelHeight);
	    vPanel = new VPanel(grids, panelWidth, panelHeight);
	    
	    uPanel.add(new JLabel("Diffusion of Chemical U"), BorderLayout.NORTH);
	    vPanel.add(new JLabel("Diffusion of Chemical V"), BorderLayout.NORTH);
	    
	    uPlotter = new UPlotter(grids, panelWidth, panelHeight);
	    vPlotter = new VPlotter(grids, panelWidth, panelHeight);
	    
	    uPlotter.add(new JLabel("Diffusion Plot of Chemical U"), BorderLayout.NORTH);
	    vPlotter.add(new JLabel("Diffusion Plot of Chemical V"), BorderLayout.NORTH);
	}
	
	public void setFrameComponents(JPanel top, int panelWidth, int panelHeight) {
	    splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    splitH.setDividerLocation(panelWidth);
	    top.add(splitH);
	    
	    splitVU = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    splitVU.setDividerLocation(panelHeight);
	    splitVV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	    splitVV.setDividerLocation(panelHeight);
	    
	    splitVU.setLeftComponent(uPanel);
	    splitVU.setRightComponent(uPlotter);
	    
	    splitVV.setLeftComponent(vPanel);
	    splitVV.setRightComponent(vPlotter);
	    
	    splitH.setLeftComponent( splitVU );
	    splitH.setRightComponent( splitVV );
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	public void createAndShowGUI() {
		// Set up the content pane.
		this.setPreferredSize(screenSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}


}
