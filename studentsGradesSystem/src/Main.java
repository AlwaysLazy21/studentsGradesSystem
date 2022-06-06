import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Student> transcripts = new ArrayList<>();
        read(transcripts);
        menu();
        control(transcripts);
    }

    public static int isExist(List<Student> transcripts, String id) {
        for (int i = 0; i < transcripts.size(); i++) {
            if (id.equals(transcripts.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void menu() {
        System.out.println("欢迎使用学生成绩管理系统");
        System.out.println("1.查看所有学生成绩信息");
        System.out.println("2.根据学号查询学生信息");
        System.out.println("3.添加学生信息，求总分和平均值");
        System.out.println("4.修改或删除学生信息");
        System.out.println("5.对学生的成绩进行排序输出");
        System.out.println("0.退出系统");
        System.out.println("请输入你要进行的操作前的序号：");
    }

    public static void control(List<Student> transcripts) throws IOException {
        Scanner input = new Scanner(System.in);
        while (true) {
            char select = input.next().charAt(0);
            switch (select) {
                case '1' ->//1.查看所有学生成绩信息
                        allStudentGrades(transcripts);
                case '2' ->//2.根据学号查询学生信息
                        getElementById(transcripts);
                case '3' ->//3.添加学生信息，求总分和平均值
                        addStudentMessage(transcripts);
                case '4' ->//4.修改或删除学生信息
                        deleteOrSetStudentMessage(transcripts);
                case '5' ->//5.对学生的成绩进行排序输出
                        printAllStudentsSort(transcripts);
                case '0' -> {//0.退出系统
                    save(transcripts);
                    System.out.println("已退出系统");
                    System.exit(0);
                }
                default -> System.out.println("您输入的序号有误，请重新输入。");
            }
        }
    }

    public static void read(List<Student> transcripts) throws IOException {
        File x = new File("src\\scores.txt");
        if (!x.exists()) {
            if (x.createNewFile()) {
                System.out.println("源文件不存在，已自动创建新文件");
            } else {
                System.out.println("源文件丢失，无法创建新文件");
            }
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream("src\\scores.txt"));
        char[] chs = new char[1024];
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < chs.length; i++) {
                int ch = isr.read();
                if (ch == -1) {
                    flag = false;
                    break;
                }
                chs[i] = (char) ch;
                if (chs[i] == '\n') {
                    break;
                }
            }
            if (flag) {
                int[] colon = new int[5];
                int in_co = 0;
                int[] blank = new int[5];
                int in_bl = 0;
                for (int i = 0; i < chs.length; i++) {
                    if (chs[i] == ':') {
                        colon[in_co++] = i;
                    } else if (chs[i] == ' ') {
                        blank[in_bl++] = i;
                        if (in_bl == 5) {
                            break;
                        }
                    }
                }
                StringBuilder temp_id = new StringBuilder();
                for (int i = colon[0] + 1; i < blank[0]; i++) {
                    temp_id.append(chs[i]);
                }
                String id = temp_id.toString();
                StringBuilder temp_name = new StringBuilder();
                for (int i = colon[1] + 1; i < blank[1]; i++) {
                    temp_name.append(chs[i]);
                }
                String name = temp_name.toString();
                int chinese = 0;
                for (int i = colon[2] + 1; i < blank[2]; i++) {
                    chinese = chinese * 10 + chs[i] - '0';
                }
                int math = 0;
                for (int i = colon[3] + 1; i < blank[3]; i++) {
                    math = math * 10 + chs[i] - '0';
                }
                int english = 0;
                for (int i = colon[4] + 1; i < blank[4]; i++) {
                    english = english * 10 + chs[i] - '0';
                }
                Student student = new Student(id, name, chinese, math, english);
                transcripts.add(student);
            }
        }
        isr.close();
    }

    public static void save(List<Student> transcripts) throws IOException {
        File x = new File("src\\scores.txt");
        if (!x.exists()) {
            if (x.createNewFile()) {
                System.out.println("源文件不存在，已自动创建新文件");
            } else {
                System.out.println("源文件丢失，无法创建新文件");
            }
        }
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("src\\scores.txt"));
        for (Student transcript : transcripts) {
            osw.write(transcript.toString());
        }
        osw.close();
    }

    public static void allStudentGrades(List<Student> transcripts) {
        if (transcripts.size() == 0) {
            System.out.println("暂无学生成绩信息");
            return;
        }
        int sum = 0;
        for (Student student : transcripts) {
            System.out.print(student.toString());
            sum += student.getSum();
        }
        System.out.println("一共" + transcripts.size() + "位同学，总分的平均分为：" + (sum / transcripts.size()) + "分。");
        System.out.println("已自动退出该功能");
    }

    public static void getElementById(List<Student> transcripts) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入您要查找的学号");
        String id = input.nextLine();
        int index = isExist(transcripts, id);
        if (index == -1) {
            System.out.println("对不起！查无此人!!!");
            System.out.println("已自动退出该功能");
        } else {
            System.out.println("该同学的成绩信息为：");
            System.out.print(transcripts.get(index).toString());
            System.out.println("已自动退出该功能");
        }
    }

    public static void addStudentMessage(List<Student> transcripts) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入您要添加的学生的学号：");
        String Id = input.nextLine();
        System.out.println("请输入添加的学生姓名：");
        String name = input.nextLine();
        System.out.println("请输入该学生的语文成绩：");
        int chinese = input.nextInt();
        System.out.println("请输入该学生的数学成绩：");
        int math = input.nextInt();
        System.out.println("请输入该学生的英语成绩：");
        int english = input.nextInt();
        Student student = new Student(Id, name, chinese, math, english);
        System.out.print(student);
        transcripts.add(student);
        System.out.println("添加成功，已自动退出该功能");

    }

    public static void deleteOrSetStudentMessage(List<Student> transcripts) {
        Scanner input = new Scanner(System.in);
        System.out.println("请选择你要进行的操作");
        System.out.println("1.删除学生信息");
        System.out.println("2.修改学生信息");
        char select = input.nextLine().charAt(0);
        if (select == '1') {
            System.out.println("请输入你要删除的学生的学号：");
            String id = input.nextLine();
            int index = isExist(transcripts, id);
            if (index == -1) {
                System.out.println("对不起！查无此人!!!");
                System.out.println("已自动退出该功能");
            } else {
                System.out.println("已找到该同学");
                System.out.print(transcripts.get(index).toString());
                System.out.println("请问是否删除？(是/否)");
                if (input.next().equals("是")) {
                    transcripts.remove(index);
                    System.out.println("删除成功,已退出该功能");
                } else if (input.next().equals("否")) {
                    System.out.println("删除失败，已退出该功能");
                } else {
                    input.nextLine();
                    System.out.println("您输入的有误。删除失败，已退出该功能");
                }
            }
        } else if (select == '2') {
            System.out.println("请输入您要修改的学生的学号：");
            String id = input.nextLine();
            int index = isExist(transcripts, id);
            if (index == -1) {
                System.out.println("对不起！查无此人!!!");
                System.out.println("已自动退出该功能");
            } else {
                System.out.println("已找到该同学");
                System.out.print(transcripts.get(index).toString());
                System.out.println("0.姓名 1.语文 2.数学 3.英语 4.*退出*");
                System.out.println("请选择您要修改的内容：");
                int option = input.nextInt();
                switch (option) {
                    case 0 -> {
                        System.out.println("请输入您要改为的名字：" + input.nextLine());
                        String name = input.nextLine();
                        transcripts.get(index).setName(name);
                        System.out.print(transcripts.get(index).toString());
                        System.out.println("修改成功");
                    }
                    case 1 -> {
                        System.out.println("请输入您要改为的语文分数：" + input.nextLine());
                        int chinese = input.nextInt();
                        transcripts.get(index).setChinese(chinese);
                        transcripts.get(index).setSum();
                        transcripts.get(index).setAverage();
                        System.out.print(transcripts.get(index).toString());
                        System.out.println("修改成功");
                    }
                    case 2 -> {
                        System.out.println("请输入您要改为的数学分数：" + input.nextLine());
                        int math = input.nextInt();
                        transcripts.get(index).setMath(math);
                        transcripts.get(index).setSum();
                        transcripts.get(index).setAverage();
                        System.out.print(transcripts.get(index).toString());
                        System.out.println("修改成功");
                    }
                    case 3 -> {
                        System.out.println("请输入您要改为的英语分数：" + input.nextLine());
                        int english = input.nextInt();
                        transcripts.get(index).setEnglish(english);
                        transcripts.get(index).setSum();
                        transcripts.get(index).setAverage();
                        System.out.print(transcripts.get(index).toString());
                        System.out.println("修改成功");
                    }
                    case 4 -> System.out.println("已退出该功能");
                    default -> System.out.println("您输入的有误，已退出该功能");
                }
                System.out.println("已退出该功能");
            }
        } else {
            System.out.println("您输入的有误，已自动退出该功能。");
        }
    }

    public static void printAllStudentsSort(List<Student> transcripts) throws IOException {
        File x = new File("src\\grades.txt");
        if (!x.exists()) {
            if (x.createNewFile()) {
                System.out.println("源文件不存在，已自动创建新文件");
            } else {
                System.out.println("源文件丢失，无法创建新文件");
            }
        }
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("src\\grades.txt"));
        System.out.println("请问您想要按照什么为标准排序？");
        System.out.println("1.学号 2.总分");
        Scanner input = new Scanner(System.in);
        int select = input.nextInt();
        if (select == 1) {
            sort_id(transcripts);
        } else if (select == 2) {
            sort_sum(transcripts);
        } else {
            System.out.println("对不起，您输入的有误，已退出该功能");
            return;
        }
        Date time = new Date();
        SimpleDateFormat yMdhms = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss");
        String now = yMdhms.format(time);
        osw.write("导出时间：" + now + "\r\n");
        for (Student transcript : transcripts) {
            osw.write(transcript.toString());
        }
        osw.close();
        System.out.println("导出成功,已自动退出该功能，退出系统即可查看");
    }

    public static void sort_sum(List<Student> transcripts) {
        for (int i = 0; i < transcripts.size(); i++) {
            for (int j = 0; j < transcripts.size() - i - 1; j++) {
                if (transcripts.get(j).getSum() < transcripts.get(j + 1).getSum()) {
                    Student student = transcripts.get(j + 1);
                    transcripts.remove(j + 1);
                    transcripts.add(j, student);
                }
            }
        }
    }

    public static void sort_id(List<Student> transcripts) {
        for (int i = 0; i < transcripts.size(); i++) {
            for (int j = 0; j < transcripts.size() - i - 1; j++) {
                long id1 = Long.parseLong(transcripts.get(j).getId());
                long id2 = Long.parseLong(transcripts.get(j + 1).getId());
                if (id1 > id2) {
                    Student student = transcripts.get(j + 1);
                    transcripts.remove(j + 1);
                    transcripts.add(j, student);
                }
            }
        }
    }
}
