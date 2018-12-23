/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class RunProcess {
	
	public void runAG()
	{
		String order = "";
		int temp = 0, idx = 0;
    	String str;
        Scanner read = new Scanner(System.in);
        System.out.println("Enter the num of pro");
        int num_of_processes = read.nextInt();
        Vector<Processes> pro = new Vector();
        Vector<Integer> fixed = new Vector();
        
        Vector<Processes> proFCFS = new Vector();
        Vector<Processes> proPriority = new Vector();
        Vector<Processes> proSJF = new Vector();
        
        for (int i = 0; i < num_of_processes; i++) {
            Processes p = new Processes();
            System.out.println("P" + (i + 1) + " arrivaltime");
            int arriv = read.nextInt();
            System.out.println("P" + (i + 1) + " bursttime");
            int burst = read.nextInt();
            System.out.println("P" + (i + 1) + " priority");
            p.priority = read.nextInt();
            System.out.println("Enter quentem size");
            int quentem_size = read.nextInt();
            p.quentem = quentem_size;
            p.arrivaltim = arriv;
            p.bursttime = burst;
            p.name = ("p" + (i + 1)).toString();
            fixed.add(p.bursttime);
            p.name = "p" + (i + 1);
            pro.add(p);
        }
        
        
        Vector<Processes> pro1 = new Vector(pro);
        int counter = 0;
        
        while(pro1.size() > 0)
        {
        	Vector<Processes> pro2 = new Vector();
            for (Processes temp1 : pro1) {
                if (temp1.arrivaltim <= counter) {
                    pro2.add(temp1);
                }
            }
            
            for (Processes temp1 : pro2) {
                proFCFS.add(temp1);
            }
            
            //first queue
            if(!proFCFS.isEmpty())
            {
            	idx = 0;int  idxproFCFS = 0;
            	for(int i = 0; i < proFCFS.size(); i++)
            	{
            		str = proFCFS.get(i).name;
            		for(int k = 0; k < pro.size(); k++)
            		{
            			if(pro.get(k).name.equals(str))
            			{
            				idx = k;
            				break;
            			}
            		}
            		
            		for(int k = 0; k < pro1.size(); k++)
            		{
            			if(pro1.get(k).name.equals(str))
            				idxproFCFS = k;
            		}
            		if(proFCFS.get(i).bursttime > 0)
            		{
            			if(proFCFS.get(i).bursttime > (int)(Math.ceil(proFCFS.get(i).quentem * 0.25)))
            			{
            				order += proFCFS.get(i).name;
            				//System.out.println(pro.get(idx).name+ " " +pro.get(idx).bursttime);
            				counter += (int)(Math.ceil(proFCFS.get(i).quentem * 0.25)); // stooooooooooop
            				//System.out.println("Counter is: " + counter);
            				proFCFS.get(i).bursttime -= (int)(Math.ceil(proFCFS.get(i).quentem * 0.25));
            				
            				//System.out.println(pro.get(idx).name+ " " +pro.get(idx).bursttime);
            				pro1.remove(idxproFCFS);
            				proPriority.addElement(pro.get(idx));
        
            				
            			} else { // if the process will end now
            				order += proFCFS.get(i).name;
            				counter += proFCFS.get(i).bursttime;
            				pro.get(idx).com = counter;
            				pro.get(idx).bursttime = 0;
            				pro1.remove(idxproFCFS);
            				proFCFS.get(i).com = counter;
            				proFCFS.get(i).bursttime = 0;
            				
            			}
            		}
            	}// end for
            	//System.out.println(counter);
            	//System.out.println(pro1.size());
            	for(int i = 0; i < proFCFS.size(); i++)
            		proFCFS.remove(i);
            	boolean check1 = false;
            	for (Processes temp1 : pro1) {
                    if (temp1.arrivaltim <= counter)
                    	{
                    		check1 = true;
                    	}
                    }
                    
                    //System.out.println(check1);
            	if(check1)
            		continue;
            } // proFCFS end
            
//            for(int i = 0; i < proPriority.size(); i++)
//            {
//            	System.out.println(proPriority.get(i).name + " " + proPriority.get(i).bursttime);
//            }
            
            //second queue
            boolean check = false;
            if(!proPriority.isEmpty())
            {
            	CPUassi.sort1(proPriority);
            	temp = 0; idx = 0;
            	
            	for(int i = 0; i < proPriority.size(); i++)
            	{
            		str = proPriority.get(i).name;
            		for(int k = 0; k < pro.size(); k++)
            		{
            			{if(pro.get(k).name.equals(str))
            				idx = k;}
            		}
            		if(proPriority.get(i).bursttime > 0)
            		{
            			if(proPriority.get(i).bursttime > (int)(Math.ceil(proPriority.get(i).quentem * 0.25)))
            			{
            				order += proPriority.get(i).name;
            				pro.get(idx).bursttime -= (int)(Math.ceil(proPriority.get(i).quentem * 0.25));
            				
            				counter += (int)(Math.ceil(proPriority.get(i).quentem * 0.25));
            				temp += (int)(Math.ceil(proPriority.get(i).quentem * 0.25));
            				proSJF.addElement(proPriority.get(i));
            				proPriority.remove(i);
            				i--;
            				while(temp >= 5)
            				{
            					temp -= 5;
            					for(int k = 0; k < proPriority.size(); k++)
            					{
            						if(proPriority.get(k).priority > 1)
            						{
            							proPriority.get(k).priority -= 1;
            							CPUassi.sort1(proPriority);
            							
            						}
            					}
            					i = -1;
            				}// end temp loop
//            				
            			}
            			else
            			{
            				order += proPriority.get(i).name;
            				counter += proPriority.get(i).bursttime;
            				temp += proPriority.get(i).bursttime;
            				proPriority.get(i).bursttime = 0;
            				pro.get(idx).bursttime = 0;
            				pro.get(idx).com = counter;
            				proPriority.remove(i);
            				i--;
            				while(temp >= 5)
            				{
            					temp -= 5;
            					for(int k = 0; k < proPriority.size(); k++)
            					{
            						if(proPriority.get(k).priority > 1)
            						{
            							proPriority.get(k).priority -= 1;
            							CPUassi.sort1(proPriority);
            							i = -1;
            						}
            					}
            				}// end temp loop
            			}// end else
            		}// end burst time > 0
            		/*check if there is a process in pro1 which arrival <= counter*/
            		check = false;
            		for (Processes temp1 : pro1) {
                        if (temp1.arrivaltim <= counter) {
                        	check = true;
                        }
            		}
            		if(check)
            		{
            			break;
            		}
            	} // end loop
            	
            	if(check)
            		continue;
            	
//            	for(int i = 0; i < proPriority.size(); i++)
//                {
//            		System.out.println(proPriority.get(i).name + " " + proPriority.get(i).bursttime);
//                }
            	
//            	for(int i = 0; i < proPriority.size(); i++)
//            		proPriority.remove(i);
            	
            } //end priority
            
            //third queue
            check = false;
//            for(int i = 0; i < proSJF.size(); i++)
//            {
//            	System.out.println(proSJF.get(i).name + " " + proSJF.get(i).bursttime);
//            }
            if(!proSJF.isEmpty())
            {

            	//System.out.println("Size is: "+proSJF.size());
            	CPUassi.sort(proSJF);
            	idx = 0;
            	for(int i = 0; i < proSJF.size(); i++)
            	{
            		str = proSJF.get(i).name;
            		for(int k = 0; k < pro.size(); k++)
            		{
            			{if(pro.get(k).name.equals(str))
            				idx = k;
            			}
            		}
            		while(proSJF.get(i).bursttime > 0)
            		{
            			//System.out.println("Burst time of process: " + pro.get(idx).name+ "= " + pro.get(idx).bursttime);
            			proSJF.get(i).bursttime -= 1;
            			
            			//System.out.println("Burst time of process: " + pro.get(idx).name+ "= " + pro.get(idx).bursttime);
            			counter++;
            			order += proSJF.get(i).name;
            			if(proSJF.get(i).bursttime == 0)
            			{
            				proSJF.get(i).com = counter;
            				pro.get(idx).com = counter;
            			}
            			check = false;
            			if(pro1.size() > 0) {
            				for (Processes temp1 : pro1) {
                                if (temp1.arrivaltim <= counter) {
                                	check = true;
                                }
            				}
                		}
            			
            			if(check)
            				break;
            			
            		}// end while loop
            		if(check)
            		{
            			for(int j = 0; j < i; j++)
            				proSJF.remove(j);
            			break;
            		}
            	} // end for loop
            	if(check)
            		continue;
            	
            	for(int i = 0; i < proSJF.size(); i++)
            		proSJF.remove(i);
            }// end SJF queue
        } // end while
        
        /*end*/
        for (int i = 0; i < pro.size(); i++) {
            //pro.get(i).com += 1;
            pro.get(i).bursttime = fixed.get(i);
            pro.get(i).tround = pro.get(i).com - pro.get(i).arrivaltim;
            pro.get(i).waiting = pro.get(i).com - (pro.get(i).arrivaltim + pro.get(i).bursttime);
        }
        System.out.println("name  arrival  burst  complete turn wating");
        for (int i = 0; i < pro.size(); i++) {
            Processes temp1 = pro.get(i);
            System.out.println(temp1.name + "\t" + temp1.arrivaltim + "\t" + temp1.bursttime + "\t" + temp1.com + "\t" + temp1.tround + "\t" + temp1.waiting);

        }

        float avr_wating = 0;
        float avr_turn = 0;
        for (int i = 0; i < pro.size(); i++) {
            avr_wating += pro.get(i).waiting;
            avr_turn += pro.get(i).tround;
        }

        System.out.println(avr_turn / (float) pro.size());
        System.out.println(avr_wating / pro.size());
        System.out.println(order);
	}
	
    public void runSJF() {

        String order = "";
        Scanner read = new Scanner(System.in);
        System.out.println("Enter the num of pro");
        int num_of_processes = read.nextInt();
        System.out.println("Enter context size");
        int context = read.nextInt();
        Vector<Processes> pro = new Vector();
        Vector<Integer> fixed = new Vector();
        for (int i = 0; i < num_of_processes; i++) {
            Processes p = new Processes();
            System.out.println("P" + (i + 1) + " arrivaltime");
            int arriv = read.nextInt();
            System.out.println("P" + (i + 1) + " bursttime");
            int burst = read.nextInt();
            p.arrivaltim = arriv;
            p.bursttime = burst;
            fixed.add(burst);
            p.name = ("p" + (i + 1)).toString();
            pro.add(p);
        }
        boolean flag=true;
        Vector<Processes> pro1 = new Vector(pro);
        int counter = 0;
        
        
        while (pro1.size() > 0) {
            Vector<Processes> pro2 = new Vector();
            for (Processes temp1 : pro1) {
                if (temp1.arrivaltim <= counter) {
                    pro2.add(temp1);
                }

            }
            CPUassi.sort(pro2);
            Processes p1 = new Processes();
            if(pro2.size()==0) {
            counter++;
            continue;
            	
            }
            p1 = pro2.get(0);
            
            int x = p1.bursttime;
            p1.bursttime = x - 1;
            if (flag) {order += p1.name; flag=false;}
            else 
            {
                String tt="";
	            char v=order.charAt(order.length()-2);
	            tt+=v;
	            char e=order.charAt(order.length()-1);
	            tt+=e;
	            if (p1.name.equals(tt)){}
	            else {order+=p1.name;
	            counter+=context;}
            }
            int index = 0;
            int index2 = 0;
            if (p1.bursttime == 0) {

                for (int i = 0; i < pro1.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro1.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index = i;
                        break;
                    }
                }
                for (int i = 0; i < pro.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index2 = i;
                        break;
                    }
                }
                pro.get(index2).com = counter;
                pro1.remove(index);
            } else {

                for (int i = 0; i < pro1.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro1.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index = i;
                        break;
                    }
                }
                pro1.set(index, p1);

            }
            counter++;
        }
        /*end*/
        for (int i = 0; i < pro.size(); i++) {
            pro.get(i).com += 1;
            pro.get(i).bursttime = fixed.get(i);
            pro.get(i).tround = pro.get(i).com - pro.get(i).arrivaltim;
            pro.get(i).waiting = pro.get(i).com - (pro.get(i).arrivaltim + pro.get(i).bursttime);
        }
        System.out.println("name  arrival  burst  complete turn wating");
        for (int i = 0; i < pro.size(); i++) {
            Processes temp = pro.get(i);
            System.out.println(temp.name + "\t" + temp.arrivaltim + "\t" + temp.bursttime + "\t" + temp.com + "\t" + temp.tround + "\t" + temp.waiting);

        }

        float avr_wating = 0;
        float avr_turn = 0;
        for (int i = 0; i < pro.size(); i++) {
            avr_wating += pro.get(i).waiting;
            avr_turn += pro.get(i).tround;
        }

        System.out.println(avr_turn / (float) pro.size());
        System.out.println(avr_wating / pro.size());
        System.out.println(order);

    }

    public void runPriority() {

        String order = "";
        Scanner read = new Scanner(System.in);
        System.out.println("Enter the num of pro");
        int num_of_processes = read.nextInt();
        Vector<Processes> pro = new Vector();
        Vector<Integer> fixed = new Vector();
        Vector<Integer> poriorty = new Vector();
        for (int i = 0; i < num_of_processes; i++) {
            Processes p = new Processes();
            System.out.println("P" + (i + 1) + " arrivaltime");
            int arriv = read.nextInt();
            System.out.println("P" + (i + 1) + " bursttime");
            int burst = read.nextInt();
            System.out.println("P" + (i + 1) + " priority");
            p.arrivaltim = arriv;
            p.bursttime = burst;
            p.priority = read.nextInt();
            fixed.add(p.bursttime);
            poriorty.add(p.priority);
            p.name = "p" + (i + 1);
            pro.add(p);
        }
        boolean flag= true;
        Vector<Processes> pro1 = new Vector(pro);
        int counter = 0;
        while (pro1.size() > 0) {
            Vector<Processes> pro2 = new Vector();
            for (Processes temp1 : pro1) {
                if (temp1.arrivaltim <= counter) {
                    pro2.add(temp1);
                }

            }
            CPUassi.sort1(pro2);
            Processes p1 = new Processes();
            if(pro2.size()==0) {
                counter++;
                continue;
                	
                }
            p1 = pro2.get(0);
//             pro2.get( pro2.size()-1).priority=pro2.get( pro2.size()-1).priority-1;
            int x = p1.bursttime;
            p1.bursttime = x - 1;
            if (flag) {order += p1.name; flag=false;}
            else 
            {
                String tt="";
            char v=order.charAt(order.length()-2);
            tt+=v;
            char e=order.charAt(order.length()-1);
            tt+=e;
            if (p1.name.equals(tt)){}
            else order+=p1.name;
            }
            int index = 0;
            int index2 = 0;
            if (p1.bursttime == 0) {

                for (int i = 0; i < pro1.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro1.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index = i;
                        break;
                    }
                }
                for (int i = 0; i < pro.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index2 = i;
                        break;
                    }
                }
                pro.get(index2).com = counter;
                pro1.remove(index);
            } else {

                for (int i = 0; i < pro1.size(); i++) {
                    Processes p2 = new Processes();
                    p2 = pro1.get(i);
                    if (p1.arrivaltim == p2.arrivaltim) {
                        index = i;
                        break;
                    }
                }
                pro1.set(index, p1);

            }
            counter++;
        }
        for (int i = 0; i < pro.size(); i++) {
            pro.get(i).com += 1;
            pro.get(i).priority = poriorty.get(i);
            pro.get(i).bursttime = fixed.get(i);
            pro.get(i).tround = pro.get(i).com - pro.get(i).arrivaltim;
            pro.get(i).waiting = pro.get(i).com - (pro.get(i).arrivaltim + pro.get(i).bursttime);
        }
        System.out.println("name  arrival  burst  priority  complete turn wating");
        for (int i = 0; i < pro.size(); i++) {
            Processes temp = pro.get(i);
            System.out.println(temp.name + "\t" + temp.arrivaltim + "\t" + temp.bursttime + "\t" + temp.priority + "\t" + temp.com + "\t" + temp.tround + "\t" + temp.waiting);

        }

        float avr_wating = 0;
        float avr_turn = 0;
        for (int i = 0; i < pro.size(); i++) {
            avr_wating += pro.get(i).waiting;
            avr_turn += pro.get(i).tround;
        }

        System.out.println(avr_turn / (float) pro.size());
        System.out.println(avr_wating / pro.size());
        System.out.println(order);

    }

    public void runRoundRobin() {
        String order = "";
        Scanner read = new Scanner(System.in);
        System.out.println("Enter the num of pro");
        int num_of_processes = read.nextInt();
        System.out.println("Enter quentem size");
        int quentem_size = read.nextInt();
        System.out.println("Enter context size");
        int context = read.nextInt();
        Vector<Processes> pro = new Vector();
        int burst1[] = new int[num_of_processes];
        for (int i = 0; i < num_of_processes; i++) {
            Processes p = new Processes();
            System.out.println("P" + (i + 1) + " bursttime");
            int burst = read.nextInt();
            p.bursttime = burst;
            burst1[i] = burst;
            p.name = "p" + (i + 1);
            pro.add(p);
        }
        Vector<Processes> pro1 = new Vector(pro);
        int counter = 0;
        while (true) {
            boolean done = true;
            for (int i = 0; i < num_of_processes; i++) {
                
                if (pro.get(i).bursttime > 0) {
                    done = false;
                    if (pro.get(i).bursttime > quentem_size) {
                        order += pro.get(i).name;
                        counter += quentem_size + context;
                        pro.get(i).bursttime -= quentem_size;
                    } else {
                        order += pro.get(i).name;
                        counter += pro.get(i).bursttime;
                        pro.get(i).com = counter;
                        pro.get(i).bursttime = 0;
                        counter += context;
                    }
                }
            }

            // If all processes are done 
            if (done == true) {
                break;
            }
        }
        for (int i = 0; i < num_of_processes; i++) {

            pro.get(i).bursttime = burst1[i];
            pro.get(i).waiting = pro.get(i).com - pro.get(i).bursttime;
            pro.get(i).tround=pro.get(i).com;
        }
        System.out.println("name  burst  complete watting  turn");
        for (Processes temp : pro) {

            System.out.println(temp.name + "\t" + temp.bursttime + "\t" + temp.com + "\t" + temp.waiting+"\t"+temp.com);

        }
        float avr_w = 0;
        float avr_tur = 0;
        for (Processes temp : pro) {
            avr_tur+=temp.tround;
            avr_w += temp.waiting;

        }
        System.out.println("The avrage tur time " + avr_tur / pro.size());
         System.out.println("The avrage  time " + avr_w / pro.size());
        System.out.println(order);
    }

    
}
