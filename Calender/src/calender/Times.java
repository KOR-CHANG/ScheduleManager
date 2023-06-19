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
    private JComboBox<Integer> yearComboBox; // 년을 선택
    private JComboBox<Integer> monthComboBox; // 달을 선택
    private JComboBox<Integer> dayComboBox; // 날을 선택
    private JComboBox<Integer> hourComboBox; // 시간을 선택하는 콤보박스
    private JComboBox<Integer> minuteComboBox; // 분을 선택하는 콤보박스
    private JButton setButton; // 설정 버튼
    private Timer alarmTimer; // 알람을 처리하기 위한 타이머 객체
    private List<String> alarmList; // 알람 설정 목록
    private JFrame alarmListFrame; // 알람 설정 목록을 보여주는 프레임
    private JTextArea alarmListTextArea; // 알람 설정 목록을 표시하는 텍스트 영역

    public Times() {
        setTitle("시간 선택");
        setSize(300, 200);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        yearComboBox = new JComboBox<>();
        // 현재 연도부터 10년 이내의 연도를 콤보박스에 추가
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(i);
        }
        add(yearComboBox);

        monthComboBox = new JComboBox<>();
        // 1부터 12까지의 월을 콤보박스에 추가
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        add(monthComboBox);

        dayComboBox = new JComboBox<>();
        // 1부터 31까지의 일을 콤보박스에 추가
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        add(dayComboBox);

        hourComboBox = new JComboBox<>();
        // 0부터 23까지의 시간을 콤보박스에 추가
        for (int i = 0; i <= 23; i++) {
            hourComboBox.addItem(i);
        }
        add(hourComboBox);

        minuteComboBox = new JComboBox<>();
        // 0부터 59까지의 분을 콤보박스에 추가
        for (int i = 0; i <= 59; i++) {
            minuteComboBox.addItem(i);
        }
        add(minuteComboBox);

        setButton = new JButton("설정");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (int) yearComboBox.getSelectedItem(); // 선택된 연도 가져오기
                int selectedMonth = (int) monthComboBox.getSelectedItem(); // 선택된 월 가져오기
                int selectedDay = (int) dayComboBox.getSelectedItem(); // 선택된 일 가져오기
                int selectedHour = (int) hourComboBox.getSelectedItem(); // 선택된 시간 가져오기
                int selectedMinute = (int) minuteComboBox.getSelectedItem(); // 선택된 분 가져오기
                // 선택된 날짜와 시간을 처리하는 로직 추가
                JOptionPane.showMessageDialog(null, "선택된 날짜: " + selectedYear + "-" + selectedMonth + "-" + selectedDay +"\n"
                		+ "선택된 시간: " + selectedHour + "시 " + selectedMinute + "분", "알람 중지", JOptionPane.WARNING_MESSAGE);
                //System.out.println("선택된 날짜: " + selectedYear + "-" + selectedMonth + "-" + selectedDay);
                //System.out.println("선택된 시간: " + selectedHour + ":" + selectedMinute);
                scheduleAlarm(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute); // 알람 예약
            }
        });
        add(setButton);

        alarmList = new ArrayList<>(); // 알람 설정 목록 초기화

        setVisible(true);
    }

    // 알람 예약
    private void scheduleAlarm(int year, int month, int day, int hour, int minute) {
        // 현재 시간 가져오기
        Calendar currentCalendar = Calendar.getInstance();

        // 알람 설정 시간 생성
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(year, month - 1, day, hour, minute, 0);
        alarmList.add(alarmTime.getTime().toString()); // 알람 설정 목록에 추가
        printAlarmList(); // 알람 설정 목록 출력

        // 알람 설정 시간과 현재 시간 비교
        if (alarmTime.after(currentCalendar)) {
            long delay = alarmTime.getTimeInMillis() - currentCalendar.getTimeInMillis(); // 지연 시간(밀리초 단위)
            alarmTimer = new Timer((int) delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 알람이 울릴 때 수행할 작업 추가
                	JOptionPane.showMessageDialog(null, "알람이 울립니다.", "알람 중지", JOptionPane.INFORMATION_MESSAGE);
                    playAlarmSound();
                    alarmTimer.stop(); // 알람이 울리면 타이머 중지
                }
            });
            alarmTimer.setRepeats(false); // 타이머 반복 실행 비활성화
            alarmTimer.start(); // 타이머 시작
        } else {
        	JOptionPane.showMessageDialog(null, "선택한 시간이 현재시간보다 이전 시간입니다.", "알람 중지", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 알람 소리 재생 (미구현)
    private void playAlarmSound() {
        try {
            // 알람 소리 파일 경로
            String soundFilePath = "C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\wt3.wav";

            // 사운드 파일 로딩
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));

            // 오디오 포맷 정보 가져오기
            AudioFormat audioFormat = audioInputStream.getFormat();

            // 사운드 재생을 위한 Line 정보 가져오기
            DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat);
            Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);

            // 사운드 데이터 로딩
            clip.open(audioInputStream);
            // 사운드 반복 재생 설정
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // 사운드 재생
            clip.start();
            // 알람 중지 타이머 설정 (10초 후에 알람 중지)
            int stopDelay = 5000; // 알람 중지 대기 시간 (밀리초 단위)
            Timer stopTimer = new Timer(stopDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clip.stop(); // 알람 소리 중지
                    JOptionPane.showMessageDialog(null, "알람이 자동으로 멈춥니다.", "알람 중지", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            stopTimer.setRepeats(false); // 타이머 반복 실행 비활성화
            stopTimer.start(); // 타이머 시작

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 알람 설정 목록 출력
    private void printAlarmList() {
        System.out.println("알람 설정 목록:");
        for (String alarm : alarmList) {
            System.out.println(alarm);
        }
        showAlarmListFrame();
    }
    // 알람 설정 목록 프레임 표시
    private void showAlarmListFrame() {
        if (alarmListFrame == null) {
            alarmListFrame = new JFrame();
            alarmListFrame.setTitle("알람 설정 목록");
            alarmListFrame.setSize(300, 200);
            alarmListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            alarmListFrame.setLayout(new BorderLayout());

            alarmListTextArea = new JTextArea();
            alarmListTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(alarmListTextArea);
            alarmListFrame.add(scrollPane, BorderLayout.CENTER);
        }

        // 알람 설정 목록 업데이트
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
