package com.siw;

import com.siw.frames.*;
import com.siw.model.VideoGame;
import com.siw.query.SparqlQuery;
import org.apache.jena.base.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFrame extends JFrame{
    private JButton tousLesJeuxVidéosButton;
    private JPanel panelMain;
    private JButton jeuxVidéosParGenreButton;
    private JButton jeuxVidéosParPlateformeButton;
    private JButton jeuxVidéosParEditeurButton;
    private JTextField searchVideoGame;
    private JButton searchGameButton;


    public MainFrame() {
        tousLesJeuxVidéosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AllVideoGamesFrame().setLocationRelativeTo(null);
            }
        });
        jeuxVidéosParGenreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new KindFrame().setLocationRelativeTo(null);
            }
        });
        jeuxVidéosParPlateformeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new PlatformFrame().setLocationRelativeTo(null);
            }
        });
        jeuxVidéosParEditeurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new EditorFrame().setLocationRelativeTo(null);
            }
        });
        searchGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String formattedText = searchVideoGame.getText().replace(" ","_");
                String name = SparqlQuery.getVideoGamesByName(formattedText);

                System.out.println(formattedText);
                System.out.println(name);
                if(name != null){
                    VideoGame videoGame = SparqlQuery.getVideoGameDetails(name);
                    new VideoGameDetailsFrame(name, videoGame);
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gamegle");
        frame.setContentPane(new MainFrame().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
