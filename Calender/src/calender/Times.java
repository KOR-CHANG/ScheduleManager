package calender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.sound.sampled.*;

public class Times extends JFrame {
    private JComboBox<Integer> yearComboBox; // ���� ����
    private JComboBox<Integer> monthComboBox; // ���� ����
    private JComboBox<Integer> dayComboBox; // ���� ����
    private JComboBox<Integer> hourComboBox; // �ð��� �����ϴ� �޺��ڽ�
    private JComboBox<Integer> minuteComboBox; // ���� �����ϴ� �޺��ڽ�
    private JButton setButton; // ���� ��ư
    private Timer alarmTimer; // �˶��� ó���ϱ� ���� Ÿ�̸� ��ü
    private List<String> alarmList; // �˶� ���� ���
    private JFrame alarmListFrame; // �˶� ���� ����� �����ִ� ������
    private JTextArea alarmListTextArea; // �˶� ���� ����� ǥ���ϴ� �ؽ�Ʈ ����

    public Times() {
        setTitle("�ð� ����");
        setSize(300, 200);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        yearComboBox = new JComboBox<>();
        // ���� �������� 10�� �̳��� ������ �޺��ڽ��� �߰�
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(i);
        }
        add(yearComboBox);

        monthComboBox = new JComboBox<>();
        // 1���� 12������ ���� �޺��ڽ��� �߰�
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        add(monthComboBox);

        dayComboBox = new JComboBox<>();
        // 1���� 31������ ���� �޺��ڽ��� �߰�
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        add(dayComboBox);

        hourComboBox = new JComboBox<>();
        // 0���� 23������ �ð��� �޺��ڽ��� �߰�
        for (int i = 0; i <= 23; i++) {
            hourComboBox.addItem(i);
        }
        add(hourComboBox);

        minuteComboBox = new JComboBox<>();
        // 0���� 59������ ���� �޺��ڽ��� �߰�
        for (int i = 0; i <= 59; i++) {
            minuteComboBox.addItem(i);
        }
        add(minuteComboBox);

        setButton = new JButton("����");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (int) yearComboBox.getSelectedItem(); // ���õ� ���� ��������
                int selectedMonth = (int) monthComboBox.getSelectedItem(); // ���õ� �� ��������
                int selectedDay = (int) dayComboBox.getSelectedItem(); // ���õ� �� ��������
                int selectedHour = (int) hourComboBox.getSelectedItem(); // ���õ� �ð� ��������
                int selectedMinute = (int) minuteComboBox.getSelectedItem(); // ���õ� �� ��������
                // ���õ� ��¥�� �ð��� ó���ϴ� ���� �߰�
                JOptionPane.showMessageDialog(null, "���õ� ��¥: " + selectedYear + "-" + selectedMonth + "-" + selectedDay +"\n"
                		+ "���õ� �ð�: " + selectedHour + "�� " + selectedMinute + "��", "�˶� ����", JOptionPane.WARNING_MESSAGE);
                //System.out.println("���õ� ��¥: " + selectedYear + "-" + selectedMonth + "-" + selectedDay);
                //System.out.println("���õ� �ð�: " + selectedHour + ":" + selectedMinute);
                scheduleAlarm(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute); // �˶� ����
            }
        });
        add(setButton);

        alarmList = new ArrayList<>(); // �˶� ���� ��� �ʱ�ȭ

        setVisible(true);
    }

    // �˶� ����
    private void scheduleAlarm(int year, int month, int day, int hour, int minute) {
        // ���� �ð� ��������
        Calendar currentCalendar = Calendar.getInstance();

        // �˶� ���� �ð� ����
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(year, month - 1, day, hour, minute, 0);
        alarmList.add(alarmTime.getTime().toString()); // �˶� ���� ��Ͽ� �߰�
        printAlarmList(); // �˶� ���� ��� ���

        // �˶� ���� �ð��� ���� �ð� ��
        if (alarmTime.after(currentCalendar)) {
            long delay = alarmTime.getTimeInMillis() - currentCalendar.getTimeInMillis(); // ���� �ð�(�и��� ����)
            alarmTimer = new Timer((int) delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // �˶��� �︱ �� ������ �۾� �߰�
                	JOptionPane.showMessageDialog(null, "�˶��� �︳�ϴ�.", "�˶� ����", JOptionPane.INFORMATION_MESSAGE);
                    playAlarmSound();
                    alarmTimer.stop(); // �˶��� �︮�� Ÿ�̸� ����
                }
            });
            alarmTimer.setRepeats(false); // Ÿ�̸� �ݺ� ���� ��Ȱ��ȭ
            alarmTimer.start(); // Ÿ�̸� ����
        } else {
        	JOptionPane.showMessageDialog(null, "������ �ð��� ����ð����� ���� �ð��Դϴ�.", "�˶� ����", JOptionPane.WARNING_MESSAGE);
        }
    }

    // �˶� �Ҹ� ��� (�̱���)
    private void playAlarmSound() {
        try {
            // �˶� �Ҹ� ���� ���
            String soundFilePath = "C:\\Users\\dlckd\\OneDrive\\���� ȭ��\\Schedule\\wt3.wav";

            // ���� ���� �ε�
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));

            // ����� ���� ���� ��������
            AudioFormat audioFormat = audioInputStream.getFormat();

            // ���� ����� ���� Line ���� ��������
            DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat);
            Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);

            // ���� ������ �ε�
            clip.open(audioInputStream);
            // ���� �ݺ� ��� ����
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // ���� ���
            clip.start();
            // �˶� ���� Ÿ�̸� ���� (10�� �Ŀ� �˶� ����)
            int stopDelay = 5000; // �˶� ���� ��� �ð� (�и��� ����)
            Timer stopTimer = new Timer(stopDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clip.stop(); // �˶� �Ҹ� ����
                    JOptionPane.showMessageDialog(null, "�˶��� �ڵ����� ����ϴ�.", "�˶� ����", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            stopTimer.setRepeats(false); // Ÿ�̸� �ݺ� ���� ��Ȱ��ȭ
            stopTimer.start(); // Ÿ�̸� ����

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // �˶� ���� ��� ���
    private void printAlarmList() {
        System.out.println("�˶� ���� ���:");
        for (String alarm : alarmList) {
            System.out.println(alarm);
        }
        showAlarmListFrame();
    }
    // �˶� ���� ��� ������ ǥ��
    private void showAlarmListFrame() {
        if (alarmListFrame == null) {
            alarmListFrame = new JFrame();
            alarmListFrame.setTitle("�˶� ���� ���");
            alarmListFrame.setSize(300, 200);
            alarmListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            alarmListFrame.setLayout(new BorderLayout());

            alarmListTextArea = new JTextArea();
            alarmListTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(alarmListTextArea);
            alarmListFrame.add(scrollPane, BorderLayout.CENTER);
        }

        // �˶� ���� ��� ������Ʈ
        StringBuilder sb = new StringBuilder();
        for (String alarm : alarmList) {
            sb.append(alarm).append("\n");
        }
        alarmListTextArea.setText(sb.toString());

        alarmListFrame.setVisible(true);
    }

    public static void main(String[] args) {

    }
}
