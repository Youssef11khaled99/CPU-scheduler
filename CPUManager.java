/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ID: 20160385 	Name: Buthaina Hamed
 * ID: 20160315		Name: Abdelaalem Abdelhamed
 * ID: 20160296		Name: Youssef Khaled Roshdy
 * ID: 20160329		Name: Khaled Hamadallah
 * ID: 20160297		Name: Youssuf Samir Sayed 
 * ID: 20160284		Name: Walied Ahmed Shoqib
 * */



import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class CPUManager {

    /**
     * @param args the command line arguments
     */
	
	// sorting processes according to burstTime
    public static void sort(Vector<Processes> v) {
        if (v.size() > 0) {
            for (int t = 0; t < v.size() - 1; t++) {
                for (int j = 0; j < v.size() - t - 1; j++) {
                    if (v.get(j).bursttime > v.get(j + 1).bursttime) {
                        Processes temp1 = new Processes();
                        temp1 = v.get(j);
                        v.set(j, v.get(j + 1));
                        v.set(j + 1, temp1);
                    }
                }
            }
        }

    }

 // sorting processes according to priority
    public static void sort1(Vector<Processes> v) {
        if (v.size() > 0) {
            for (int t = 0; t < v.size() - 1; t++) {
                for (int j = 0; j < v.size() - t - 1; j++) {
                    if (v.get(j).priority > v.get(j + 1).priority) {
                        Processes temp1 = new Processes();
                        temp1 = v.get(j);
                        v.set(j, v.get(j + 1));
                        v.set(j + 1, temp1);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner read = new Scanner(System.in);
        RunProcess procesess = new RunProcess();
        while (true) {
            System.out.println("Enter the technumber");
            System.out.println("Enter 1 to SJF");
            System.out.println("Enter 2 to Proirity");
            System.out.println("Enter 3 to RoundRobin");
            System.out.println("Enter 4 to AG");
            System.out.println("Enter 5 to End");
            int x = read.nextInt();
            if (x == 1) {
                procesess.runSJF();
            } else if (x == 2) {
                procesess.runPriority();
            } else if (x == 3) {
                procesess.runRoundRobin();
            } else if (x == 4) {
            	procesess.runAG();
            } else if (x == 5) {
                break;
            }
        }
    }

}
