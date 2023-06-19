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
       //������ ����
        setTitle("Memo Reader");
        setSize(500, 500);
       
        //�ؽ�Ʈ ����
        textArea = new JTextArea();
        //��ũ�� ����
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        //���ΰ�ħ ��ư�����Ͽ� ����߰�
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("���ΰ�ħ");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTextArea();
            }
        });
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        //���� �ּ� �ֱ�
        String folderPath = "C:\\Users\\dlckd\\OneDrive\\���� ȭ��\\Schedule\\"; // ����� ���� ��θ� �����ϼ���
        //���� ��ó ����
        File folder = new File(folderPath);
        //�������� �������� Ȯ���Ͽ� ������ �� �ȿ� �ִ� ���� �б�
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt"); // Ư�� Ȯ���ڸ� ���� ���ϸ� ���͸��ϵ��� �����մϴ�. ���⼭�� .txt ������ ���͸��Ͽ����ϴ�.
                }
            });
            //�����ȿ� �ִ� ���ϵ��� ������� �迭�ȿ� �ֱ�
            if (files != null) {
                
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    // ���� �̸��� ������� ����
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
            textArea.setText("������ ã�� �� �����ϴ�.");
        }
        setVisible(true);
    }
    //�ý�Ʈ�� ���� �̸��� ���� ���
    private void readAndDisplayFileContents(File file) {
       //���� Ȯ���ڸ� ������ �̸� ����
       String filename=file.getName();
        String newfilname=filename.substring(0, filename.lastIndexOf('.'));
        //���� �̸��� ���� ���
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
    //���ΰ�ħ (�ٽ� ���)
    private void refreshTextArea() {
        textArea.setText("");
        String folderPath = "C:\\Users\\dlckd\\OneDrive\\���� ȭ��\\Schedule\\"; // ����� ���� ��θ� �����ϼ���

        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt"); // Ư�� Ȯ���ڸ� ���� ���ϸ� ���͸��ϵ��� �����մϴ�. ���⼭�� .txt ������ ���͸��Ͽ����ϴ�.
                }
            });

            if (files != null) {
                // ���� �̸��� ������� ����
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
            textArea.setText("������ ã�� �� �����ϴ�.");
        }
    }
    
    public static void main(String[] args) {
       
    }
}