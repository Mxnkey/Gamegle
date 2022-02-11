package com.siw.frames;

import com.siw.model.VideoGame;
import com.siw.query.SparqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AllVideoGamesFrame extends JFrame {

    static JList videoGamesJList;

    public AllVideoGamesFrame(){
        JFrame frame = new JFrame("All Video Games");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<String> videoGamesArrayList = SparqlQuery.getAllVideoGames();
        videoGamesJList = new JList(videoGamesArrayList.toArray());
        videoGamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        videoGamesJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    String selectedVideoGameName = list.getSelectedValue().toString();
                    VideoGame selectedVideoGame = SparqlQuery.getVideoGameDetails(selectedVideoGameName);
                    new VideoGameDetailsFrame(selectedVideoGameName, selectedVideoGame);

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
