//this class is all the graphics 
import java.util.Hashtable;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class Graphics_panels_and_combo_boxes extends JFrame 
{
public Hashtable<Date,String> hash_table;    
public JComboBox<Integer> day_selector;
public JComboBox<Integer> month_selector;
public JComboBox<Integer> year_selector;
public JLabel the_tizkoret;
public JTextField tizkoret;
public JButton print_tizkoret;
public JButton write_tizkoret_button;
public JButton load_file_button;
public JButton new_file;
public JLabel file_name;
public JTextField file_name_text;
public static Scanner file_name_scanner;
public static String file_name_for_reseting_file;
public static Formatter output_to_memo_file;

public Graphics_panels_and_combo_boxes()
{
    super("main graphics class");
    hash_table=new Hashtable<>();
    setLayout (new FlowLayout()); 
    Integer[] days=new Integer[31];
    for(int i=0;i<days.length;i++)
    days[i]=i+1;
    Integer[] months=new Integer[12];
    for(int i=0;i<months.length;i++)
    months[i]=i+1;
    Integer[] years=new Integer[10];
    for(int i=0;i<years.length;i++)
    years[i]=i+1+2012;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize(500,210);
    setResizable(false);
    setVisible(true);
    Listener lis=new Listener();
    this.the_tizkoret=new JLabel("use the following jtext area to write or read tizkoret");
    this.print_tizkoret=new JButton("print the selected tizkoret");
    this.print_tizkoret.addActionListener(lis);
    this.write_tizkoret_button=new JButton("write or edit tizkoret");
    this.write_tizkoret_button.addActionListener(lis);
    this.load_file_button=new JButton("load file");
    this.load_file_button.addActionListener(lis);
    this.new_file=new JButton("change file");
    this.new_file.addActionListener(lis);
    this.file_name_text=new JTextField(20);
    this.tizkoret=new JTextField(40);
    this.day_selector=new JComboBox<Integer>(days);
    this.month_selector=new JComboBox<Integer>(months);
    this.year_selector=new JComboBox<Integer>(years);
    this.file_name=new JLabel("the next line shows the file name which you write and read memos from");
    add(day_selector);
    add(month_selector);
    add(year_selector);
    add(the_tizkoret);
    add(tizkoret);
    add(print_tizkoret);
    add(write_tizkoret_button);
    add(load_file_button);
    add(new_file);
    add(file_name);
    add(file_name_text);
    getContentPane().setBackground(Color.lightGray);
    revalidate();
}
//the following method is just an action listener for each of the buttons
//load file is load contents of a file to the hash table and must be first action
//change file is used if the user wants to use other file
//write tizkoret button checks if the the hash table has a memo on the selected date if so it edits it otherwise it writes a new memo on this date
//print tizkoret button checks if a memo is written on the selected date,if a memo does exists it print it on the top jtext area
private class Listener implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==load_file_button)
        {
            try
            {
                file_name_scanner=new Scanner(Paths.get(file_name_text.getText()));
                file_name_for_reseting_file=file_name_text.getText();
                hash_table.clear();
                int a,b,c;
                Date date;
                String memo=new String();
                int day;
                int month;
                int year;
                while(file_name_scanner.hasNext())
                {
                    day=file_name_scanner.nextInt();
                    month=file_name_scanner.nextInt();
                    year=file_name_scanner.nextInt();
                    memo=file_name_scanner.next();
                    date=new Date(day,month,year);
                    hash_table.put(date,memo);
                }
                System.out.println(hash_table.toString());
                
            }
            catch(IOException ioexception)
            {
                System.out.println("file not found program terminating");
                System.exit(1);
            }
        }
        if(e.getSource()==new_file)
        {
            file_name_scanner.close();
            try
            {
                file_name_scanner=new Scanner(Paths.get(file_name_text.getText()));
                file_name_for_reseting_file=file_name_text.getText();
                hash_table.clear();
                Date date;
                String memo=new String();
                int day;
                int month;
                int year;
                while(file_name_scanner.hasNext())
                {
                    day=file_name_scanner.nextInt();
                    month=file_name_scanner.nextInt();
                    year=file_name_scanner.nextInt();
                    memo=file_name_scanner.next();
                    date=new Date(day,month,year);
                    hash_table.put(date,memo);
                }
            }
            catch(IOException ioexception)
            {
                System.out.println("file not found program terminating");
                System.exit(1);
            }
                
            }
        if(e.getSource()==write_tizkoret_button)
        {
            int day,month,year;
            day=(int)day_selector.getSelectedItem();
            month=(int)month_selector.getSelectedItem();
            year=(int)year_selector.getSelectedItem();
            Date selected_date=new Date(day,month,year);
            if(hash_table.containsKey(selected_date)==true)
            {
               hash_table.remove(selected_date);
               hash_table.put(selected_date,tizkoret.getText());
            }
            else
            {
               hash_table.put(selected_date, tizkoret.getText());
            }
            file_name_scanner.close();
            File reset_file=new File(file_name_for_reseting_file);
            reset_file.delete();
            try
            {
            reset_file.createNewFile();
            }
            catch(IOException ioexception)
            {
                System.out.println("cant create new file, program terminating");
                System.exit(1);
            }
            try
            {
            file_name_scanner=new Scanner(Paths.get(file_name_for_reseting_file));
            output_to_memo_file=new Formatter(file_name_for_reseting_file);
            }
            catch(IOException ioexception)
            {
                System.out.println("cant open the newly created file, program terminating");
                System.exit(1);
            }
            //only method i could fine on the internet that can iterate through hash table
            Set<Map.Entry<Date,String>> hashtable_entries=hash_table.entrySet();
            for(Map.Entry<Date,String> item:hashtable_entries)
            {
                output_to_memo_file.format("%d %d %d %s%n",item.getKey().GetDay(),item.getKey().GetMonth(),item.getKey().GetYear(),item.getValue());
            }
            output_to_memo_file.close();
        }
        if(e.getSource()==print_tizkoret)
        {
            int day,month,year;
            day=(int)day_selector.getSelectedItem();
            month=(int)month_selector.getSelectedItem();
            year=(int)year_selector.getSelectedItem();
            Date selected_date=new Date(day,month,year);
            if(hash_table.containsKey(selected_date)==true)
            {
               tizkoret.setText(hash_table.get(selected_date));
            }
            else
            {
               JOptionPane.showMessageDialog(null,"there is no memo on the selected date");
            }
        }
    }
            
    }
}


