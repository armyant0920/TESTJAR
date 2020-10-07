public class Test {

    public static void main(String[] args) {
        int a = 10;
        System.out.println(a);
        print(a);
        System.out.println(a);
        Emp emp = new Emp();
        emp.age = 20;
        System.out.printf("emp age=%d\n", emp.age);
        emp.update(100);
        System.out.printf("emp age=%d\n", emp.age);

        String s = "H";
        System.out.println(s);
        addS(s);
        System.out.println(s);


    }

    public static void print(int a) {
        a += 1;
        System.out.println(a);


    }

    public static void addS(String s) {
        s += "i";
        System.out.println(s);
    }

    public static class Emp {
        int age;

        public void update(int n) {
            this.age = n;
        }

    }



}

