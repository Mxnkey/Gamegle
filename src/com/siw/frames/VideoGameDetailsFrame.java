package com.siw.frames;

import com.siw.MainFrame;
import com.siw.model.VideoGame;

import javax.swing.*;
import java.awt.*;

public class VideoGameDetailsFrame {
    private JLabel videoGameName;
    private JLabel videoGameKind;
    private JLabel videoGamePlatforms;
    private JLabel videoGameEditor;
    private JLabel videoGameNameResult;
    private JLabel videoGameKindResult;
    private JLabel videoGamePlatformsResult;
    private JLabel videoGameEditorResult;
    private JPanel detailsPanel;

    public VideoGameDetailsFrame(String videoGameNameString, VideoGame videoGame){

        JFrame frame = new JFrame(videoGameNameString);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        videoGameNameResult.setText(videoGame.getName());
        videoGamePlatformsResult.setText(videoGame.getPlatforms().toString());
        videoGameEditorResult.setText(videoGame.getEditor());
        videoGameKindResult.setText(videoGame.getKind());

        frame.setContentPane(detailsPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
