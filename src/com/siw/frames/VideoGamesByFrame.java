package com.siw.frames;

import com.siw.model.VideoGame;
import com.siw.query.SparqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VideoGamesByFrame extends JFrame{
    static JList videoGamesJList;
    static String videoGameName;
    static ArrayList<String> videoGamesArrayList;
    int KIND = 0;
    int PLATFORM = 1;
    int EDITOR = 2;

    public VideoGamesByFrame(String frameName, int type, String filter){
        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if(type == KIND){
            videoGamesArrayList = SparqlQuery.getVideoGamesByKind(filter);
        }
        else if(type == PLATFORM){
            videoGamesArrayList = SparqlQuery.getVideoGamesByPlatform(filter);
        }
        else if(type == EDITOR){
            videoGamesArrayList = SparqlQuery.getVideoGamesByEditors(filter);
        }
        else{
            System.out.println("UNKOWN TYPE");
        }

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
