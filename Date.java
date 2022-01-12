//this class is just a basic date class
public class Date 
{
private int day;
private int month;
private int year; 
public Date(int day,int month,int year)
{
    this.day=day;
    this.month=month;
    this.year=year;
}
public int GetDay()
{
    return this.day;
}
public int GetMonth()
{
    return this.month;
}
public int GetYear()
{
    return this.year;
}
public boolean equals(Object compared_year)
{
    Date y=(Date)compared_year;
    if(this.day==y.day)
        if(this.month==y.month)
            if(this.year==y.year)
                return true;
    return false;
}
public int hashCode()
{
    return (this.day+this.month+this.year)*10-15;
}
public String toString()
{
    String a=new String();
    a=a+"day "+this.day+" month "+this.month+" year "+this.year;
    return a;
}
}
