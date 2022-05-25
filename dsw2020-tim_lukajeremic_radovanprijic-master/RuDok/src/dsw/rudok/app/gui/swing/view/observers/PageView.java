package dsw.rudok.app.gui.swing.view.observers;

import dsw.rudok.app.command.CommandManager;
import dsw.rudok.app.gui.swing.state.StateManager;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.gui.swing.view.painters.CirclePainter;
import dsw.rudok.app.gui.swing.view.painters.RectanglePainter;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.gui.swing.view.painters.TrianglePainter;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotCircle;
import dsw.rudok.app.repository.slots.SlotRectangle;
import dsw.rudok.app.repository.slots.SlotTriangle;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;


@Getter
@Setter

public class PageView extends JInternalFrame implements ISubscriber {
    private Page page;
    private JLabel label;
    private String name;
    private ArrayList<SlotPainter> slotPainters = new ArrayList<>();

    private JPanel framework;
    static final int xOffset = 30, yOffset = 30;
    int openFrameCount = 0;

    private Point2D lastPosition = null; //pozicija naseg Lasso pravouganoika (potrebna nam je u selectState klasi)
    private Rectangle selectionRectangle; //nas Lasso pravougaonik
    private Point rectanglePosition; //pozicija koju koristimo za crtanje selekcionog pravougaonika u paint metodi
    private Dimension rectangleDimension; //dimenzije koje koristimo za crtanje selekcionog pravougaonika u paint metodi

    private CommandManager commandManager = new CommandManager();//cuva sve promene za undo i redo
    private StateManager stateManager = new StateManager(this);
    //ovo je klasa koja drzi informaciju koji state imamo, i prosledjujemo ga dole komandi koja pokrene
    //mousePressed comandu od svakog state, i oni urade nesto
    public void startCircleState() {//ove metode menjaju ovo gore drzalac statea, IZ DUGMICA SE AKTIVIRAJU
        stateManager.setCircleState();
    }
    public void startSelectState() {
        stateManager.setSelectState();
    }
    public void startTriangleState(){
        stateManager.setTriangleState();
    }
    public void startRectangleState(){
        stateManager.setRectangleState();
    }
    public void startRotateState(){
        stateManager.setRotateState();
    }
    public void startDeleteState(){
        stateManager.setDeleteState();
    }
    public void startScaleState(){
        stateManager.setScaleState();
    }
    public void startMoveState(){
        stateManager.setMoveState();
    }
    public StateManager getStateManager() {
        return stateManager;
    }

    //label sa putanjom
    public PageView(String path, Page page) {
        super("" ,
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        this.page = page;
        page.addSubscriber(this);
        this.name = page.getName();
        label = new JLabel(path);
        // add(label);
        setTitle(name);
        MainFrame.getInstance().setOpenFrameCounter(MainFrame.getInstance().getOpenFrameCounter() + 1); //globalni counter je potreban da ne bi prozori klizili pri primeni projekata
        openFrameCount = MainFrame.getInstance().getOpenFrameCounter();

        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        setIconifiable(true);
        setClosable(true);
        setSize(400,400);
        setVisible(true);

        framework=new Framework();
        framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
        framework.setBackground(Color.WHITE);
        getContentPane().add(framework,BorderLayout.CENTER);
        PageController c = new PageController();
        framework.addMouseListener(c);
        framework.addMouseMotionListener(c);
        framework.add(label); //za testiranje

        this.selectionRectangle = new Rectangle();
        this.rectanglePosition = new Point();
        this.rectangleDimension = new Dimension();

    }

    public void update(Object notification, ActionType action) {//uzmemo stari label/path i isecemo ga i zalepimo novo ime
        if(action == ActionType.SLOT_ADDITION) {
            slotPainters.removeAll(slotPainters);
            repaint();
        }
        else if(action == ActionType.SLOT_REMOVAL) {
            slotPainters.removeAll(slotPainters);
            repaint();
        }
        else if(action == ActionType.SLOT_MODIFICATION){
            slotPainters.removeAll(slotPainters); //mora da se obrisu svi postojeci painteri jer inace isSlotAt blokira dalje postavljanje
            repaint();
        }
        else if(action == ActionType.PAGE_RENAMING) {
            String oldLabel = label.getText();
            String newLabel;
            String splitPath[] = oldLabel.split("-");
            newLabel = splitPath[0] + "-" + splitPath[1] + "-" + ((Page) notification).getName();
            label.setText(newLabel);//stavimo novo ime
            this.name = ((Page) notification).getName(); // moramo da promenimo ime da bi mogli da izbrisemo tab ako je promenjeno ime
        }
    }

    private class PageController extends MouseAdapter implements MouseMotionListener {//uradi mouseEvent od state koji trenutno imamo selektovano
        public void mousePressed(MouseEvent e) {
            stateManager.getCurrentState().mousePressed(e);
        }
        public void mouseReleased(MouseEvent e) {
            stateManager.getCurrentState().mouseReleased(e);
        }
        public void mouseDragged(MouseEvent e ){
            stateManager.getCurrentState().mouseDragged(e);
        }
    }

    public int getSlotIndexAtPosition(Point point){//sluzi da bi detektovali da li postoji vec slot(slika) na poziciji na koju smo kliknuli
        for(int i = slotPainters.size()-1; i>=0; i--) {
            if(slotPainters.get(i).isElementAt(point)){
                return i;
            }
        }
        return -1;
    }

    private class Framework extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g.drawRect((int)rectanglePosition.getX(),(int)rectanglePosition.getY(),(int)rectangleDimension.getWidth(),(int)rectangleDimension.getHeight());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));//omogucava providnost elemenata prilikom njihovog preklapanja
            AffineTransform old = g2.getTransform();//koristi se za rotaciju

            ArrayList<RuNode> slots = new ArrayList<>(page.getChildren());
            for (RuNode s : slots) {
                if (s instanceof SlotRectangle) {
                    RectanglePainter painter = new RectanglePainter((Slot) s);
                    g2.rotate(((SlotRectangle) s).getAngle(), ((SlotRectangle) s).getRotationPoint().getX(), ((SlotRectangle) s).getRotationPoint().getY());
                    painter.paint(g2, (Slot) s);
                    g2.setTransform(old);
                    slotPainters.add(painter);
                }
                if (s instanceof SlotCircle) {
                    CirclePainter painter = new CirclePainter((Slot) s);
                    g2.rotate(((SlotCircle) s).getAngle(), ((SlotCircle) s).getRotationPoint().getX(), ((SlotCircle) s).getRotationPoint().getY());
                    painter.paint(g2, (Slot) s);
                    g2.setTransform(old);
                    slotPainters.add(painter);
                }
                if (s instanceof SlotTriangle) {
                    TrianglePainter painter = new TrianglePainter((Slot) s);
                    g2.rotate(((SlotTriangle) s).getAngle(), ((SlotTriangle) s).getRotationPoint().getX(), ((SlotTriangle) s).getRotationPoint().getY());
                    painter.paint(g2, (Slot) s);
                    g2.setTransform(old);
                    slotPainters.add(painter);
                }
            }
        }
    }
}
