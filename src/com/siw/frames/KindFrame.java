package com.siw.frames;

import com.siw.query.SparqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KindFrame extends JFrame{

    static JList videoGamesJList;
    int KIND = 0;

    public KindFrame(){
        JFrame frame = new JFrame("Genre de jeux vidéos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<String> videoGamesArrayList = SparqlQuery.getKindOfVideoGames();
        videoGamesJList = new JList(videoGamesArrayList.toArray());
        videoGamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        videoGamesJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    String filter = list.getSelectedValue().toString();
                    new VideoGamesByFrame("Jeux vidéos de " + filter, KIND, filter);
                }
            }
        });

        JScrollPane videoGamesScrollPane = new JScrollPane(videoGamesJList);
        Container contentPane = frame.getContentPane();
        contentPane.add(videoGamesScrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
