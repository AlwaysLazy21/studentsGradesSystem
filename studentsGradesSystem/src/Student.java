public class Student {
    private String id;
    private String name;
    private int chinese;
    private int math;
    private int english;
    private int sum;
    private double average;

    public Student() {
    }

    public Student(String id, String name, int chinese, int math, int english) {
        this.id = id;
        this.name = name;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
        this.sum = chinese + math + english;
        this.average = (chinese + math + english) / 3.0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getChinese() {
        return chinese;
    }

    public int getMath() {
        return math;
    }

    public int getEnglish() {
        return english;
    }

    public int getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public void setSum() {
        sum = chinese + math + english;
    }

    public void setAverage() {
        average = (chinese + math + english) / 3.0;
    }

    public String toString() {
        return "学号:" + id + " 姓名:" + name + " 语文:" + chinese + " 数学:" + math + " 英语:" + english + " 总分:" + sum + " 平均值:" + (int) average + "\r\n";
    }

}

