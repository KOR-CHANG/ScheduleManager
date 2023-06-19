package calender;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
import java.text.DateFormatSymbols;

public class Calender extends JFrame {
    Calendar calendar = Calendar.getInstance();
    static int month, year;
    static int days  = 0;
    private static int currentMonth;
    private static int currentYear;
    private static JButton[] dayButtons;
    private static JPanel calendarPanel;
    private static JTextArea scheduleArea;
    private static JScrollPane scrollPane;
    private static String Calnum;
    private static JPanel cal_title;
    private static JLabel sun = new JLabel("일");
    private static JLabel mon = new JLabel("월");
    private static JLabel tue = new JLabel("화");
    private static JLabel wed = new JLabel("수");
    private static JLabel thu = new JLabel("목");
    private static JLabel fri = new JLabel("금");
    private static JLabel sat = new JLabel("토");


    public static JButton prevMonthButton, nextMonthButton, prevYearButton, nextYearButton,
            saveScheduleButton, deleteScheduleButton, memoList, todayButton, setButton;
    public static Calender frame;

    //생성자
    public Calender()
    {
        this.setTitle("[일정관리 프로그램]");
        this.pack();
        this.setSize(900,800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        components(this);
        addEvent();
        this.setVisible(true);
    }
    public void components(Calender frame)
    {
        addText(frame,"", 50, 10, 100, 30, 10.0f); //0
        todayButton = addButton(frame, "Today", 10, 10, 60, 30, Color.gray, 8.0f); //1
        todayButton.setForeground(Color.white);
        prevMonthButton = addButton(frame, "<", 80, 60, 40, 30, Color.gray, 10.0f);
        prevMonthButton.setForeground(Color.white);
        addText(frame, month + " / " + year, 155, 60, 100, 30, 10.0f); //3
        
        nextMonthButton = addButton(frame, ">", 280, 60, 40, 30, Color.gray, 10.0f); //4
        nextMonthButton.setForeground(Color.white);
        prevYearButton = addButton(frame, "<<", 20, 60, 50, 30, Color.gray, 10.0f);
        prevYearButton.setForeground(Color.white);
        nextYearButton = addButton(frame, ">>", 340, 60, 50, 30, Color.gray, 10.0f);
        nextYearButton.setForeground(Color.white);
        addText(frame, "일정을 입력하세요.", 450, 120, 100, 30, 10.0f);
        calTitle(frame);
        //저장, 삭제버튼
        saveScheduleButton = addButton(frame, "메모저장", 470, 600, 80,30, Color.gray, 10.0f);
        saveScheduleButton.setForeground(Color.white);
        deleteScheduleButton = addButton(frame, "메모삭제", 560, 600, 80,30, Color.gray, 10.0f);
        deleteScheduleButton.setForeground(Color.white);
        memoList = addButton(frame, "메모목록", 650, 600, 80, 30, Color.gray, 10.0f);
        memoList.setForeground(Color.white);
        //알람 설정
        setButton= addButton(frame, "알람설정", 740, 600, 80, 30, Color.gray, 10.0f);
        setButton.setForeground(Color.white);
        //달력생성
        createCalendar(frame);
        //메모영역 생성
        createTextArea(frame);
    }

    public void addEvent()
    {
        todayButton.addActionListener(new EventListener());
        prevMonthButton.addActionListener(new EventListener());
        nextMonthButton.addActionListener(new EventListener());
        prevYearButton.addActionListener(new EventListener());
        nextYearButton.addActionListener(new EventListener());
        saveScheduleButton.addActionListener(new EventListener());
        deleteScheduleButton.addActionListener(new EventListener());
        memoList.addActionListener(new EventListener());
        setButton.addActionListener(new EventListener());
    }
    //버튼추가함수
    public static JButton addButton(Calender frame, String btnName, int x, int y, int width, int height, Color color, float size)
    {

        JButton btn = new JButton(btnName);
        btn.setBounds(x,y,width,height);
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setFont(btn.getFont().deriveFont(size));
        frame.getContentPane().setLayout(null);
        frame.add(btn);
        //frame.setVisible(true);
        return btn;
    }

    //텍스트 추가함수
    public static void addText(Calender frame, String text, int x, int y, int width, int height, float size)
    {
        JLabel label = new JLabel();
        label.setBounds(x,y,width,height);
        label.setText(text);
        label.setHorizontalAlignment(JLabel.CENTER); //중앙정렬
        label.setFont(label.getFont().deriveFont(size));
        frame.add(label);
    }

    //날자얻기
    public static String getDate()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    //달 얻기
    public static String getMonth()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return formatter.format(date);
    }

    //년도 얻기
    public static String getYear()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY");
        return formatter.format(date);
    }

    //라벨(현재 보고있는 달력의 년과 월) 업데이트
    public static void updateLabel(Calender frame)
    {
        JLabel label = (JLabel) frame.getContentPane().getComponent(3);
        label.setText(month + " / " + year);
    }
    public static void calTitle(Calender frame)
    {
        cal_title = new JPanel();
        cal_title.setLayout(new GridLayout(1,7));
        cal_title.setBounds(40, 40, 400, 250);

        sun.setForeground(Color.red);
        cal_title.add(sun);
        cal_title.add(mon);
        cal_title.add(tue);
        cal_title.add(wed);
        cal_title.add(thu);
        cal_title.add(fri);
        sat.setForeground(Color.blue);
        cal_title.add(sat);


        frame.add(cal_title, BorderLayout.SOUTH);
    }
    //달력 만들기
    public static void createCalendar(Calender frame)
    {
        calendarPanel = new JPanel(); //달력 패널 생성
        calendarPanel.setLayout(new GridLayout(6, 7));//세로 6 가로 7
        calendarPanel.setBounds(20, 200, 400, 250); //패널 위치 설정
        frame.add(calendarPanel, BorderLayout.SOUTH);//프레임에 패널 추가

        dayButtons = new JButton[42];//버튼 42개 추가(배열) - 6 * 7

        for(int i = 0; i < dayButtons.length; i++)//배열의 크기를 구한 후 반복
        {
            dayButtons[i] = new JButton(); //0번 부터 버튼 생성
            dayButtons[i].setBorderPainted(false);
            dayButtons[i].setContentAreaFilled(false);
            calendarPanel.add(dayButtons[i]);//패널에 버튼 추가
        }
        currentMonth = month;
        currentYear = year;

        updateCalendar(currentMonth, currentYear);//달력 업데이트
    }

    //파일 존재 유무 확인
    public static boolean isFileExists(File file)
    {
        return file.exists() && ! file.isDirectory();
    }
    //달력 업데이트
    public static void updateCalendar(int m, int y)
    {

        for(int i = 0; i < dayButtons.length; i++) //배열의 크기를 구한 후 반복
        {
            dayButtons[i].setText(""); //버튼의 텍스트 초기화
            dayButtons[i].setBackground(null); //버튼 색 초기화
        }

        Calendar cal = Calendar.getInstance(); //Calendar 클래스를 인스턴스함
        cal.set(y, m, 0); //Calendar를 설정함(y = 년, m = 월, 0 해당 월의 마지막 일
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //월의 최대 일수를 반환함

        int offset = cal.get(Calendar.DAY_OF_WEEK) - 1; //일요일을 0으로 표기하기 위해 -1을 해줌 (배열이 0부터 시작하기 때문)
        for(int i = 0; i < numDays; i++)//해당 월의 최대일수 만큼 반복
        {
            dayButtons[offset + i].setText(Integer.toString(i+1));//일수롤 1부터 시작하기 위해 +1을 해줌
            //offset + i는 해당 일의 offset에서부터 i만큼 떨어진 인덱스를 나타냄
            //offset + i는 첫번째 1일 부터 해당 월의 마지막 날까지 반복해서 텍스트 삽입


            //filePath 변수에 해당 결로에 파일을 불러옴
            File filePath = new File("C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\" + y + "." + m + "." + (i + 1) + ".txt");
            //파일이 존재한다면
            if(isFileExists(filePath)) {
                //버튼 색을 초록색으로 바꾼다.
                dayButtons[offset + i].setContentAreaFilled(true);
               dayButtons[offset + i].setBackground(Color.green);
            }
        }
    }

    //메모영역 생성
    public static void createTextArea(Calender frame)
    {
        //텍스트 영역 생성
        scheduleArea = new JTextArea();
        //텍스트 영억을 수정
        scheduleArea.setEditable(true);
        //텍스트 영역 위치설정
        scheduleArea.setBounds(450,150,400,400);
        //텍스트 영역에 스크롤을 추가
        scrollPane = new JScrollPane(scheduleArea);
        //위치를 텍스트 영역과 같도록 설정
        scrollPane.setBounds(450,150,400,400);
        //프레임에 스크롤 추가
        frame.getContentPane().add(scrollPane);
    }
    //텍스트 영역을 업데이트한다
    public static void updateTextArea()
    {
        //텍스트 영억의 텍스트를 공백으로 초기화함
        scheduleArea.setText("");
    }
    //파일의 내용을 읽는 함수
    public static void readTextFile()
    {
        //버튼 크기만큼 반복
        for(int i = 0; i < dayButtons.length; i++)
        {
            //버튼의 i번째 를 button 변수에 삽입
            JButton button = dayButtons[i];
            //버튼의 이벤트 처리
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //텍스트 영역 업데이트(초기화)`
                    updateTextArea();
                    //button의 텍스트 값을 받아옴
                    String day = button.getText();
                    //Calnum(전역변수)에 버튼의 텍스트 값을 집어넣음
                    Calnum = day;
                    //파일 불러오기 (해당 일자 버튼을 클릭하면 메모장 안에 내용을 불러옴
                    try {
                        //file변수에 해당 결로를 삽입
                        File file = new File("C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\"
                                + year + "." + month + "." + Calnum + ".txt");
                        try {
                            //scanner에 file파일의 경로을 호출
                            Scanner scanner = new Scanner(file);
                            while(scanner.hasNextLine())//스캐너의 다음 줄을 받아옴
                            //다음 줄이 없다면 반복 종료
                            {
                                //파일의 다음 라인을 line변수에 넣음
                                String line = scanner.nextLine();
                                //file경로에 있는 파이릉ㄹ 읽어옴
                                FileReader reader = new FileReader(file);
                                //데이터를 버퍼링하고 효율적으로 읽어드리게함
                                BufferedReader bufferedReader = new BufferedReader(reader);
                                //문자열을 추가, 수정 작업 효율적 처리하도록함
                                StringBuilder stringBuilder = new StringBuilder();
                                //String line = null;
                                while((line = bufferedReader.readLine()) != null)
                                {
                                    //스트링 빌더에 line을 추가함
                                    stringBuilder.append(line);
                                }
                                //리더를 닫는다.
                                reader.close();
                                //일정 영역에 스트링 빌더를 통해 받아온 문자를 삽입한다.
                                scheduleArea.setText(stringBuilder.toString());
                            }
                            //스캐너를 닫는다.
                            scanner.close();
                        }
                        catch (FileNotFoundException ex1){
                            // System.out.println("C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\"+ year + "." + month + "." + Calnum + ".txt" + " File Not Found!");
                        }
                    }
                    catch(IOException e1){
                        //예외가 어디서 발생했는지, 예외를 생성시킨 메서드를 호출 스택 정보 확인
                        e1.printStackTrace();
                    }
                }
            });
        }
    }
    public static void main(String[] args)
    {
        //프레임 호출(생성)
        frame = new Calender();

        //month변수에 getMonth로 얻어온 문자열 값을 정수형으로 변환후 대입
        month = Integer.parseInt(getMonth());
        //year변수에 getYear로 얻어온 문자열 값을 정수형으로 변환 후 대입
        year = Integer.parseInt(getYear());
        //label을 업데이트 한다(달력을 이동할때 사용)
        updateLabel(frame);
        //눌린 버튼의 TXT값을 불러옴
        readTextFile();

        //달력을 업데이트 한다.
        updateCalendar(month, year);
    }
    class EventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //이벤트를 발생시킨 객체를 source에 삽입
            Object source = e.getSource();
            //달력의 전 월으로 이동하는 버튼
            if(source == todayButton)
            {
                JOptionPane.showMessageDialog(frame, "오늘은 " + getDate() + "입니다.", "일정관리프로그램", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(source == prevMonthButton)
            {
                //만약 달력의 값이 2보다 작아지면
                if(month < 2) {
                    //month = 13을 한다
                    month = 13;
                    //이러면 년수를 하나 줄인다.
                    year--;
                }
                //달력의 값도 하나 줄인다 13으로 입력했기 때문
                month--;
                for(int i = 0; i < dayButtons.length; i++)
                {
                   dayButtons[i].setBorderPainted(false);
                    dayButtons[i].setContentAreaFilled(false);
                }
                //라벨을 업데이트함
                updateLabel(frame);
                //달력을 업데이트 한다.
                updateCalendar(month, year);

                //달력의 다음 월로 이동하는 버튼
            } else if(source == nextMonthButton)
            {
                //만 약 달력의 값이 11보다 크면
                if(month > 11) {
                    //month = 0
                    month = 0;
                    //year에 year에 1을 더해준다.
                    year++;
                }
                //month를 0으로 초기화했기 때문에 1을 더해준다.
                month++;
                for(int i = 0; i < dayButtons.length; i++)
                {
                   dayButtons[i].setBorderPainted(false);
                    dayButtons[i].setContentAreaFilled(false);
                }
                //라벨을 업데이트한다.
                updateLabel(frame);
                //달력을 업데이트한다.
                updateCalendar(month, year);
                //이전 년으로 이동하는 버튼
            } else if(source == prevYearButton)
            {
                //년도가 2보다 아래로 가면
                if(year < 2) {
                    //JOptionPane을 이용하여 팝업을 띄운다.
                    JOptionPane.showMessageDialog(frame, "년도는 0이하로 내려갈 수 없습니다.", "일정관리프로그램", JOptionPane.WARNING_MESSAGE);
                    //년도를 다시 2로 초기화함
                    year = 2;
                }
                //년도에 -1을 해준다 (1년으로 만들기 위해)
                year--;
                for(int i = 0; i < dayButtons.length; i++)
                {
                   dayButtons[i].setBorderPainted(false);
                    dayButtons[i].setContentAreaFilled(false);
                }
                //라벨을 업데이트한다.
                updateLabel(frame);
                //달력을 업데이트 한다.
                updateCalendar(month, year);
                //다음 년으로 이동하는 버튼
            } else if(source == nextYearButton)
            {
                //년도가 2029년보다 크다면
                if(year > 2029) {
                    //JOptionPane을 이용하여 팝업을 띄운다.
                    JOptionPane.showMessageDialog(frame, "년도는 2030년 이상으로 갈 수없습니다.", "일정관리프로그램", JOptionPane.WARNING_MESSAGE);
                    //year를 2029로 초기화한다
                    year = 2029;
                }
                //년도를 2030까지 하기위해 +1을 해준다
                year++;
                for(int i = 0; i < dayButtons.length; i++)
                {
                   dayButtons[i].setBorderPainted(false);
                    dayButtons[i].setContentAreaFilled(false);
                }
                //라벨을 업데이트한다
                updateLabel(frame);
                //달력을 업데이트한다.
                updateCalendar(month, year);
                //저장 버튼이 눌렸을 때
            } else if(source == saveScheduleButton)
            {
                //Calnum이 null이거나 Calnum이 공백이라면
                if(Calnum == null || Calnum == "")
                {
                    //JOptionPane을 이용해 팝업을 띄운다.
                    JOptionPane.showMessageDialog(frame, "날짜를 선택 한 후 일정을 저장해주세요!", "일정관리프로그램", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    //Calnum이 null이 아니고 공백이 아니라면
                    //해당경로에 있는 파일을 받아옴
                    File file = new File("C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\" + year + "." + month + "." + Calnum + ".txt");
                    try {
                        //파일을 적성한다.
                        FileWriter writer = new FileWriter(file);
                        //파일을 작성한다
                        writer.write(scheduleArea.getText());
                        //JOptionPane을 이용해서 팝업을 띄운다.
                        JOptionPane.showMessageDialog(frame, "파일이 저장되었습니다.", "일정관리프로그램", JOptionPane.INFORMATION_MESSAGE);
                        //writer을 닫는다.
                        writer.close();
                    }catch(IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                //달력을 업데이트한다.
                updateCalendar(month, year);
                //눌린 버튼이 삭제 버튼이라면
            } else if(source == deleteScheduleButton)
            {
                //파일 경로를 받아온다.
                File file = new File("C:\\Users\\dlckd\\OneDrive\\바탕 화면\\Schedule\\"
                        + year + "." + month + "." + Calnum + ".txt");
                if(file.delete())//파일이 제거 한게 참이라면
                {
                    //JOptionPane을 이용해 팝업을 띄운다.
                    JOptionPane.showMessageDialog(frame, "파일이 제거되었습니다.", "일정관리프로그램", JOptionPane.INFORMATION_MESSAGE);
                    //텍스트영역을 업데이트 한다.
                    for(int i = 0; i < dayButtons.length; i++)
                    {
                       dayButtons[i].setBorderPainted(false);
                        dayButtons[i].setContentAreaFilled(false);
                    }
                    updateTextArea();
                }else {
                    //JOptionPane을 이용해 팝업을 띄운다.
                    JOptionPane.showMessageDialog(frame, "해당 일에는 파일이 존재하지 않습니다.", "일정관리프로그램", JOptionPane.INFORMATION_MESSAGE);
                }
                //달력을 업데이트 한다,
                updateCalendar(month, year);

                //눌린 버튼이 메모 목록이라면
            } else if(source == memoList)
            {
                //메모 리더 클래스를 불러온다.
                new MemoReader();
            }
            else if(source==setButton) new Times();
        }
    }
}