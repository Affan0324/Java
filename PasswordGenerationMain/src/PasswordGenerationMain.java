
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class PasswordGeneration {

    String pass;
    int Length;
    String username;

    PasswordGeneration(int length, String username) {
        this.Length = length;
        this.username = username;
        this.pass="";
    }

    void makepass(int min, int max,Thread t) {
	int length = 0;
        while (this.Length-1 > this.pass.length()){
            try {


        boolean want = new Random().nextBoolean();
        if (want) {
            this.pass += (char) (min + (int) (Math.random() * ((max - min) + 1)));
            System.out.println(t.getName().toUpperCase()+"  this thread made pass  :::: "+this.pass);
        }
                Thread.sleep(500); }catch (Exception e){
                System.out.println(e);
            }
        }

    }

    public String  getDetail(){
        return ("username : "+this.username+"\n"+"password : "+this.pass);
    }
}


class Lowerpass extends Thread{

        PasswordGeneration pg;
        Lowerpass(PasswordGeneration pg){
            this.pg = pg;
        }

    @Override
    public synchronized void run() {

                pg.makepass(97,122,currentThread());

        }
    }




class Upperpass extends Thread{

    PasswordGeneration pg;
    Upperpass(PasswordGeneration pg){
        this.pg = pg;
    }

    @Override
    public synchronized void run() {

                pg.makepass(65,90,currentThread());

    }
}

class Numberpass extends Thread{

    PasswordGeneration pg;
    Numberpass(PasswordGeneration pg){
        this.pg = pg;
    }

    @Override
    public synchronized void run() {

                pg.makepass(48,57,currentThread());

    }
}

class Symbolpass extends Thread{

    PasswordGeneration pg;
    Symbolpass(PasswordGeneration pg){
        this.pg = pg;
    }

    @Override
    public synchronized void run() {

                pg.makepass(33,47,currentThread());
    }
}


public class PasswordGenerationMain {
    public static void main(String[] args) throws InterruptedException {

        Scanner input= new Scanner(System.in);

        System.out.print("Enter username : ");
        String username = input.next();

        System.out.print("length of password : ");
        int length = input.nextInt();

        if(length>24){
            System.out.println(" above range Please enter within range of 8 - 24 ");
        }
        else if(length<8){
            System.out.println(" low range given please enter within range of 8 - 24");
        }

        else{
        PasswordGeneration pg = new PasswordGeneration(length,username);

        Upperpass up = new Upperpass(pg);
        Lowerpass lp = new Lowerpass(pg);
        Numberpass np = new Numberpass(pg);
        Symbolpass sp = new Symbolpass(pg);

        up.setName("Uppercase Thread");
        np.setName("Number Thread");
        lp.setName("lowercase Thread");
        sp.setName("Symbols Thread");

        ArrayList<Thread> threadList = new ArrayList<Thread>();
        threadList.add(up);
        threadList.add(lp);
        threadList.add(np);
        threadList.add(sp);

        for(Thread t : threadList) {
            t.start();
        }


        for(Thread t : threadList) {
            t.join();
        }

        System.out.println("The password is written in the file :"+pg.getDetail());
        try {
            FileWriter myWriter = new FileWriter("F:\\project\\usernameandpass\\"+username+".txt");
            myWriter.write(pg.getDetail());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
}