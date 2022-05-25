package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.gui.swing.view.sloteditor.ImageSlotEditor;
import dsw.rudok.app.gui.swing.view.sloteditor.TextSlotEditor;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter

public class SelectState extends State{
    private PageView mediator;

    public SelectState(PageView mediator) {
        this.mediator = mediator;
    }

    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();

        mediator.getSelectionRectangle().setSize(0, 0); //selection pravougaonik od prosle selekcije ponistimo
        for(Slot slot: mediator.getPage().getSelectedSlots()){//brisemo sve stare selektovane zelene boje
            slot.setIsGreen(false);
        }
        mediator.repaint();
        mediator.getPage().getSelectedSlots().clear(); //cistimo listu starih selektovanih slotova

        if (e.getButton()==MouseEvent.BUTTON1){
            if(mediator.getSlotIndexAtPosition(position)!=-1){
                for(SlotPainter sp: mediator.getSlotPainters()) {
                    if(sp.isElementAt(position)){
                        mediator.getPage().getSelectedSlots().add(sp.getSlot());//solo selektovani se dodaje u listu
                        sp.getSlot().setIsGreen(true);//solo selektovani se pretvara u zeleno
                        mediator.repaint();
                    }
                }
            }

            if (e.getClickCount() == 2){ //Ovo je za manipulaciju sadrzaja slota
                if(mediator.getSlotIndexAtPosition(position)!=-1){
                    for(SlotPainter sp: mediator.getSlotPainters()) {
                        if(sp.isElementAt(position)){
                            if(sp.getSlot().getSlotType() == null) {
                                JFrame fr1 = new JFrame();
                                String[] answers = {"Tekst", "Slika"};
                                String answer = (String) JOptionPane.showInputDialog(fr1, "Izaberite sadrzaj slota:", "Slot type picker", JOptionPane.DEFAULT_OPTION, null, answers, "0");

                                if (answer == "Tekst") {//OVO JE PRVI PUT EDIT SLOT
                                    sp.getSlot().setSlotType(SlotType.TEXT);
                                    TextSlotEditor textSlotEditor = new TextSlotEditor(sp.getSlot());
                                    textSlotEditor.setVisible(true);
                                } else if (answer == "Slika") {
                                    sp.getSlot().setSlotType(SlotType.IMAGE);
                                    ImageSlotEditor imageSlotEditor = new ImageSlotEditor(sp.getSlot());
                                    imageSlotEditor.setVisible(true);
                                }
                                else {
                                    fr1.dispose();
                                }
                                return;
                            }
                            else {
                                JFrame f = new JFrame();
                                int a = JOptionPane.showConfirmDialog(f, "Da li zelite odabranom slotu promeniti tip?");

                                if(a == JOptionPane.YES_OPTION){//OVO JE PROMENA SLOTA IZ JEDNOG U DRUGI
                                    sp.getSlot().setSlotType(null);
                                    JFrame fr2 = new JFrame();
                                    String[] answers = {"Tekst", "Slika"};
                                    String answer = (String) JOptionPane.showInputDialog(fr2, "Izaberite sadrzaj slota:", "Slot type picker", JOptionPane.DEFAULT_OPTION, null, answers, "0");
                                    sp.getSlot().setSlotFile(null);

                                    if (answer == "Tekst") {
                                        sp.getSlot().setSlotType(SlotType.TEXT);
                                        TextSlotEditor textSlotEditor = new TextSlotEditor(sp.getSlot());
                                        textSlotEditor.setVisible(true);
                                    } else if (answer == "Slika"){
                                        sp.getSlot().setSlotType(SlotType.IMAGE);
                                        ImageSlotEditor imageSlotEditor = new ImageSlotEditor(sp.getSlot());
                                        imageSlotEditor.setVisible(true);
                                    }
                                    else {
                                        fr2.dispose();
                                    }
                                }
                                else if (a == JOptionPane.NO_OPTION && sp.getSlot().getSlotType() == SlotType.TEXT) {//OVO JE OSTAJANJE NA STAROM SLOT TYPE
                                    TextSlotEditor textSlotEditor = new TextSlotEditor(sp.getSlot());
                                    textSlotEditor.setVisible(true);
                                }
                                else if (a == JOptionPane.NO_OPTION && sp.getSlot().getSlotType() == SlotType.IMAGE) {
                                    ImageSlotEditor imageSlotEditor = new ImageSlotEditor(sp.getSlot());
                                    imageSlotEditor.setVisible(true);
                                }
                                else if (a == JOptionPane.CANCEL_OPTION) {
                                    f.dispose();
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
        mediator.setLastPosition(position);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(RuNode slot: mediator.getPage().getChildren()){
            if(mediator.getSelectionRectangle().intersects(((Slot)slot).getPosition().getX(), ((Slot)slot).getPosition().getY(), //intersekcija pravougaonika za selekovanje i slotova
                                                            ((Slot)slot).getSize().width, ((Slot)slot).getSize().height)){
                mediator.getPage().getSelectedSlots().add((Slot)slot);//selektovani se dodaju u listu selektovanih
                System.out.println(mediator.getPage().getSelectedSlots());
            }
        }
        for(Slot slot: mediator.getPage().getSelectedSlots()){//selektovanje stavljamo u zeleno
            slot.setIsGreen(true);
        }
        mediator.getRectangleDimension().setSize(0,0);//selection rectangle smanjujemo da se ne vidi kada se pusti mis
        mediator.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double selectionRectangleWidth = e.getX() - mediator.getLastPosition().getX();
        double selectionRectangleHeight = e.getY() - mediator.getLastPosition().getY();
        double absSelectionRectangleHeight;
        double absSelectionRectangleWidth;
        double topLeftX;
        double topLeftY;

        if (selectionRectangleHeight >= 0 && selectionRectangleWidth >= 0){//pomeraj dole desno
            topLeftX = mediator.getLastPosition().getX();//racunamo gornju levu kordinatu za selekciju
            topLeftY = mediator.getLastPosition().getY();
            absSelectionRectangleHeight = Math.abs(selectionRectangleHeight);//radimo absolutnu vrednost da bi ubili - pri oduzimanju
            absSelectionRectangleWidth = Math.abs(selectionRectangleWidth);

            mediator.getSelectionRectangle().setRect(topLeftX, topLeftY, absSelectionRectangleWidth,absSelectionRectangleHeight);//selekcija
            mediator.getRectanglePosition().setLocation(topLeftX,topLeftY);//dajemo vizuelnom pravougaoniku tacku za crtanje
            mediator.getRectangleDimension().setSize(absSelectionRectangleWidth,absSelectionRectangleHeight);//dajemo vizuelnom pravougaoniku W i H
            mediator.repaint();
        }
        else if(selectionRectangleHeight <= 0 && selectionRectangleWidth <= 0){//pomeraj gore levo
            topLeftX = e.getX();//racunamo gornju levu kordinatu za selekciju
            topLeftY = e.getY();
            absSelectionRectangleHeight = Math.abs(selectionRectangleHeight);//radimo absolutnu vrednost da bi ubili - pri oduzimanju
            absSelectionRectangleWidth = Math.abs(selectionRectangleWidth);

            mediator.getSelectionRectangle().setRect(topLeftX, topLeftY, absSelectionRectangleWidth,absSelectionRectangleHeight);//selekcija
            mediator.getRectanglePosition().setLocation(topLeftX,topLeftY);//dajemo vizuelnom pravougaoniku tacku za crtanje
            mediator.getRectangleDimension().setSize(absSelectionRectangleWidth,absSelectionRectangleHeight);//dajemo vizuelnom pravougaoniku W i H
            mediator.repaint();
        }
        else if(selectionRectangleHeight <= 0 && selectionRectangleWidth >= 0){//pomeraj gore desno
            topLeftX = mediator.getLastPosition().getX();//racunamo gornju levu kordinatu za selekciju
            topLeftY = e.getY();
            absSelectionRectangleHeight = Math.abs(selectionRectangleHeight);//radimo absolutnu vrednost da bi ubili - pri oduzimanju
            absSelectionRectangleWidth = Math.abs(selectionRectangleWidth);

            mediator.getSelectionRectangle().setRect(topLeftX, topLeftY, absSelectionRectangleWidth,absSelectionRectangleHeight);//selekcija
            mediator.getRectanglePosition().setLocation(topLeftX,topLeftY);//dajemo vizuelnom pravougaoniku tacku za crtanje
            mediator.getRectangleDimension().setSize(absSelectionRectangleWidth,absSelectionRectangleHeight);//dajemo vizuelnom pravougaoniku W i H
            mediator.repaint();
        }
        else if(selectionRectangleHeight >= 0 && selectionRectangleWidth <= 0){//opmeraj dole levo
            topLeftX = e.getX();//racunamo gornju levu kordinatu za selekciju
            topLeftY = mediator.getLastPosition().getY();
            absSelectionRectangleHeight = Math.abs(selectionRectangleHeight);//radimo absolutnu vrednost da bi ubili - pri oduzimanju
            absSelectionRectangleWidth = Math.abs(selectionRectangleWidth);

            mediator.getSelectionRectangle().setRect(topLeftX, topLeftY, absSelectionRectangleWidth,absSelectionRectangleHeight);//selekcija
            mediator.getRectanglePosition().setLocation(topLeftX,topLeftY);//dajemo vizuelnom pravougaoniku tacku za crtanje
            mediator.getRectangleDimension().setSize(absSelectionRectangleWidth,absSelectionRectangleHeight);//dajemo vizuelnom pravougaoniku W i H
            mediator.repaint();
        }
        mediator.getPage().getSelectedSlots().clear();
    }
}
