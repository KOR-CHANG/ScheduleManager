package calender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class MemoReader extends JFrame {
    private JTextArea textArea;

    public MemoReader() {
       //프레임 생성
        setTitle("Memo Reader");
        setSize(500, 500);
       
        //텍스트 생성
        textArea = new JTextArea();
        //스크롤 생성
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        //새로고침 버튼생성하여 기능추가
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTextArea();
            }
        });
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        //폴더 주소 넣기
        String folderPath = "C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\"; // 출력할 폴더 경로를 지정하세요
        //폴더 객처 생성
        File folder = new File(folderPath);
        //폴더인지 파일인지 확인하여 폴더면 그 안에 있는 파일 읽기
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt"); // 특정 확장자를 가진 파일만 필터링하도록 설정합니다. 여기서는 .txt 파일을 필터링하였습니다.
                }
            });
            //폴더안에 있는 파일들을 순서대로 배열안에 넣기
            if (files != null) {
                
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    // 파일 이름을 순서대로 정렬
                    public int compare(File file1, File file2) {
                        return file1.getName().compareTo(file2.getName());
                    }
                });

                for (File file : files) {
                    if (file.isFile()) {
                        readAndDisplayFileContents(file);
                    }
                }
            }
        } else {
            textArea.setText("폴더를 찾을 수 없습니다.");
        }
        setVisible(true);
    }
    //택스트에 파일 이름과 내용 출력
    private void readAndDisplayFileContents(File file) {
       //파일 확장자를 제외한 이름 생성
       String filename=file.getName();
        String newfilname=filename.substring(0, filename.lastIndexOf('.'));
        //파일 이름과 내용 출력
         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             String line;
             textArea.append(newfilname + "\n- ");
             while ((line = reader.readLine()) != null) {
                 textArea.append(line+ "\n");
             }
             textArea.append("\n");
         } catch (IOException e) {
             e.printStackTrace();
         }

    }
    //새로고침 (다시 출력)
    private void refreshTextArea() {
        textArea.setText("");
        String folderPath = "C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\"; // 출력할 폴더 경로를 지정하세요

        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt"); // 특정 확장자를 가진 파일만 필터링하도록 설정합니다. 여기서는 .txt 파일을 필터링하였습니다.
                }
            });

            if (files != null) {
                // 파일 이름을 순서대로 정렬
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File file1, File file2) {
                        return file1.getName().compareTo(file2.getName());
                    }
                });

                for (File file : files) {
                    if (file.isFile()) {
                        readAndDisplayFileContents(file);
                    }
                }
            }
        } else {
            textArea.setText("폴더를 찾을 수 없습니다.");
        }
    }
    
    public static void main(String[] args) {
       
    }
}