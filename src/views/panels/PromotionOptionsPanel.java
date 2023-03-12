package views.panels;

import boardelements.pieces.Pawn;
import boardelements.pieces.PieceType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PromotionOptionsPanel extends JPanel{

    public PromotionOptionsPanel(){
        super();
        ButtonGroup group = new ButtonGroup();
        JRadioButton queenPromotion = new JRadioButton("Queen");
        queenPromotion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(queenPromotion.isSelected()){
                    Pawn.setPromotionPieceType(PieceType.QUEEN);
                }
            }
        });
        queenPromotion.setSelected(true);

        JRadioButton knightPromotion = new JRadioButton("Knight");
        knightPromotion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(knightPromotion.isSelected()){
                    Pawn.setPromotionPieceType(PieceType.KNIGHT);
                }
            }
        });
        JRadioButton bishopPromotion = new JRadioButton("Bishop");
        bishopPromotion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(bishopPromotion.isSelected()){
                    Pawn.setPromotionPieceType(PieceType.BISHOP);
                }
            }
        });
        JRadioButton rookPromotion = new JRadioButton("Rook");
        rookPromotion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(rookPromotion.isSelected()){
                    Pawn.setPromotionPieceType(PieceType.ROOK);
                }
            }
        });
        group.add(queenPromotion);
        group.add(knightPromotion);
        group.add(bishopPromotion);
        group.add(rookPromotion);



        JLabel label = new JLabel("Promote To");
        this.add(label);
        this.add(queenPromotion);
        this.add(knightPromotion);
        this.add(bishopPromotion);
        this.add(rookPromotion);

    }



}
