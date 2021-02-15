package sample;

import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    static boolean flag = true;
    static void command_run(String path) {
        try {
            String res_command = "cmd.exe /c " + path + " -m timeit -r 10 ";
            Process p = Runtime.getRuntime().exec(res_command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        flag = false;
    }
    static void timer()  {
        long time = 1;
        while (flag) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = false;
            }
            if (flag) System.out.println("you are waiting for " + time + " seconds!");
            time++;
        }
    }
    public static void main(String[] arg) throws IOException {
            Scanner sc = new Scanner(System.in);
            String path = sc.nextLine();
            sc.close();
            ExecutorService pool = Executors.newFixedThreadPool(2);
            pool.submit(() -> command_run(path));
            pool.submit(Main::timer);
            pool.shutdown();
    }
}
