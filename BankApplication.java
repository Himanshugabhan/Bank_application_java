/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.application;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
class ExtraMethods {
     public static void appendfile(String filename , double x , String str){
            try{
                String dt;
                dt=Datetime();
                BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
                out.write("Transaction : "+dt+" "+str+x+"\n");
                out.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        public static double readmood(String filename){
            String line = null;
            String [] lines;
            double x=0;
            try{
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                for(int i=0 ; i<5 ;i++){
                     line = reader.readLine();
                }
                lines=line.split(":");
                x=Double.parseDouble(lines[1]);
                
            }
            catch(Exception e){
            System.out.println(e);
            }
            return x;
        }
        public static void modifyfile(String filename , String newstring){
             String line="",oldstring="",oldcontent="",newcontent="";  
             int i=1;
           // File fm = new File(filename);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                line=reader.readLine();
                while(line!=null){
                    oldcontent=oldcontent+line+System.lineSeparator();
                    if(i==5){
                        oldstring=line;
                    }
                    line=reader.readLine();
                    i++;
                }
                newcontent = oldcontent.replaceAll(oldstring,newstring);
                FileWriter writer = new FileWriter(filename);
                writer.write(newcontent);
                reader.close();
                writer.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        
        public static String Datetime(){
            DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now=LocalDateTime.now();
            String ret=dtf.format(now);
            return ret;
        }
        public static int Password1(String filename){
            String [] lines;
            int x,flag=0,x1,count=0;
            String line="";
            Scanner obj1= new Scanner(System.in);
             try{
                 BufferedReader reader = new BufferedReader(new FileReader(filename));
                line=reader.readLine();
                while(line!=null){
                    if(line.startsWith("password ")){
                        lines=line.split(":");
                        x1=Integer.parseInt(lines[1]);
                       while(true){
                        if(count==3){
                            break;
                        }
                        System.out.println("Enter the Password");
                        x=obj1.nextInt();
                        if(x==x1){
                            flag=1;
                            break;
                            
                        }
                        else{
                            System.out.println("Sorry your Password is WRONG");
                        }
                        count++;
                    }
                       break;
                    }
                    line=reader.readLine();
                }
                reader.close();
            }
             catch(Exception e){
                 System.out.println(e);
             }
             return flag;
        }
    
}
class Bank extends ExtraMethods{
        String name;
        int accno,pass;
	String type;
	double bal;
        
        public void getdata(){
                String name1;
                Scanner obj1= new Scanner(System.in);
                System.out.println("\nENTER the name");
                name = obj1.next();
                System.out.println("\nENTER the Account Number");
                accno=obj1.nextInt();
                System.out.println("\nENTER the The Type of Account");
                type=obj1.next();
                System.out.println("\nENTER the Password");
                pass=obj1.nextInt();
                System.out.println("\nENTER the Balance in Account");
                bal=obj1.nextDouble();
                String dt = Datetime();
                try {
                    name1="E:\\"+accno+".txt";
                    FileWriter obj = new FileWriter(""+name1+"");
                    obj.write("Name :\t"+name+"\n"+"Account No. :\t"+accno+"\n"+"Type :\t"+type+"\n"+"password :"+pass+"\n"+"Balance :"+bal+"\n"+"Transaction : "+dt+"  Amount Add in Account is "+bal);
                    obj.close();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            } 
        public void getbal(){
            double x;
            int x1;
            Scanner obj1= new Scanner(System.in);
            System.out.println("Enter the account Number to Add balance");
                x1=obj1.nextInt();
            String name1="E:\\"+x1+".txt";    
            System.out.println("\nENTER the Balance You Want To Add In Account");
            x=obj1.nextDouble();
            bal=readmood(name1)+x;
            String newstring="Balance :"+bal;
                  modifyfile(name1,newstring);
                  appendfile(name1,x," Amount Add in Account is ");
             
        }
        public void withdraw(){
            int x1,x2;
            Scanner obj1= new Scanner(System.in);
            System.out.println("Enter the account Number to Withdraw balance");
                x1=obj1.nextInt();
            String name1="E:\\"+x1+".txt";
            x2=Password1(name1);
            if(x2==1) {
                bal=readmood(name1);
             if(bal<=1000){
                    System.out.println("You can't withdroal money your balance is low");
                }
	      else{
		Double x;
                System.out.println("Enter the money you want to withdral");
		x=obj1.nextDouble();
		if((bal-x)<=1000){
		    System.out.println("Sorry you Don't have sufficient Amount");
		}
		else{
			bal=bal-x;
                        String newstring="Balance :"+bal;
                              modifyfile(name1,newstring);
                              appendfile(name1,x," Amount Withdraw From Account ");
                }
             }
            }
            else{
                System.out.println("Sorry Your Password is WRONG Please Contact Your Bank");
            }
        }
        public void  display(){
            String name1;
            int x1;
            Scanner obj1= new Scanner(System.in);
            System.out.println("Enter the account Number to Display the data ");
                x1=obj1.nextInt();
          try{
               name1="E:\\"+x1+".txt";
               FileReader obj = new FileReader(""+name1+"");
               int i;
               System.out.println("\n");
               while((i=obj.read())!=-1)
                     {System.out.print((char)i);}
               obj.close();
          }
          catch(Exception e){
              System.out.println(e);
          }
}
        public void  transaction(){
           String [] lines;
          int x1;
          String name1,line="";
            Scanner obj1= new Scanner(System.in);
            System.out.println("Enter the account Number to get Transaction Detail");
                x1=obj1.nextInt();
            try{
               name1="E:\\"+x1+".txt";
                BufferedReader reader = new BufferedReader(new FileReader(name1));
                line=reader.readLine();
                while(line!=null){
                    if(line.startsWith("Transaction ")){
                        lines=line.split(" : ");
                        System.out.println(lines[1]);
                    }
                    line=reader.readLine();
                }
               
          }
          catch(Exception e){
              System.out.println(e);
          }
            
}
       public void balanceinquiry(){
          int x1;
          String name1,line="";
            Scanner obj1= new Scanner(System.in);
            System.out.println("Enter the account Number to get Balance Info.");
            x1=obj1.nextInt();
            name1="E:\\"+x1+".txt";   
            try{
                BufferedReader reader = new BufferedReader(new FileReader(name1));
                line=reader.readLine();
                while(line!=null){
                    if(line.startsWith("Balance :")){
                        System.out.println(line);
                        break;
                    }
                    line=reader.readLine();
                }
               
          }
          catch(Exception e){
              System.out.println(e);
          }
       }       
}       
             
   public class BankApplication {
    public static void main(String[] args) {
        Bank obj=new Bank();
        int a,b,ei,flag=0;                                                     //ei=employee id
        Scanner obj1= new Scanner(System.in);
        int [] empid={11,22,33,44};
	char ch='y',ch1='y';
        do{
            System.out.println("Are You employee Type 1 \n Are you user type 2  ");
            b=obj1.nextInt();
           if(b==1) {
            for(int j=0;j<3;j++)  {
              System.out.println("Please Enter Your Empolyee Id ");
              ei=obj1.nextInt();
           for(int i=0 ; i<4 ; i++){
               if(ei==empid[i]){
                   flag=1;
                   break;
               }
             }
             if(flag==1){
                 break;
             }
             if(flag==0){
                 System.out.println("Sorry Your Id May Be Wrong");
             }
           }
           if(flag==1){
           System.out.println("Following Option For Work \nEnter '1' For New Account \nEnter '2' For Adding Amount in Bank Account \nEnter '3' For Withdraw the Amount from Bank Account \nEnter '4' For Display The Complete Info for Bank Account \nEnter '5' For See Transaction");
           do{
              
          System.out.println( "Enter Work option");
	  a=obj1.nextInt();
	switch(a){
	
	case 1 : {
                obj.getdata();
		break;
         	}
       case 2 : {
                obj.getbal();
		break;
	        }
       case 3 : {
                obj.withdraw();
		break;
	        }
       case 4 : {
                 obj.display();
                  break;
	        }
       case 5 : {
                obj.transaction();
		break;
	        }

       }
                System.out.println( "Do you want to continue For Work y/n");
                ch=obj1.next().charAt(0);
     }while(ch=='y');
        }
        else{
               System.out.println("Sorry You Enter Wrong Id 3 Times  Please Wait for 15sec..... ");
               try{
                   Thread.currentThread().sleep(15*1000);
               }
               catch(Exception e){
                   System.out.println(e);
               }
           }      
    }
    if(b==2){
        System.out.println("Following Option For Work \nEnter '1' For Balance Inquiry \nEnter '2' For Adding Amount in Bank Account \nEnter '3' For Withdraw the Amount from Bank Account \nEnter '4' For Display The Complete Info for Bank Account \nEnter '5' For See Transaction");
           do{
              
          System.out.println( "Enter Work option");
	  a=obj1.nextInt();
	switch(a){
	
	case 1 : {
                obj.balanceinquiry();
		break;
         	}
       case 2 : {
                obj.getbal();
		break;
	        }
       case 3 : {
                obj.withdraw();
		break;
	        }
       case 4 : {
                 obj.display();
                  break;
	        }
       case 5 : {
                obj.transaction();
		break;
	        }

       }
                System.out.println( "Do you want to continue For Work y/n");
                ch=obj1.next().charAt(0);
     }while(ch=='y');
        
    }
              System.out.println("Do you Wish To Continue As User Or Employee y/n");
              ch1=obj1.next().charAt(0);    
    }while(ch1=='y');     
        
   System.out.println(" Thank You ! ");
   }         
   }

    
